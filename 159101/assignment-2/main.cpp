#include <iostream>
#include <iomanip>

using namespace std;

int numOfJudges;
float currentScore, highestScore = 0.0f, lowestScore = 10.0f, totalScore = 0.0f;

int main() {
    cout << "Please enter in how many judges there are (4 - 8): "; //Num of judges
    cin >> numOfJudges;
    while (numOfJudges > 8 || numOfJudges < 4) {
        cout << "Invalid input. Number should be between 4 and 8: ";
        cin >> numOfJudges;
    }

    for (int i = 0; i < numOfJudges; i++) { //Get the various scores for each judge
        cout << "Please enter in a value for judge " << i + 1 << " (0.0 - 10.0): ";
        cin >> currentScore;
        while (currentScore < 0 || currentScore > 10) {
            cout << "Invalid score. Score should be between 0.0 and 10.0: ";
            cin >> currentScore;
        }

        totalScore += currentScore;

        if (currentScore > highestScore) { //Store highest and lowest values.
            highestScore = currentScore;
        }
        if (currentScore < lowestScore) {
            lowestScore = currentScore;
        }
    }

    totalScore -= (highestScore + lowestScore); //Get the average.
    totalScore /= (numOfJudges - 2);

    cout << setprecision(2) << fixed << "Your final score is " << totalScore;
}
