// Ostale uporabne funkcije
Color color = g.getColor();  // Dobi trenurno barvo
Font font = g.getFont();  // Dobi trenurni font
// Dobi velikost in ostale stvari o trenutnem fontu
FontMetrics metrics = g.getFontMetrics();  // Lahko ma kot argument font
g.setColor(color);
g.setFont(font);
int dolzinaStringa = metrics.stringWidth(str);

// Generalna funkcija za draw
// Tako naredimo 2D pravokotnik
g.draw (new Rectangle2D.Double(x, y, sirina, visina));  // vsi parametri so double
g.draw (new RoundRectangle2D.Double(x, y, sirina, visina));
g.fill (new Ellipse2D.Double(x,y, sirina, visina));
// Drugi dve monosti sta Arc2D.OPEN, Arc2D.CHORD
g.fill (new Arc2D.Double(x,y, sirina, visina, zacKot, kot, Arc2D.PIE));

// fill lahko naredimo tudi na ostalih metodah
g.fillRect(x,y,sirina,visina)
g.fillRoundRect(x,y,sirina,visina,rH,rV)
g.fill3DRect(x,y,sirina,visina,dvig)
g.fillOval(x,y,sirina,visina)
g.fillPolygon(p)
g.fillPolygon(x,y,n)
g.fillArc(x,y,sirina,visina,zacKot,kot)
