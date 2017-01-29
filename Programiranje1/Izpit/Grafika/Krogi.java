// Risanje ovalnih oblik
int x = 30, y = 30, sirina = 50, visina = 80;
int hPolmerKotnegaLoka = 10;  // horizontalno
int vPolmerKotnegaLoka = 5;  // vertikalno
// Ovalni pravokotnik
g.drawRoundRect(x, y, sirina, visina, hPolmerKotnegaLoka, vPolmerKotnegaLoka);
g.drawString("Ovalni pravokotnik",x, y);

x += sirina * 4;

// Elipsa
g.drawOval (x, y, sirina, visina);
g.drawString("Elipsa",x, y);

x += sirina * 4;

int zacetniKot = 0;
int kot = 270;
g.drawArc(x,y, sirina, visina, zacetniKot, kot);
g.drawString("Lok",x, y);
