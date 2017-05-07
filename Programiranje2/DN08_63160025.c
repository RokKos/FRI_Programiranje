#include <stdio.h>
#include <stdlib.h>

const int meseci[12] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

typedef unsigned long long ull;  // Spremeni definicijo za krajse pisanje

typedef struct {
	int izposojena;
	int clan_id;
	int izvod_id;
	ull datum;
} vnos;

int c, k, p, d, z, v;
int stevilo_vnosov = 0;
int *knjige;
vnos *tabela;

void swap (vnos* a, vnos* b) {
	vnos t = *a;
	*a = *b;
	*b = t;
}


void print_sez_struct () {
	for (int i = 0; i < stevilo_vnosov; ++i) {
		printf("izposojena: %d clan: %d izvod: %d datum: %llu \n",
			tabela[i].izposojena, tabela[i].clan_id, tabela[i].izvod_id,
			tabela[i].datum);
	}

}

void buuble_sort (int po_cem_sortira) {

	//print_sez_struct();

	for (int i = 0; i < stevilo_vnosov; ++i){
		for (int j = 0; j < stevilo_vnosov - i - 1; ++j) {
			if (po_cem_sortira == 1 && tabela[j].clan_id > tabela[j+1].clan_id){
				swap(&tabela[j], &tabela[j + 1]);
			}

			if (po_cem_sortira == 2 && tabela[j].izvod_id > tabela[j+1].izvod_id){
				swap(&tabela[j], &tabela[j + 1]);
			}
		}

	}
 	//printf("----------------------------\n");
 	//print_sez_struct();
}


// Funkcija vrne ali si lahko uporabnik sposodi knjigo ali ne
int poskusi_sposoditi (int clan, int knjiga) {

	if (knjige[knjiga] <= 0) {
		return 0;
	}
	for (int i = 0; i < stevilo_vnosov; ++i) {
		if (tabela[i].clan_id == clan && tabela[i].izvod_id == knjiga
			&& tabela[i].izposojena == 1) {
			return 0;
		}
	}

	return 1;
}

int prestopno (int leto) {
	if ( (leto % 4 == 0) && (!(leto % 100 == 0) || (leto % 400 == 0))) {
		return 1;
	}
	return 0;
}

ull izracun_datuma (int dan, int mesec, int leto) {
	int c = dan;
	for (int i = 1901; i <= leto; ++i) {
		// prestopno leto
		if ( (i != leto || (i == leto && mesec > 2))  && prestopno(i) ) {
			c++;
		}
		int konec = i == leto ? mesec - 1 : 12;
		for (int j = 0; j < konec; ++j) {
			c += meseci[j];
		}
	}

	return c;

}

ull abs_ull (ull a, ull b) {
	return a > b ? a - b : b - a;
}

long long zamudnina (int clan, int knjiga, ull dan) {
	for (int i = 0; i < stevilo_vnosov; ++i) {
		if (tabela[i].clan_id == clan && tabela[i].izvod_id == knjiga
			&& tabela[i].izposojena == 1) {
			tabela[i].izposojena = 0;
			return abs_ull(dan, tabela[i].datum);
		}
	}

	return -1;  // To pomeni da ni nasel nobenega clana s tem izvodom
}

int poizvedba_clan (int clan) {
	int c = 0;
	for (int i = 0; i < stevilo_vnosov; ++i) {
		if (tabela[i].clan_id == clan && tabela[i].izposojena == 1) {
			c++;
		}
	}

	return c;
}

char* int_to_datum (ull dnevi) {
	int dan = 1, mesec = 1, leto = 1901;
	while (dnevi > meseci[mesec - 1]) {
		//printf("Dnevi ostali: %llu, mesec: %d, leto: %d \n", dnevi, mesec, leto);
		if (mesec == 2 && prestopno(leto)) {
			dnevi--;
		}

		dnevi -= meseci[mesec - 1];
		if (mesec == 12) {
			mesec = 0;
			leto++;
		}
		mesec++;
	}
	dan = dnevi;
	char *izpis_datuma = (char*) malloc (sizeof(char) * 10);
	sprintf(izpis_datuma, "%02d.%02d.%04d", dan, mesec, leto);
	return izpis_datuma;
}

