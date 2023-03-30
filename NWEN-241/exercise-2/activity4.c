#include <stdio.h>

#define MAX 100

int *find_max(int *a, int alen) {
  int *max = a, *p = a;

  for (; p - a < alen; p++) {
    if (*p > *max)
      max = p;
  }

  return max;
}

int main(int argc, char *argv[]) {
  int n, array[MAX];
  scanf("%d", &n);
  for (int i = 0; i < n; i++) {
    scanf("%d", array + i);
  }
  int *max = find_max(array, n);
  printf("%d", *max);
  return 0;
}
