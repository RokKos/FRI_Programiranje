double pi = Math.PI;  // Math.E
double a = abs(a);  // Tudi za int, long in float
double naj = Math.max(int, int)  // lahko tudi double, float in long
double naj = Math.min(int, int)  // lahko tudi double, float in long

// Trigonometricne funkcije
double kot = asin(val) / Math.PI * 180;  // acos, atan vrne vrednost v PI radianih
double val = sin(kot * Math.PI / 180);  // cos, tan
// Mozna pretvorba tudi z toDegrees ali toRadians

// Hiperbolicne funkcije
double h = sinh(val);  // cosh, tanh

// Zaokrozevanje
double navzgor = Math.ceil(decimalka);
double navzdol = Math.flor(decimalka);
long navzdol = Math.round(decimalka);  // vrne celo stevilo(lahko tudi int)

// Korenjenje
double kvadratni = Math.sqrt(koren);
double kubicni = Math.cbrt(koren);

// Eksponetna funkcija
double potenca = Math.pow(osnova, eksponent);
double eNaEks = Math.exp(naDecimalko);
double obratno = Math.log(naravni);  // lahko tudi z desetisko osnovo (log10(a))

// Random
double r = Math.random()  // vrne od 0.0 do 1.0
