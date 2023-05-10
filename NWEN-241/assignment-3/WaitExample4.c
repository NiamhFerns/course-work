#include<stdio.h>
#include<sys/wait.h>
#include<unistd.h>
#include<stdlib.h>

int main(void) {    

  pid_t pid;    

  printf("fork test\n");    
  if ((pid = fork ()) < 0) {        
   printf(" fork error\n");    
    } else if (pid == 0) { /* child */        
   printf(" at child process\n");  
     
    } else { /* parent */        
   printf("parent: wait for child\n");        
   wait(NULL);        
   printf("parent: child complete\n"); 
 } 
} 
