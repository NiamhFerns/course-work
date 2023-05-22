#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <unistd.h>

#define DEFAULT_STRLEN 100
#define SERVER_PORT 23456

int create_server_sock(struct sockaddr_in* address);
void handle_client(int clientfd);
void reverse_input(char* word, char outmsg[DEFAULT_STRLEN]);

void err(char* err_msg, int code)
{
    printf("ERROR: %s\n", err_msg);
    exit(code);
}

void warn(char* warn_msg) { printf("WARNING: %s\n", warn_msg); }

void success(char* s_msg) { printf("SUCCESS: %s\n", s_msg); }

int main(void)
{
    struct sockaddr_in address;
    int sockfd = create_server_sock(&address);

    /* Listen for incoming connections. Print an error message if unsucessful */
    if (listen(sockfd, SOMAXCONN) < 0)
        err("Failed to listen for connections.", 3);
    success("Listening on server port 2345.");

    /* Accept */
    for (;;) {
        int addrlen = sizeof(address);
        int clientfd = accept(sockfd, (struct sockaddr*)&address,
            (socklen_t*)&addrlen);
        /* Handle client */
        handle_client(clientfd);
        close(clientfd);
    }

    return 0;
}

int create_server_sock(struct sockaddr_in* address)
{
    int f_descriptor = socket(AF_INET, SOCK_STREAM, 0);
    if (f_descriptor < 0)
        err("Failed to create socket.", 2);
    success("Socket created.");

    // Create Server Address.
    address->sin_family = AF_INET;
    address->sin_port = htons(SERVER_PORT);
    address->sin_addr.s_addr = INADDR_ANY;
    success("Address created.");

    // Bind Address
    int b_descriptor = bind(f_descriptor, (struct sockaddr*)address,
        sizeof(struct sockaddr_in));
    if (b_descriptor < 0)
        err("Failed to bind to port.", 3);
    success("Port bound.");
    return f_descriptor;
}

void handle_client(int clientfd)
{
    char outmsg[DEFAULT_STRLEN] = {};
    char inmsg[DEFAULT_STRLEN] = {};

    if (clientfd < 0) {
        printf("Error: Failed to accept client connection\n");
        return;
    }

    if (read(clientfd, inmsg, DEFAULT_STRLEN) < 0)
        warn("Error reading from client.");
    
    reverse_input(inmsg, outmsg);

    if (write(clientfd, outmsg, DEFAULT_STRLEN) < 0)
        warn("Error writing to client.");
}

void reverse_input(char* word, char reversed_word[DEFAULT_STRLEN])
{
    int actual_str_len = strlen(word);
    for (int i = actual_str_len - 1, j = 0; i >= 0; i--, j++)
        reversed_word[j] = word[i];
}
