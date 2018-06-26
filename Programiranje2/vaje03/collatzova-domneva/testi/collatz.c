#include <stdio.h>

#define N 1000000

int zaporedje[N];  // So nule ker je to staticna spremenljivka

int cola (long long n) {

	// Pogledam ce ga nemorem izracunati
	if (n > N - 1) {
		if (n % 2 == 0) {
			return cola(n / 2) + 1;
		} else {
			return cola(3 * n + 1) + 1;
		}
	} 
	// Pogledam ce ga morem se izracunati
	if (zaporedje[n] == 0) {
		if (n % 2 == 0) {
			zaporedje[n] = cola(n / 2) + 1;
		} else {
			zaporedje[n] = cola(3 * n + 1) + 1;
		}
		
	}
	// Vrnem izracunano
	return zaporedje[n];


}


int main () {
	int n;
	zaporedje[1] = 1;  // Nastavimo koncen pogoj ko se konca zaporedje
	int m = 0;
	long long ker = 0;
	scanf("%d", &n);
	for (int i = 1; i <= n; ++i) {
		long long t = cola(i);
		if (t > m){
			ker = i;
			m = t;
		}
	}
	printf("%d %d\n", ker, m);

	return 0;
}