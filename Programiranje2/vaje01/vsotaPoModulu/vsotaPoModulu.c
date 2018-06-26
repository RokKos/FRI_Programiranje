#include <stdio.h>

#define mod 10

int main () {
	int a, b;
	scanf("%d%d", &a, &b);
	printf("%d\n", (a + b) % mod);
	
	return 0;
}