import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;

// To je mnozica in v njej se vsi elementi pojavijo samo enkrat
Set<Integer> set = new HashSet<Integer>();

set.add(5);  // dodajanje
set.remove(5);  // Odstrani specificen element
int s = set.size();  // Velikost
boolean prazna = set.isEmpty();  // Prazen
boolean notri = set.contains(5);  // Ali ima dolocen element

// Posortirano
TreeSet<Integer> drevensaOblika = new TreeSet<Integer>(set);
int prvi = drevesnaOblika.first();  // poolFirst ga se odstrani
int zadnji = drevesnaOblika.last();  // poolLast ga se odstrani

int infinum = drevesnaOblika.floor(5);  // Vrne najvecji element, ki je manjsi od 5
int infinum = drevesnaOblika.ceiling(5);  // Vrne najmanjsi element, ki je vecji od 5

// + ima se vse zgornje funkcije od normalnega Set-a
