#include <iostream>
#include <stdint.h>

void printCoolerBinary(uint8_t num) {
	short currentBit = 7; //zero indexing
	while (currentBit >= 0) {
		for (int i = 4; i > 0; i--, currentBit--) {
			std::cout << ((num >> currentBit) & 1);\
		}
		std::cout << " ";
	}
	std::cout << std::endl;
}

//TODO OKI SO DUMB FUCK. GO BACKWARD. REMEMBER CARS MOVIE...
//1000 0000 & num then bit shift to the left to check the next num
//  1000 0000
//& 0100 1010
//= 0
//
//  1000 0000
//& 1001 0100
//= 1
//etc etc.

void printBinary(uint8_t num) {
	uint8_t result = num;
	for (int i = 8; i > 0; i--) {
		result = (result << 1) + (num & 1);
		num = num >> 1;
	}
	while (result > 0) {
		for (int i = 0; i < 4; i++) {
			std::cout << (result & 1);
			result = result >> 1;
		}
		std::cout << " ";
	}
	std::cout << std::endl;
}

int main() {
	printCoolerBinary(10);
	printCoolerBinary(243);
	printCoolerBinary(67);
	printCoolerBinary(42);
	printCoolerBinary(72);
	printCoolerBinary(54);
	printCoolerBinary(9);
	return 0;
}

void printOldBinary(std::string decimalVal) {
	std::string binaryVal = "";
	if (std::stoi(decimalVal) > 255) {
		std::cout << "This decimal number is outside the range 0 to 255.\n";
		return;
	}
	
	// Start at the max number, and divide by 2, checking whether the number's division by 2
	// is even or odd. Repeat after dividing the number by 2.
	for (int i = stoi(decimalVal); i > 0; i /= 2) {
		binaryVal = std::to_string(i % 2) + binaryVal;
	}
	
	for (int i = 8 - binaryVal.length(); i > 0; --i) {
		binaryVal = "0" + binaryVal;
	}
	
	binaryVal = binaryVal.substr(0, 4) + " " + binaryVal.substr(4, 7);
	std::cout << "Converting decimal to binary. The result is " << binaryVal << std::endl;
}

void validateInput(std::string str) {
	// First we check to make sure there are no strange characters.
	for (int i = 0; i < str.length(); ++i) {
		if (str[i] < '0' || str[i] > '9') {
			std::cout << "This is not a valid number.\n";
			return;
		}
	}

	if (str[0] != '0' || str == "0") {
		printBinary(str);
		return;
	}
	printDecimal(str);
}
