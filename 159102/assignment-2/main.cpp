// Niamh Kirsty Ferns - 21007069 - MASSEY Machine Simulator
#include <ios>
#include <iostream>
#include <string>
#include <fstream>
#include <iomanip>

int memory[256] = {0}, reg[16], pc = 0, ir = 0, memSize = 0x0000FFFF;
bool halted = 0;

void loadInstructions(std::string instuctionsPath);      // Load instructions into memory. "Start" the MASSEY machine.
void fetchInstruction();                                 // Load instruction register and increment program counter.
void decodeInstruction(int *opcode, int operand[3]);     // Decode instruction from ir into opcode and individual operands.
void executeInstruction(int *opcode, int operand[3]);    // Execute appopriate opcode and operands.
void printCycle(int *opcode, int operand[3]);

int main() {
    std::string userInput = "";
    std::cout << "Enter the file name of the MASSEY machine code: ";
    getline(std::cin, userInput);

    // Boot up the MASSEY machine.
    loadInstructions(userInput);
    std::cout << "\nSIMULATING MASSEY MACHINE...\n"
              << "IR      PC    Result\n"
              << "-------------------------------\n";

    int opcode = 0, operand[3] = {0};
    while (!halted && pc < 256) {
       fetchInstruction();
       decodeInstruction(&opcode, operand);
       executeInstruction(&opcode, operand);
       // Printing is done this way because in a real computer... you wouldn't print every instruction, so it's not part of the execute phase.
       printCycle(&opcode, operand);
    }

    // There are conceivably some cases where your PC would go back to 00 and then you could use this as a loop with an end...
    // However, this is bad practice... Don't do it. c:
    if (pc > 255 && !halted) std::cout << "FATAL ERROR: Infinite loop detected!\n";
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
        std::cout << std::hex << std::setfill('0')  << "Memory[" << std::uppercase << std::setw(2) << i << "] = " << std::uppercase << std::setw(4) << memory[i] << std::endl;
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
    // NOTE: Each command comment follows the structure opcode, operand 1, operand 2, operand 3.
    switch (*opcode) {
        case 1: // 1RXY: Load R with value XY.
            reg[operand[0]] = (operand[1] << 4) + operand[2];
            break;

        case 2: // 20RS: Load R with S.
            reg[operand[1]] = reg[operand[2]];
            break;

        case 3: // 3RXY: Load R with memory at XY.
            reg[operand[0]] = memory[(operand[1] << 4) + operand[2]];
            break;

        case 4: // 4RXY: Store R into memory at XY.
            memory[(operand[1] << 4) + operand[2]] = reg[operand[0]];
            break;

        case 6: // 6RST: Add S and T then store the result in R.
            reg[operand[0]] = reg[operand[1]] + reg[operand[2]] & memSize;
            break;

        case 7: // 70R0: Negate the value in R.
            reg[operand[1]] = reg[operand[1]] * - 1 & memSize;
            break;

        case 8: // 8R0X: Bitshift R to the right by X.
            reg[operand[0]] = reg[operand[0]] >> operand[2] & memSize;
            break;

        case 9: // 9R0X: Bitshift R to the left by X.
            reg[operand[0]] = reg[operand[0]] << operand[2] & memSize;
            break;

        case 0xA: // ARST: And S with T then store the result in R.
            reg[operand[0]] = reg[operand[1]] & reg[operand[2]] & memSize;
            break;

        case 0xB: // BRST: Or S with T then store the result in R.
            reg[operand[0]] = reg[operand[1]] | reg[operand[2]] & memSize;
            break;

        case 0xC: // CRST: Xor S with T then store the result in R.
            reg[operand[0]] = reg[operand[1]] ^ reg[operand[2]] & memSize;
            break;

        case 0xD: // DRXY: Jump program counter to XY if the contents of R equal the contents of register 0.
            if (reg[operand[0]] == reg[0]) pc = (operand[1] << 4) + operand[2];
            break;

        case 0xE: // E000: Halt.
            halted = 1;
            break;

        default:
            *opcode = -1; // This functions as a fail bit to show that no action was taken.
            break;
    }
}

void printCycle(int *opcode, int operand[3]) {
    std::cout << std::hex << std::setw(4) << std::setfill('0') << ir << "    " << std::setw(2) << std::setfill('0') << (pc & 0xFF);
    switch(*opcode) {
        case 4:
            std::cout << std::hex << "    Memory[" << std::setw(2) << (operand[1] << 4) + operand[2] << "] = " << std::setw(4) << reg[operand[0]] << std::endl;
            break;

        case 2: case 7:
            std::cout << std::hex << "    R" << operand[1] << " = " << std::setw(4) << reg[operand[1]] << std::endl;
            break;

        case 0xD:
            if (reg[operand[0]] == reg[0]) std::cout << "    JUMP\n";
            else std::cout << "    CONTINUE\n";
            break;

        case 0xE:
            std::cout << "    Halt\n";
            break;

        case -1:
            std::cout << "    NO ACTION TAKEN\n";
            break;

        default:
            std::cout << std::hex << "    R" << operand[0] << " = " << std::setw(4) << reg[operand[0]] << std::endl;
            break;
    }
}
