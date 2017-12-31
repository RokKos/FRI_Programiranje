import java.util.*;

public class Naloga3 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int maxMassVlaka = in.nextInt();
		int maxNumVagonov = in.nextInt();

		DoubleLinkedList Vlak = new DoubleLinkedList(maxNumVagonov, maxMassVlaka);

		for (int i = 0; i < maxNumVagonov; ++i) {
			int maxMassVagona = in.nextInt();
			int maxTipTovora = in.nextInt();

			Vagon vagon = new Vagon(maxMassVagona, maxTipTovora);

			for (int j = 0; j < maxTipTovora; ++j) {
				String imeTipa = in.next();
				int tezaTipa = in.nextInt();
				vagon.DodajTovor(imeTipa, tezaTipa);
			}

			Vlak.AddLast(vagon);
		}

		//Vlak.Print();

		while (in.hasNext()) {
			String ukaz = in.next();
			//System.out.println(ukaz);
			switch (ukaz){
				case "ODSTRANI_LIHE":
					Vlak.RemoveOddElements();
					break;
				case "ODSTRANI_HET":
					int n = in.nextInt();
					//System.out.print(n);
					Vlak.RemoveToFullElements(n);
					break;
				case "ODSTRANI_ZAS":
					int p = in.nextInt();
					Vlak.RemoveProcentElements(p);
					break;
				case "OBRNI":
					Vlak.InverseList();
					break;
				case "UREDI":
					Vlak.SortAscending();
					break;
				case "PREMAKNI":
					String tip = in.next();
					int indVagon1 = in.nextInt();
					int indVagon2 = in.nextInt();
					Vlak.MoveMass(tip, indVagon1, indVagon2);
					break;

			}
			//Vlak.Print();
		}
		//System.out.println();
		Vlak.Print();

		if (Vlak.CheckVlak()) { System.out.println("DA"); }
		else { System.out.println("NE"); }
	}

}


class LinkedListElement {
	public LinkedListElement next;
	public LinkedListElement previus;
	public Object element;

	LinkedListElement(Object obj) {
		element = obj;
		next = null;
		previus = null;
	}

	LinkedListElement(Object obj, LinkedListElement nxt) {
		element = obj;
		next = nxt;
		previus = null;
	}

	LinkedListElement(Object obj, LinkedListElement nxt, LinkedListElement prev) {
		element = obj;
		next = nxt;
		previus = prev;
	}

}

class LinkedList{
	protected LinkedListElement first;
	protected LinkedListElement last;
	private int SizeOfList = 0;

	LinkedList () {
		first = new LinkedListElement(null, null, null);
		last = null;
		SizeOfList = 0;
	}

	public void addLast (Object obj) {

		LinkedListElement newElement = new LinkedListElement(obj, null);
		if (SizeOfList == 0) {
			first.next = newElement;
			last = first;
		} else {
			last.next.next = newElement;
			last = last.next;
		}

		SizeOfList++;
	}

	public void Print () {

		LinkedListElement temp = first.next;
		while (temp != null) {
			System.out.print(temp.element);

			temp = temp.next;
		}
		System.out.println();
	}

	public void addFirst(Object obj) {

		LinkedListElement newElement = new LinkedListElement(obj, null);

		if (SizeOfList == 0) {
			first.next = newElement;
			last = first;
		} else if (SizeOfList == 1){
			last = last.next;
			newElement.next = first.next;
			first.next = newElement;
		} else {
			newElement.next = first.next;
			first.next = newElement;
		}

		SizeOfList++;
	}

	public int SumAllElements () {
		LinkedListElement temp = first.next;
		int sum = 0;
		while (temp != null) {
			Tovor t = (Tovor)temp.element;
			sum += t.Mass;
			temp = temp.next;
		}
		return sum;
	}

	public int RemoveTovor(String tip) {
		if (SizeOfList == 0) { return -1; }

		LinkedListElement temp = first.next;
		LinkedListElement prev = null;
		int mass = -1;
		boolean removedElement = false;
		while (temp != null) {
			Tovor vag = (Tovor)temp.element;
			if (vag.Tip.equals(tip)) {
				mass = vag.Mass;

				if (SizeOfList == 1) {
					last = null;
					first.next = null;
				} else {

					if (temp == last) {
						last = prev;
					} else if (temp == last.next) {
						last = temp;
					}


					// check
					if (prev != null) {
						prev.next = temp.next;
					} else {
						first.next = temp.next;
					}
					temp = null;
				}



				removedElement = true;

				break;
			}


			prev = temp;
			temp = temp.next;
		}

		if (removedElement) { SizeOfList--; }
		return mass;
	}

