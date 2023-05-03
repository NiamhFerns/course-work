#include <stdio.h>
#include <stdlib.h>

struct node {
    int data;
    struct node * next;
};

int main() {
    struct node *head = NULL;
    struct node *tail;
    
    head = tail = (struct node *)malloc(sizeof(struct node));
    tail->data = 100;

    for (int i = 1; i < 10; i++) {
        tail->next = (struct node *)malloc(sizeof(struct node));
        tail = tail->next;
        tail->data = 100 * (i + 1);
    }

    struct node * rover = head;
    while(rover) {
        printf("%d ", rover->data);
        rover = rover->next;
    }
    printf("\n");

    return 0;
}
