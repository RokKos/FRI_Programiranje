import java.util.*;

public class Izziv8_0 {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    int k = in.nextInt();
    int[][] tab = new int[n + 1][];
    for (int i = 0; i < n + 1; ++i) {
      tab[i] = new int[i + 1];
      for (int j = 0; j < i + 1; ++j) {
        // System.out.printf("rac: %d %d \n", i, j);
        if (j == 0 || j == i) {
          tab[i][j] = 1;
        } else {
          tab[i][j] = tab[i - 1][j - 1] + tab[i - 1][j];
        }

        System.out.printf("%d ", tab[i][j]);
      }
      System.out.println();
    }

    System.out.println("rez: " + tab[n][k]);
  }
}