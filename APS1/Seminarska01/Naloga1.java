import java.util.*;

public class Naloga1 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		// Branje podatkov

		int NumTrakov = in.nextInt();
		int LengthTrakov = in.nextInt();
		LinkedList[] AllTrakovi = new LinkedList[NumTrakov];

		for (int i = 0; i < NumTrakov; ++i) {
			AllTrakovi[i] = new LinkedList(LengthTrakov);
		}

		String RawZaboji = in.next();
		String CurrentZaboj = new String();
		String[] ImenaZabojev = RawZaboji.split(",", -1);


		// Priprava spremenljivk

		int LokacijaVozicka = -1;
		int IndexZabojaNaVrhu = 0;
		String ZabojVVozicku = "";
		int IndexUkaz = 1;


		while (in.hasNext()) {
			String Ukaz = in.next();
			//System.out.println(Ukaz + " idU:" + IndexUkaz);
			switch (Ukaz) {
				case "PREMIK":
					LokacijaVozicka = in.nextInt() - 1;
					//sSystem.out.println(LokacijaVozicka);
					break;

				case "NALOZI":
					if (ZabojVVozicku != "") {
						break;
					}

					if (LokacijaVozicka == -1 && IndexZabojaNaVrhu < ImenaZabojev.length) {
						ZabojVVozicku = ImenaZabojev[IndexZabojaNaVrhu];
						IndexZabojaNaVrhu++;
					} else if (LokacijaVozicka != -1){
						Object valueOnTrack = AllTrakovi[LokacijaVozicka].RemoveFirst();
						if (valueOnTrack == null) {
							ZabojVVozicku = "";
						} else {
							ZabojVVozicku = valueOnTrack.toString();
						}
					}


					break;

				case "ODLOZI":
					if(LokacijaVozicka == -1) {
						break;
					}

					if (ZabojVVozicku == "") {
						break;
					}

					if (AllTrakovi[LokacijaVozicka].LookFirst() != null) {
						break;
					}

					AllTrakovi[LokacijaVozicka].AddFirst(ZabojVVozicku);
					ZabojVVozicku = "";

					break;


				case "GOR":
					if (LokacijaVozicka == -1) {
						break;
					}

					AllTrakovi[LokacijaVozicka].MoveElementsUp();

					break;

				case "DOL":
					if (LokacijaVozicka == -1) {
						break;
					}
					// QESTIONABLE TO MYSELF -> REVIEW THIS LINE
					if (AllTrakovi[LokacijaVozicka].SizeOfList == 0) {
						break;
					}

					AllTrakovi[LokacijaVozicka].MoveElementsDown();
					break;
			}
			// Uncoment for debugging
			/*
			System.out.println("loc: " + LokacijaVozicka + " na voz: " + ZabojVVozicku);
			for (int i = 0; i < NumTrakov; ++i) {
				System.out.print(i + 1 + ":");
				System.out.print("sizeOfList: " + AllTrakovi[i].SizeOfList + " " + AllTrakovi[i].AllowedSize);
				AllTrakovi[i].Print();
			}
			System.out.println("------------------------");
			*/
			IndexUkaz++;
		}

		for (int i = 0; i < NumTrakov; ++i) {
			System.out.print(i + 1 + ":");
			AllTrakovi[i].Print();
		}

        /*
		LinkedList linkedList = new LinkedList(5);

		System.out.println(linkedList.RemoveFirst());

		for (int i = 0; i < 7; ++i) {
			linkedList.AddFirst(i);
		}

		System.out.println(linkedList.RemoveFirst());
		linkedList.AddFirst(7);
		linkedList.Print();
		linkedList.MoveElementsUp();
		linkedList.Print();
		linkedList.MoveElementsUp();
		linkedList.Print();
		linkedList.MoveElementsDown();
		linkedList.Print();
		linkedList.MoveElementsDown();
		linkedList.Print();
		linkedList.MoveElementsDown();
		linkedList.Print();
		*/

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

class LinkedList {
	protected LinkedListElement first;
	protected LinkedListElement last;
	public int SizeOfList = 0;
	public int AllowedSize = 0;

	LinkedList (int size) {
		SizeOfList = 0;
		AllowedSize = size;
		first = new LinkedListElement(null);
		last = null;
	}

	public void AddFirst (Object value) {
		LinkedListElement newElement = null;  // spremenjeno
		if (first.next != null && first.next.element == null) {
			newElement = first.next;
			newElement.element = value;
		} else if (first.next == null){  // spremenjeno
			newElement = new LinkedListElement(value);

			LinkedListElement previusFirstElement = first.next;
			newElement.next = previusFirstElement;
			if (previusFirstElement != null) {
				previusFirstElement.previus = newElement;
			}

			newElement.previus = first;
			first.next = newElement;
			SizeOfList++;
		}


		// TO BI MORALO BITI POHANDLANO ZE PREJ
		if (SizeOfList == 1) {
			last = newElement;
		}else if (SizeOfList == 2) {
			last.previus = first.next;
			//System.out.println("#######");
			//System.out.println(last.element + " " + last.previus.element);
			//System.out.println("#######");
		} else if (SizeOfList > AllowedSize) {
			//System.out.println(SizeOfList + " " + AllowedSize);
			last = last.previus;
			// Garage collector pocisti za mano ta element, pri katerem tukaj zgubim referenco nanga
			last.next = null;
		}
	}

	public Object RemoveFirst () {

		if (SizeOfList == 0) { return null; }

		LinkedListElement returnElement = first.next;
		if (returnElement == null) { return null; }

		Object returnValue = returnElement.element;
		returnElement.element = null;
		//LinkedListElement emptyElement = new LinkedListElement(null, returnElement.next, first);
		//first.next = emptyElement;

		return returnValue;

	}

	public Object LookFirst () {
		if (first.next == null) { return null; }
		return first.next.element;
	}

	public void MoveElementsUp () {

		//System.out.println(last.element + " prev:" + last.previus.element + " first:" + first.next.element + "lastnext: " + last.next);
		LinkedListElement previusFirstElement = first.next;
		LinkedListElement emptyElement = new LinkedListElement(null, previusFirstElement, first);
		first.next = emptyElement;
		if (previusFirstElement != null) { previusFirstElement.previus = emptyElement;}


		if (SizeOfList >= AllowedSize) {
			last = last.previus;
			last.next = null;
		} else {
			SizeOfList++;
		}

	}

	public void MoveElementsDown () {
		LinkedListElement secondOnTrack = first.next.next;
		//first.next = null;
		first.next = secondOnTrack;
		SizeOfList = Math.max(SizeOfList - 1, 0);
	}

	public void Print () {
		LinkedListElement temp = first.next;
		String stackIzpis = "";
		while (temp != null) {


			if (temp.element == null) {
				stackIzpis += ",";
			} else {
				//if (stackIzpis == "") {
					//System.out.print("," + temp.element);
				//} else {
					stackIzpis += temp.element + ",";
			}
			/*
			if (temp.element != null) {
				if (temp == first.next || (temp.previus.element == null && temp.previus == first.next)) {
					System.out.print(temp.element);
				} else {
					System.out.print("," + temp.element);
				}

			} else {
				if (temp != first.next || (temp == first.next && first.next.next != null && first.next.next.element != null)) {
					System.out.print(",");
				}

			}*/

			temp = temp.next;
		}

		int endIndex = 0;
		for (int i = stackIzpis.length()-1; i>=0; --i) {
			if (stackIzpis.charAt(i) != ',') {
				endIndex = i + 1;
				break;
			}

		}

		System.out.println(stackIzpis.substring(0, endIndex));
		//System.out.println();
	}
}