	// If tovor was appended return true else false
	public boolean AddTovor(String tip, int mass) {
		LinkedListElement temp = first.next;
		while (temp != null) {
			Tovor vag = (Tovor)temp.element;
				if (vag.Tip.equals(tip)) {
					vag.Mass += mass;  // check if ads
					return false;
				}

			temp = temp.next;
		}

		Tovor novTovor = new Tovor(tip, mass);
		addLast(novTovor);

		return true;
	}
}


class DoubleLinkedList {
	protected LinkedListElement first;
	protected LinkedListElement last;
	private int SizeOfList = 0;

	DoubleLinkedList (int size, int maxMass) {
		SizeOfList = 0;
		Lokomotiva lokomotiva = new Lokomotiva(maxMass, size);
		first = new LinkedListElement(lokomotiva);
		last = first;
	}

	public void Print () {

		// Lokomotiva
		Lokomotiva lokomotiva = (Lokomotiva)first.element;
		System.out.println(lokomotiva.MaxMass + " " + lokomotiva.NumVagonov);

		LinkedListElement temp = first.next;
		while (temp != null) {
			Vagon v = (Vagon)temp.element;
			System.out.println(v.MaxMass + " " + v.MaxNumTovora); //+ "( " + v.ActualMass + " )");
			v.Print();
			temp = temp.next;
		}

		//System.out.println("##############################");
	}

	public void AddFirst(Object obj) {
		LinkedListElement previusFirstElement = first.next;
		LinkedListElement newFirst = new LinkedListElement(obj, previusFirstElement);
		if (previusFirstElement != null) {
			previusFirstElement.previus = newFirst;
		}

		first.next = newFirst;
		newFirst.previus = first;

		if (SizeOfList == 0) {
			last = newFirst;
		}
		SizeOfList++;
	}

	public void AddLast(Object obj) {
		LinkedListElement previusLastElement = last;
		LinkedListElement newLast = new LinkedListElement(obj, null, previusLastElement);
		previusLastElement.next = newLast;
		last = newLast;

		SizeOfList++;
	}

	public void RemoveOddElements () {
		LinkedListElement temp = first.next;
		int index = 0;
		Lokomotiva lokomotiva = (Lokomotiva)first.element;
		while (temp != null) {
			if (index % 2 == 1) {
				LinkedListElement previus = temp.previus;
				LinkedListElement next = temp.next;

				previus.next = next;
				if (next != null) {
					next.previus = previus;
				} else {
					last = previus;
				}

				lokomotiva.NumVagonov--;

			}
			temp = temp.next;
			index++;
		}
	}

	public void RemoveToFullElements (int N) {
		LinkedListElement temp = first.next;
		Lokomotiva lokomotiva = (Lokomotiva)first.element;
		while (temp != null) {
			Vagon v = (Vagon)temp.element;
			if (v.MaxNumTovora >= N) {
				LinkedListElement previus = temp.previus;
				LinkedListElement next = temp.next;

				previus.next = next;
				if (next != null) {
					next.previus = previus;
				} else {
					last = previus;
				}

				lokomotiva.NumVagonov--;

			}
			temp = temp.next;
		}
	}

	public void RemoveProcentElements (int p) {
		LinkedListElement temp = first.next;
		Lokomotiva lokomotiva = (Lokomotiva)first.element;
		while (temp != null) {
			Vagon v = (Vagon)temp.element;

			// Izracunaj procent
			int sumMass = v.TovorVagona.SumAllElements() * 100;

			if (sumMass /  v.MaxMass >= p) {
				LinkedListElement previus = temp.previus;
				LinkedListElement next = temp.next;

				previus.next = next;
				if (next != null) {
					next.previus = previus;
				} else {
					last = previus;
				}

				lokomotiva.NumVagonov--;

			}
			temp = temp.next;
		}
	}

	public void InverseList() {
		LinkedListElement previusFirst = first.next;
		LinkedListElement previusLast = last;
		LinkedListElement temp = last;
		LinkedListElement prev = null;
		while (temp != first) {
			temp.next = temp.previus;
			temp.previus = prev;
			prev = temp;
			temp = temp.next;
		}
		previusFirst.next = null;
		previusLast.previus = first;
		first.next = previusLast;
		last = previusFirst;
	}

