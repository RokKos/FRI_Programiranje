import java.util.*;

public class Izziv9 {
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

  private static String izpisResitev(ArrayList<Pair<Integer, Integer>> resitve, int index) {
    String s = index + ":";
    for (int i = 0; i < resitve.size(); i++) {
      s += " (" + resitve.get(i).getFirst() + ", " + resitve.get(i).getSecond() + ")";
    }
    return s;
  }

  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("Podaj argumente prijatelj.");
      System.exit(1);
      return;
    }

    Scanner in = new Scanner(System.in);

    int stElementov = Integer.parseInt(args[0]);

    ArrayList<Pair<Integer, Integer>> podatki = new ArrayList<Pair<Integer, Integer>>(stElementov);

    for (int i = 0; i < stElementov; ++i) {
      int prostornina = in.nextInt();
      podatki.add(new Pair<Integer, Integer>(prostornina, 0));
    }

    for (int i = 0; i < stElementov; ++i) {
      int cena = in.nextInt();
      podatki.get(i).setSecond(cena);
    }

    int maxProstornina = in.nextInt();

    ArrayList<Pair<Integer, Integer>> resitve = new ArrayList<Pair<Integer, Integer>>();
    resitve.add(new Pair<Integer, Integer>(0, 0));

    System.out.println(izpisResitev(resitve, 0));

    for (int i = 0; i < stElementov; ++i) {
      Pair<Integer, Integer> trenutniElement = podatki.get(i);
      // System.out.println("f: " + trenutniElement.getFirst() + " s: " +
      // trenutniElement.getSecond());
      int lenRes = resitve.size();
      for (int j = 0; j < lenRes; ++j) {
        Pair<Integer, Integer> moznaResitev = resitve.get(j);
        int novaProstornina = moznaResitev.getFirst() + trenutniElement.getFirst();
        int novaCena = moznaResitev.getSecond() + trenutniElement.getSecond();
        if (novaProstornina <= maxProstornina) {
          Pair<Integer, Integer> novElement = new Pair<Integer, Integer>(novaProstornina, novaCena);
          resitve.add(novElement);
        }
      }

      Collections.sort(resitve, new Comparator<Pair<Integer, Integer>>() {
        @Override
        public int compare(Pair<Integer, Integer> a, Pair<Integer, Integer> b) {
          if (a.getFirst() > b.getFirst()) {
            return 1;
          } else if (a.getFirst() < b.getFirst()) {
            return -1;
          } else {
            if (a.getSecond() < b.getSecond()) {
              return 1;
            } else if (a.getSecond() > b.getSecond()) {
              return -1;
            } else {
              return 0;
            }
          }
        }
      });

      int trCena = 0;
      ArrayList<Integer> indexiZaOdstranit = new ArrayList<>();
      for (int j = 1; j < resitve.size(); ++j) {
        Pair<Integer, Integer> moznaResitev = resitve.get(j);
        if (moznaResitev.getSecond() <= trCena) {
          indexiZaOdstranit.add(j);
        } else {
          trCena = moznaResitev.getSecond();
        }
      }

      for (int j = 0; j < indexiZaOdstranit.size(); ++j) {
        int index = indexiZaOdstranit.get(j);
        Pair<Integer, Integer> neveljavnaResitev = resitve.get(index - j);
        System.out.println("Odstranimo (" + neveljavnaResitev.getFirst() + ", "
            + neveljavnaResitev.getSecond() + ")");
        resitve.remove(index - j);
      }

      System.out.println(izpisResitev(resitve, i + 1));
    }
  }
}