import java.util.*;

public class Naloga4 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int NumElementov = in.nextInt();
		int MAX_TEZA = in.nextInt();
		int sum = 0;

		Element[] vsiElementi = new Element[NumElementov];

		for (int i = 0; i < NumElementov; ++i) {
			String imeElementa = in.next();
			int massElementa = in.nextInt();
			Element e = new Element(imeElementa, massElementa);
			vsiElementi[i] = e;
		}

		Arrays.sort(vsiElementi);

		/*for (int i = 0; i < NumElementov; ++i) {
			System.out.println(vsiElementi[i].Tip + " " + vsiElementi[i].Mass);
		}
		*/

		// Damo vse skupaj
		for (int i = 0; i < NumElementov; ++i) {
			if (vsiElementi[i] == null) { continue; }

			int curMass = vsiElementi[i].Mass;
			String curTip = vsiElementi[i].Tip;

			for (int j = i + 1; j < NumElementov; ++j) {
				if (vsiElementi[j] == null) { continue; }
				if (!curTip.equals(vsiElementi[j].Tip)) { break; }

				if (curMass + vsiElementi[j].Mass <= MAX_TEZA) {
					curMass += vsiElementi[j].Mass;
					vsiElementi[j] = null;
				}

			}

			vsiElementi[i].Mass = curMass;
		}
		/*System.out.println("$$$$$$$$$$$$$$");
		for (int i = 0; i < NumElementov; ++i) {
			if (vsiElementi[i] == null) { continue; }
			System.out.println(vsiElementi[i].Tip + " " + vsiElementi[i].Mass);
		}*/

		// Razlicni tipi
		for (int i = 0; i < NumElementov; ++i) {
			if (vsiElementi[i] == null) { continue; }

			int curMass = vsiElementi[i].Mass;

			for (int j = i + 1; j < NumElementov; ++j) {
				if (vsiElementi[j] == null) { continue; }

				if (curMass + vsiElementi[j].Mass <= MAX_TEZA) {
					curMass += vsiElementi[j].Mass;
					vsiElementi[j] = null;
					sum++;
				}

			}

			vsiElementi[i].Mass = curMass;
		}

		//System.out.println("$$$$$$$$$$$$$$");
		for (int i = 0; i < NumElementov; ++i) {
			if (vsiElementi[i] == null) { continue; }
			//System.out.println(vsiElementi[i].Tip + " " + vsiElementi[i].Mass);
			sum += 2;
		}

		System.out.println(sum);
	}

}

class Element implements Comparable<Element> {
	public String Tip;
	public int Mass;

	Element (String _tip, int _mass) {
		Tip = _tip;
		Mass = _mass;
	}

	public int compareTo(Element e) {
		if (Tip.compareTo(e.Tip) == 0) {
			return e.Mass - Mass;
		}

		return Tip.compareTo(e.Tip);
	}
}