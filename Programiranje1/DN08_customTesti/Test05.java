
public class Test05 {

    private static final String[] IMENA_DNI = {"PO", "TO", "SR", "CE", "PE"};

    public static void main(String[] args) {
        Urnik urnik = new Urnik();
        System.out.println( urnik.dodajBlok(25, 1,  7, 1) );
        System.out.println( urnik.dodajBlok(26, 1,  7, 1) );
        System.out.println( urnik.dodajBlok(25, 1,  7, 1) );
        System.out.println( urnik.dodajBlok(26, 1,  9, 1) );
        System.out.println( urnik.dodajBlok(25, 1,  9, 1) );
        System.out.println( urnik.dodajBlok(25, 1,  8, 1) );
        System.out.println( urnik.dodajBlok(26, 1,  8, 1) );
        System.out.println( urnik.dodajBlok(26, 1, 10, 1) );
        System.out.println( urnik.dodajBlok(25, 2, 15, 1) );
        System.out.println( urnik.dodajBlok(26, 4,  9, 1) );
        System.out.println( urnik.dodajBlok(26, 5, 18, 1) );
        System.out.println( urnik.dodajBlok(26, 5, 17, 1) );
        System.out.println( urnik.dodajBlok(25, 5, 16, 1) );
        izpisiUrnik(urnik);
        System.out.println("--------------------------------------------------------------------------");
        urnik.odstraniPredmet(26);
        izpisiUrnik(urnik);
    }

    private static void izpisiUrnik(Urnik urnik) {
        System.out.print("  ");
        for (int ura = 7;  ura < 24;  ura++) {
            System.out.printf("%3d", ura);
        }
        System.out.println();

        for (int dan = 1;  dan <= 5;  dan++) {
            System.out.print(IMENA_DNI[dan - 1]);
            for (int ura = 7;  ura < 24;  ura++) {
                int sifra = urnik.kaj(dan, ura);
                if (sifra == 0) {
                    System.out.print("  -");
                } else {
                    System.out.printf("%3d", sifra);
                }
            }
            System.out.println();
        }
    }
}
