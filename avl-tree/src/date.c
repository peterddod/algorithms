/*
 * SP Exercise 1a
 * Peter Dodd, 2308057D
 * This is my own work as defined in the 
 * Academic Ethics agreement I have signed.
 */

#include "date.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

// Structs

typedef struct date {
    int day;
    int month;
    int year;
} Date;

// Functions

/*
 * date_create creates a Date structure from `datestr`
 * `datestr' is expected to be of the form "dd/mm/yyyy"
 * returns pointer to Date structure if successful,
 *         NULL if not (syntax error)
 */
Date *date_create(char *datestr) {
    // checks length matches format
    if (strlen(datestr) != 10) {
        return NULL;
    }

    // checks delimiters match format
    if (datestr[2] != '/' || datestr[5] != '/') {
        return NULL;
    }

    int day = 0;
    int month = 0;
    int year = 0;

    // checks day numbers are digits and adds to struct if they are
    if (isdigit(datestr[0]) && isdigit(datestr[1])) {
        day = ((datestr[0] - '0') *10) + (datestr[1] - '0');
    } else {
        return NULL;
    }

    // checks month numbers are digits and adds to struct if they are
    if (isdigit(datestr[3]) && isdigit(datestr[4])) {
        month = ((datestr[3] - '0') *10) + (datestr[4] - '0');
    } else {
        return NULL;
    }

    // checks year numbers are digits and adds to struct if they are
    if (isdigit(datestr[6]) && isdigit(datestr[7]) && isdigit(datestr[8]) && isdigit(datestr[9])) {
        year = ((datestr[6] - '0') *1000) + ((datestr[7] - '0') *100) + ((datestr[8] - '0') *10) + (datestr[9] - '0');
    } else {
        return NULL;
    }
    
    Date * new_date = malloc(sizeof(Date));

    if (new_date == NULL) {
        return NULL;
    }

    new_date->day = day;
    new_date->month = month;
    new_date->year = year;
    return new_date;
}

/*
 * date_duplicate creates a duplicate of `d'
 * returns pointer to new Date structure if successful,
 *         NULL if not (memory allocation failure)
 */
Date *date_duplicate(Date *d) {    
    Date * new_date = malloc(sizeof(Date));

    if (new_date == NULL){
        return NULL;
    }

    new_date->day = d->day;
    new_date->month = d->month;
    new_date->year = d->year;
    
    return new_date;
}

/*
 * date_compare compares two dates, returning <0, 0, >0 if
 * date1<date2, date1==date2, date1>date2, respectively
 */
int date_compare(Date *date1, Date *date2) {
    // finds total of days for each date
    int date1_tot = ((date1->year*365) + (date1->month * 31) + date1->day);
    int date2_tot = ((date2->year*365) + (date2->month * 31) + date2->day);

    return date1_tot - date2_tot;
}

/*
 * date_destroy returns any storage associated with `d' to the system
 */
void date_destroy(Date *d) {
    free(d);
}