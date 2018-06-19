import java.util.Map;

public class Krat extends Izraz {
	private Izraz e1;
	private Izraz e2;

	public Krat(Izraz e1, Izraz e2) {
		super();
		this.e1 = e1;
		this.e2 = e2;
	}

	@Override
	public Double eval(Map<String, Double> env) {
		return e1.eval(env) * e2.eval(env);
	}

	@Override
	public String toString() {
		return "(* " + e1 + " " + e2 + ")";
	}
}
