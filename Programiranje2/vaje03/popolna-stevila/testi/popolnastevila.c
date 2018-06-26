#include <stdio.h>
#include <math.h>

int jePopolno (int a) {
	int s = 0;
	for (int i = 2; i < sqrt(a); ++i) {
		if (a % i == 0) {
			s += i;
			s += a / i;
		}
	}

	return (s + 1) == a;  // ker smo enko spustili

}

int main () {
	int n;
	scanf("%d", &n);
	for (int i = 2; i <= n; ++i) {
		if (jePopolno(i)) {
			printf("%d ", i);
		}
	}

	printf("\n");

	return 0;
}