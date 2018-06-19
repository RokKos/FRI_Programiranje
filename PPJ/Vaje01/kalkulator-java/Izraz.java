import java.util.Map;

public abstract class Izraz {
  public abstract Double eval(Map<String, Double> env);

  public abstract String toString();

  public Izraz poenostavi() {
    System.out.println("Hereprvi");
    return this;
  }
}
