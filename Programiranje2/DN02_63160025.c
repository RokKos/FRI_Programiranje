#include <stdio.h>

int main () {
	int moznost, n;
	scanf("%d%d", &moznost, &n);
	if (n > 0) {
		if (moznost == 1) {
			int prej;
			int c = 1;
			scanf("%d", &prej);
			for (int i = 0; i < n - 1; ++i) {
				int t;
				scanf("%d", &t);
				if (t != prej) {
					printf("%d %d ", c, prej);
					prej = t;
					c = 1;
				} else {
					c++;
				}
			}
			printf("%d %d\n", c, prej);
		} else {
			for (int i = 0; i < n; ++i) {
				int kaj, koliko;
				scanf("%d%d", &koliko, &kaj);
				for (int j = 0; j < koliko; ++j) {
					printf("%d ", kaj);
				}
			}
			printf("\n");
		}
	} else {
		printf("\n");
	}
	


	return 0;
}