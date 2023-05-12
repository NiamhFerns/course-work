#include<stdio.h>
#include<sys/socket.h>
#include<string.h>
#include<stdlib.h>
#include <netinet/in.h> 
#include<unistd.h>


#define DEFAULT_STRLEN 100
#define SERVER_PORT  23456

int create_server_sock(struct sockaddr_in *address);
void handle_client(int clientfd);
void reverse_input(char *word, int begin, int end);

int main(void)
{
    int sockfd;
    
    struct sockaddr_in address; 
    
          
    /* Create and bind socket using the function create_server_sock. Print an error message if unsucessful*/
   
    
    /* Listen for incoming connections. Print an error message if unsucessful */
    if(listen(sockfd, SOMAXCONN) < 0) {
        printf("Error: Failed to listen for connections\n"); 
        return 0;
    }
               
        /* Accept */
        while(1){
        int addrlen = sizeof(address); 
        int clientfd = accept(sockfd, (struct sockaddr *)&address,  
                       (socklen_t*)&addrlen);
    
        /* Handle client */
        handle_client(clientfd);
        }
    return 0;
}

int create_server_sock(struct sockaddr_in *address)
{
    int fd;
    
   // Create socket file descriptor. Return -1 if error
    
    

    // Define Server address
    

       
    // Bind socket to server address. Return -2 if error
    
    
    //return socket file descriptor
}


void handle_client(int clientfd)
{
    char outmsg[DEFAULT_STRLEN];
    char inmsg[DEFAULT_STRLEN];
    

    if(clientfd < 0) {
        printf("Error: Failed to accept client connection\n"); 
        return;
    }
     /* Read the msg from the client*/
            
        
               
    
     /* Reverse the msg using function reverse_input*/
     
     
    
     /* Send the reversed string back to client*/
        
}

void reverse_input(char *word, int begin, int end){
  
}
