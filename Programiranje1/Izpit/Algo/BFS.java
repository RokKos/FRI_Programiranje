private static class Povezava {
    public int pretekliCas;
    public int indeks;

    Povezava (int _pretekliCas, int _indeks) {
        this.pretekliCas = _pretekliCas;
        this.indeks = _indeks;
    }
}

int n = in.nextInt();
int cas = in.nextInt();
int[][] sosedi = new int[n][n];
// Preberem vse sosede
for (int i = 0; i < n; ++i) {
    for (int j = 0; j < n; ++j) {
        sosedi[i][j] = in.nextInt();
    }
}
// Nastavim cas potovanja na najvec mogoce
int[] casPotovanja = new int[n];
for (int i = 0; i < n; ++i) {
    casPotovanja[i] = 2147483647;
}

// Naredim Queue z svojim classom Povezava v katerega bom potem shranil
// povezava ki jih morem se obdelati
ArrayDeque<Povezava> q = new ArrayDeque<Povezava>();
// Dam zacetnika sporocil v povezavo
q.addLast(new Povezava(0,0));
while (!q.isEmpty()) {
	// Vzamem eno povezavo
    Povezava tr = q.pollFirst();
	// Ce je cas trenutne poti vecji od prejsnje poti potem ne rabimo iti po tej poti
    if (tr.pretekliCas > casPotovanja[tr.indeks]) {
        continue;
    }else {
		// Drugace je to trenutna najkrajsa pot
        casPotovanja[tr.indeks] = tr.pretekliCas;
    }
	// Gremo cez vse sosede trenutne povezave
    for (int i = 0; i < n; ++i) {
        if (sosedi[tr.indeks][i] != 0) {
            Povezava naslednja = new Povezava(
            tr.pretekliCas + sosedi[tr.indeks][i], i);
            q.addLast(naslednja);
        }

    }
}

int rez = 0;
for (int i = 0; i < n; ++i) {
    if (casPotovanja[i] <= cas) {
        rez++;
    }
}
