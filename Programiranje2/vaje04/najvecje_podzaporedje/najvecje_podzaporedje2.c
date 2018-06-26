#include <stdio.h>

#define MAX 1000000
#define RANGE 1000000000

int main () {
	int n, a;
	scanf("%d", &n);
	int max_ind = 0, min_len = n + 1, zacetek = 0;
	long long max_val = (-1) * RANGE;
	long long val = 0;
	for (int i = 0; i < n; ++i) {

		scanf("%d", &a);
		val += a;
		if (val > max_val || (val == max_val && max_ind > zacetek) 
				|| (val == max_val && max_ind == i && min_len > i - zacetek + 1)) {
			max_val = val;
			max_ind = zacetek;
			min_len = i - zacetek + 1;

		}
		//printf("%d %d %d\n", zacetek, val, i);
		if (val < 0) {
			zacetek = i + 1;
			val = 0;
		}
		//printf("%d %d %d\n", zacetek, val, i);

	}

	printf("%d %d %lld\n",  max_ind, min_len, max_val);
	return 0;
}