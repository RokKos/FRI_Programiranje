#include <stdio.h>
#include <stdlib.h>
#include <math.h>

void print_polje(int** polje, int p, int q) {
	for (int i = 0; i < p; ++i) {
		for (int j = 0; j < q; ++j) {
			printf("%d ", polje[i][j]);
		}
		printf("\n");
	}
}


typedef struct _cord {
	int x;
	int y;
} cord;

int main () {
	int p, q, d;
	scanf ("%d %d %d\n", &p, &q, &d);
	//int** polje = (int**) malloc (sizeof(int*) * p);
	cord* kordinate = (cord*) malloc (sizeof(cord) * p * q);
	for (int i = 0; i < p; ++i) {
		for (int j = 0; j < q; ++j) {
			int st;
			scanf("%d", &st);
			cord temp;
			temp.x = i;
			temp.y = j;
			kordinate[st] = temp;
		}
	}
	int sum = 0;
	int dan = 0;
	for (int i = 0; i < p * q - 1; ++i) {
		sum += abs(kordinate[i].x - kordinate[i + 1].x) + abs(kordinate[i].y - kordinate[i + 1].y);
		if (sum <= d) {
			dan++;
		}
	}
	printf("%d\n", dan);

	return 0;
}