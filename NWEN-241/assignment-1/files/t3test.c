/**
 * NWEN 241 Programming Assignment 1
 * Sample Test Code for Task 3.
 * 
 * This must be compiled with your implementation of Task 3 which is in editor.c
 * To compile: 
 *     gcc editor.c t3test.c -o t3test
 * 
 * If everything goes well, this will generate an executable file t3test.
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
    
    printf("Sample test for Task 3\n");
    
    printf("----------------------\n");   
    strcpy(editing_buffer, "The quick brown fox");
    printf("Initial  buffer contents: %s\n", editing_buffer);
    printf("Call: editor_replace_str(editing_buffer, 21, \"brown\", \"blue\", 0);\n");
    ret = editor_replace_str(editing_buffer, 21, "brown", "blue", 0);
    strcpy(expected_buffer, "The quick blue fox");
    printf("Expected buffer contents: %s\n", expected_buffer);
    printf("Actual   buffer contents: %s\n", editing_buffer);
    printf("Expected return value: 13\n");
    printf("Actual   return value: %d\n", ret);
    
    printf("----------------------\n");   
    strcpy(editing_buffer, "The quick brown fox");
    printf("Initial  buffer contents: %s\n", editing_buffer);
    printf("Call: editor_replace_str(editing_buffer, 21, \"brown\", \"blue\", 10);\n");
    ret = editor_replace_str(editing_buffer, 21, "brown", "blue", 10);
    strcpy(expected_buffer, "The quick blue fox");
    printf("Expected buffer contents: %s\n", expected_buffer);
    printf("Actual   buffer contents: %s\n", editing_buffer);
    printf("Expected return value: 13\n");
    printf("Actual   return value: %d\n", ret);
    
    printf("----------------------\n");
    strcpy(editing_buffer, "The quick brown fox");
    printf("Initial  buffer contents: %s\n", editing_buffer);
    printf("Call: editor_replace_str(editing_buffer, 21, \"brown\", \"blue\", 11);\n");
    ret = editor_replace_str(editing_buffer, 21, "brown", "blue", 11);
    strcpy(expected_buffer, "The quick brown fox");
    printf("Expected buffer contents: %s\n", expected_buffer);
    printf("Actual   buffer contents: %s\n", editing_buffer);
    printf("Expected return value: -1\n");
    printf("Actual   return value: %d\n", ret);
    
    printf("----------------------\n");
    strcpy(editing_buffer, "The quick brown fox");
    printf("Initial  buffer contents: %s\n", editing_buffer);
    printf("Call: editor_replace_str(editing_buffer, 21, \"brown\", \"blue\", 30);\n");
    ret = editor_replace_str(editing_buffer, 21, "brown", "blue", 30);
    strcpy(expected_buffer, "The quick brown fox");
    printf("Expected buffer contents: %s\n", expected_buffer);
    printf("Actual   buffer contents: %s\n", editing_buffer);
    printf("Expected return value: -1\n");
    printf("Actual   return value: %d\n", ret);
    
    return 0;
}
