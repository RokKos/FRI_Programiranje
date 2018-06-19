import java.util.Map;

public class Plus extends Izraz {
  private Izraz e1;
  private Izraz e2;

  public Plus(Izraz e1, Izraz e2) {
    super();
    this.e1 = e1;
    this.e2 = e2;
  }

  @Override
  public Double eval(Map<String, Double> env) {
    return e1.eval(env) + e2.eval(env);
  }

  @Override
  public String toString() {
    return "(+ " + e1 + " " + e2 + ")";
  }

  /*@Override
  public Izraz poenostavi() {
    return new Plus(e1.poenostavi(), e2.poenostavi());
  }*/
}
