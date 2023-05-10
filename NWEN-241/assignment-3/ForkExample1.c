#include<stdio.h>
#include<sys/wait.h>
#include<unistd.h>
#include<stdlib.h>

int main(){

    int pid;
    printf("fork test\n");    
    pid = fork (); //spawn a child
   
    if (pid < 0) {        
       printf(" fork error\n"); 
      exit(1);
    } 
    
    else if (pid == 0) { /* child */        
      printf(" at child process, my pid is: %d\n", (int) getpid());}
    
    else { /* parent */        
      printf(" at parent process, my pid is: %d\n", (int) getpid());
      printf(" my child process is: %d\n", pid);
         }   
 }
