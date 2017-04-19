/*
 * Name: Mohammad Mirabian
 * CruzID: mmirabia
 * Student ID# 1377020
 * File Name: Dictionary.c
 */

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<assert.h>
#include"Dictionary.h"

/********************* Private Functions **********************/
const int tableSize = 101;


typedef struct Node {
    char* key;
    char* value;
    struct Node* next;
} Node;
typedef Node* NodeRef;


NodeRef newNode(char* x, char* y) {
    NodeRef N = malloc(sizeof (Node));
    assert(N != NULL);
    N->key = x;
    N->value = y;
    N->next = NULL;
    return (N);
}



void freeNode(NodeRef* pt) {
    if (pt != NULL && *pt != NULL) {
        free(*pt);
        *pt = NULL;
    }
}

//creates the list data type

typedef struct List {
    NodeRef front;
    int size;
} List;
typedef List* ListRef;

//constructor for the node data type

ListRef newList(void) {
    ListRef L = malloc(sizeof (List));
    assert(L != NULL);
    L->front = NULL;
    L->size = 0;
    return L;
}

//free the heap memory from list

void freeList(ListRef* pL) {
    free(*pL);
    *pL = NULL;
}

//creates the Dictionary data type

typedef struct DictionaryObj {
    ListRef* table;
    int numItems;
} DictionaryObj;

// rotate_left()
// rotate the bits in an unsigned int

unsigned int rotate_left(unsigned int value, int shift) {
    int sizeInBits = 8 * sizeof (unsigned int);
    shift = shift & (sizeInBits - 1);
    if (shift == 0)
        return value;
    return (value << shift) | (value >> (sizeInBits - shift));
}
// pre_hash()
// turn a string into an unsigned int

unsigned int pre_hash(char* input) {
    unsigned int result = 0xBAE86554;
    while (*input) {
        result ^= *input++;
        result = rotate_left(result, 5);
    }
    return result;
}
// hash()
// turns a string into an int in the range 0 to tableSize - 1

int hash(char* key) {
    return pre_hash(key) % tableSize;
}

/********************** Public Funtions **********************/

// newDictionary()
// constructor for the Dictionary type

Dictionary newDictionary(void) {
    int i;
    Dictionary D = malloc(sizeof (DictionaryObj));
    assert(D != NULL);
    D->table = calloc(tableSize + 1, sizeof (ListRef));
    D->numItems = 0;
    for (i = 0; i < tableSize; i++) {
        D->table[i] = newList();
    }
    return D;
}

// freeDictionary()
// destructor for the Dictionary type

void freeDictionary(Dictionary* pD) {
    free(*pD);
    *pD = NULL;
}

// isEmpty()
// returns 1 (true) if S is empty, 0 (false) otherwise
// pre: none

int isEmpty(Dictionary D) {

    if (D->numItems == 0) {
        return (1);
    }
    return 0;
}

// size()
// returns the number of (key, value) pairs in D
// pre: none

int size(Dictionary D) {
    if (D == NULL) {
        fprintf(stderr, "Stack Error: calling size() on NULL DictionaryRef\n");
        exit(EXIT_FAILURE);
    }
    return (D->numItems);
}

// lookup()
// returns the value v such that (k, v) is in D, or returns NULL if no
// such value v exists.
// pre: none

char* lookup(Dictionary D, char* k) {
    NodeRef N = D->table[hash(k)]->front;
    while (N != NULL) {
        if (N->key == k) {
            return N->value;
        }
        N = N->next;
    }
    return NULL;
}

// insert()
// inserts new (key,value) pair into D
// pre: lookup(D, k)==NULL

void insert(Dictionary D, char* k, char* v) {
    int h = hash(k);
    NodeRef P = newNode(k, v);
    if (D == NULL) {
        fprintf(stderr, "Stack Error: calling insert() on NULL DictionaryRef\n");
        exit(EXIT_FAILURE);
    }
    if (lookup(D, k) != NULL) {
        fprintf(stderr, "Cannot insert() dublicate key\n");
        exit(EXIT_FAILURE);
    }
    if (D->table[h]->front == NULL) {
        D->table[h]->front = P;
    } else {
        P->next = D->table[h]->front;
        D->table[h]->front = P;
    }
    D->numItems++;
    D->table[h]->size++;
}

// delete()
// deletes pair with the key k
// pre: lookup(D, k)!=NULL

void delete(Dictionary D, char* k) {
    int h = hash(k);
    NodeRef N = D->table[h]->front;
    if (D == NULL) {
        fprintf(stderr, "Stack Error: calling delete() on NULL DictionaryRef\n");
        exit(EXIT_FAILURE);
    }
    if (lookup(D, k) == NULL) {
        fprintf(stderr, "Cannot delete() non-existant key\n");
        exit(EXIT_FAILURE);
    }
    if (N->key == k) {

        if (D->table[h]->front->next == NULL)
        {
            freeNode(&D->table[h]->front);
            D->numItems--;
        }
        else {
            N = D->table[h]->front;
            D->table[h]->front = D->table[h]->front->next;
            freeNode(&N);
            D->numItems--;
        }
    }

}

// makeEmpty()
// re-sets D to the empty state.
// pre: none

void makeEmpty(Dictionary D) {
    if (D == NULL) {
        fprintf(stderr, "Stack Error: calling makeEmpty() on NULL DictionaryRef\n");
        exit(EXIT_FAILURE);
    }
    int i;
    for (i = 0; i < tableSize; i++) {
        D->table[i]->size = 0;
        freeNode(&D->table[i]->front);
    }
    D->numItems = 0;

}

// printDictionary()
// pre: none
// prints a text representation of D to the file pointed to by out

void printDictionary(FILE* out, Dictionary D) {
    if (D == NULL) {
        fprintf(stderr, "Stack Error: calling printDictionary() on NULL DictionaryRef\n");
        exit(EXIT_FAILURE);
    }
    NodeRef N = NULL;
    int i;
    for (i = 0; i < tableSize; i++) {
        N = D->table[i]->front;
        while (N != NULL) {
            fprintf(out, "%s %s\n", N->key, N->value);
            N = N->next;
        }
    }
}
