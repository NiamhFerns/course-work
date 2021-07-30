#include <iostream>
#include <string>

void printDecimal(std::string binaryVal);
void printBinary(std::string decimalVal);
void validateInput(std::string str);

int main() {
	std::string userIn;

	std::cout << "Enter a number: ";
	std::getline(std::cin, userIn);
	while (userIn != "e") {	
		validateInput(userIn);
		std::cout << "Enter a number: ";
		std::getline(std::cin, userIn);
	}

	return 0;
}

void printDecimal(std::string binaryVal) {
	int decimalVal = 0;
	if (binaryVal.length() > 9) {
		std::cout << "This binary number has more than 9 binary digits.\n";
		return;
	}
	
	int bitValue = 1;
	for (int i = binaryVal.length() - 1; i > 0; --i) {
		if (binaryVal[i] != '1' && binaryVal[i] != '0') {
			std::cout << "This is not a valid binary number.\n";
			return;
		}
		std::cout << (int)binaryVal[i] << std::endl;
		decimalVal += (((int)binaryVal[i] - 48) * bitValue);
		bitValue *= 2;
	}

	std::cout << "Converting binary to decimal. The result is " << decimalVal << std::endl;
}

void printBinary(std::string decimalVal) {
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
