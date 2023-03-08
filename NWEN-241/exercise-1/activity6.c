#include <stdio.h>

#define SUM(x, y) (x + y)

int main (int argc, char *argv[])
{
    int a, b, s;
    scanf("%d %d", &a, &b);
    s = SUM(a, b);
    printf("%d", s);
    return 0;
}
