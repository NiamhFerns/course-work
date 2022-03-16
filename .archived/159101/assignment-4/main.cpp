#include<iostream>
#include<string>

using std::string;

string PromptUser(string prompt, int maxLength);
string JustifyString(string inputStr, char seperator, int toAdd);
// string FormatString(string str);
// string TrimString(string str);

int main() {
    string inputStr = PromptUser("Enter a line of text: ", 40);
    string justifiedStr = JustifyString(inputStr, ' ', 40 - inputStr.length());
    std::cout << "1234567890123456789012345678901234567890\n";
    std::cout << justifiedStr << std::endl;
}

string PromptUser(string prompt, int maxLength) {
    string output;
    std::cout << prompt;
    getline(std::cin, output);
    while (output.length() > maxLength) {
        std::cout << "Input was invalid.\n" << prompt;
        getline(std::cin, output);
    }
    return output;
}

string JustifyString(string inputStr, char seperator, int toAdd) { //Justify a string by n amount.
    // inputStr = FormatString(inputStr); // Not needed for the test cases in this assignment. Left just in case.
    int spaces = 0;
    for (int i = 0; i < inputStr.length(); i++) { //Find how many words we have.
        if (inputStr[i] == ' ') ++spaces;
    }
    int remainder = toAdd % spaces; //Use that to give us the remainder and the amount of spaces needed.
    spaces = toAdd / spaces;

    for (int i = 0, k = 1; i < inputStr.length(); i += k, k = 1) { //Get our new index and reset k.
        if (inputStr[i] == seperator) {
            inputStr.insert(i, spaces + (remainder > 0), seperator);
            k += spaces + (remainder > 0); //How many spaces do we need to skip past.
            --remainder;
        }
    }
    return inputStr;
}

// string FormatString(string str) { //formates a string so that each word is always seperated by 1 and only 1 space.
//     string outStr = "";
//     for (int i = 0; i < str.length(); ++i) {
//         if (outStr[outStr.length() - 1] != ' ' || str[i] != ' ') outStr += str[i];
//     }
//     return TrimString(outStr);
// }

// string TrimString(string str) { //removes spaces from the front and back of a string if it has them.
//     string outStr = "";
//     int start = 0, end = str.length() - 1;
//     if (str[0] == ' ') start = 1;
//     if (str[str.length() - 1] == ' ') end = str.length() - 1;
//     for (int i = start; i < end; ++i) {
//         outStr += str[i];
//     }
//     return outStr;
// }
