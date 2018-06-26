#include <stdio.h>
#include <stdlib.h>

int** polje;

void izpisi_polje() {
	for (int i = 0; i < 300; ++i) {
		for (int j = 0; j < 300; ++j) {
			printf("%d", polje[i][j]);
		}
		printf("\n");
	}
}

void napolni(int x, int y, int s) {
	for (int i = 0; i < s; ++i) {
		for (int j = 0; j < s; ++j) {
			if (x + i < 300 && y + j < 300) {
				polje[x + i][y + j] += 1;
			}

		}
	}
}

int main () {
	int n, x, y, s;
	scanf("%d\n", &n);
	polje = (int**) malloc (302 * sizeof(int*));
	for (int i = 0; i < 300; ++i) {
		polje[i] = (int*) calloc(301, sizeof(int));
	}
	for (int i = 0; i < n; ++i) {
		scanf("%d %d %d\n", &x, &y, &s);
		napolni(x + 100, y + 100, s);
		//izpisi_polje();
		//printf("%d %d %d\n", x,y,s);
		//printf("########################################\n");

	}

	//izpisi_polje();

	int* rez = (int*) calloc (n, sizeof(int));
	for (int i = 0; i < 300; ++i) {
		for (int j = 0; j < 300; ++j) {
			if (polje[i][j] > 0) {
				rez[polje[i][j] - 1] += 1;
			}

		}
	}

	for (int i = 0; i < n; ++i) {
		printf("%d\n", rez[i]);
	}

	free(polje);
	return 0;
}