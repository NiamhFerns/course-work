#include "editor.h"
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

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
            in_word = 1;
            if (str[str_pos + 1] == '\0')
                break;
        } else {
            offset = i;
        }
    }

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

    // Replace the string.
    for (int i = offset, j = 0; replacement[j] != '\0'; ++i, ++j) {
        editing_buffer[i] = replacement[j];
    }

    return 0;
}

void editor_view(int rows, int cols, char viewing_buffer[rows][cols],
    const char editing_buffer[], int editing_buflen, int wrap)
{
    printf("Unimplemented");
}
