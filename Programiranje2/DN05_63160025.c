#include <stdio.h>
#include <stdlib.h>

void printSeznam (unsigned int* tab, int n) {
	for (int i = 0; i < n; ++i) {
		printf("%u ", tab[i]);
	}
	printf("\n");
}

void swap (unsigned int* a, unsigned int* b) {
	int t = *a;
	*a = *b;
	*b = t;
}


void sodiBubleSort(unsigned int* tab, unsigned int* menjaj, int n) {
	for (int i = 0; i < n; ++i) {
		for (int j = 0; j < n - i - 1; ++j) {
			//printf("%u %u %d\n", tab[menjaj[j]], tab[menjaj[j + 1]], tab[menjaj[j]] < tab[menjaj[j + 1]]);
			if (tab[menjaj[j]] > tab[menjaj[j + 1]]) {
				//printf("here\n");
				swap (&tab[menjaj[j]], &tab[menjaj[j + 1]]);
			}
		}
	}
}


int main () {
	int n;
	scanf("%d", &n);

	unsigned int *tabela = (unsigned int*) malloc (sizeof(unsigned int) * n);
	unsigned int *nova_tabela = (unsigned int*) malloc (sizeof(unsigned int) * n);
	if (tabela == NULL || nova_tabela == NULL) {
		return 1;
	}

	int j = 0;
	for (int i = 0; i < n; ++i) {
		scanf("%u", &tabela[i]);
		if (tabela[i] % 2 == 0) {
			nova_tabela[j] = i;
			j++;
		}
	}

	//printSeznam(tabela, n);
	//printSeznam(nova_tabela, j);

	sodiBubleSort(tabela, nova_tabela, j);

	printSeznam(tabela, n);	

	return 0;
}