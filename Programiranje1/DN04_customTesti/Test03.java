
public class Test03 {

    public static void main(String[] args) {
        preveri(7);
        preveri(3);
        preveri(29);
        Zp z = new Zp(3);
    }

    private static void preveri(int modul) {
        System.out.println("----------------------------------");
        System.out.printf("Z_%d%n", modul);
        System.out.println("----------------------------------");
        Zp zp = new Zp(modul);
        for (int i = 0;  i < modul;  i++) {
            for (int j = 0;  j < modul;  j++) {
                System.out.printf("%3d", zp.vsota(i, j));
            }
            System.out.println();
        }
    }
}
