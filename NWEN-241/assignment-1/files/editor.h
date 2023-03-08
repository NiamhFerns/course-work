/**
 * NWEN 241 Programming Assignment 1
 * editor.h Header File
 * 
 * This header file contains the function prototypes for Tasks 1-4. 
 * Include this in your implementation of editor.c
 * Do not modify this file.
 */

#ifndef __EDITOR_H__
#define __EDITOR_H__


/**
 * Task 1.
 * Insert a charater in the editing buffer.
 * @param editing_buffer Editing buffer
 * @param editing_buflen Length of editing buffer in bytes
 * @param to_insert Character to insert
 * @param pos Index where insertion should occur
 * @return 1 if the insertion occurred, otherwise 0
 */
int editor_insert_char(char editing_buffer[], int editing_buflen, char to_insert, int pos);


/**
 * Task 2.
 * Delete the first occurrence of the character in the editing buffer.
 * @param editing_buffer Editing buffer
 * @param editing_buflen Length of editing buffer in bytes
 * @param to_delete Character to delete
 * @param offset Index to start searching for the character to delete
 * @return 1 if the deletion occurred, otherwise 0
 */
int editor_delete_char(char editing_buffer[], int editing_buflen, char to_delete, int offset);


/**
 * Task 3.
 * Replace the first occurrence of the string in the editing buffer.
 * @param editing_buffer Editing buffer
 * @param editing_buflen Length of editing buffer in bytes
 * @param str String to replace
 * @param replacement Replacement string
 * @param offset Index to start searching for the string
 * @return If the string replacement occurred, the function should return the index corresponding the last letter of @str in @editing_buffer, otherwise, it should return -1.
 */
int editor_replace_str(char editing_buffer[], int editing_buflen, const char *str, const char *replacement, int offset);



/**
 * Task 4.
 * Replace all occurrences of the string in the editing buffer.
 * @param rows Number of rows in viewing buffer
 * @param cols Numbber of columns in viewing buffer
 * @param editing_buffer Editing buffer
 * @param editing_buflen Length of editing buffer in bytes
 * @param wrap Whether wrapping is applied or not
 */
void editor_view(int rows, int cols, char viewing_buffer[rows][cols], const char editing_buffer[], int editing_buflen, int wrap);



#endif /* __EDITOR_H__ */

