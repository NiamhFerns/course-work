#include <iostream>

int factorial (int n) {
    return n == 0 ? 1 : n * factorial(n - 1);
}

int main() {
    int n = 6, r = 2;
    std::cout << factorial(n) / (factorial(r) * (n - r));
    return 0;
}
