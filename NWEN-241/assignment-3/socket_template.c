#include<stdio.h>
#include<sys/socket.h>
#include<netinet/in.h>
#include<string.h>
#include<stdlib.h>
#include<unistd.h>

#define SERVER_PORT 12345

void error(const char * msg){

    printf("Error : %s\n",msg);
    exit(1);
}

int main(){
    
    //step 1 create a socket
    int fd = socket(AF_INET, SOCK_STREAM,0);
    if(fd == -1){
    
        error("Error creating socket");
    }
    printf("Sockt created\n");
      
    //step 2(a) Create an address and bind socket to it 
    
    struct sockaddr_in serveraddr;
    serveraddr.sin_family = AF_INET;
    serveraddr.sin_port = htons(SERVER_PORT);
    serveraddr.sin_addr.s_addr = INADDR_ANY;
    
    printf("Address created\n");
    
    //Step 2(b)bind address to socket
    int bd;
    bd = bind(fd,(struct sockaddr *)&serveraddr,sizeof(serveraddr));
    
    if(bd ==-1){
        error("Error binding\n");
    }
    printf("Bind successful\n");
    
       
    //step 3 listen
    if(listen(fd,SOMAXCONN)<0){
     error("Listening error");   
    }
    printf("Listen successful\n");
    
    //Step 4 accept client connection
    
    struct sockaddr_in client_addr;
    int addrlen = sizeof(client_addr);
    int client_fd = accept(fd,(struct sockaddr *)&client_addr,(socklen_t*)&addrlen);
    if(client_fd<0){
     error("Error accepting client");   
    }
     printf("Accept successful\n");
    
     //step 5 Read and write
     
     char buffer[100];
     memset(buffer,0,100);
     
     strncpy(buffer,"Hello from server\n",strlen("Hello from server\n"));
     
     //int s = send(client_fd,buffer,100,0);
     int s = write(client_fd, buffer,100);
     if(s<0){
         error("Error writing to socket");
     }
     printf("Write success\n");
     
     memset(buffer,0,100);
     int r = read(client_fd, buffer,100);
     
    if(r<0){
         error("Error reading from client socket");
     }
     printf("Read success. Msg received: %s\n",buffer);

close(client_fd);
}
