#include <iostream>
using namespace std;
int monthLength, difference, day, month, year, isFebruary;
bool isLeapYear;

int main() {
    cout << "Please enter a difference in days: \n"; //Get user input.
    cin >> difference;
    cout << "Please enter a date (dd mm yyyy): \n";
    cin >> day >> month >> year;
    
    isLeapYear = ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0); //get leap year status (from workshop)
    isFebruary = (month == 2) * (isLeapYear - 2); //get month length
    monthLength = 30 + ((month + (month > 7)) % 2) + isFebruary;

    day = day + difference; //modify day and go back/forward 1 month/year if needed
    if (day > monthLength) {
        month++;
        day = day - monthLength;
        if (month > 12) {
            month = 1;
            year++;
        }
    }
    else if (day < 1) {
        month--;
        if (month < 1) {
            month = 12;
            year--; 
        }
        else { //get the new month length to figure out new date.
            isFebruary = (month == 2) * (isLeapYear - 2);
            monthLength = 30 + ((month + (month > 7)) % 2) + isFebruary;
        }
        day = monthLength + day;
    }

    cout << "The modified date is: " << day << "/" << month << "/" << year; //output final date
}
