/**
 * t2test.c
 * Sample test program for Task 2
 * The test program assumes that you have already implemented 
 * the db_show_row() function.
 *
 * To compile with your implementation:
 * gcc t2test.c dbms.c -o t2test
 *
 * If successful, executable file t2test should have been
 * created.
 */

#include <stdio.h>

#include "dbms.h"

/**
 * First set of 3 albums that we will add into the db.
 */ 
struct album albums1[] = {
    { 10, "The Dark Side of the Moon", 1973, "Pink Floyd" },
    { 14, "Back in Black", 1980, "AC/DC" },
    { 23, "Their Greatest Hits", 1976, "Eagles" }
};

/**
 * Second set of albums that we will add into the db.
 */ 
struct album albums2[] = {
    { 37, "Falling into You", 1996, "Celine Dion" },
    { 43, "Come Away With Me", 2002, "Norah Jones" },
    { 55, "21", 2001, "Adele" }
};

/** 
 * Format of the expected outputs for set 1
 */
char outputs1[][80] = {
    "    10:The Dark Side of the:          Pink Floyd:1973",
    "    14:       Back in Black:               AC/DC:1980",
    "    23: Their Greatest Hits:              Eagles:1976"
};

/** 
 * Format of the expected outputs for set 2
 */
char outputs2[][80] = {
    "    37:    Falling into You:         Celine Dion:1996",
    "    43:   Come Away With Me:         Norah Jones:2002",
    "    55:                  21:               Adele:2001"
};


int main(void)
{
    /**
     * This is the actual db table
     * We initialize the members accordingly.
     */ 
    struct db_table db = {rows_total: 0, rows_used: 0, table: NULL};
       
    printf("Important: The test program assumes that you have already implemented the db_show_row() function.\n");
    
    printf("Adding 3 entries...\n");
    for(int i=0; i<sizeof(albums1)/sizeof(struct album); i++)
        db_add_row(&db, albums1+i);
    printf("Expected      : rows_used = 3, rows_total = 5\n");
    printf("From db_table : rows_used = %d, rows_total = %d\n", db.rows_used, db.rows_total);
    for(int i=0; i<3; i++) {
        printf("Invoking db_show_row(&db, %d)...\n", i);
        printf("Expected output: %s\n", outputs1[i]);
        printf("Actual output  : ");                
        int r = db_show_row(&db, i);
        printf("Expected return value: 1\n");
        printf("Actual return value  : %d\n", r);
    }

    printf("Adding 3 more entries...\n");
    for(int i=0; i<sizeof(albums2)/sizeof(struct album); i++)
        db_add_row(&db, albums2+i);
    printf("Expected      : rows_used = 6, rows_total = 10\n");
    printf("From db_table : rows_used = %d, rows_total = %d\n", db.rows_used, db.rows_total);
    for(int i=3; i<6; i++) {
        printf("Invoking db_show_row(&db, %d)...\n", i);
        printf("Expected output: %s\n", outputs2[i-3]);
        printf("Actual output  : ");        
        int r = db_show_row(&db, i);
        printf("Expected return value: 1\n");
        printf("Actual return value  : %d\n", r);
    }  

    return 0;
}
