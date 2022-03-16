#include <iostream>

int main() {
    float foo = 0.2f, *bar = &foo, *dee = new float(0.3f);
    *dee = 0.5f;
    std:: cout << "This is a pointer: " << dee << " and this is it's value: " << *dee << std::endl;
    std::cout << "This is a variable value: " << foo << " and it is located at " << &foo << std::endl;
    return 0;
}
