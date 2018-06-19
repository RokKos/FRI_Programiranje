import java.util.Map;

public class UnarniMinus extends Izraz {
  private Izraz e;

  public UnarniMinus(Izraz e) {
    super();
    this.e = e;
  }

  @Override
  public Double eval(Map<String, Double> env) {
    return -e.eval(env);
  }

  @Override
  public String toString() {
    return "(- " + e + ")";
  }

  @Override
  public Izraz poenostavi() {
    System.out.println("Here");
    if (e instanceof UnarniMinus) {
      return ((UnarniMinus)e).e.poenostavi();
    }

    return this;
  }
}
