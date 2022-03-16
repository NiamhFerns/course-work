#include<iostream>
#include<fstream>
#include<string>
#include<vector>

void splitString(std::string arr[], std::string str, char delim);

class UserPlan { //Class used to store information for a single user. 
    private:
        std::string name, phoneNumber;
        int mobileData, callMins, texts;
        float planCost;
    
    public: 
        void PrintPlan();
        UserPlan(std::string csvStr) { //Constructor used to fill out data for each UserPlan.
            std::string userData[5] = {""};
            splitString(userData, csvStr, ';'); //CSV line split into arr of strings then added to UserPlan.
            name = userData[0];
            phoneNumber = userData[1];
            mobileData = stoi(userData[2]);
            callMins = stoi(userData[3]);
            texts = stoi(userData[4]);
            planCost = mobileData * 0.1 + callMins * 0.04 + texts * 0.03;
        }
};

int main() {
    std::string filename = "plans.txt", currentPlan;
    std::fstream PlansData;
    PlansData.open(filename.c_str(), std::fstream::in);
    
    if (!PlansData.is_open()) { //Make Sure the file is open.
        std::cout << filename << " could not be found. Please make sure it is in the right directory and try again." << std::endl;
    } else {
        std::vector<UserPlan> plans; //Store each line in a vector of UserPLans.
        while (getline(PlansData, currentPlan)) {
            plans.emplace_back(currentPlan); //emplace_back() used to reduce need for copying after vector resize.
            plans[plans.size() - 1].PrintPlan();
        }
        PlansData.close();
    }
}

void UserPlan::PrintPlan() {
    printf("%s (%s) %dMB %dmins %dtxt ", name.c_str(), phoneNumber.c_str(), mobileData, callMins, texts);
    printf("Plan costs $%.2f\n", planCost);
}

void splitString(std::string wordList[], std::string str, char delim) { //Split a single line by user selected delimiter.
    int i = 0;
    for (char ch : str) {
        if (ch == delim) ++i;
        else wordList[i] += ch;
    }   
}
