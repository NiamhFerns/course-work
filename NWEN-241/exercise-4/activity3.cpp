#include <iostream>
#include <ostream>
#include <string>

namespace Complex {
class complex {
private:
    int a, b;

public:
    complex() {
        a = b = 1;
    }

    complex(int a, int b) {
        this->a = a;
        this->b = b;
    }

    int geta() {
        return a;
    }

    int getb() {
        return b;
    }
};
}

int main (int argc, char *argv[])
{
    auto c1 = Complex::complex();
    auto c2 = Complex::complex(5, 10);

    std::cout << "Complex number 1:" << c1.geta() << " + " << c1.getb() << "i\n";
    std::cout << "Complex number 2:" << c2.geta() << " + " << c2.getb() << "i" << std::endl;
    return 0;
}