void poizvedba_knjige_od_clana (int clan) {
	printf("/");
	buuble_sort(2);
	for (int i = 0; i < stevilo_vnosov; ++i) {
		if (tabela[i].clan_id == clan && tabela[i].izposojena == 1) {
			char *izpis_d = int_to_datum (tabela[i].datum + d);
			printf("%d:%s/", tabela[i].izvod_id + 1, izpis_d);
		}
	}
	printf("\n");
}

void poizvedba_clani_od_knjige (int knjiga) {
	printf("/");
	buuble_sort(1);
	for (int i = 0; i < stevilo_vnosov; ++i) {
		if (tabela[i].izvod_id == knjiga && tabela[i].izposojena == 1) {
			char *izpis_d = int_to_datum (tabela[i].datum + d);
			printf("%d:%s/", tabela[i].clan_id, izpis_d);
		}
	}
	printf("\n");
}


int main () {
	scanf("%d %d %d %d %d %d\n", &c, &k, &p, &d, &z, &v);
	// Nastavimo knjige
	knjige = (int*) malloc (sizeof(int) * k);
	for (int i = 0; i < k; ++i) {
		knjige[i] = p;
	}
	if (knjige == NULL) {
		return 1;
	}

	// Nastavimo vnose
	tabela = (vnos*) malloc (sizeof(vnos) * v);
	if (tabela == NULL) {
		return 2;
	}
	for (int i = 0; i < v; ++i) {
		char chr;
		int dan, mesec, leto, clan, knjiga;
		ull datum;
		scanf("%c", &chr);
		//printf("%c\n", chr);
		switch (chr) {
			case '+':
				scanf(" %d.%d.%d %d %d\n", &dan, &mesec, &leto, &clan, &knjiga);
				knjiga--;
				//printf("%d %d %d\n", dan, mesec, leto);
				datum = izracun_datuma(dan, mesec, leto);
				if (poskusi_sposoditi(clan, knjiga)) {
					tabela[stevilo_vnosov].izposojena = 1;
					tabela[stevilo_vnosov].clan_id = clan;
					tabela[stevilo_vnosov].izvod_id = knjiga;
					tabela[stevilo_vnosov].datum = datum;
					knjige[knjiga]--;
					stevilo_vnosov++;
					printf("D\n");
				} else {
					printf("N\n");
				}

				break;

			case '-':
				scanf(" %d.%d.%d %d %d\n", &dan, &mesec, &leto, &clan,  &knjiga);
				knjiga--;
				//printf("%d %d %d %d %d\n", dan, mesec, leto, clan,  knjiga);
				datum = izracun_datuma(dan, mesec, leto);
				long long racun = zamudnina(clan, knjiga, datum);
				if (racun == -1) {
					printf("N\n");
				} else {
					knjige[knjiga]++;
					// Ce je bilo vec dni kot dovoljeno potem vzami samo une ki
					// so prevec, drugace pa ni zamudnine
					racun = racun > d ? (racun - d) : 0;
					printf("%lld\n", racun * z);
				}
				break;

			case 'A':
				scanf("%d\n", &clan);
				printf("%d\n", poizvedba_clan(clan));
				break;

			case 'B':
				scanf("%d\n", &knjiga);
				knjiga--;
				printf("%d\n", p - knjige[knjiga]);
				break;

			case 'C':
				scanf("%d\n", &clan);
				poizvedba_knjige_od_clana(clan);
				break;

			case 'D':
				scanf("%d\n", &knjiga);
				poizvedba_clani_od_knjige(knjiga);
				break;
		}

	}

	free(knjige);
	free(tabela);
	return 0;
}