#include <cstdlib>
#include <iostream>
#include <fstream>
#include <string>

class Stack {

};

void run_operation(char c, Stack & s);

int main(int argc, char** argv) {

    if (argc == 1) {
        std::cout << "Please provide a file argument..." << std::endl;
        exit(0);
    }

    std::ifstream file;
    file.open(argv[1]);
    if (!file.good()) {
        std::cout << "Unfortunately, your file failed to open." << std::endl;
        exit(0);
    }

    std::string s;
    while(!file.eof()) {
        getline(file, s);
        if (isdigit(s[0])) {
            // expression.push(s) you'll need a try catch here for if it's not actually an int...
            continue;
        }
        // run_operation(s[0], expression);
    }
    return 0;
}
