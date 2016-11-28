
public class Test30 {

    public static void main(String[] args) {
        preveri(67);
    }

    private static void preveri(int modul) {
        Zp zp = new Zp(modul);

        System.out.printf("Testni primer za prastevilo : %d Mnozica: %s%n", zp.vrniModul(), zp.toString());
        System.out.println("----------------------------------");
        System.out.println("Vsota: ");
        for (int i = 0;  i < modul;  i++) {
            for (int j = 0;  j < modul;  j++) {
                System.out.printf("%3d", zp.vsota(i, j));
            }
            System.out.println();
        }
        System.out.println("----------------------------------");
        System.out.println("Zmnozek: ");
        for (int i = 0;  i < modul;  i++) {
            for (int j = 0;  j < modul;  j++) {
                System.out.printf("%3d", zp.zmnozek(i, j));
            }
            System.out.println();
        }
        System.out.println("----------------------------------");
        System.out.println("Nasprotno: ");
        for (int i = 0;  i < modul;  i++) {
            System.out.printf("%d|", zp.nasprotno(i));
        }
        System.out.println();
        System.out.println("----------------------------------");
        System.out.println("Razlika: ");
        for (int i = 0;  i < modul;  i++) {
            for (int j = 0;  j < modul;  j++) {
                System.out.printf("%3d", zp.razlika(i, j));
            }
            System.out.println();
        }
        System.out.println("----------------------------------");
        System.out.println("Obratno: ");
        for (int i = 1;  i < modul;  i++) {
            System.out.printf("%d|", zp.obratno(i));
        }
        System.out.println("----------------------------------");
        System.out.println("Kolicnik: ");
        for (int i = 0;  i < modul;  i++) {
            for (int j = 1;  j < modul;  j++) {
                System.out.printf("%3d", zp.kolicnik(i, j));
            }
            System.out.println();
        }
        System.out.println("----------------------------------");
        System.out.println("Navadna potenca: ");
        for (int i = 1;  i < modul;  i++) {
            for (int j = -10;  j <= 10;  j++) {
                System.out.printf("%3d", zp.potenca(i, j));
            }
            System.out.println();
        }
        System.out.println("----------------------------------");
        System.out.println("Kvadratni koreni: ");
        for (int i = 0;  i < modul;  i++) {
            System.out.printf("%d -> %d%n", i, zp.steviloKvadratnihKorenov(i));
        }
        System.out.println("----------------------------------");
        System.out.println("Multiplikativni Generator: ");
        for (int i = 0;  i < modul;  i++) {
            if (zp.jeMultiplikativniGenerator(i)) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
        System.out.println("----------------------------------");

    }
}
