#include <stdio.h>
#include <stdlib.h>

#define MAX (100 * 100)
#define STEVILO_SMERI 4
int** polje;
int w, h, n;

typedef struct _cord {
	int x;
	int y;
} cord;

cord smeri[STEVILO_SMERI] = {{.x = 1, .y = 0},
							{.x = 0, .y = 1},
							{.x = -1, .y = 0},
							{.x = 0, .y = -1}};


cord sosedi[MAX];
int front = 0;
int rear = -1;
int itemCount = 0;

int rez = 0;

cord peek() {
   return sosedi[front];
}

int isEmpty() {
   return itemCount == 0;
}

int isFull() {
   return itemCount == MAX;
}

int size() {
   return itemCount;
}

void insert(cord data) {

   if(!isFull()) {

      if(rear == MAX-1) {
         rear = -1;
      }

      sosedi[++rear] = data;
      itemCount++;
   }
}

cord removeData() {
   cord data = sosedi[front++];

   if(front == MAX) {
      front = 0;
   }

   itemCount--;
   return data;
}

void potegni_crto(char smer, int x, int y, int l) {
	for (int i = 0; i < l; ++i) {
		if (smer == 'n') {
			if (y + i < h && y + i >= 0) {
				polje[y + i][x] = 1;
			}
		} else {
			if (x + i < w && x + i >= 0) {
				polje[y][x + i] = 1;
			}
		}
	}
}

void izpisi_polje () {
	for (int i = 0; i < h; ++i) {
		for (int j = 0; j < w; ++j) {
			printf("%d ", polje[i][j]);
		}
		printf("\n");
	}
}

void float_fill (int x, int y) {
	cord temp;
	temp.x = x;
	temp.y = y;
	insert(temp);
	while (itemCount != 0) {
		cord trenutni = removeData();
		//printf("%d %d\n", trenutni.x, trenutni.y);
		for (int i = 0; i < STEVILO_SMERI; ++i) {
			if (smeri[i].y + trenutni.y >= 0 && smeri[i].y + trenutni.y < h &&
				smeri[i].x + trenutni.x >= 0 && smeri[i].x + trenutni.x < w &&
				polje[smeri[i].y + trenutni.y][smeri[i].x + trenutni.x] == 0) {
				polje[smeri[i].y + trenutni.y][smeri[i].x + trenutni.x] = 1;
				temp.x = smeri[i].x + trenutni.x;
				temp.y = smeri[i].y + trenutni.y;
				//printf("%d %d\n", temp.x, temp.y);
				insert(temp);
			}
		}
	}
	//printf("%d %d %d\n", x, y, rez);
	//izpisi_polje();
	//printf("###############################\n");
	rez++;

}

int main () {

	scanf ("%d %d\n", &w, &h);
	scanf ("%d\n", &n);
	polje = (int**) malloc (sizeof(int*) * h);

	if (polje == NULL) {
		exit(1);
	}

	for (int i = 0; i < h; ++i){
		int* vrsta = (int*) calloc (w, sizeof(int));
		if (vrsta == NULL) {
			exit(2);
		}
		polje[i] = vrsta;
	}
	for (int i = 0; i < n; ++i) {
		int x,y,l;
		char smer;
		scanf("%c %d %d %d\n", &smer, &x, &y, &l);
		//printf("%c %d %d %d\n", smer, x, y, l);
		potegni_crto(smer, x, y, l);
		//izpisi_polje();
		//printf("-------------------------------\n");
	}

	for (int i = 0; i < h; ++i) {
		for (int j = 0; j < w; ++j) {
			if (polje[i][j] == 0) {
				float_fill(j,i);
			}

		}
	}

	printf("%d\n", rez);

	//for (int i = 0; i < n; ++i){
	//	free(polje[i]);
	//}
	//free(polje);

	return 0;
}