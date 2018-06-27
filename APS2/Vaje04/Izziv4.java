import java.util.*;

public class Izziv4 {
  private static final int PONOVITVE = 1000;
  private static Random rnd = new Random(System.currentTimeMillis() % 1000); // new Random(42);

  private static class Pair<T, S> {
    private T first;
    private S second;

    Pair(T _first, S _second) {
      first = _first;
      second = _second;
    }

    public T getFirst() {
      return first;
    }

    public S getSecond() {
      return second;
    }

    public void setFirst(T _first) {
      first = _first;
    }

    public void setSecond(S _second) {
      second = _second;
    }
  }

  private static <T extends Comparable<? super T>> T Min(T a, T b) {
    if (a.compareTo(b) <= 0) {
      return a;
    }
    return b;
  }

  private static <T extends Comparable<? super T>> T Max(T a, T b) {
    if (a.compareTo(b) >= 0) {
      return a;
    }
    return b;
  }

  private static int[] generateTable(int n) {
    int[] tab = new int[n];
    for (int i = 0; i < n; ++i) {
      tab[i] = rnd.nextInt();
    }
    return tab;
  }

  private static int RandomPositiveNumber() {
    return rnd.nextInt() & Integer.MAX_VALUE; // This will zero out last bit
  }

  private static int RandomNumberBetweenBounds(Pair<Integer, Integer> bounds) {
    return rnd.nextInt(bounds.getSecond() - bounds.getFirst()) + bounds.getFirst();
  }

  private static Pair<Pair<Integer, Integer>, Integer> minMaxIter(int[] tab) {
    int n = tab.length;
    int stPrim = 0;
    Pair<Integer, Integer> minmax = new Pair<Integer, Integer>(tab[0], tab[0]);

    for (int i = 1; i < n / 2 + 1; ++i) {
      int el1 = tab[i];
      int el2 = tab[n - i];
      // System.out.printf(
      //    "i: %d, n-i: %d, tab[i]: %d, tab[n - i]: %d \n", i, n - i, tab[i], tab[n - i]);

      if (el1 < el2) {
        minmax.setFirst(Min(minmax.getFirst(), el1));
        minmax.setSecond(Max(minmax.getSecond(), el2));
      } else {
        // Tudi ce sta stevili enaki lahko naredimo to operacijo
        minmax.setFirst(Min(minmax.getFirst(), el2));
        minmax.setSecond(Max(minmax.getSecond(), el1));
      }
      stPrim += 3;
    }

    return new Pair<Pair<Integer, Integer>, Integer>(minmax, stPrim);
  }

  private static Pair<Pair<Integer, Integer>, Integer> minMaxRek(int[] tab) {
    if (tab.length == 1) {
      return new Pair<Pair<Integer, Integer>, Integer>(
          new Pair<Integer, Integer>(tab[0], tab[0]), 0);
    }

    if (tab.length == 2) {
      if (tab[0] < tab[1]) {
        return new Pair<Pair<Integer, Integer>, Integer>(
            new Pair<Integer, Integer>(tab[0], tab[1]), 1);
      }

      return new Pair<Pair<Integer, Integer>, Integer>(
          new Pair<Integer, Integer>(tab[1], tab[0]), 1);
    }

    int n = tab.length;
    Pair<Pair<Integer, Integer>, Integer> rezOfTabLeft =
        minMaxRek(Arrays.copyOfRange(tab, 0, n / 2));
    Pair<Pair<Integer, Integer>, Integer> rezOfTabRight =
        minMaxRek(Arrays.copyOfRange(tab, n / 2, n));

    int stPrim = rezOfTabLeft.getSecond() + rezOfTabRight.getSecond();
    int min = Min(rezOfTabLeft.getFirst().getFirst(), rezOfTabRight.getFirst().getFirst());
    int max = Max(rezOfTabLeft.getFirst().getSecond(), rezOfTabRight.getFirst().getSecond());
    stPrim += 2;

    return new Pair<Pair<Integer, Integer>, Integer>(new Pair<Integer, Integer>(min, max), stPrim);
  }

  private static int IterAsimp(int n) {
    return n / 2 * 3;
  }

  private static int RekAsimp(int n) {
    return ((n * 3) / 2) + 2;
  }

  public static void main(String[] args) {
    for (int i = 0; i < PONOVITVE; ++i) {
      int[] tab = generateTable(RandomNumberBetweenBounds(new Pair<Integer, Integer>(0, 10000)));
      Pair<Pair<Integer, Integer>, Integer> mmIter = minMaxIter(tab);
      Pair<Pair<Integer, Integer>, Integer> mmRek = minMaxRek(tab);
      System.out.println(tab.length + ": " + mmIter.getFirst().getFirst() + " "
          + mmIter.getFirst().getSecond() + " " + mmIter.getSecond() + " " + IterAsimp(tab.length)
          + " " + mmRek.getFirst().getFirst() + " " + mmRek.getFirst().getSecond() + " "
          + mmRek.getSecond() + " " + RekAsimp(tab.length));
    }
  }
}