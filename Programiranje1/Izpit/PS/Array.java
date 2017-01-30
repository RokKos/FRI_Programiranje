import java.util.Arrays;

final int SIZE = 10;
// definicija(lahko je kateregakoli tipa, tudi class)
int[] seznam = new int[SIZE];  // to ga ze nastavi na default vrednost (0)

// napolni z 0 odKje(inkluzivno) do doKje(eksluzivno), ce spustimo ta dva argumenta
// bo napolnilo celoten array
Arrays.fill(seznam, 5, odKje, doKje);

// zgornja metoda je ekvivalenta temu
for (int i = 0; i < SIZE; ++i) {
	seznam[i] = 5;
}

Arrays.sort(seznam, fromIndex, toIndex);  //Uredi seznam narascajoce (1,2,3,4, ...)

// Ko imamo urejen sezna in samo v tem primeru lahko poklicemo to funkcijo
int kajIscemo = 5;  // seznam in kajIscemo morata biti enakega tipa
Arrays.binarySearch(seznam, kajIscemo);  // Ki nam vrne mesto elementa

boolean enaka = Arrays.equals(seznam1, seznam2);  // primerja
// za primerjavo gnezdenih seznamov
boolean enaka = Arrays.deepEquals(new int[][], new int[][]);


// Dinamicni array
import java.util.ArrayList;

ArrayList<Integer> seznam = new ArrayList<Integer>();

seznam.add(4);
seznam.add(1,3);  // Da na index 1 integer 3
seznam.remove(4);  // Odstrani specificen element
seznam.remove(0);  // Odstrani na specificnem indeksu
int pos = seznam.indexOf(3);  // Dobi pozicijo
seznam.set(0, 1);  // Nastavi element na poziciji 0 na 1
int dobi = seznam.get(2);  // Dobi elemnt na 2 poziciji
int s = seznam.size();
boolean vsebuje = seznam.contains(3);
