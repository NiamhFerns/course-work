#include <stdio.h>

int main(int argc, char *argv[]) {
  printf("Enter a string without whitespaces: ");

  char string[100];
  scanf("%s", string);

  printf("%s", string);

  return 0;
}
