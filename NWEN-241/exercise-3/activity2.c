#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char *argv[])
{
    int a, b;
    FILE *in;
    FILE *out;

    in = fopen("raw.txt", "r");
    out = fopen("processed.txt", "w");

    char p[10];
    char q[10];

    while (fscanf(in, "%s", p) != EOF) {
        fscanf(in, "%s", q);
        fprintf(out, "%s %s %d\n", p, q, (atoi(p) + atoi(q)));
    }   

    fclose(in);
    fclose(out);

    return EXIT_SUCCESS;
}
