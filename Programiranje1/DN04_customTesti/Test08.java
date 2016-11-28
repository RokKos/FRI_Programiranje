
public class Test08 {

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
            System.out.printf("%d -> %d%n", i, zp.steviloKvadratnihKorenov(i));
        }
    }
}
