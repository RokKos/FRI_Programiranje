#include <stdio.h>
#include <stdlib.h>

typedef unsigned long long ull;

int tabela[9 * 10][9 * 10];
int u,n,m;

ull from_tabela_to_number (int x, int y) {
	ull sum = 0;
	ull power_of_2 = 1;
	for (int k = 7; k >= 0; --k) {
		for (int l = 7; l >= 0; --l) {
			if (tabela[x * 8 + k][y * 8 + l] == 1) {
				sum += power_of_2;
			}
			power_of_2 *= 2;
		}
	}

	return sum;
}

void print_tabela () {
	for (int i = 0; i < 8 * m; ++i) {
		for (int j = 0; j < 8 * n; ++j) {
			printf("%d", tabela[i][j]);
		}
		printf("\n");
	}
}

void number_to_tabela (ull desetisko, int x, int y) {
	for (int k = 7; k >= 0; --k) {
		for (int l = 7; l >= 0; --l) {
			int c = desetisko % 2;
			desetisko /= 2;
			tabela[x * 8 + k][y * 8 + l] = c;
		}
	}
}


int main () {
	char* vhod = (char*) malloc (sizeof(char) * 11);
	char* izhod = (char*) malloc (sizeof(char) * 15);
	scanf ("%d\n", &u);
	scanf ("%d %d\n", &m, &n);
	scanf ("%s\n", vhod);
	scanf ("%s\n", izhod);
	//int tabela[8 * m][8 * n];

	FILE* vhodna_datoteka = fopen(vhod, "r");
	FILE* izhodna_datoteka = fopen(izhod, "w");

	if (u == 1) {
		for (int i = 0; i < m; ++i) {
			for (int k = 0; k < 8; ++k) {
				for (int j = 0; j < n; ++j) {
					for (int l = 0; l < 8; ++l) {
						char znak;
						fscanf(vhodna_datoteka, "%c", &znak);
						//printf("%c %d %d", znak, i * 8 + k, j * 8 + l);
						if (znak == ' ') {
							tabela[i * 8 + k][j * 8 + l] = 0;
						} else if (znak == '*') {
							tabela[i * 8 + k][j * 8 + l] = 1;
						}
					}
				}
				char t;
				fscanf(vhodna_datoteka, "%c", &t);
				//printf("\n");
			}
		}

		for (int i = 0; i < m; ++i) {
			for (int j = 0; j < n; ++j) {
				ull rez = from_tabela_to_number(i,j);
				//printf("%llu ", rez);
				fprintf(izhodna_datoteka, "%llu ", rez);
			}
			//printf("\n");
			fprintf(izhodna_datoteka, "\n");
		}

	} else {
		for (int i = 0; i < m; ++i) {
			for (int j = 0; j < n; ++j) {
				ull desetisko;
				fscanf(vhodna_datoteka, "%llu", &desetisko);
				//printf("%llu\n", desetisko);
				number_to_tabela(desetisko, i, j);
			}

		}

		for (int i = 0; i < 8 * m; ++i) {
			for (int j = 0; j < 8 * n; ++j) {
				if (tabela[i][j] == 1) {
					fprintf(izhodna_datoteka, "*");
				} else if (tabela[i][j] == 0) {
					fprintf(izhodna_datoteka, " ");
				}

			}
			fprintf(izhodna_datoteka, "\n");
		}
	}
	//printf("\n");
	//print_tabela(m, n);


	return 0;
}