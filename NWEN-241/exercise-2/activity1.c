#include <ctype.h>
#include <stdio.h>

void capitalize(char *str) {
  int i = 0;
  while (str[i] != '\0') {
    if (islower(str[i]))
      str[i] = toupper(str[i]);
    i++;
  }
}

int main(int argc, char *argv[]) {
  char s[50] = "ABC123 is the most common password";
  capitalize(s);
  printf("%s\n", s);
}

// The issue is that the value is stored as a pointer to a char. It should be a
// char array so that each index of the array can be editted as if it were a
// mutable string. Program crashes on line 8 with an i value of 7.
