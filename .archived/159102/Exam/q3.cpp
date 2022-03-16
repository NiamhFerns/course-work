#include <iostream>
#include <string>
#include <vector>

class student_class {
private:
    int id;
    std::string degree;  // BA, BCom, BSc
public:
    void loaddata();
    void display();
    bool is_BSc();
};

int main() {
    int numStudents = 3;
    std::vector<student_class> students;
    // Change the loop to change how many students are in the vector...
    // Doing it this way gives me great pain but we weren't allowed to change the class :c
    for (int i = 0; i < numStudents; ++i) {
        students.emplace_back(); // Create a black student.
        students.back().loaddata();
    }

    std::cout << "\nThese are the students that are studying a BSc:";
    for (auto student : students) {
        if (student.is_BSc()) {
            std::cout << std::endl;
            student.display();
        }
    }
}

void student_class::loaddata() {
    std::cout << "What is your id? ";
    std::cin >> id;

    std::cout << "What is your degree (BA, BCom, BSc)? ";
    std::cin >> degree;
}

void student_class::display() {
    std::cout << "Student ID: " << id << std::endl;
    std::cout << "Student Degree: " << degree << std::endl;
}

bool student_class::is_BSc() {
    return degree == "BSc";
}
