#include <cctype>
#include <iostream>
#include <string>
#include <fstream>
#include <sstream>

// globals
// pc = program counter, ir = instuction register.
int memory[256] = {0}, reg[16], pc, ir;

// FUNCTIONS

// Go through and store all the instuctions from the txt file and move all the
// various instruction nums to memory.
void loadInstructions(std::string instuctionsPath);
void decodeInstruction();
void executeInstruction();

// Print the current instuction being completed.
void printInstruction(/* if any other params are needed I'm not sure yet... */);

int main() {
    std::string userInput = "";
    std::cout << "Enter the file name of the MASSEY machine code: ";
    getline(std::cin, userInput);

    // Boot up the MASSEY machine.
    loadInstructions(userInput);

    // Main program loop.

    // Remember... fetch, decode, execute.
    executeInstruction();
    ++pc;

    return 0;
}

void loadInstructions(std::string instuctionsPath) {
    std::fstream commands;

    commands.open(instuctionsPath.c_str(), std::fstream::in);
    if (!commands.is_open()) {
        std::cout << "Sorry, but that file doesn't seem to be in the in your programs current directory. Exiting...\n";
    }

    int instuction = 0;
    std::string currentLine;

    while (std::getline(commands, currentLine)) {
        // to load into memory we need to convert each char from hex to binary
        for (int i = 0; i < 4; ++i) { // this will break without more checks but the notes said to not validate input so...
            memory[instuction] = memory[instuction] << 8; // All instuctions are seperated by a
            if (currentLine[i] <= '9') {
                memory[instuction] += currentLine[i] - 48;
                continue;
            }

            memory[instuction] += toupper(currentLine[i]) - 55;
        }

        ++instuction;
    }

    commands.close();
}

void printInstruction() {

}
