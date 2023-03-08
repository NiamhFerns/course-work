/**
 * NWEN 241 Programming Assignment 1
 * Sample Test Code for Task 4.
 * 
 * This must be compiled with your implementation of Task 4 which is in editor.c
 * To compile: 
 *     gcc editor.c t4test.c -o t4test
 * 
 * If everything goes well, this will generate an executable file t4test.
 * 
 * You are free to modify this file, for you to add in more test cases.
 */

#include <stdio.h>
#include <string.h>

#include "editor.h"

#define TEST_STRING "The quick brown\nfox jumps over\n\nthe lazy dog\n"

void reset_viewing_buffer(int rows, int cols, char vb[rows][cols])
{
    int i, j;

    for(i=0; i<rows; i++) 
        for(j=0; j<cols; j++)
            vb[i][j] = '\0';
}

void print_viewing_buffer(int rows, int cols, char vb[rows][cols])
{
    int i, j;
    
    //printf("\n");
    for(i=0; i<rows; i++) 
        printf("[%s]\n", vb[i]);
    printf("\n");
    printf("\n");
}

int main(void)
{
    int ret;
    char editing_buffer[48];
    char viewing_buffer[8][11];
    char expected_viewing_buffer[8][11];
    
    printf("Sample test for Task 4\n");
    
    printf("----------------------\n");   
    strcpy(editing_buffer, TEST_STRING);
    printf("Editing  buffer contents:\n%s\n", editing_buffer);
    printf("Call: editor_view(8, 11, viewing_buffer, editing_buffer, 48, 1);\n");
    reset_viewing_buffer(8, 11, viewing_buffer);
    editor_view(8, 11, viewing_buffer, editing_buffer, 48, 1);
    reset_viewing_buffer(8, 11, expected_viewing_buffer);
    strcpy(expected_viewing_buffer[0], "The quick ");
    strcpy(expected_viewing_buffer[1], "brown");
    strcpy(expected_viewing_buffer[2], "fox jumps ");
    strcpy(expected_viewing_buffer[3], "over");
    strcpy(expected_viewing_buffer[5], "the lazy d");
    strcpy(expected_viewing_buffer[6], "og");
    printf("Expected viewing buffer contents:\n");
    print_viewing_buffer(8, 11, expected_viewing_buffer);
    printf("Actual   viewing buffer contents:\n");
    print_viewing_buffer(8, 11, viewing_buffer);

    
    printf("----------------------\n");   
    strcpy(editing_buffer, TEST_STRING);
    printf("Editing  buffer contents:\n%s\n", editing_buffer);
    printf("Call: editor_view(8, 11, viewing_buffer, editing_buffer, 48, 0);\n");
    reset_viewing_buffer(8, 11, viewing_buffer);
    editor_view(8, 11, viewing_buffer, editing_buffer, 48, 0);
    reset_viewing_buffer(8, 11, expected_viewing_buffer);
    strcpy(expected_viewing_buffer[0], "The quick ");
    strcpy(expected_viewing_buffer[1], "fox jumps ");
    strcpy(expected_viewing_buffer[3], "the lazy d");
    printf("Expected viewing buffer contents:\n");
    print_viewing_buffer(8, 11, expected_viewing_buffer);
    printf("Actual   viewing buffer contents:\n");
    print_viewing_buffer(8, 11, viewing_buffer);
    
    return 0;
}
