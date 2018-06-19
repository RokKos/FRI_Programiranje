import java.util.Map;

public class Konstanta extends Izraz {
	private Double vrednost;
	
	public Konstanta(Double vrednost) {
		super();
		this.vrednost = vrednost;
	}

	@Override
	public Double eval(Map<String, Double> env) {
		return this.vrednost;
	}

	@Override
	public String toString() {
		return vrednost.toString();
	}
}
