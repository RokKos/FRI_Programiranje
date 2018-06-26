#include <stdio.h>

int sestej(int a){
	int r = 0;
	while(a > 0){
		r+= a % 10;
		a /= 10;
	}
	return r;
}

int vsotaStevk (int v, int n) {
	if (n == 0) {
		return v;
	}

	return vsotaStevk(sestej(v), n-1);

}


int main() {
	int p, q, n;
	scanf("%d %d %d\n", &p, &q, &n);
	printf("%d\n", vsotaStevk(p*q, n));

}