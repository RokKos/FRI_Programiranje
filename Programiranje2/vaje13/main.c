// System imports
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
// Custom imports
#include "kopica.h"

int main () {
	int n = 0;
	scanf("%d\n", &n);
	node* root = NULL;//(node*) calloc (1, sizeof(node));
	for (int i = 0; i < n; ++i) {
		char* ukaz = (char*) malloc (sizeof(char) * 10);
		scanf("%s", ukaz);
		if (strcmp(ukaz, "izpisi") == 0) {
			izpisi(root);
			printf("\n");
		} else if (strcmp(ukaz, "dodaj") == 0) {
			int value;
			scanf("%d\n", &value);
			root = dodaj(root, value);
		} else if (strcmp(ukaz, "odvzemi") == 0) {
			root = odvzemi(root);
		}
	}
	return 0;
}
