#include <stdio.h>

int main (int argc, char *argv[])
{
    printf("Enter a string: ");

    char string[100];

    scanf("%[^\n]", string);
    printf("%s", string);

    return 0;
}
