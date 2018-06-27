import java.util.*;

public class Izziv8_1 {
  public static void main(String[] args) {
    // Razlka od prejsnjega je da si tukja samo zadnjo tabelo hranim
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    int k = in.nextInt();
    int[] row = new int[1];
    for (int i = 0; i < n + 1; ++i) {
      int[] tab = new int[i + 1];
      for (int j = 0; j < i + 1; ++j) {
        // System.out.printf("rac: %d %d \n", i, j);
        if (j == 0 || j == i) {
          tab[j] = 1;
        } else {
          tab[j] = row[j - 1] + row[j];
        }

        System.out.printf("%d ", tab[j]);
      }
      row = tab;
      System.out.println();
    }

    System.out.println("rez: " + row[k]);
  }
}