////////////////   159201   ///////////////////////////////////
//SAMPLE CODE FOR ASSIGNMENT 4 - Big Numbers
//2012 
#include <cstdlib>
#include <iostream>
#include <fstream>
#include <locale>
#include <sstream>
#include <string>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

using namespace std;

/////// PART A

//copy the template class List here


/////// PART B
class BigNumber {
private:
//complete here...
//include here a List of integers, or shorts etc

public:
//complete here...
//what methods do you need?  
  BigNumber();
  ~BigNumber();
  void ReadFromString (string decstring );
  void PrintBigNumber ();
  void AddBigNumbers(BigNumber B1,BigNumber B2);
};

BigNumber::BigNumber(){
// anything here?
}

BigNumber::~BigNumber(){
//you can keep that empty
}

void BigNumber::ReadFromString (string decstring ) {
  //cout<< "testing string passing: "<< decstring << endl;
	//read a string, adding a new node per digit of the decimal string
	// To translate 'digits' to integers: myinteger=decstring[index]-48
	
  for(int i = 0; i < decstring.size(); i++) {
    if(decstring[i]!='\n' && decstring[i]!='\r'){
      int temp=decstring[i]-48;
      cout << "Digit " << i << " is " << temp << endl;  //Comment this out for your final version
      //You need to use the AddtoFront() 
    }
  }
}

void BigNumber::PrintBigNumber () {
//complete here, print the list (i.e., use FirstItem() and NextItem() )
//remember that the print out may be inverted, depending on the order you enter the 'digits'
}

void BigNumber::AddBigNumbers(BigNumber B1,BigNumber B2){
//complete here.
//use FirstItem(), NextItem() and AddNode()
//to add two big numbers, what do you have to do? Be careful about the carry
//Remember to add the last carry, the resulting number can have one more digit than B1 or B2
}

/////// PART C

BigNumber B1,B2,RES;

main (int argc, char ** argv) {
  string numberstring;
  int stringseq=0;
  ifstream input_file;
  if(argc==2) input_file.open(argv[1]);
  else { cout<< "cannot read the file " << argv[1] << endl; exit(0);}
  while(!input_file.eof()){
    getline(input_file,numberstring);
    if(!input_file.eof()){
      cout << "reading a big number from file:" << numberstring << endl;//Comment this out for your final version
      if(stringseq==0){
        B1.ReadFromString(numberstring);
        stringseq=1;
      }
      else B2.ReadFromString(numberstring);
    }
  }
  //print
  B1.PrintBigNumber();//PROBLEM: Is the printing inverted? How can you solve this problem? Before or after using AddBigNumbers()?
  cout << "+" << endl;
  B2.PrintBigNumber();
  cout << "=" << endl;
  //compute the addition
  RES.AddBigNumbers(B1,B2);
  //print the result
  RES.PrintBigNumber();
}
