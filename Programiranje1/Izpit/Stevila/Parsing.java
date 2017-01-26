String s = "123456789";
int a = Integer.parseInt(s);
long l = long.parseLong(s);
double d = Double.parseDouble(s);

String besedilo = "Neka dolga poved v kateri mores dobit vsako besedo";
String[] besede = besedilo.split(" ");  // Lahko tudi po , . !, ali celo po regex
for (int i = 0; i < besede.length; ++i) {
	System.out.println(besede[i]);
}
