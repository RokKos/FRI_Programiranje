import java.util.Scanner;

public class Izziv10 {
  private static String IzpisStanja(int[] stanje) {
    if (stanje.length == 0) {
      return "";
    }

    // stanje[-] bo vedno bilo 0 in nikoli Inf
    String s = stanje[0] + " ";

    for (int i = 1; i < stanje.length; ++i) {
      if (stanje[i] == Integer.MAX_VALUE) {
        s += "Inf ";
      } else {
        s += stanje[i] + " ";
      }
    }

    return s;
  }

  public static void main(String[] args) {
    // Parsing argumentov
    if (args.length == 0) {
      System.out.println("Ni podanih argumentov.");
      System.exit(1);
      return;
    }

    int stVozlisc = Integer.parseInt(args[0]);

    int[][] povezave = new int[stVozlisc][stVozlisc];

    for (int i = 0; i < stVozlisc; ++i) {
      for (int j = 0; j < stVozlisc; ++j) {
        povezave[i][j] = Integer.MAX_VALUE;
      }
    }

    Scanner in = new Scanner(System.in);

    while (in.hasNextInt()) {
      int zVozl = in.nextInt();
      int kVozl = in.nextInt();
      int cena = in.nextInt();

      povezave[zVozl][kVozl] = cena;
    }

    int[] prejsnjeStanje = new int[stVozlisc];

    for (int i = 1; i < stVozlisc; ++i) {
      prejsnjeStanje[i] = Integer.MAX_VALUE;
    }

    System.out.println("h0: " + IzpisStanja(prejsnjeStanje));

    for (int h = 1; h < stVozlisc; ++h) {
      int[] novoStanje = new int[stVozlisc];

      for (int i = 1; i < stVozlisc; ++i) {
        novoStanje[i] = prejsnjeStanje[i];
      }

      for (int v = 0; v < stVozlisc; ++v) {
        for (int e = 0; e < stVozlisc; e++) {
          if (povezave[e][v] == Integer.MAX_VALUE || prejsnjeStanje[e] == Integer.MAX_VALUE) {
            continue;
          }
          // System.out.println("v: " + v + " e:" + e + " prej:" + prejsnjeStanje[v]
          //    + " prejE:" + prejsnjeStanje[e] + " cena:" + povezave[e][v]);
          novoStanje[v] = Math.min(
              novoStanje[v], Math.min(prejsnjeStanje[v], prejsnjeStanje[e] + povezave[e][v]));
        }
      }
      prejsnjeStanje = novoStanje;
      System.out.println("h" + h + ": " + IzpisStanja(prejsnjeStanje));
    }
  }
}
