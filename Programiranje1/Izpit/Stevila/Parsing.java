String s = "123456789";
int a = Integer.parseInt(s);
long l = Long.parseLong(s);
double d = Double.parseDouble(s);

// Dodatna moznost se spreminja iz razlicnih sistemov
int deset = Integer.parseInt("10101010", 2);  // Pretvori iz dvojiskega v desetisko
String trojisko = Integer.toString(15, 3);  // Pretvori iz desetiskega v trojisko

String besedilo = "Neka dolga poved v kateri mores dobit vsako besedo";
String[] besede = besedilo.split(" ");  // Lahko tudi po , . !, ali celo po regex
for (int i = 0; i < besede.length; ++i) {
	System.out.println(besede[i]);
}
