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
        ret_exec = execl("/bin/ps", "ps", "-A", NULL);
        if (ret_exec < 0) printf("Error executing exec");
        break;

    default:
        pid = wait(&status);
        if (WIFEXITED(status)) {
            printf("%d\n", getpid());
            printf("%d\n", pid);
            printf("%d\n", WEXITSTATUS(status));
        }
        break;
    }

    return 0;
}
