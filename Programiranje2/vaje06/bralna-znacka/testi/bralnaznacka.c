#include <stdio.h>
#include <stdlib.h>

#define MAX 1000

void swapBeseda (char** tabela, int j) {
	char* t = tabela[j];
	tabela[j] = tabela[j+1];
	tabela[j+1] = t;
}

void swapStevilka (int* a, int* b) {
	int t = *a;
	*a = *b;
	*b = t;
}

void buuble_sort (char** tabela, int* knjige, int n) {
	for (int i = 0; i < n; ++i){
		for (int j = 0; j < n - i - 1; ++j) {
			if (knjige[j] < knjige[j + 1]){ //|| (knjige[j] == knjige[j + 1] && )) {
				swapBeseda(tabela, j); 
				swapStevilka(&knjige[j], &knjige[j + 1]);
			}
		}

	}
}

int main () {
	int n,k;
	scanf("%d %d", &n, &k);
	
	char** tekmovalci = (char**) malloc (sizeof(char*) * n);
	if (tekmovalci == NULL) {
		return 1;
	}
	int* knjige = (int*) malloc (sizeof(int) * n);
	if (knjige == NULL) {
		return 2;
	}

	for (int i = 0; i < n; ++i) {
		tekmovalci[i] = (char*) malloc(sizeof(char) * 17);
		scanf("%s", tekmovalci[i]);	
	}

	for (int i = 0; i < n; ++i) {
		scanf("%d", &knjige[i]);	
	}

	buuble_sort(tekmovalci, knjige, n);

	//for (int i = 0; i < n; ++i) {
	//	printf("%d. %s (%d)\n", i, tekmovalci[i], knjige[i]);
	//}
	//printf("-----------------------\n");
	for (int i = 0; i < k; ++i) {
		printf("%d. %s (%d)\n", i + 1, tekmovalci[i], knjige[i]);
	}


	return 0;
}