	public void SortAscending () {
		boolean smoZamenjaliEnElement = true;
		while(smoZamenjaliEnElement) {
			smoZamenjaliEnElement = false;
			LinkedListElement temp = first.next;
			// Important to check both because we could just change order and then something would go wrong
			while (temp != null && temp.next != null) {  // You could optimize to go every time one less
				Vagon vFirst = (Vagon)temp.element;
				Vagon vSecond = (Vagon)temp.next.element;
				if (vFirst.ActualMass > vSecond.ActualMass) {
					//System.out.println("Menjamo:" + vFirst.ActualMass + " z " + vSecond.ActualMass);
					//Print();
					//System.out.println();

					// ORDER IS IMPORTANT
					// Dont change unless thought of
					LinkedListElement changeFirst = temp;
					LinkedListElement changeSecond = temp.next;

					if (changeSecond == last) { last = changeFirst; }

					changeFirst.next = changeSecond.next;
					changeSecond.previus = changeFirst.previus;
					if (changeFirst.previus != null) {
						changeFirst.previus.next = changeSecond;  // important
					}

					if (changeSecond.next != null) {
						changeSecond.next.previus = changeFirst;  // important
					}

					changeFirst.previus = changeSecond;
					changeSecond.next = changeFirst;
					smoZamenjaliEnElement = true;
				}
				temp = temp.next;
			}

		}
	}
	// TODO CHECK THIS FUNCTION
	public void MoveMass(String tip, int indexVagon1, int indexVagon2) {
		LinkedList tovor1 = null;
		LinkedList tovor2 = null;
		Vagon vagon1 = null;
		Vagon vagon2 = null;
		LinkedListElement temp = first.next;
		int index = 0;
		while (temp != null) {
			if (index == indexVagon1) {
				vagon1 = (Vagon)temp.element;
				tovor1 =  vagon1.TovorVagona;
			}
			if (index == indexVagon2) {
				vagon2 = (Vagon)temp.element;
				tovor2 = vagon2.TovorVagona;
			}
			index++;
			temp = temp.next;
		}

		if (tovor1 == null || tovor2 == null) {
			return;
		}

		int massToMove = tovor1.RemoveTovor(tip);
		if (massToMove == -1) { return; }

		vagon1.ActualMass -= massToMove;
		vagon2.ActualMass += massToMove;
		vagon1.MaxNumTovora -= 1;


		if (tovor2.AddTovor(tip, massToMove)) {
			vagon2.MaxNumTovora += 1;
		}


	}

	public boolean CheckVlak (){
		Lokomotiva lokomotiva = (Lokomotiva)first.element;

		LinkedListElement temp = first.next;
		int sum = 0;
		while (temp != null) {
			Vagon v = (Vagon)temp.element;
			if (v.MaxMass < v.ActualMass) { return false; }
			sum += v.ActualMass;

			temp = temp.next;
		}

		if (lokomotiva.MaxMass < sum) { return false; }

		return true;
	}

}

class Lokomotiva {
	public int MaxMass = 0;
	public int NumVagonov = 0;

	Lokomotiva (int maxMass, int numVagonov) {
		MaxMass = maxMass;
		NumVagonov = numVagonov;
	}
}

class Vagon {
	public int MaxMass = 0;
	public int MaxNumTovora = 0;
	public int ActualMass = 0;
	public LinkedList TovorVagona;

	Vagon (int maxMass, int maxNumTovora) {
		TovorVagona = new LinkedList();
		MaxMass = maxMass;
		MaxNumTovora = maxNumTovora;
		ActualMass = 0;
	}

	public void DodajTovor (String tip, int mass) {
		Tovor novTovor = new Tovor(tip, mass);
		TovorVagona.addLast(novTovor);
		ActualMass += mass;
	}

	public void Print () {

		LinkedListElement temp = TovorVagona.first.next;
		while (temp != null) {
			Tovor tovor = (Tovor) temp.element;
			System.out.println(tovor.Tip + " " + tovor.Mass);

			temp = temp.next;
		}
	}

}

class Tovor {
	public String Tip = "";
	public int Mass = 0;

	Tovor(String tip, int mass) {
		Tip = tip;
		Mass = mass;
	}
}