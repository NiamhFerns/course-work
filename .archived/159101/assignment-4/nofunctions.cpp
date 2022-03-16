#include<iostream>
#include<string>

int main() {
    std::string output;
    std::cout << "Enter a line of text: ";
    getline(std::cin, output);
    while (output.length() > 40) {
        std::cout << "Input was invalid.\n" << "Enter a line of text: ";
        getline(std::cin, output);
    }
    int indexSkip = 1, toAdd = 40 - output.length();
    while(toAdd > 0) {
        for (int i = 0; i < output.length() && toAdd > 0; i++) {
            if (output[i] == ' ') {
                output.insert(i, " ");
                toAdd--;
                i += indexSkip;
            }
        }
        indexSkip++;
    }
    std::cout << "1234567890123456789012345678901234567890\n";
    std::cout << output << std::endl;
}