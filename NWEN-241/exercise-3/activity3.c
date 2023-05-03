#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int main(int argc, char *argv[])
{
    int sum = 0;

    for (int i = 1; i < argc; i++) {
        sum += atoi(argv[i]);
    }

    printf("%d\n", sum);

    return EXIT_SUCCESS;
}
