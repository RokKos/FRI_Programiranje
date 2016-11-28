
import java.util.Random;

public class Test10 {

    public static void main(String[] args) {
        preveri(11);
        preveri(43);
        preveri(53);
        preveri(97);
    }

    private static void preveri(int modul) {
        System.out.println("----------------------------------");
        System.out.printf("Z_%d%n", modul);
        System.out.println("----------------------------------");

        Zp zp = new Zp(modul);
        for (int i = 0;  i < modul;  i++) {
            if (zp.jeMultiplikativniGenerator(i)) {
                System.out.print(i + " ");
            }
        }
        System.out.println();

        Random random = new Random(123456);
        for (int i = 0;  i < 10;  i++) {
            int stevilo = random.nextInt(modul - 1) + 1;
            int ee = random.nextInt(18);
            int predznak = 1 - 2 * random.nextInt(2);
            long spodnjaMeja = potenca10(ee);
            long zgornjaMeja = 10 * spodnjaMeja;
            long eksponent = predznak * (random.nextLong() % (zgornjaMeja - spodnjaMeja) + spodnjaMeja);

            System.out.printf("%d ^ %d = %d%n", stevilo, eksponent, zp.potenca(stevilo, eksponent));
        }
    }

    private static long potenca10(int e) {
        long potenca = 1;
        for (int i = 0;  i < e;  i++) {
            potenca *= 10;
        }
        return potenca;
    }
}
