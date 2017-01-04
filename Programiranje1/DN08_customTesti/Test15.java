public class Test15 {

    private static final String[] IMENA_DNI = {"PO", "TO", "SR", "CE", "PE"};

    public static void main(String[] args) {
        Urnik urnik = new Urnik();
        System.out.println(urnik.dodajBlok(5, 2, 7, 17));
        System.out.println(urnik.dodajBlok(99, 5, 18, 2));
        System.out.println(urnik.dodajBlok(99, 5, 16, 1));
        System.out.println(urnik.dodajBlok(10, 3, 12, 3));
        System.out.println(urnik.dodajBlok(99, 5, 17, 2));  
        System.out.println(urnik.dodajBlok(10, 4, 7, 2));
        System.out.println(urnik.dodajBlok(3, 4, 18, 1));
        izpisiUrnik(urnik);
        System.out.println("vrzeli");
        for(int i=0; i<5; ++i) {
            System.out.println(IMENA_DNI[i] + ": " + urnik.vrzeli(i+1));
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
