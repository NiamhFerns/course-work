#include "dbms.h"
#include <stdio.h>
#include <stdlib.h>

/**
 * Function prototype for Task 1. See handout for details.
 */
int db_show_row(const struct db_table* db, unsigned int row)
{
    if (row > db->rows_used)
        return 0;

    struct album instance = db->table[row];

    printf("%6lu:%20.20s:%20.20s:%-4.4d\n", instance.id, instance.title, instance.artist, instance.year);
    return 1;
}

/**
 * Function prototype for Task 2. See handout for details.
 */
int db_add_row(struct db_table* db, struct album* a)
{
    if (db->rows_used >= db->rows_total) {
        db->table = (struct album*)realloc(db->table, sizeof(struct album) * (db->rows_total + 5));
        db->rows_total += 5;
    }


    db->table[db->rows_used] = *a;
    db->rows_used++;

    return 1;
}

/**
 * Function prototype for Task 3. See handout for details.
 */
int db_remove_row(struct db_table* db, unsigned long id)
{   
    int i;
    for (i = 0; i < db->rows_used; ++i) {
        if (db->table[i].id == id) break;
    }
    if (i >= db->rows_used) return 0;

    db->rows_used--;
    if (db->rows_used <= 0) {
        db->rows_total = 0;
        free(db->table);
        return 1;
    }

    for (int j = i; j < db->rows_used; j++) {
        db->table[j] = db->table[j + 1];
    }

    if (db->rows_total - db->rows_used >= 5) {
        db->rows_total -= 5;
        db->table = realloc(db->table, sizeof(struct album) * db->rows_total);
    }

    return 1;
}
