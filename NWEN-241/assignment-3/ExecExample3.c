#include<stdlib.h>
#include<unistd.h>
#include<stdio.h>
 
   int main()
   {       
       if( execl("./fork","fork",NULL) == -1){         
         printf("Exec failed");
       }
       printf("Success\n");
       return 0;
   }
