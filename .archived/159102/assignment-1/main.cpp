/*
    Niamh Ferns
    21007069
    Binary/Decimal number converter.
*/

#include <iostream>
#include <string>

void validateInput(std::string str);
void printBinary(std::uint8_t num);
void printDecimal(std::string binaryVal);

int main() {
	std::string userIn;

	std::cout << "Enter a number: ";
	std::getline(std::cin, userIn);
	validateInput(userIn);
	return 0;
}

void validateInput(std::string str) {
	if (str[0] == '0' && str.length() > 9) { // Validate string length for binary.
		std::cout << "This binary number has more than 9 binary digits.\n";
		return;
	}
	
	for (int i = 0; i < str.length(); ++i) { // Validate characters for conversion mode.
		if (str[i] < '0' || str[i] > '9') {
			std::cout << "This is not a valid number.\n";
			return;
		}

		if (str[0] == '0' && str[i] > '1') {
			std::cout << "This is not a valid binary number.\n";
			return;
		}
	}

	if (str[0] != '0' || str == "0") {
		if (std::stoi(str) > 255) { // Check whether number is greater than 255
			std::cout << "This decimal number is outside the ranger 0 to 255.\n";
			return;
		}
		
		printBinary(stoi(str));
		return;
	
	}
	printDecimal(str);
}

void printBinary(uint8_t num) {
	short currentBit = 7; //zero indexing
	std::cout << "Converting decimal to binary. The result is ";
	while (currentBit >= 0) {
		for (int i = 4; i > 0; i--, currentBit--) {
			std::cout << ((num >> currentBit) & 1);
		}
		std::cout << " ";
	}
	std::cout << std::endl;
}

void printDecimal(std::string binaryVal) {
	int decimalVal = 0;
	for (int i = binaryVal.length() - 1; i > 0; --i) {
		decimalVal = (decimalVal << 1) + ((int)binaryVal[i] - 48);
	}

	std::cout << "Converting binary to decimal. The result is " << decimalVal << std::endl;
}
