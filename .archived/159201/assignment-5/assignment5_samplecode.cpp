#include <stdio.h>
#include <stdlib.h>

#include <iostream>
#include <fstream>

using namespace std;

/* implement the Tree class here, you can copy the one from week 4 slides */
/* the contents of the Tree nodes can be of type char, so they can store operators (+, *, / and -) as well as digits (0,1,2...9) */
/* all the RPN*.txt test files contain numbers with single digits */


/* implement your stack here*/
/* remember that the content of the stack is of type Tree *


//Stack S; //Declare your stack of Tree* here 

/* implement your recursive funtions for traversals */


main( int argc, char** argv ){//get filename from arguments
	char digit;
	char oper;
	char expression[100];
	ifstream input_file;
	if(argc==2) input_file.open(argv[1]);
	else {printf("The program needs a filename as argument \n");exit(0);}
	/* both operator and digits are of type char */
	while(input_file >> expression){
		if(isdigit(expression[0])){
			sscanf(expression,"%c",&digit);
			printf("reading a number: %c \n",digit);
			//modify here to deal with the Stack
		}
		else {
			sscanf(expression,"%c",&oper);
			printf("reading an operator: %c \n",oper);
			//modify here to deal with the Stack
		}
	}
	//Now we can traverse the tree in a certain way and print the expression
	//in-order with parenthesis
	
	//post-order

}

