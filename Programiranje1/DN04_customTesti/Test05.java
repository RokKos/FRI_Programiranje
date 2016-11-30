
public class Test05 {

    public static void main(String[] args) {
        preveri(7);
        preveri(3);
        preveri(29);
    }

    private static void preveri(int modul) {
        Zp zp = new Zp(modul);

        System.out.println("----------------------------------");
        System.out.printf("Z_%d%n", modul);
        System.out.println("----------------------------------");
        for (int i = 0;  i < modul;  i++) {
            System.out.printf("%d|", zp.nasprotno(i));
        }

        System.out.println();
        System.out.println("----------------------------------");
        for (int i = 0;  i < modul;  i++) {
            for (int j = 0;  j < modul;  j++) {
                System.out.printf("%3d", zp.razlika(i, j));
            }
            System.out.println();
        }
    }
}
