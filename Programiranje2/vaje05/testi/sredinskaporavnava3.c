#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

void izpis (char * besedilo, int poravnava) {

	// Izbrisem zadnji presledek ki je nepotreben
	besedilo[strlen(besedilo) - 1] = '\0';
	int zamik = (poravnava - strlen(besedilo)) / 2;

	//printf("%d\n", zamik);
	
	// Oklepaji na zacetku
	for (int i = 0; i < zamik; ++i) {
		printf(" ");
	}

	printf("%s\n", besedilo);
}

int main () {
	const int MAX = 100000;

	int poravnava;
	scanf("%d", &poravnava);
	char *temp = (char*)malloc(sizeof(char) * MAX);
	char *out = (char*)malloc(sizeof(char) * MAX);
	while(scanf("%s", temp) != EOF) {
		//printf("%s\n", out);
		if (strlen(out) + strlen(temp)> poravnava){
			
			izpis(out, poravnava);

			free(out);
			char *out = (char*)malloc(sizeof(char) * MAX);
			// Nastavim out na prazen niz
			out[0] = '\0';
		}
		
		strcat(out, temp);
		strcat(out, " ");
		
	}

	izpis(out, poravnava);

	return 0;
}