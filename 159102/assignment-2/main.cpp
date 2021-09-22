// Niamh Kirsty Ferns - 21007069 - MASSEY Machine Simulator
#include <iostream>
#include <string>
#include <fstream>
#include <iomanip>

int memory[256] = {0}, reg[16], pc = 0, ir = 0;
bool halted = 0;

void loadInstructions(std::string instuctionsPath);
void fetchInstruction();                                 // Load instruction register and increment program counter.
void decodeInstruction(int *opcode, int operand[3]);     // Decode instruction from ir into opcode and individual operands.
void executeInstruction(int *opcode, int operand[3]);    // Execute appopriate opcode and operands.

int main() {
    std::string userInput = "";
    std::cout << "Enter the file name of the MASSEY machine code: ";
    getline(std::cin, userInput);
    std::cout << std::endl;

    // Boot up the MASSEY machine.
    loadInstructions(userInput);
    std::cout << "SIMULATING MASSEY MACHINE...\nIR      PC    Result\n-------------------------------";
    int opcode = 0, operand[3] = {0};
    while (!halted && pc < 256) {
       fetchInstruction();
       decodeInstruction(&opcode, operand);
       executeInstruction(&opcode, operand);
    }
    if (pc > 255 && !halted) std::cout << "\nFATAL ERROR: Infinite loop detected!\n";
    std::cout << "Exiting simulation...\n";
    return 0;
}

void loadInstructions(std::string instuctionsPath) {
    std::fstream commands;

    commands.open(instuctionsPath.c_str(), std::fstream::in);
    if (!commands.is_open()) {
        std::cout << "Sorry, but that file doesn't seem to be in the in your programs current directory. Exiting...\n";
    }

    int i = 0, currentLine;
    while (commands >> std::hex >> currentLine) {
        memory[i] = currentLine;
        std::cout << std::hex << "Memory[" << i << "] = " << std::uppercase << std::setw(4) << std::setfill('0') << memory[i] << std::endl;
        ++i;
    }

    commands.close();
    std::cout << std::endl;
}

void fetchInstruction() {
    ir = memory[pc];
    ++pc;
}

void decodeInstruction(int *opcode, int operand[3]) {
    // First we get the opcode.
    *opcode = (ir & 0xF000) >> 12;

    // Then we fill out an array of our operands
    for (int i = 0; i < 3; ++i) {
        int mask = (0xF00 >> (i * 4));             // Set or mask to match the correct part of the ir. (Left --> Right)
        operand[i] = (ir & mask) >> (4 * (2 - i)); // Set the apropriate operand and shift it to the right so it's a single hex digit.
    }
}

void executeInstruction(int *opcode, int operand[3]) {
    std::cout << std::endl << std::hex << std::setw(4) << std::setfill('0') << ir << "    " << std::setw(2) << std::setfill('0') << (pc & 0xFF);

    // NOTE: Each command comment follows the structure opcode, operand 1, operand 2, operand 3.
    switch (*opcode) {
        case 1: // 1RXY: Load R with value XY.
            reg[operand[0]] = (operand[1] << 4) + operand[2];
            std::cout << std::hex << "    R" << operand[0] << " = " << std::setw(4) << reg[operand[0]];
            break;

        case 2: // 20RS: Load R with S.
            reg[operand[1]] = reg[operand[2]];
            std::cout << std::hex << "    R" << operand[1] << " = " << std::setw(4) << reg[operand[1]];
            break;

        case 3: // 3RXY: Load R with memory at XY.
            reg[operand[0]] = memory[(operand[1] << 4) + operand[2]];
            std::cout << std::hex << "    R" << operand[0] << " = " << std::setw(4) << reg[operand[0]];
            break;

        case 4: // 4RXY: Store R into memory at XY.
            memory[(operand[1] << 4) + operand[2]] = reg[operand[0]];
            std::cout << std::hex << "    Memory[" << std::setw(2) << (operand[1] << 4) + operand[2] << "] = " << std::setw(4) << reg[operand[0]];
            break;

        case 6: // 6RST: Add S and T then store the result in R.
            reg[operand[0]] = reg[operand[1]] + reg[operand[2]] & 0x0000FFFF; // The mask here is to force max of 2 byte memory size.
            std::cout << std::hex << "    R" << operand[0] << " = " << std::setw(4) << reg[operand[0]];
            break;

        case 7: // 70R0: Negate the value in R.
            reg[operand[1]] = reg[operand[1]] * - 1 & 0x0000FFFF; // The mask here is to force max of 2 byte memory size.
            std::cout << std::hex << "    R" << operand[1] << " = " << std::setw(4) << reg[operand[1]];
            break;

        case 8: // 8R0X: Bitshift R to the right by X.
            reg[operand[0]] = reg[operand[0]] >> operand[2] & 0x0000FFFF; // The mask here is to force max of 2 byte memory size.
            std::cout << std::hex << "    R" << operand[0] << " = " << std::setw(4) << reg[operand[0]];
            break;

        case 9: // 9R0X: Bitshift R to the left by X.
            reg[operand[0]] = reg[operand[0]] << operand[2] & 0x0000FFFF; // The mask here is to force max of 2 byte memory size.
            std::cout << std::hex << "    R" << operand[0] << " = " << std::setw(4) << reg[operand[0]];
            break;

        case 0xA: // ARST: And S with T then store the result in R.
            reg[operand[0]] = reg[operand[1]] & reg[operand[2]] & 0x0000FFFF; // The mask here is to force max of 2 byte memory size.
            std::cout << std::hex << "    R" << operand[0] << " = " << std::setw(4) << reg[operand[0]];
            break;

        case 0xB: // BRST: Or S with T then store the result in R.
            reg[operand[0]] = reg[operand[1]] | reg[operand[2]] & 0x0000FFFF; // The mask here is to force max of 2 byte memory size.
            std::cout << std::hex << "    R" << operand[0] << " = " << std::setw(4) << reg[operand[0]];
            break;

        case 0xC: // CRST: Xor S with T then store the result in R.
            reg[operand[0]] = reg[operand[1]] ^ reg[operand[2]] & 0x0000FFFF; // The mask here is to force max of 2 byte memory size.
            std::cout << std::hex << "    R" << operand[0] << " = " << std::setw(4) << reg[operand[0]];
            break;

        case 0xD: // DRXY: Jump program counter to XY if the contents of R equal the contents of register 0.
            if (reg[operand[0]] == reg[0]) {
                pc = (operand[1] << 4) + operand[2];
                std::cout << "    JUMP";
                break;
            }
            std::cout << "    CONTINUE";
            break;

        case 0xE: // E000: Halt.
            halted = 1;
            std::cout << "    Halt\n";
            break;

        default:
            std::cout << "    NO ACTION TAKEN";
            break;
    }
}
