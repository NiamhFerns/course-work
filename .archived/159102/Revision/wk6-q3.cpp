#include <cstddef>
#include <iostream>

int main() {
    int *ptr = new int(3);
    ptr = NULL;
    if (ptr == NULL) std::cout << "Oh god, oh fuck...\n";
    return 0;
}
