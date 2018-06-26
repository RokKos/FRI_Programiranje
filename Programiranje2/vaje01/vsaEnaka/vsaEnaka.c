#include <stdio.h>

#define mod 10

int main () {
	int n, a, t;
	int flag = 1;

	scanf("%d", &n);
	scanf("%d", &t);
	for (int i = 0; i < n - 1; ++i){
		scanf("%d", &a);
		if (t != a) {
			flag = 0;
		}
	}
	printf("%d\n", flag);
	
	return 0;
}