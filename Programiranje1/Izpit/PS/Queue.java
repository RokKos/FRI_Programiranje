import java.util.Queue;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Comparator;

ArrayDeque<Integer> q = new ArrayDeque<Integer>();

q.addLast(3);  // doda na konec (addFirst doda od spredaj)
int prvi = q.pollFirst();  // Dobi in odstrani prvi element (pollLast dobi zadnjega)
int prvi = q.peekFirst();  // Dobi vendar ne odstani
// Obe metodi vrneta null ce je queue prazen

boolean prazna = q.isEmpty();

// Vrsta pri kateri so elementi urejeni po prioritety
Comparator<Objekt> comparator = new PrimerjaObjekt();  // kako se bojo elementi primerjali
PriorityQueue<Objekt> q = new PriorityQueue<Objek>(velikost, comparator);

// Ta objekt je lahko tudi tipa int, long, string itd. ali pa celo kaksen svoj custom class
q.add(objekt);
Objekt o = q.poll();  // Metodi delujeta enako kot zgoraj le da vrneta prvega po prioriteti
Objekt o = q.peek();
boolean prazna = q.isEmpty();

private static class PrimerjajObjekt implements Comparator<Objekt> {
	@Override
	public int compare (Objekt a, Objekt b) {
		if (a.c > b.c) {
			return -1;
		}
		if (a.c < b.c) {
			return 1;
		}
		return 0;
	}

}
