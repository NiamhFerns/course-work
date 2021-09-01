#include <iostream>
#include <iomanip>
#include <cmath>

int main() {
    float avgVoltage = 0.0f, voltageReadings[6];
    int issueCount = 0;

    std::cout << "Please enter in 6 voltages: "; //Get user input then divide total by len of readings for avgVoltage
    for (int i = 0; i < 6; i++){
        std::cin >> voltageReadings[i];
        avgVoltage += voltageReadings[i];
    }
    avgVoltage /= 6;
    std::cout << std::setprecision(1) << std::fixed << "The average is " << avgVoltage << " volts.\n" << std::endl;
    std::cout << std::setprecision(1) << std::fixed << "10% = " << avgVoltage * 0.1 << " volts." << std::endl;
    std::cout << std::setprecision(1) << std::fixed << "15% = " << avgVoltage * 0.15 << " volts.\n" <<  std::endl;

    for (int i = 0; i < 6; i++) { //print error if voltage difference is too high.
        if (fabs(avgVoltage - voltageReadings[i]) > (avgVoltage * 0.1)) {
            if (issueCount == 0) std::cout << "The following problems occurred: \n"; //Check if the heading has been printed.
            std::cout << issueCount + 1 << ". Voltage at hour " << i + 1 << " was " << voltageReadings[i];
            std::cout << " (difference of " << fabs(avgVoltage - voltageReadings[i]) << " volts).\n";
            issueCount++;
        }
    }
    for (int i = 0; i < 5; i++) { //print error if voltage change is too high.
        if (fabs(voltageReadings[i] - voltageReadings[i + 1]) > (avgVoltage * 0.15)) {
            if (issueCount == 0) std::cout << "The following problems occurred: \n"; //Check if the heading has been printed.
            std::cout << issueCount + 1 << ". Voltage change from hour " << i + 1 << " to hour " << i + 2 << " was ";
            std::cout << fabs(voltageReadings[i] - voltageReadings[i + 1]) << " volts.\n";
            issueCount++;
        }
    }

    if (issueCount == 0) std::cout << "No problems were encountered.\n";
}
