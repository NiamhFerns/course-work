#include <iostream>
#include <string>
#include <fstream>

// globals
// pc = program counter, ir = instuction register.
int memory[256] = {0}, reg[16], pc = 0, ir;
bool halted = 0;

// FUNCTIONS

// Go through and store all the instuctions from the txt file and move all the
// various instruction nums to memory.
void loadInstructions(std::string instuctionsPath);
void fetchInstruction();
void decodeInstruction(int cache[4]);
void executeInstruction(int cache[4]);

int main() {
    std::string userInput = "";
    std::cout << "Enter the file name of the MASSEY machine code: ";
    getline(std::cin, userInput);

    // Boot up the MASSEY machine.
    loadInstructions(userInput);
    while (!halted && pc < 256) {
        int cache[4] = {0};
        fetchInstruction();
        decodeInstruction(cache);
        executeInstruction(cache);
        ++pc;
    }

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
            memory[instuction] = memory[instuction] << 4; // All instuctions are seperated by a
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

void fetchInstruction() {
    ir = memory[pc];
    ++pc;
}

void decodeInstruction(int cache[4]) {
    // NOTE: The part is 0 indexed. EG: In 3RXY to access the register we need part = 1.
    for (int i = 0; i < 4; ++i) {
        int mask = (0xF000 >> i * 4);
        cache[i] = (ir & mask) >> ((3 - i) * 4);
    }
}

void executeInstruction(int cache[4]) {
    std::cout << std::hex << ir;
    switch (cache[0]) {
        case 1:
            reg[cache[1]] = (cache[2] << 4) + cache[3];
            std::cout << std::hex << "    R" << cache[1] << " = " << reg[cache[1]];
            break;

        case 2:
            reg[cache[2]] = reg[cache[3]];
            std::cout << std::hex << "    R" << cache[2] << " = " << reg[cache[2]];
            break;

        case 3:
            reg[cache[1]] = memory[(cache[2] << 4) + cache[3]];
            std::cout << std::hex << "    R" << cache[1] << " = " << reg[cache[1]];
            break;

        case 4:
            memory[(cache[2] << 4) + cache[3]] = reg[cache[1]];
            std::cout << std::hex << "    Memory[" << (cache[2] << 4) + cache[3] << "] = " << reg[cache[1]];
            break;

        case 6:
            reg[cache[1]] = reg[cache[2]] + reg[cache[3]];
            std::cout << std::hex << "    R" << cache[1] << " = " << reg[cache[1]];
            break;

        case 7:
            reg[cache[2]] = reg[cache[2]] * -1;
            std::cout << std::hex << "    R" << cache[2] << " = " << reg[cache[2]];
            break;

        case 8:
            reg[cache[1]] = reg[cache[1]] << cache[3];
            std::cout << std::hex << "    R" << cache[1] << " = " << reg[cache[1]];
            break;

        case 9:
            reg[cache[1]] = reg[cache[1]] >> cache[3];
            std::cout << std::hex << "    R" << cache[1] << " = " << reg[cache[1]];
            break;

        case 0xA:
            reg[cache[1]] = reg[cache[2]] & reg[cache[3]];
            std::cout << std::hex << "    R" << cache[1] << " = " << reg[cache[1]];
            break;

        case 0xB:
            reg[cache[1]] = reg[cache[2]] | reg[cache[3]];
            std::cout << std::hex << "    R" << cache[1] << " = " << reg[cache[1]];
            break;

        case 0xC:
            reg[cache[1]] = reg[cache[2]] ^ reg[cache[3]];
            std::cout << std::hex << "    R" << cache[1] << " = " << reg[cache[1]];
            break;

        case 0xD:
            if (reg[cache[1]] == reg[0]) {
                pc = (cache[2] << 4) + cache[3];
                std::cout << "    R" << cache[1] << "== R0 : JUMP";
            }
            std::cout << "    R" << cache[1] << "!= R0 : NO JUMP";
            break;

        case 0xE:
            halted = 1;
            std::cout << "Halt\n";
            break;

        default:
            std::cout << "    This is not a valid instruction... \n";
    }
    if (!halted) std::cout << std::hex << "    PC = " << pc << std::endl;
}
