/**
 * t3test.c
 * Sample test program for Task 3
 * The test program assumes that you have already implemented 
 * the db_show_row() and db_add_row() functions.
 *
 * To compile with your implementation:
 * gcc t3test.c dbms.c -o t3test
 *
 * If successful, executable file t3test should have been
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
 * Format of the expected outputs after db_remove_row() calls
 */
char outputs[][80] = {
    "    10:The Dark Side of the:          Pink Floyd:1973",
    "    23: Their Greatest Hits:              Eagles:1976",
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
       
    printf("Important: The test program assumes that you have already implemented the db_show_row() and db_add_row() functions.\n");
    
    printf("Adding 6 entries...\n");
    for(int i=0; i<sizeof(albums1)/sizeof(struct album); i++)
        db_add_row(&db, albums1+i);
    for(int i=0; i<sizeof(albums2)/sizeof(struct album); i++)
        db_add_row(&db, albums2+i);

    printf("Removing entry with id=100 [not in table]...\n");
    int r = db_remove_row(&db, 100);
    printf("Expected return value: 0\n");
    printf("Actual return value  : %d\n", r);   
    printf("Expected      : rows_used = 6, rows_total = 10\n");
    printf("From db_table : rows_used = %d, rows_total = %d\n", db.rows_used, db.rows_total);    

    printf("Removing entry with id=37...\n");
    r = db_remove_row(&db, 37);
    printf("Expected return value: 1\n");
    printf("Actual return value  : %d\n", r);   
    printf("Expected      : rows_used = 5, rows_total = 5\n");
    printf("From db_table : rows_used = %d, rows_total = %d\n", db.rows_used, db.rows_total);  
    
    printf("Removing entry with id=14...\n");
    r = db_remove_row(&db, 14);
    printf("Expected return value: 1\n");
    printf("Actual return value  : %d\n", r);   
    printf("Expected      : rows_used = 4, rows_total = 5\n");
    printf("From db_table : rows_used = %d, rows_total = %d\n", db.rows_used, db.rows_total);  

    for(int i=0; i<4; i++) {
        printf("Invoking db_show_row(&db, %d)...\n", i);
        printf("Expected output: %s\n", outputs[i]);
        printf("Actual output  : ");        
        r = db_show_row(&db, i);
        printf("Expected return value: 1\n");
        printf("Actual return value  : %d\n", r);
    }
    
    return 0;
}
