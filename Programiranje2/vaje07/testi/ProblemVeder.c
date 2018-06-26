#include <stdio.h>
#include <stdlib.h>

int n,k;
int* vedra;
int* voda;

int min (int a, int b) {
	return a < b ? a : b;
}

void printSeznam (int* tab, int n) {
	for (int i = 0; i < n; ++i) {
		printf("%d ", tab[i]);
	}
	printf("\n");
}

int izracun (int koraki) {
	int c = 0;

	//printSeznam(voda, n);

	for (int i = 0; i < n; ++i) {
		if (voda[i] == k){
			return 1;
		}
	}

	if (koraki == 0) {
		return 0;
	}

	for (int i = 0; i < n; ++i) {
		if (voda[i] == vedra[i]){
			continue;
		}
		int temp = voda[i];
		voda[i] = vedra[i];
		c += izracun(koraki - 1);
		voda[i] = temp;
	}

	for (int i = 0; i < n; ++i) {
		if (voda[i] == 0){
			continue;
		}
		int temp = voda[i];
		voda[i] = 0;
		c += izracun(koraki - 1);
		voda[i] = temp;
	}

	for (int i = 0; i < n; ++i) {
		if (voda[i] == 0) {
			continue;
		}
		int temp1 = voda[i];
		for (int j = 0; j < n; ++j) {
			if (i == j || vedra[j] == voda[j]) {
				continue;
			}
			int prelij = min(voda[i], (vedra[j] - voda[j]));
			
			int temp2 = voda[j];
			voda[i] -= prelij;
			voda[j] += prelij;
			c += izracun(koraki - 1);
			
			voda[j] = temp2;
			voda[i] = temp1;
		}
		
	}

	return c;
}


int main () {
	scanf("%d", &n);
	vedra = (int*) malloc (sizeof(int) * n);
	voda = (int*) calloc (n, sizeof(int));  // nastavi vse na 0
	for (int i = 0; i < n; ++i) {
		scanf("%d", &vedra[i]);
	}	

	scanf("%d", &k);

	int poskusi = 1;
	while (1) {
		//printf("%d\n", poskusi);
		if (izracun(poskusi) > 0){
			printf("%d\n", poskusi);
			break;
		}
		poskusi++;
	}	

	return 0;
}