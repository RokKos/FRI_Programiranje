import java.util.Random;

public class Test43 {

    public static void main(String[] args) {
	preveri(97);
    }

    private static void preveri(int modul) {
        Zp zp = new Zp(modul);

        System.out.printf("Testni primer za prastevilo : %d Mnozica: %s%n", zp.vrniModul(), zp.toString());
        Random random = new Random(42);
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
