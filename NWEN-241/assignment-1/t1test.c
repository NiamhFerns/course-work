/**
 * NWEN 241 Programming Assignment 1
 * Sample Test Code for Task 1.
 * 
 * This must be compiled with your implementation of Task 1 which is in editor.c
 * To compile: 
 *     gcc editor.c t1test.c -o t1test
 * 
 * If everything goes well, this will generate an executable file t1test.
 * 
 * You are free to modify this file, for you to add in more test cases.
 */

#include <stdio.h>
#include <string.h>

#include "editor.h"

int main(void)
{
    int ret;
    char editing_buffer[21];
    char expected_buffer[21];
    
    printf("Sample test for Task 1\n");
    
    printf("----------------------\n");   
    strcpy(editing_buffer, "The quick brown fox");
    printf("Initial  buffer contents: %s\n", editing_buffer);
    printf("Call: editor_insert_char(editing_buffer, 21, 'T', 0);\n");
    ret = editor_insert_char(editing_buffer, 21, 'T', 0);
    strcpy(expected_buffer, "TThe quick brown fox");
    printf("Expected buffer contents: %s\n", expected_buffer);
    printf("Actual   buffer contents: %s\n", editing_buffer);
    printf("Expected return value: 1\n");
    printf("Actual   return value: %d\n", ret);
    
    printf("----------------------\n");   
    strcpy(editing_buffer, "The quick brown fox");
    printf("Initial  buffer contents: %s\n", editing_buffer);
    printf("Call: editor_insert_char(editing_buffer, 21, 's', 9);\n");
    ret = editor_insert_char(editing_buffer, 21, 's', 9);
    strcpy(expected_buffer, "The quicks brown fox");
    printf("Expected buffer contents: %s\n", expected_buffer);
    printf("Actual   buffer contents: %s\n", editing_buffer);
    printf("Expected return value: 1\n");
    printf("Actual   return value: %d\n", ret);
    
    printf("----------------------\n");
    strcpy(editing_buffer, "The quick brown fox");
    printf("Initial  buffer contents: %s\n", editing_buffer);
    printf("Call: editor_insert_char(editing_buffer, 21, 's', 20);\n");
    ret = editor_insert_char(editing_buffer, 21, 's', 20);
    strcpy(expected_buffer, "The quick brown fox");
    printf("Expected buffer contents: %s\n", expected_buffer);
    printf("Actual   buffer contents: %s\n", editing_buffer);
    printf("Expected return value: 1\n");
    printf("Actual   return value: %d\n", ret);    
    
    printf("----------------------\n");
    strcpy(editing_buffer, "The quick brown fox");
    printf("Initial  buffer contents: %s\n", editing_buffer);
    printf("Call: editor_insert_char(editing_buffer, 21, 's', 21);\n");
    ret = editor_insert_char(editing_buffer, 21, 's', 21);
    strcpy(expected_buffer, "The quick brown fox");
    printf("Expected buffer contents: %s\n", expected_buffer);
    printf("Actual   buffer contents: %s\n", editing_buffer);
    printf("Expected return value: 0\n");
    printf("Actual   return value: %d\n", ret);  
    
    return 0;
}


