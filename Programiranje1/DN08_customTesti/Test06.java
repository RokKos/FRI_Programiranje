
public class Test06 {

    private static final String[] IMENA_DNI = {"PO", "TO", "SR", "CE", "PE"};

    public static void main(String[] args) {
        Urnik urnik = new Urnik();
        System.out.println( urnik.dodajBlok(33, 2, 10, 5) );
        System.out.println( urnik.dodajBlok(87, 3,  8, 2) );
        System.out.println( urnik.dodajBlok(52, 5, 15, 6) );
        System.out.println( urnik.dodajBlok(45, 1, 12, 1) );
        System.out.println( urnik.dodajBlok(33, 1, 18, 6) );
        System.out.println( urnik.dodajBlok(52, 4,  7, 3) );
        System.out.println( urnik.dodajBlok(87, 2, 15, 4) );
        System.out.println( urnik.dodajBlok(69, 1, 14, 4) );
        System.out.println( urnik.dodajBlok(33, 3, 13, 5) );
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
