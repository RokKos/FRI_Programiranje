#include <stdio.h>
#include <stdlib.h>

int* stevila;

int max (int a, int b) {
	return a > b ? a : b;
}

int zaporedje(int st) {
	int c = 0;
	for (int i = 1; i < st; ++i) {
		if (st % i == 0) {
			c = max(c, zaporedje(i));
		}
	}

	return c + stevila[st];
}

int main () {
	int n;
	scanf("%d", &n);
	stevila = (int*) calloc (2002, sizeof(int));
	for (int i = 0; i < n; ++i) {
		int st = 0;
		scanf("%d ", &st);
		stevila[st]++;
	}

	int m = 0;
	for (int i = 0; i < 2002; ++i) {
		if (stevila[i] > 0) {
			int z = zaporedje(i);
			m = max(m, z);
			//printf("%d %d %d\n", m, z, i);
		}
	}

	printf("%d\n", m);

	free(stevila);
	return 0;
}