
public class Test04 {

    private static final String[] IMENA_DNI = {"PO", "TO", "SR", "CE", "PE"};

    public static void main(String[] args) {
        Urnik urnik = new Urnik();
        System.out.println( urnik.dodajBlok(31, 4, 11, 1) );
        System.out.println( urnik.dodajBlok(32, 2,  9, 1) );
        System.out.println( urnik.dodajBlok(33, 3, 10, 1) );
        System.out.println( urnik.dodajBlok(31, 4, 11, 1) );
        System.out.println( urnik.dodajBlok(32, 2, 10, 1) );
        System.out.println( urnik.dodajBlok(33, 3, 10, 1) );
        System.out.println( urnik.dodajBlok(31, 2,  9, 1) );
        System.out.println( urnik.dodajBlok(32, 4, 11, 1) );
        System.out.println( urnik.dodajBlok(33, 2, 10, 1) );
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
