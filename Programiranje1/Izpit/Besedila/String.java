char a = besedilo.charAt(index);
int l = besedilo.length();
String s = str1.concat(str2);  // Doda str2 nakoncu str1

// Primerjanje
boolean enaka = string1.equals(string2);  // NUJNO UPORABLJAJ TO ZA PRIMERJANJE
int pred = string1.compareTo(string2);  // Vrne -1 ce je str1 pred str2 in 1 obratno, 0 ce sta enaka
// compareToIgnoreCase in equalsIgnoreCase je tudi na voljo

// Manipulacija stringov
String sub = str1.substring(zacetek, konec);
String[] s = str1.split(' ');  // Split po nekem znaku ali regex pravilu

String rep = str1.replace(kateriChar, zKaterimChar);  // zamenja vse pojavitve
String rep = str1.replaceAll(regex, sCim);  // zamnenja vse pojavitve, ki ustrezajo regex(lahko tudi normalen string)
String rep = str1.replaceFirst(regex, sCim);  // zamnenja prvo pojavitev, ki ustreza regexu

String lower = str1.toLowerCase();
String upper = str1.toUpperCase();

boolean match = str1.matches(regex);

// Iskanje po stringu
int index = str1.indexOf(chr, fromIndex);  // Namesto chr lahko tudi String
int index = str1.lastIndexOf(chr, fromIndex);  // Namesto chr lahko tudi String

boolean seZacne = str1.startsWith(str2, fromIndex);
boolean seKonca = str1.startsWith(str2);
