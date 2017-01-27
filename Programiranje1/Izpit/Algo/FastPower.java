int mod = 13;  // Neko prastevilo, ki predstavlja sistem

private int FastPow (int a, long e) {
	int rez = 1;
	while (e > 0) {
		if (e % 2 == 1) {
			rez = (rez * a) % mod;
		}
		a = a * a % mod;
		e /= 2;
	}

	return rez;
}
