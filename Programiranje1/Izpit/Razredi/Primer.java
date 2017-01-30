public class Primer {
	private int skrito;
	private static final int skritoSamoEnoKoncno = 1;
	protected int polaPola;  // Vidno razredom, ki dedujejo ta class, ostalim ne
	public int vsiVidijo;

	// Constructor
	public Primer () {
		this.skrito = 0;
		this.polaPola = 0;
	}
	public Primer (int _skrito, int _polaPola) {
		this.skrito = _skrito;
		this.polaPola = _polaPola;
	}
	
	protected int Metoda(){		// da lahko funkcijo Metoda klicemo  iz podrazredov mora biti VSAJ protected
		return 0;		// po stopnjah dostopnosti private<default<protected<public
					// vec info na https://www.tutorialspoint.com/java/java_access_modifiers.htm
	}

}

public class PodPrimer extends Primer {
	private int samoOdTega;

	public PodPrimer(int _skrito, int _polaPola, int _samoOdTega) {
		super(_skrito, _polaPola);  // Klic contruktorja od Primer
		this.samoOdTega = _samoOdTega;
	}
	@Override
	public int Metoda() {
		super.Metoda();  // Klic metode Primer
		return 1;  // Mogoce je narobe
	}

}

public abstract class AbstraktenPrimer {
	// Enak kot primer samo da so v njem definirane metode in spremenljivke, ki jih kasneje
	// drugi razredi podeduje, kot nek modelcek po katerem se dela ostale clase
	// PAZI: ce podedujes tak class moras napisati definicije za vse njegove abstraktne metode

	// Ce imamo abstrakno metodo hocemo, da imajo vsi, ki se dedujejo iz tega to metodo
	// ampak jo vsak po svoje implementira
	public abstract int Metoda();
}

public interface interfacePrimer {
	// V interfacu samo specificiramo katere metode imamo(vse so abstrakne) in
	// tudi class sam je abstrakten, ce ga podedujemo modramo definirati vse njegove
	// metode

	public void Metoda();
}

public class interfacePodPrimer implements interfacePrimer { // Lahko bi implementira si en class takole : Primer, SeEnPrimer
	public void Metoda() {
		return 0;
	}

}

public static void main(String[] args) {
	Primer[] t = Primer[3];
	t[0] = new Primer();
	t[1] = new Primer(1,2);
	t[2] = new PodPrimer(1,2,3);
}


// Sortiranje objektov
public class Primerjava implements Comparable<Primerjava> {
	private int a;
	@Override
	public int compareTo (Primerjava other) {
		if (this.a < other.a) {
			return -1;
		} else if (this.a > other.a) {
			return 1;
		}
		return 0;
	}
}

// Drug Primer
import java.util.Comparator;

private class Obj {
	public int c;
}

private static class PrimerjajObj implements Comparator<Obj> {
	@Override
	public int compare (Obj a, Obj b) {
		if (a.c < b.c) {
			return -1;
		} else if (a.c > b.c) {
			return 1;
		}
		return 0;
	}
}

boolean jeTegaTipa = obj instanceof Primer;
