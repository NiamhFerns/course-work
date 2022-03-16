#include <iostream>

int main() {
    int val;
    int *ptr;
    ptr = &val;

    std::cout << "Please enter in a number: ";
    std::cin >> *ptr;
    *ptr += 3;

    std::cout << "Your new value is " << *ptr << std::endl;
    return 0;
}
