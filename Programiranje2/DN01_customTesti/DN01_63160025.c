# include <stdio.h>
# include <math.h>

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

long long dobiPrvihNMest (long long *stevilka, int mesta) {
	
	int dolzina = log10(*stevilka) + 1;
	//printf("%d dol\n", dolzina);
	long long desetMesta = potenca(10, dolzina - mesta);
	long long ostanek = *stevilka % desetMesta;
	long long vrni = *stevilka / desetMesta;
	//printf("Mesta: %lld ostanek: %lld vrni: %lld\n", desetMesta, ostanek, vrni);
	*stevilka = ostanek;
	return vrni;
}

int main () {
	long long osnova, mesta;
	//printf("%lld\n", potenca(10, 2));
	scanf("%lld%lld", &osnova, &mesta);
	//printf("%lld | %lld\n", osnova, mesta);

	while (mesta > 0) {
		int kolikoIzpisi = dobiPrvihNMest(&mesta, 1);
		printf("%lld %d m\n", mesta, kolikoIzpisi);
		printf("%lld\n", dobiPrvihNMest(&osnova, kolikoIzpisi)); 
		
	}

	return 0;
}