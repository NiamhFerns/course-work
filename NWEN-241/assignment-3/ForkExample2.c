#include<stdio.h>
#include <sys/types.h>
#include <unistd.h>

int main(){ // p1
  int p=10;
  //fork();//spawns a child //p2
  //fork(); //spawns a child . executed both p1 and p2
  //fork();//
  
  
  
  fork()&&fork();
  fork() && fork();
  fork();
  
  
  printf(" %d\n", p);
    
}
