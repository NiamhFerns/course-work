#include <ctype.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>

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

struct server_instance* start_server(const int port);
void accept_client(const struct server_instance* s_instance);
void maintain_client(const struct server_instance* s_instance, struct client_instance* c_instance);

void server_get(struct client_instance* c_instance, const char* argument);
void server_put(struct client_instance* c_instance, const char* argument);

int main(int argc, char* argv[])
{
    // Verify Port
    if (argc < 2 || atoi(argv[1]) < 1024 || atol(argv[1]) > 65535) {
        exit(-1);
    }

    // Server Setup
    const int port = atoi(argv[1]);
    const struct server_instance* s_instance = start_server(port);

    // Run Server
    while (RUNNING)
        accept_client(s_instance);

    return 0;
}

struct server_instance* start_server(const int port)
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

void accept_client(const struct server_instance* s_instance)
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

    // Parent instance should return to accept new clients.
    if (pid > 0) {
        // Hand off maintenance of this client to the child proccess.
        close(client_f_descriptor);
        return;
    }

    // Child instance.
    // Don't account for pid_t < 0 because of Task 3 brief.
    struct client_instance* c_instance = malloc(sizeof(struct client_instance));
    c_instance->c_descriptor = client_f_descriptor;
    c_instance->address = client_address;
    maintain_client(s_instance, c_instance);
    free(c_instance);
}

void maintain_client(const struct server_instance* s_instance, struct client_instance* c_instance)
{
    char* msg = "HELLO\n";

    if ((write(c_instance->c_descriptor, msg, strlen(msg))) < 0) {
        warn("Failed to write to socket.");
    }

    char buffer[100];

    while (1) {
        memset(buffer, 0, 100);
        if ((read(c_instance->c_descriptor, buffer, 100)) < 0) {
            warn("Failed to read from client.");
            close(c_instance->c_descriptor);
            break;
        }

        // This is 10 to account for client typos and bad commands.
        char request[10];
        memset(request, 0, 10);
        char argument[100];
        memset(argument, 0, 100);
        sscanf(buffer, "%s %s", request, argument);

        if (strlen(request) == 3 && !strncasecmp(request, "GET", 3)) {
            server_get(c_instance, argument);
        } else if (strlen(request) == 3 && !strncasecmp(request, "PUT", 3)) {
            server_put(c_instance, argument);
        } else if (strlen(request) == 3 && !strncasecmp(request, "BYE", 3)) {
            close(c_instance->c_descriptor);
            return;
        } else {
            char* msg = "SERVER 502 Command Error\n";
            if ((write(c_instance->c_descriptor, msg, strlen(msg))) < 0) {
                warn("Failed to write to socket.");
            }
        }
    }
}

void server_get(struct client_instance* c_instance, const char* argument)
{
    // Check argument.
    if (strlen(argument) == 0) {
        char* msg = "SERVER 500 Get Error\n";
        if ((write(c_instance->c_descriptor, msg, strlen(msg))) < 0) {
            warn("Failed to write to socket.");
        }
        return;
    }

    // Open File
    FILE* file_descriptor;
    if ((file_descriptor = fopen(argument, "r")) == NULL) {
        char* msg = "SERVER 404 Not Found\n";
        if ((write(c_instance->c_descriptor, msg, strlen(msg))) < 0) {
            warn("Failed to write to socket.");
        }
        return;
    }

    char* msg = "SEVER 200 OK\n\n";
    write(c_instance->c_descriptor, msg, strlen(msg));

    char c[2];
    memset(c, 0, 2);
    while (!feof(file_descriptor)) {
        c[0] = fgetc(file_descriptor);
        if ((write(c_instance->c_descriptor, c, 2)) < 0) {
            warn("Failed to write to socket.");
        }
    }

    fclose(file_descriptor);

    msg = "\n\n\n";
    if ((write(c_instance->c_descriptor, msg, strlen(msg))) < 0) {
        warn("Failed to write to socket.");
    }
}

void server_put(struct client_instance* c_instance, const char* argument)
{
    if (strlen(argument) == 0) {
        char* msg = "SERVER 502 Command Error\n";
        if ((write(c_instance->c_descriptor, msg, strlen(msg))) < 0) {
            warn("Failed to write to socket.");
        }
        return;
    }

    FILE* file_descriptor;
    if ((file_descriptor = fopen(argument, "w")) == NULL) {
        char* msg = "SERVER 501 Put Error\n";
        if ((write(c_instance->c_descriptor, msg, strlen(msg))) < 0) {
            warn("Failed to write to socket.");
        }
        return;
    }

    int STOP = 0;
    char buffer[1000];
    memset(buffer, 0, 1000);
    while (STOP != 2) {
        memset(buffer, 0, 1000);
        if ((read(c_instance->c_descriptor, buffer, 1000)) < 0) {
            warn("Failed to read from client.");
        }
        for (int i = 0; i < strlen(buffer); ++i) {
            fputc(buffer[i], file_descriptor);
            if (buffer[i] == '\n')
                STOP++;
            else
                STOP = 0;
        }
    }

    char* msg = "SEVER 201 Created\n";
    if ((write(c_instance->c_descriptor, msg, strlen(msg))) < 0) {
        warn("Failed to write to socket.");
    }

    fclose(file_descriptor);
}

void err(char* err_msg, int code)
{
    printf("ERROR: %s\n", err_msg);
    exit(code);
}

void warn(char* warn_msg) { printf("WARNING: %s\n", warn_msg); }

void success(char* s_msg) { printf("SUCCESS: %s\n", s_msg); }
