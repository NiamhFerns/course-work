/**
 * t1test.c
 * Sample test program for Task 1. 
 *
 * To compile with your implementation:
 * gcc t1test.c dbms.c -o t1test
 *
 * If successful, executable file t1test should have been
 * created.
 */

#include <stdio.h>

#include "dbms.h"

/**
 * For this test, we will just supply the function
 * db_show_row() with a static array. It should work
 * since db_show_row() is not meant to modify the 
 * array.
 */ 
struct album defaultAlbums[5] = {
    { 10, "The Dark Side of the Moon", 1973, "Pink Floyd" },
    { 14, "Back in Black", 1980, "AC/DC" },
    { 23, "Their Greatest Hits", 1976, "Eagles" }
};

/** 
 * Format of the expected outputs
 */
char outputFormats[][80] = {
    "    10:The Dark Side of the:          Pink Floyd:1973",
    "    14:       Back in Black:               AC/DC:1980",
    "    23: Their Greatest Hits:              Eagles:1976"
};

int main(void)
{
    struct db_table db;
    db.table = defaultAlbums;
    db.rows_used = 3;
    db.rows_total = 5;
    
    printf("Invoking db_show_row(&db, 3)...\n");
    printf("Expected output: \n");
    printf("Actual output  : ");
    int r = db_show_row(&db, 4);
    printf("\nExpected return value: 0\n");
    printf("Actual return value  : %d\n", r);

    for(int i=0; i<3; i++) {
        printf("Invoking db_show_row(&db, %d)...\n", i);
        printf("Expected output: %s\n", outputFormats[i]);
        printf("Actual output  : ");        
        r = db_show_row(&db, i);
        printf("Expected return value: 1\n");
        printf("Actual return value  : %d\n", r);
    }
    
    return 0;
}
