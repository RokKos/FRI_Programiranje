#include <stdio.h>

void zamenjaj (int *a, int *b) {
	int t = *a;
	*a = *b;
	*b = t;
}


int main () {
	int n, prva, druga, t;

	scanf("%d", &n);
	scanf("%d", &prva);
	scanf("%d", &druga);
	if (prva < druga) {
		zamenjaj(&prva, &druga);
	}

	//printf("%d %d\n", prva, druga);
	for (int i = 0; i < n - 2; ++i){
		scanf("%d", &t);
		if (t >= prva) {
			zamenjaj(&prva, &druga);
			zamenjaj(&t, &prva);
		}

		if (t > druga) {
			zamenjaj(&t, &druga);
		}
	}
	printf("%d\n", druga);
	
	return 0;
}