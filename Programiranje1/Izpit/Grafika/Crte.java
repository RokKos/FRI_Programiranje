// Risanje crt
int x1 = 5, y1 = 100, x2 = 500, y2 = 250;
g.drawString(String.format("(%d,%d)",x1, y1),x1,y1);
g.drawString(String.format("(%d,%d)",x2, y2),x2,y2);
g.drawLine(x1, y1, x2, y2);

// Risanje mnogokotnikov
// Desni na sliki
Polygon p = new Polygon();
p.addPoint(450, 50);
p.addPoint(475, 70);
p.addPoint(500, 50);
p.addPoint(500, 125);
p.addPoint(500, 150);
g.drawPolygon(p);

g.drawString("Prvi mnogokotnik",500, 150);


// Levi na sliki
int[] xKordinateOgljisc = new int[] {50, 100, 120, 180, 190};
int[] yKordinateOgljisc = new int[]  {190, 180, 120, 100, 50};

g.drawPolygon(xKordinateOgljisc, yKordinateOgljisc, xKordinateOgljisc.length);

g.drawString("Drugi mnogokotnik",190, 50);

for (int i = 0; i < xKordinateOgljisc.length; ++i) {
	xKordinateOgljisc[i] += 50;
	yKordinateOgljisc[i] += 50;
}
// Na enak nacin lahko narisemo lomljeno crto
g.drawPolyline(yKordinateOgljisc, xKordinateOgljisc, xKordinateOgljisc.length);

g.drawString("Lomljenka",240, 100);
