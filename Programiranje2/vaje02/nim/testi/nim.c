#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main () {
	int s, k, n, a;
	scanf("%d", &s);
	srand(s);
	scanf("%d %d", &k, &n);

	while(n > 0) {
		scanf("%d", &a);
		printf("%d - %d = %d\n", n, a, n-a);
		n-=a;
		if (n == 0) {
			printf("RACUNALNIK\n");
			return 0;
		}
		int t = rand() % k + 1;
		if (t > n){
			t = n;
		} 
		printf("%d - %d = %d\n", n, t, n-t);
		n-=t;
		if (n == 0) {
			printf("IGRALEC\n");
			return 0;
		}
	}


	return 0;
}