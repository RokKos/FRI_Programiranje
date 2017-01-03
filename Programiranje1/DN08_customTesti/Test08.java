
public class Test08 {

    private static final String[] IMENA_DNI = {"PO", "TO", "SR", "CE", "PE"};

    public static void main(String[] args) {
        Urnik urnik = new Urnik();
        System.out.println( urnik.dodajBlok(30, 4,  8, 3) );
        System.out.println( urnik.dodajBlok(60, 5, 13, 2) );
        System.out.println( urnik.dodajBlok(90, 2, 17, 1) );
        System.out.println( urnik.dodajBlok(30, 3, 12, 3) );
        System.out.println( urnik.dodajBlok(40, 1, 10, 3) );
        System.out.println( urnik.dodajBlok(50, 5, 18, 1) );
        System.out.println( urnik.dodajBlok(30, 3, 14, 2) );  // prekrivanje
        System.out.println( urnik.dodajBlok(20, 3,  7, 3) );
        System.out.println( urnik.dodajBlok(60, 2,  8, 6) );
        System.out.println( urnik.dodajBlok(60, 1, 16, 2) );
        System.out.println( urnik.dodajBlok(30, 3, 18, 3) );
        System.out.println( urnik.dodajBlok(20, 5, 11, 1) );
        System.out.println( urnik.dodajBlok(60, 2, 10, 3) );  // prekrivanje
        System.out.println( urnik.dodajBlok(50, 4, 17, 3) );
        System.out.println( urnik.dodajBlok(30, 4,  7, 1) );
        System.out.println( urnik.dodajBlok(60, 2, 14, 3) );
        System.out.println( urnik.dodajBlok(40, 4, 15, 3) );  // prekrivanje
        System.out.println( urnik.dodajBlok(50, 5, 16, 1) );
        System.out.println( urnik.dodajBlok(30, 1, 18, 3) );
        System.out.println( urnik.dodajBlok(60, 5,  7, 2) );
        System.out.println( urnik.dodajBlok(30, 4, 12, 2) );
        System.out.println( urnik.dodajBlok(10, 4, 11, 2) );  // prekrivanje
        izpisiUrnik(urnik);

        System.out.println("------ vrzeli ------");
        for (int dan = 1;  dan <= 5;  dan++) {
            System.out.printf("%s: %d%n", IMENA_DNI[dan - 1], urnik.vrzeli(dan));
        }
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
