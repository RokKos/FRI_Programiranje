import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Kalkulator {
  public static void main(String[] args) throws IOException {
    HashMap<String, Double> env = new HashMap<String, Double>();
    env.put("x", 42.0);
    env.put("y", 3.14);
    env.put("z", 1.0);

    Scanner stdin = new Scanner(System.in);
    while (true) {
      try {
        System.out.print("Izraz: ");
        String s = stdin.nextLine();
        if (s.isEmpty())
          break;
        Izraz e = Parser.parse(s);
        System.out.println("Sintaktično drevo: " + e);
        Izraz e2 = e.poenostavi();
        System.out.println("Poenostavljeno: " + e2);
        Double v = e.eval(env);
        System.out.println("Vrednost: " + v);
      } catch (Parser.Error e) {
        System.err.println(e.getMessage());
      } catch (NoSuchElementException e) {
        break;
      }
    }
    stdin.close();
  }
}
