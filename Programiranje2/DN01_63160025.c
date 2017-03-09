# include <stdio.h>
# include <math.h>

int vrniDolzino (long long st) {
	int d = 0;
	while (st > 0) {
		st /= 10;
		d++;
	}
	return d;
}

long long potenca (long long p, long long k) {
	long long rezultat = 1;
	while (k > 0){
		// Ce je potenca liha potem jo zmanjsaj za ena in pomnozi rezultat
		// Dobi 1 prvi bit in ga primerja z 1 kar pomenice je je liha 
		if ((k >> 0) & 1) {
			k--;
			rezultat *= p;
		}
		// Pomnozi osnovo z sabo in zmanjas potenco za eno
		// Princip kvadratnega ekposniranja
		// 3 ^ 10 = 3 * 3 * 3 * 3 * 3 * 3 * 3 * 3 * 3 * 3
		// 3 ^ 10 = (3 * 3) * (3 * 3) * (3 * 3) * (3 * 3) * (3 * 3)
		// 3 ^ 10 = ((3 * 3) ^ 5)
		// 3 ^ 10 = 9 ^ 5
		// 
		// in tako dalje
		p *= p;
		k /= 2;
	}
	return rezultat;
}

long long dobiPrvihNMest (long long stevilka, int mesta, int kje) {
	
	int dolzina = vrniDolzino(stevilka);
	// Koliko mest
	long long desetMesta = potenca(10, dolzina - mesta - kje);
	// Od kje zacnemo
	long long desetKje = potenca(10, dolzina - kje);
	// Najprej poreze do prave velikosti v desno potem pa se v levo
	long long vrni = (stevilka % desetKje) / desetMesta;
	return vrni;
}

int main () {
	long long osnova, mesta;
	int kjeMesta = 0, kjeOsnova = 0;
	
	scanf("%lld%lld", &osnova, &mesta);
	while (kjeMesta < vrniDolzino(mesta)) {
		int kolikoIzpisi = dobiPrvihNMest(mesta, 1, kjeMesta);
	
		printf("%lld\n", dobiPrvihNMest(osnova, kolikoIzpisi, kjeOsnova)); 
		kjeOsnova += kolikoIzpisi;
		kjeMesta++;
	}

	return 0;
}