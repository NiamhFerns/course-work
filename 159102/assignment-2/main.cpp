#include <iostream>
#include <string>
#include <fstream>

// globals
// pc = program counter, ir = instuction register.
int memory[256], reg[16], pc, ir;

// FUNCTIONS

// Go through and store all the instuctions from the txt file and move all the
// various instruction nums to memory.
void readInstructions(std::string instuctionsPath);

// Print the current instuction being completed.
void printInstruction(/* if any other params are needed I'm not sure yet... */);

int main() {
    std::string userInput = "";
    std::cout << "Enter the file name of the MASSEY machine code: ";
    getline(std::cin, userInput);

    return 0;
}

void readInstructions(std::string instuctionsPath) {

}

void printInstruction() {

}
