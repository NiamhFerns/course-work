#include <stdio.h>

int main (int argc, char *argv[])
{
    float ctemp, ftemp;
    printf("Enter temperature: ");

    scanf("%f", &ctemp);
    
    ftemp = (9.0f / 5.0f) * ctemp + 32.0f;

    printf("%.3f", ftemp);

    return 0;
}
