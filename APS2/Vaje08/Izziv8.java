import java.util.*;

public class Izziv8 {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    int k = in.nextInt();

    int[][] tab = new int[n + 1][k];
    int[] one = new int[k];
    Arrays.fill(one, 1);
    tab[1] = one;
    for (int i = 2; i < n + 1; ++i) {
      for (int j = 0; j < k; ++j) {
        int minimum = i - 1;
        if (j != 0) {
          for (int l = 1; l <= i; ++l) {
            minimum = Math.min(minimum, Math.max(tab[l - 1][j - 1], tab[i - l][j]));
          }
        }
        tab[i][j] = 1 + minimum;
      }
    }

    for (int i = 0; i < n + 1; ++i) {
      for (int j = 0; j < k; ++j) {
        System.out.print(tab[i][j] + " ");
      }
      System.out.println();
    }
  }
}