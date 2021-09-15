#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <errno.h>
#include <unistd.h>
#include <netdb.h>
#include <string.h>

int main(int argc, char *argv[]) {

    char buf[INET6_ADDRSTRLEN];

    if (argc == 1) {
        printf("ERROR: Provide DNS names as arguments\n");
        return -1;
    }

    struct addrinfo hints;
    struct addrinfo *ai0;

    memset(&hints, 0, sizeof(hints));
    hints.ai_family = PF_UNSPEC;
    hints.ai_socktype = SOCK_STREAM;

    struct addrinfo *ai;

    for (int index = 1; index < argc; index++) {
        int i;
        char *dns = argv[index];

        if ((i = getaddrinfo(dns, "5000", &hints, &ai0)) != 0) {
            printf("ERROR: %s %s\n", dns, gai_strerror(i));
            continue;
        }

        for (ai = ai0; ai != NULL; ai = ai->ai_next) {
            void *addr;
            char *ipver;

            if (ai->ai_family == AF_INET) {
                struct sockaddr_in *ipv4 = (struct sockaddr_in *)ai->ai_addr;
                addr = &(ipv4->sin_addr);
                ipver = "IPv4";
            } else {
                struct sockaddr_in6 *ipv6 = (struct sockaddr_in6 *)ai->ai_addr;
                addr = &(ipv6->sin6_addr);
                ipver = "IPv6";
            }

            if (inet_ntop(ai->ai_family, addr, buf, sizeof(buf)) == NULL) {
                printf("%d\n", errno);
                continue;
            }

            printf("%s %s %s\n", dns, ipver, buf); 
        }
    }
}