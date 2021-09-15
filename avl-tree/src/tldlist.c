/*
 * SP Exercise 1a
 * Peter Dodd, 2308057D
 * This is my own work as defined in the 
 * Academic Ethics agreement I have signed.
 */

#include "tldlist.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

// Structs

typedef struct tldnode {
    TLDNode *parent;    // parent node
    TLDNode *child_l;   // left child node
    TLDNode *child_r;   // right child node
    long tld_count;     // number of elements with same TLD
    char hostname[4];   // top level domain
} TLDNode;

typedef struct tldlist {
    TLDNode *root;      // root node 
    long add_count;     // total number of succesful tld adds
    int elements;       // total number of elements (unique tlds)
    Date *begin;        // lowest date filter (inclusive)
    Date *end;          // highest date filter (inclusive)
} TLDList;

typedef struct tlditerator {
    size_t current;     // index of current node in array
    TLDNode *tld_array; // array of pointers to nodes in TLDList
    size_t limit;       // number of elements in tld_array
} TLDIterator;

// AVL Tree Functions

/*
 * tldlist_create generates a list structure for storing counts against
 * top level domains (TLDs)
 *
 * creates a TLDList that is constrained to the `begin' and `end' Date's
 * returns a pointer to the list if successful, NULL if not
 */
TLDList *tldlist_create(Date *begin, Date *end) {
    TLDList * new_list = malloc(sizeof(TLDList));
    
    if (new_list == NULL) { // checks for malloc failure
        return NULL;
    }
    
    // assignment of attributes to new TLDList
    new_list->add_count = 0;
    new_list->elements = 0;
    new_list->begin = begin;
    new_list->end = end;
    
    return new_list;
}

/*
 * set_node is a setter method to quickly allow the change of a nodes
 * `parent', `child_l' and `child_r', and tld `hostname' after its creation
 * 
 * sets the tld_count to 1, and adds 1 to the element counter in the 
 * TLDList holding the node
 * returns a pointer to the node
 */
TLDNode *set_node(TLDList *tld, TLDNode *node, TLDNode *parent, TLDNode *child_l, TLDNode *child_r, char *hostname) {
    node->parent = parent;
    node->child_l = child_l;
    node->child_r = child_r;
    strcpy(node->hostname, hostname);
    node->tld_count = 1;
    tld->elements += 1;
    
    return node;
}

/*
 * free_node recursivley destroys the node structure in `p', as well as its children
 *
 * all heap allocated storage associated with the node is returned to the heap
 */
void free_node(TLDNode *p) {
    if(p == NULL) {return;}
    free_node(p->child_l);
    free_node(p->child_r);
    free(p);
}

/*
 * tldlist_destroy destroys the list structure in `tld'
 *
 * all heap allocated storage associated with the list is returned to the heap
 */
void tldlist_destroy(TLDList *tld) {
    TLDNode * root = tld->root;
    if(root == NULL) {return;}
    free_node(root->child_l);
    free_node(root->child_r);
    free(root);
    free(tld);
}

/*
 * node_height finds the height of a given node `n' recursivley 
 *
 * returns an int containing the node height if succsessful, or -1 if not
 */
int node_height(TLDNode *n) { 
    if (n == NULL) {
        return -1;
    }

    // find height of left and right paths
    int lefth = node_height(n->child_l);
    int righth = node_height(n->child_r);

    // compares left and right results and returns the largest
    if (lefth > righth) {
        return lefth + 1;
    } else {
        return righth + 1;
    }
}

/*
 * rotate_left is a balancing function for the TLDList AVL tree
 *
 * returns pointer to node `z' in new rotated position
 */
TLDNode *rotate_left(TLDNode *x) {  
    TLDNode * z = x->child_r;
    z->parent = x->parent;

    x->child_r = z->child_l;

    if (x->child_r != NULL) {
        x->child_r->parent = x;
    }

    z->child_l = x;
    x->parent = z;
    
    if (z->parent != NULL) {
        if (z->parent->child_r == x) {
            z->parent->child_r = z;
        } else {
            z->parent->child_l = z;
        }
    }

    return z;
}

/*
 * rotate_right is a balancing function for the TLDList AVL tree
 *
 * returns pointer to node `z' in new rotated position
 */
TLDNode *rotate_right(TLDNode *x) {
    TLDNode * z = x->child_l;
    z->parent = x->parent;

    x->child_l = z->child_r;

    if (x->child_l != NULL) {
        x->child_l->parent = x;
    }

    z->child_r = x;
    x->parent = z;
    
    if (z->parent != NULL) {
        if (z->parent->child_r == x) {
            z->parent->child_r = z;
        } else {
            z->parent->child_l = z;
        }
    }

    return z;
}

/*
 * rotate_right_left is a balancing function for the TLDList AVL tree
 *
 * returns pointer to node `n' in new rotated position
 */
TLDNode *rotate_right_left(TLDNode *n) {
    n->child_r = rotate_right(n->child_r);
    return rotate_left(n);
}

/*
 * rotate_left_right is a balancing function for the TLDList AVL tree
 *
 * returns pointer to node `n' in new rotated position
 */
TLDNode *rotate_left_right(TLDNode *n) {
    n->child_l = rotate_left(n->child_l);
    return rotate_right(n);
}

/*
 * rebalance takes node `n' from TLDList `tld' and rebalances the tree so the
 * nodes balance is less than |2|
 */
