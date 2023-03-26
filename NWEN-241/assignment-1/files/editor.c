#include "editor.h"
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int editor_insert_char(char editing_buffer[], int editing_buflen,
    char to_insert, int pos)
{
    // Error if invalid input.
    if (editing_buflen <= 0 || pos < 0 || pos > editing_buflen - 1)
        return 0;

    // Switch store the current position in a temp variable then replace it with the next
    // charatcer to insert, stopping one short of the last index to maintain termination char.
    char tmp;
    for (int i = pos; i < editing_buflen - 1; ++i) {
        tmp = editing_buffer[i];
        editing_buffer[i] = to_insert;
        to_insert = tmp;
    }
    return 1;
}

int editor_delete_char(char editing_buffer[], int editing_buflen,
    char to_delete, int offset)

{
    // Error if invalid input.
    if (editing_buflen <= 0 || offset < 0 || offset > editing_buflen - 1)
        return 0;

    bool found = 0;

    // Find the correct index of the character to remove if it's not already correct.
    if (editing_buffer[offset] != to_delete) {
        for (int i = offset + 1; i < editing_buflen; ++i) {
            if (editing_buffer[i] == to_delete) {
                offset = i;
                found = 1;
                break;
            }
        }
    }

    // This is skipped if the character does not exist. Remove character by replacing it with the next charatcer
    // in the buffer. Ignore the termination character on the end, obviously.
    for (int i = offset; i < editing_buflen - 1; ++i)
        editing_buffer[i] = editing_buffer[i + 1];

    return found;
}

int editor_replace_str(char editing_buffer[], int editing_buflen,
    const char* str, const char* replacement, int offset)
{
    // Loop over both strings to get their length.
    int str_len = 0, rep_len = 0;
    while (str[str_len] != '\0' || replacement[rep_len] != '\0') {
        if (str[str_len] != '\0')
            str_len++;
        if (replacement[rep_len] != '\0')
            rep_len++;
    }
    int diff = rep_len - str_len;

    bool in_word = 0;

    // Find the offset.
    for (int i = offset, str_pos = 0; i < editing_buflen; ++i) {
        if (editing_buffer[i] == str[str_pos]) {
            if (!in_word) {
                offset = i;
                in_word = 1;
            }
            str_pos++;
            if (str[str_pos] == '\0')
                break;
        } else {
            offset = i;
            str_pos = 0;
            in_word = 0;
        }
    }

    if (!in_word)
        return -1;

    // Shift characters to the left.
    if (str_len > rep_len) {
        for (int i = offset + rep_len; i < editing_buflen; i++) {
            // Break if it's only termination characters left in the string.
            if (editing_buffer[i] == '\0' && ((i - diff) >= editing_buflen || editing_buffer[i - diff] == '\0'))
                break;

            // Fill in remaining termination characters if needed.
            if ((i - diff) >= editing_buflen) {
                editing_buffer[i] = '\0';
                continue;
            }

            // Bread and buffer.
            editing_buffer[i] = editing_buffer[i - diff];
        }
    }

    // Shift characters to the right.
    if (str_len < rep_len) {
        // Need to go backwards.
        for (int i = editing_buflen - (diff + 1); i > offset + rep_len; --i) {
            editing_buffer[i] = editing_buffer[i - diff];
        }
    }

    int i, j;
    // Replace the string.
    for (i = offset, j = 0; replacement[j] != '\0'; ++i, ++j) {
        editing_buffer[i] = replacement[j];
    }

    return i - 1;
}

void editor_view(int rows, int cols, char viewing_buffer[rows][cols],
    const char editing_buffer[], int editing_buflen, int wrap)
{
    // Clear the viewing buffer.
    for (int y = 0; y < rows; ++y) {
        for (int x = 0; x < cols; ++x) {
            viewing_buffer[y][x] = '\0';
        }
    }

    // Insert char at the current location.
    int pos = 0;
    for (int y = 0; y < rows && pos < editing_buflen - 1; ++y) {
        for (int x = 0; (!wrap || x < cols - 1) && pos < editing_buflen - 1; ++x, ++pos) {
            // If we find a newline, just break and continue on the line and skip the character insertion part.
            if (editing_buffer[pos] == '\n') {
                ++pos;
                break;
            }
            if (x >= cols - 1) continue;
            if (editing_buffer[pos] == '\n') return;

            viewing_buffer[y][x] = editing_buffer[pos];
        }
    }
}

// int main (int argc, char *argv[])
// {
//     char* editing_buffer = malloc(sizeof(char) * 21);
//     strcpy(editing_buffer, "The quick brown fox");
//
//     editor_replace_str(editing_buffer, 21, "brown", "blue", 0);
//
//     return 0;
// }
