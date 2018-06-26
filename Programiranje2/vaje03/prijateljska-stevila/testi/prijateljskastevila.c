#include <stdio.h>

int vsotaDeliteljev (int a) {
	int s = 0;
	for (int i = 1; i <= a / 2; ++i) {
		if (a % i == 0) {
			s += i;
		}
	}

	return s;

}

int main () {
	int n;
	scanf("%d", &n);
	int prijatelj = vsotaDeliteljev(n);
	if (n == vsotaDeliteljev(prijatelj)) {
		printf("%d\n", prijatelj);	
	} else {
		printf("NIMA\n");
	}

	

	return 0;
}