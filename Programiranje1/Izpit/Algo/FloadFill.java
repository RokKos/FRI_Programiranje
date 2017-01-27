// Struct(tip), ki drzi kordinati in ceno na kordinati
private static class Cord {
	public int x;
	public int y;
	public int c;

	public Cord (int _x, int _y) {
		this.x = _x;
		this.y = _y;
		this.c = 0;
	}

	public Cord (int _x, int _y, int _c) {
		this.x = _x;
		this.y = _y;
		this.c = _c;
	}
}

// Nastavimo vsa polja na default vrednosti
int[][] zemlja = new int[n][n];
for (int i = 0; i < n; ++i) {
	for (int j = 0; j < n; ++j) {
		zemlja[i][j] = -1;
	}
}
// Vse mozne smeri v katere lahko gremo
Cord[] s = new Cord[] {new Cord(0,1), new Cord(0,-1),
                       new Cord(1,0), new Cord(-1,0)};
int x = n/2;
int y = x;

// Naredimo vrsto v katero bomo dajali katera polja moramo obiskati in iz nje
// vzemali katero bo nase naslednje polje
ArrayDeque<Cord> q = new ArrayDeque<Cord>();
q.addLast(new Cord(x,y,c));  // Naslednje polje
while (!q.isEmpty()) {  // Dokler imamo se katero polje za obiskati
	Cord t = q.pollFirst();
	// Ce smo ze bili na polju ga preskocimo
	if (zemlja[t.x][t.y] != -1) {
		continue;
	}
	zemlja[t.x][t.y] = t.c;  // Nastavimo ceno polja
	// Gremo cez vse njegove sosede
	for (int i = 0; i < s.length; ++i) {
		if (t.x + s[i].x >= 0 && t.y + s[i].y >= 0 &&
			t.x + s[i].x < n && t.y + s[i].y < n &&
			(zemlja[t.x + s[i].x][t.y + s[i].y] == -1)) {  // Pogledamo ce je veljaven sosed
			q.addLast(new Cord(t.x + s[i].x, t.y + s[i].y, Math.max(0,t.c - d)));
		}
	}
}
