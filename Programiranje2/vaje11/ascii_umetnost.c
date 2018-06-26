#include <stdio.h>
#include <stdlib.h>

typedef unsigned char uc;
typedef unsigned int ui;

int n;

uc slika[1000][1000][3];

ui izracun (int x, int y) {
	int S = 0;
	int delitelj = n * n * 3;
	for (int i = 0; i < n; ++i) {
		for (int j = 0; j < n; ++j) {
			for (int k = 0; k < 3; ++k) {
				S += slika[x + i][y + j][k];
			}
		}
	}
	//printf("%d %d\n", S, delitelj);
	return S / delitelj;
}

char pretvori (ui vrednost) {
	if (vrednost >= 230) {
		return ' ';
	}
	if (vrednost < 230 && vrednost >= 200) {
		return '.';
	}
	if (vrednost < 200 && vrednost >= 180) {
		return '\'';
	}
	if (vrednost < 180 && vrednost >= 160) {
		return ':';
	}
	if (vrednost < 160 && vrednost >= 130) {
		return 'o';
	}
	if (vrednost < 130 && vrednost >= 100) {
		return '&';
	}
	if (vrednost < 100 && vrednost >= 70) {
		return '8';
	}
	if (vrednost < 70 && vrednost >= 50) {
		return '#';
	}
	return '@';

}

int main () {
	char* vhod = (char*) malloc (sizeof(char) * 21);
	char* izhod = (char*) malloc (sizeof(char) * 21);
	scanf ("%s\n", vhod);
	scanf ("%s\n", izhod);
	scanf("%d\n", &n);
	FILE* f = fopen(vhod, "r");
	if (f == NULL) {
		exit(1);
	}

	int sirina, visina;
	fscanf(f, "P6\n%d %d\n255\n", &sirina, &visina);
	//printf("%d %d %d\n", n,sirina,visina);
	for (int i = 0; i < visina; ++i) {
		for (int j = 0; j < sirina; ++j) {
			for (int k = 0; k < 3; ++k) {
				uc barva;
				fscanf(f, "%c", &barva);
				slika[i][j][k] = barva;
			}
		}
	}

	FILE* f_iz = fopen(izhod, "w");
	if (f_iz == NULL) {
		exit(2);
	}

	for (int i = 0; i < visina; i+=n) {
		for (int j = 0; j < sirina; j+=n){
			ui t = izracun(i,j);
			//printf("%d\n", t);
			char rez = pretvori(t);
			fprintf(f_iz, "%c", rez);
			//printf("%c", rez);
		}
		fprintf(f_iz,"\n");
		//printf("\n");
	}

	fclose(f);
	fclose(f_iz);

	return 0;
}