#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int max (int a, int b) {
	return a > b ? a : b;
}

int dobi_dolzino (int n) {
	return log10(n) + 1;
}

int power (int a, int n) {
	int s = 1;
	for (int i = 0; i < n; ++i) {
		s *= a;
	}
	return s;
}

int cifra (int n, int mesto, int dolzina) {
	return (n % (power(10, mesto))) / power(10, mesto -1);
}

int main () {
	int a, b;
	scanf("%d %d", &a, &b);
	int d_a = dobi_dolzino(a);
	int d_b = dobi_dolzino(b);
	//printf("%d\n", d_a);
	//printf("%d\n", d_b);
	int* st = (int*) calloc (10, sizeof(int));
	for (int i = 1; i < d_a + 1; ++i) {
		for (int j = 1; j < d_b + 1; ++j) {
			int c_a = cifra(a, i, d_a);
			int c_b = cifra(b, j, d_b);
			//printf("%d %d %d %d\n", c_a, c_b, i, j);
			st[c_a] = 1;
			st[c_b] = 1;
		}
	}
	int c = 0;
	for (int i = 0; i < 10; ++i) {
		//printf("%d\n", st[i]);
		c += st[i];
	}
	printf("%d\n", c);

	free(st);
	return 0;
}