#include <stdio.h>
#include <stdlib.h>

typedef struct {
	int x;
	int y;
} cord;

int n;
cord* plosce;

void izpisiPlosce (){
	for (int i = 0; i < n; ++i) {
		printf("%d %d\n", plosce[i].x, plosce[i].y);
	}
}

int max (int a, int b) {
	return a > b ? a : b;
}

int najvec (cord trenutna) {
	int m = 0;
	for (int i = 0; i < n; ++i) {
		if ((trenutna.x > plosce[i].x && trenutna.y > plosce[i].y)
			|| (trenutna.x > plosce[i].y && trenutna.y > plosce[i].x)) {
			m = max(m, najvec(plosce[i]) + 1);
		}
	}

	return m;

}

int main () {

	scanf("%d", &n);
	plosce = (cord*) malloc (sizeof(cord) * n);
	for (int i = 0; i < n; ++i) {
		cord p;
		scanf("%d %d", &p.x, &p.y);
		plosce[i] = p;
	}

	//izpisiPlosce();
	int maximum = 0;
	for (int i = 0; i < n; ++i) {
		maximum = max(maximum, najvec(plosce[i]) + 1);
	}

	printf("%d\n", maximum);

	return 0;
}