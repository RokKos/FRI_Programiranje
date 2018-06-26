#include "kopica.h"

node* zdruzi (node* a, node* b) {
	if (a == NULL) {
		return b;
	}
	if (b == NULL) {
		return a;
	}

	if (a->value > b->value) {
		return zdruzi(b, a);
	}

	node* la = a->left;
	node* ra = a->right;
	a->right = la;
	a->left = zdruzi(ra, b);
	return a;
}

node* dodaj(node* kopica, int value) {
	node* new = (node*) calloc (1, sizeof(node));  // Nastavi left in rigth na NULL
	new->value = value;
	kopica = zdruzi(kopica, new);
	return kopica;
}

node* odvzemi(node* kopica) {
	if (kopica == NULL) {
		return kopica;
	}
	node* a = kopica->left;
	node* b = kopica->right;
	kopica = zdruzi (a, b);
	return kopica;
}

void izpisi (node* a) {
	if (a == NULL) {
		printf("/");
		return;
	}
	printf("%d[", a->value);
	izpisi(a->left);
	printf(", ");
	izpisi(a->right);
	printf("]");
}