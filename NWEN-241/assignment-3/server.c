#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>
#include <ctype.h>

// Flag for use by child processes.
int RUNNING = 1;

struct server_instance {
    int f_descriptor;
    struct sockaddr_in address;
};

struct client_instance {
    int c_descriptor;
    struct sockaddr_in address;
};

void err(char* err_msg, int code);
void warn(char* warn_msg);
void success(char* s_msg);

struct server_instance* server_start(const int port);
void server_accept_client(const struct server_instance* s_instance);
void maintain_client(const struct server_instance* s_instance, struct client_instance* c_instance);

int main(int argc, char* argv[])
{
    // Verify Port
    if (argc < 2 || atoi(argv[1]) < 1024 || atol(argv[1]) > 65535) {
        exit(-1);
    }

    // Server Setup
    const int port = atoi(argv[1]);
    const struct server_instance* s_instance = server_start(port);

    // Run Server
    while (RUNNING)
        server_accept_client(s_instance);

    return 0;
}

struct server_instance* server_start(const int port)
{
    // Create socket.
    int f_descriptor = socket(AF_INET, SOCK_STREAM, 0);
    if (f_descriptor < 0)
        err("Failed to create socket.", 2);
    success("Socket created.");

    // Create Server Address.
    struct sockaddr_in server_address;
    server_address.sin_family = AF_INET;
    server_address.sin_port = htons(port);
    server_address.sin_addr.s_addr = INADDR_ANY;

    success("Address created.");

    // Bind Address
    int b_descriptor = bind(f_descriptor, (struct sockaddr*)&server_address,
        sizeof(server_address));
    if (b_descriptor < 0)
        err("Failed to bind to port.", 3);
    success("Port bound.");

    // Listen on socket
    if (listen(f_descriptor, SOMAXCONN) < 0)
        err("Listening failed.", 4);

    // Message Length + Maximum Port Digit Count
    char s_port[18 + 5];
    sprintf(s_port, "Listening on port %d", port);
    success(s_port);

    // Instantiate Server instance.
    struct server_instance* instance = malloc(sizeof(struct server_instance));
    instance->f_descriptor = f_descriptor;
    instance->address = server_address;

    return instance;
}

void server_accept_client(const struct server_instance* s_instance)
{
    // Accept a client connection.
    struct sockaddr_in client_address;
    int c_addr_len = sizeof(client_address);
    int client_f_descriptor = accept(s_instance->f_descriptor, (struct sockaddr*)&client_address,
        (socklen_t*)&c_addr_len);
    if (client_f_descriptor < 0)
        warn("Failed to accept client");
    success("Client accepted.");

    // Fork here.
    pid_t pid = fork();

    // Fork error.
    if (pid < 0)
        err("Server failed to fork process for new client", 5);

    // Parent instance should return to accept new clients.
    if (pid > 0)
        return;

    // Child instance.
    struct client_instance* c_instance = malloc(sizeof(struct client_instance));
    c_instance->c_descriptor = client_f_descriptor;
    c_instance->address = client_address;
    maintain_client(s_instance, c_instance);
    free(c_instance);
}

void maintain_client(const struct server_instance* s_instance, struct client_instance* c_instance)
{
    char buffer[100];
    memset(buffer, 0, 100);
    strncpy(buffer, "Hello, world!\n", strlen("Hello, world!\n"));

    if ((write(c_instance->c_descriptor, buffer, 100)) < 0) {
        err("Failed to write to socket!", 5);
    }
    while (1) {
        memset(buffer, 0, 100);
        if ((read(c_instance->c_descriptor, buffer, 100)) < 0) {
            warn("Failed to read from client.");
            close(c_instance->c_descriptor);
            break;
        }
        printf("Client requested: %s\n", buffer);

        char request[10]; // This is 10 to account for client typos and bad commands.
        char argument[100];
        sscanf(buffer, "%s %s", request, argument);


        if (strlen(request) == 3 && !strncasecmp(request, "GET", 3)) {
            printf("Get shit: Len: %lu\n", strlen(request));
        }
        else if (strlen(request) == 3 && !strncasecmp(request, "PUT", 3)) {
            printf("Put shit: Len: %lu\n", strlen(request));
        }
        else if (strlen(request) == 3 && !strncasecmp(request, "BYE", 3)) {
            printf("Hi");
            // close(c_instance->c_descriptor);
            return;
        }
        else {
            printf("SERVER 502 Command Error\n");
        }
    }
}

void err(char* err_msg, int code)
{
    printf("ERROR: %s\n", err_msg);
    exit(code);
}

void warn(char* warn_msg) { printf("WARNING: %s\n", warn_msg); }

void success(char* s_msg) { printf("SUCCESS: %s\n", s_msg); }
