import java.util.Map;

public class Spremenljivka extends Izraz {
	private String ime;
	
	public Spremenljivka(String ime) {
		super();
		this.ime = ime;
	}

	@Override
	public Double eval(Map<String, Double> env) {
		return env.get(ime);
	}

	@Override
	public String toString() {
		return ime;
	}
}
