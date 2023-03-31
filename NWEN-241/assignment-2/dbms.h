/**
 * NWEN 241 Programming Assignment 2
 * dbms.h Header File
 * 
 * This header file contains the necessary type definitions
 * and function prototypes for Tasks 1-3. Include this in 
 * your implementation of dbms.c
 * 
 * Do not modify this file.
 */

#ifndef __DBMS_H__
#define __DBMS_H__

/**
 * album structure. See handout for details.
 */ 
struct album {
    unsigned long id;
    char title[100];
    unsigned short year;
    char artist[100];
};


/**
 * db_table structure. See handout for details.
 */   
struct db_table {
    struct album *table;
    unsigned int rows_total;
    unsigned int rows_used;
};


/**
 * Function prototype for Task 1. See handout for details.
 */ 
int db_show_row(const struct db_table *db, unsigned int row);

/**
 * Function prototype for Task 2. See handout for details.
 */ 
int db_add_row(struct db_table *db, struct album *a);

/**
 * Function prototype for Task 3. See handout for details.
 */ 
int db_remove_row(struct db_table *db, unsigned long id);

#endif /* __DBMS_H__ */
