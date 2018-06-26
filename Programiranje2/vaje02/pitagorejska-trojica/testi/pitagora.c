#include <stdio.h>
#include <math.h>
int main() {
	int a,b;
	scanf("%d %d", &a, &b);
	
	int je[b-a + 1];
	for (int i = 0; i < b-a + 1; ++i){
		je[i] = 0;
	}
	int c = 0;
	for (int i = 1; i <=b; ++i) {
		for (int j = i; j <=b; ++j) {
			int g = i*i + j*j;
			double t = sqrt(g);
			if ( (int)t == t && t >= a && t <=b && je[(int)t-a] == 0){	
				c++;
				je[(int)t-a] = 1;
			} 
		}
	}
	printf("%d\n", c);

	return 0;
}