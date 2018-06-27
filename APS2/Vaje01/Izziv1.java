import java.util.*;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

public class Izziv1 {

  private static final int PONOVITVE = 1000;

  private static int[] generateTable(int n) {
    int[] tab = new int[n];
    for (int i = 0; i < n; ++i) {
      tab[i] = i;
    }
    return tab;
  }

  private static int findLinear(int[] a, int v) {
    for (int i = 0; i < a.length; ++i) {
      if (v == a[i]) {
        return i;
      }
    }

    return -1;
  }
  private static int findBinary(int[] a, int l, int r, int v) {
    // System.out.println(l + " " + r + " " + v);
    if (l > r) {
      return -1;
    }

    int s = (l + r) / 2;
    if (a[s] == v) {
      return s;
    }

    if (a[s] < v) {
      return findBinary(a, s, r, v);
    }

    return findBinary(a, l, s, v);
  }

  private static long timeLinear(int n) {
    Random rnd = new Random();
    int[] tabela = generateTable(n);

    long startTime = System.nanoTime();
    for (int i = 0; i < PONOVITVE; ++i) {
      int nakljucno = rnd.nextInt(n);
      findLinear(tabela, nakljucno);
    }

    long executionTime = System.nanoTime() - startTime;
    return executionTime / PONOVITVE;
  }
  private static long timeBinary(int n) {
    Random rnd = new Random();
    int[] tabela = generateTable(n);

    long startTime = System.nanoTime();
    for (int i = 0; i < PONOVITVE; ++i) {
      int nakljucno = rnd.nextInt(n);
      findBinary(tabela, 0, n, nakljucno);
    }

    long executionTime = System.nanoTime() - startTime;
    return executionTime / PONOVITVE;
  }

  public static void main(String[] args) {
    System.out.println("n     |    linearno  |   dvojisko   |");
    System.out.println("---------+--------------+---------------");
    for (int i = 1000; i <= 100000; i += 1000) {
      long l = timeLinear(i);
      long b = timeBinary(i);
      System.out.printf("%10d | %10d | %10d\n", i, l, b);
    }
  }
}