void rebalance(TLDList *tld, TLDNode *n) {
    int balance = node_height(n->child_r) - node_height(n->child_l);
    
    if (balance == -2) {
        if (node_height(n->child_l->child_l) >= node_height(n->child_l->child_r)) {
            n = rotate_right(n);
        } else {
            n = rotate_left_right(n);
        }
    } else if (balance == 2) {
        if (node_height(n->child_r->child_r) >= node_height(n->child_r->child_l)) {
            n = rotate_left(n);
        } else {
            n = rotate_right_left(n);
        }
    }

    if (n->parent != NULL) {
        rebalance(tld, n->parent);
    } else {
        tld->root = n;
    }
}

/*
 * find_tld finds the top level domain of a given url, `url'
 * 
 * returns a pointer to the char array containing the tld
 */
char *find_tld(char *url) {
    // finds placement of final '.' in url
    char * p_to_dot = strrchr(url, '.');

    // returns null if no '.' present
    if (p_to_dot == NULL) {
        return NULL;
    }

    // converts to lower case if not
    for(int i = 0; p_to_dot[i]; i++){
        p_to_dot[i] = tolower(p_to_dot[i]);
    }

    // returns rest of string past final '.'
    return p_to_dot + 1;
}

/*
 * tldlist_add adds the TLD contained in `hostname' to the tldlist if
 * `d' falls in the begin and end dates associated with the list;
 * returns 1 if the entry was counted, 0 if not
 */
int tldlist_add(TLDList *tld, char *hostname, Date *d) {
    // checks if `d' is within date filters of `tld'
    if (date_compare(tld->begin, d) > 0 || date_compare(tld->end, d) < 0) {
        return 0;
    }
    
    // returns tld of hostname
    hostname = find_tld(hostname);
    
    // creates node in root position if `tld' empty
    if (tld->root == NULL) {
        TLDNode * new_node = malloc(sizeof(TLDNode) + sizeof(hostname));
        if (new_node == NULL) {
            return 0;
        }       
        
        new_node = set_node(tld, new_node, NULL, NULL, NULL, hostname);
        tld->root = new_node;
        tld->add_count++;
        return 1;
    }

    int condition_met = 0;
    TLDNode * n = tld->root;

    // loop finds position to insert node, or adds 1 to current nodes `tld_count'
    while (condition_met == 0) {
        TLDNode * parent = n;

        int go_left = strcmp(n->hostname, hostname); // compares tlds of current node and `hostname'
        
        if (go_left == 0) { // if strings are equal, then adds 1 to `tld_count' of node
            n->tld_count++;
            tld->add_count++;
            condition_met = 1;

        } else {
            // decides whether to go to left or right child based on go left comparison
            if (go_left > 0) {
                n = n->child_l;
            } else {
                n = n->child_r;
            } 

            // creates new structure if at the leaf of tree
            if (n == NULL) {
                TLDNode * new_node = malloc(sizeof(TLDNode) + sizeof(hostname));
                if (new_node == NULL) {
                    return 0;
                }

                new_node = set_node(tld, new_node, parent, NULL, NULL, hostname);

                if (go_left > 0) {
                    parent->child_l = new_node;
                } else {
                    parent->child_r = new_node;
                }

                rebalance(tld, parent);
                condition_met = 1;
                tld->add_count++;
            }
        }
    }

    return 1;
}

/*
 * tldlist_count returns the number of successful tldlist_add() calls since
 * the creation of the TLDList
 */
long tldlist_count(TLDList *tld) {
    return tld->add_count;
}

/*
 * tldnode_tldname returns the tld associated with the TLDNode
 */
char *tldnode_tldname(TLDNode *node) {
    return node->hostname;
}

/*
 * tldnode_count returns the number of times that a log entry for the
 * corresponding tld was added to the list
 */
long tldnode_count(TLDNode *node) {
    return node->tld_count;
}

// Iterator Functions

/*
 * inorder_traversal_n is the secondary function for the inorder traversal
 */
void inorder_traversal_n(TLDNode *node_array, TLDNode *node, int *count) {
    if (node == NULL) {
        return;
    }

    inorder_traversal_n(node_array, node->child_l, count);
    node_array[*count] = *node;
    *count = *count +1;
    inorder_traversal_n(node_array, node->child_r, count);
}

/*
 * inorder_traversal recursively traverses through sub-tree starting from `node'
 * 
 * it adds a pointer to each node in the tree in the array `node_array'
 */
void inorder_traversal(TLDNode *node_array, TLDNode *node) {
    if (node == NULL) {
        return;
    }

    int count = 0;

    inorder_traversal_n(node_array, node->child_l, &count);
    node_array[count] = *node;
    count++;
    inorder_traversal_n(node_array, node->child_r, &count);
}

/*
 * tldlist_iter_create creates an iterator over the TLDList; returns a pointer
 * to the iterator if successful, NULL if not
 */
TLDIterator *tldlist_iter_create(TLDList *tld) {
    TLDNode * node_array = malloc(sizeof(*node_array) * tld->elements);
    if (node_array == NULL) {
        return NULL;
    }

    inorder_traversal(node_array, tld->root); // populates `node_array'
    
    TLDIterator * iterator = malloc(sizeof(TLDIterator) + sizeof(node_array));
    if (iterator == NULL) {
        return NULL;
    }

    iterator->tld_array = node_array;
    iterator->current = 0;
    iterator->limit = tld->elements;

    return iterator;
}

/*
 * tldlist_iter_next returns the next element in the list; returns a pointer
 * to the TLDNode if successful, NULL if no more elements to return
 */
TLDNode *tldlist_iter_next(TLDIterator *iter) {
    if (iter->current == iter->limit) {
        return NULL;
    }

    TLDNode * current = &iter->tld_array[iter->current];
    iter->current++;
    return current;
}

/*
 * tldlist_iter_destroy destroys the iterator specified by `iter'
 */
void tldlist_iter_destroy(TLDIterator *iter) {
    free(iter->tld_array);
    free(iter);
}