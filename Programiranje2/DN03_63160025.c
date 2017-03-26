#include <stdio.h>
#include <stdlib.h>

#define MAX 1001
#define STEVILO_SMERI 4

typedef struct {
	int x;
	int y;
} cord;


typedef struct {
	cord c;
	int id;
} predmet;

// Definirani vse stvari, ki jih bom rabil
cord kaca[MAX];
predmet vsi[MAX];

// Kaca x, Kaca y, 
//cord kacaCord = {.x = 0, .y = 0};

// Smer x, Smer y 
cord smeri[STEVILO_SMERI] = {{.x = 1, .y = 0},
							{.x = 0, .y = 1},
							{.x = -1, .y = 0},
							{.x = 0, .y = -1}};

// Kaca gleda gor na zacetku)
int gledanjeKace = 1;
int pred;
int kacaDebela = 1;
int zacetek = 0, konec = 0;
int koncaj = 0;

void obrni (int kam) {
	gledanjeKace += kam;
	if (gledanjeKace < 0) {
		gledanjeKace = STEVILO_SMERI - 1;
	}
	gledanjeKace %= STEVILO_SMERI;
}

void pohodiPolje() {
	for (int i = 0; i < pred; ++i) {

		if (vsi[i].c.x == kaca[zacetek].x && vsi[i].c.y == kaca[zacetek].y) {
			switch (vsi[i].id) {
				case 1:
					kacaDebela++;
					break;

				case 2:
					obrni (1);
					break;

				case 3:
					obrni (-1);
					break;
			} 

		}

		//if (vsi[i].c.x == kaca[zacetek].x - smeri[gledanjeKace].x && 
		//	vsi[i].c.y == kaca[zacetek].y - smeri[gledanjeKace].y && vsi[i].id == 1) {

			//kaca[0].x = kacaCord.x - smeri[gledanjeKace].x;
			//kaca[0].y = kacaCord.y - smeri[gledanjeKace].y;
			//kacaDebela++;
		//}
	}
		
}


/*void premakniRep() {
	if (kaca[0].x == kacaCord.x && kaca[0].y == kacaCord.y) {
		// Smo pojedli nekaj in damo na prvo mesto in kaco nikamor ne premaknemo
		return;
	} else {
		// Premaknemo vse razen tistega takoj za poljem
		for (int i = kacaDebela - 1; i >= 0; --i) {
			kaca[i + 1].x = kaca[i].x;
			kaca[i + 1].y = kaca[i].y;
		}

		// In potem se tega takoj za poljem
		kaca[0].x = kacaCord.x - smeri[gledanjeKace].x;
		kaca[0].y = kacaCord.y - smeri[gledanjeKace].y;
	}
}*/


void izpisi (int n) {
	for (int i = 0; i < n; ++i) {
		printf("%d %d\n", kaca[i].x, kaca[i].y);
		//printf("%d %d %d\n", vsi[i].c.x, vsi[i].c.y, vsi[i].id);
	}
}

void izirsi_polje(int levo, int desno, int zgoraj, int spodaj) {
	for (int i = zgoraj; i < spodaj; ++i) {
		for (int j = levo; j < desno; ++j) {
			int fl = 1;
			for (int k = 0; k < pred; ++k) {
				if (i == vsi[k].c.x && j == vsi[k].c.y) {
					printf("%d", vsi[k].id);
					fl = 0;
					break;
				}
				if (i == 0 && j == 0) {
					printf("0");
					fl = 0;
					break;	
				}
			}
			if (fl) {
				printf("-");
			}
			
		}
		printf("\n");
	}
}

void preveri() {
	//printf("Zacetek: %d %d\n", kaca[zacetek].x, kaca[zacetek].y);
	for (int i = zacetek - kacaDebela + 1; i < zacetek; ++i) {
		//printf("%d %d\n", kaca[i].x, kaca[i].y);
		if (kaca[i].x == kaca[zacetek].x && kaca[i].y == kaca[zacetek].y) {
			koncaj = 1;
			kacaDebela = -1; // Tako pise v navodilih da more to izpisat 
		}

	}
}

void premik(){
	//kacaCord.x += smeri[gledanjeKace].x;
	//kacaCord.y += smeri[gledanjeKace].y;


	kaca[zacetek + 1].x = kaca[zacetek].x + smeri[gledanjeKace].x;
	kaca[zacetek + 1].y = kaca[zacetek].y + smeri[gledanjeKace].y;
	zacetek++;

	preveri();

	//printf("x: %d y: %d dx: %d dy: %d \n", kacaCord.x, kacaCord.y, smeri[gledanjeKace].x, smeri[gledanjeKace].y);

	pohodiPolje();
	//izpisi(kacaDebela);
	//premakniRep();

	if (abs(kaca[zacetek].x) > 1000000 || abs(kaca[zacetek].y) > 1000000) {
		koncaj = 1;
	}


}


int main () {
	int koraki;
	scanf("%d", &pred);

	for (int i = 0; i < pred; ++i) {
		int x,y,id;
		scanf("%d %d %d", &x, &y, &id);
		vsi[i].c.x = x;
		vsi[i].c.y = y;
		vsi[i].id = id;
	}
	//izirsi_polje(-25, 25, -25, 25);
	scanf("%d", &koraki);

	kaca[0].x = 0;	
	kaca[0].y = 0;
	while(koraki > 0) {  // Ce gre za enega prevec spremeni na vecje

		premik();
		if (koncaj) {
			break;
		}

		koraki--;
	}	 

	printf("%d %d %d\n",  kacaDebela, kaca[zacetek].x, kaca[zacetek].y);


	return 0;
}
