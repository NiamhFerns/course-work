#include <stdio.h>

struct record {
    char name[40];
    short int age;
    float height;
};

void print_record(struct record rec) {
    printf("Name  : %s\nAge   : %d\nHeight: %.2f", rec.name, rec.age, rec.height);
}

int main (int argc, char *argv[])
{
    struct record rec;

    scanf("%s %d %f", rec.name, &rec.age, &rec.height);
    print_record(rec);
    
    return 0;
}
