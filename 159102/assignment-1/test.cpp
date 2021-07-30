#include <iostream>
#include <stdint.h>

void printCoolerBinary(uint8_t num) {
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
	printBinary(10);
	printBinary(243);
	printBinary(67);
	printBinary(42);
	printBinary(72);
	printBinary(54);
	printBinary(9);
	return 0;
}
