#include <iostream>
#include <vector>
#include <string>

void sort(std::vector<float> v) {
    bool sorted = 0;
    while (!sorted) {
        sorted = 1;
        for (int i = 0; i < v.size() - 1; ++i) {
            if (v[i]   > v[i + 1]) {
                sorted = 0;
                v[i]  += v[i + 1];
                v[i+1] = v[i] - v[i + 1];
                v[i]   = v[i] - v[i + 1];
            }
        }
    }
}

void display(std::vector<float> &v) {
    for (int i = 0; i < v.size(); ++i) {
        std::cout << v[i] << std::endl;
    }
}

int main() {
    std::vector<float> floats;
    std::string userIn;

    do {
        std::cout << "Add (-1 to stop): ";
        std::cin >> userIn;

        floats.push_back(std::stof(userIn));
    } while (userIn != "-1");
    floats.pop_back();

    sort(floats);
    display(floats);

    return 0;
}
