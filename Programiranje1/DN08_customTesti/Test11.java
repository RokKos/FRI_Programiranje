public class Test11 {

    private static final String[] IMENA_DNI = {"PO", "TO", "SR", "CE", "PE"};

    public static void main(String[] args) {
        Urnik urnik = new Urnik();
        izpisiUrnik(urnik);
        System.out.println(urnik.dodajBlok(1, 1, 7, 5));
        System.out.println(urnik.dodajBlok(5, 2, 7, 17));
        System.out.println(urnik.dodajBlok(99, 5, 18, 2));
        System.out.println(urnik.dodajBlok(99, 5, 16, 1));
        System.out.println(urnik.dodajBlok(99, 5, 17, 2));
        System.out.println(urnik.dodajBlok(99, 5, 16, 2));
        System.out.println(urnik.dodajBlok(99, 5, 15, 3));
        System.out.println(urnik.dodajBlok(99, 5, 18, 4));
        System.out.println(urnik.dodajBlok(99, 5, 17, 1));
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
