#ifndef _KOPICA_H_
#define _KOPICA_H_

// System include
#include <stdio.h>
#include <stdlib.h>

typedef struct _node {
	int value;
	struct _node* left;
	struct _node* right;
} node;

node* zdruzi (node* a, node* b);
node* dodaj (node* kopica, int value);
node* odvzemi (node* a);
void izpisi (node* a);

#endif // _KOPICA_H_