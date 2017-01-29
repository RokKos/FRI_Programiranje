// Samo oznacevanje
g.setColor(Color.BLACK);
g.drawString("(0,0)",7,10);
g.drawString("(0,hp)",7,ri(hp-10));
g.drawString("(wp,0)",ri(wp)-40,10);
g.drawString("(wp,hp)",ri(wp)-50,ri(hp)-10);
g.drawString("x", ri(wp / 2), 10);
g.drawString("y", 0, ri(hp / 2));

// Risanje pravokotnikov
int levoZgorajX = 50;
int levoZgorajY = 100;
int dolzina = 75;
int visina = 200;
// Zaznamek za levi zgornji kot
g.drawString(String.format("(%d,%d)",levoZgorajX, levoZgorajY),levoZgorajX,levoZgorajY);
g.drawRect(levoZgorajX, levoZgorajY, dolzina, visina);
// Zaznamek za levi zgornji kot
g.drawString(String.format("(%d,%d)",levoZgorajX * 2, levoZgorajY * 2),levoZgorajX * 2,levoZgorajY * 2);
g.setColor(Color.BLUE);
g.fillRect(levoZgorajX * 2, levoZgorajY * 2, dolzina * 2, visina * 2);
