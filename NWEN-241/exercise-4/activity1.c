#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

int main()
{

    int pid, ret_exec, status;
    pid = fork();

    switch (pid) {
    case -1:
        printf("Error");
        exit(1);

    case 0:
        // Use execl system call to execute the ps -A command.
        // If it fails, display error message "Error executing exec"
        break;

    default:
        // Wait for termination of child process then if WIFEXITED(status) is set, display the following values in the same order:
        // - Process ID of the parent process.
        // - Process ID of the child process.
        // - The termination status of the child process. 
        break;
    }

    return 0;
}
