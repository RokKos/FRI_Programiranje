import java.util.Scanner;
Scanner in = new Scanner(System.in);
byte b = in.nextByte();
int i = in.nextInt();
long l = in.nextLong();
double d = in.nextDouble();
String s = in.next();  // Vrne naslednji string
String line = in.nextLine();  // Prebere celotno vrstico in skoci v novo

// Branje do konca inputa
while(in.hasNextInt()){
	int a = in.nextInt();
}
// Namesto hasNextInt bi lahko bilo tudi:
// -hasNext()
// -hasNextDouble()
// -hasNextLong
// -hasNextLine
