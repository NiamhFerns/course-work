#include <stdio.h>

int main (int argc, char *argv[])
{
    int i = 4;
    while (i >= 0) {
        printf("20/%d=%d\n", i, 20/i);
        --i;
    }
    return 0;
}
