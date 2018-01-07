from time import time
from random import seed, randint

current_miliseconds = int(round(time() * 1000))
seed(current_miliseconds)

MAX_N = 3000

def pick_n_and_m():
	n = randint(0, MAX_N)
	m = randint(0, int(n**2))
	return (n, m)

def pick_connection(used_connections, cicles):
	while True:
		a,b = randint(1,n), randint(1,n)
		tup = (a,b)
		tup_inv = (tup[1], tup[0])
		is_valid = a != b and tup not in used_connections and (cicles or tup_inv not in used_connections)
		if is_valid:
			return tup


if __name__ == "__main__":
	n, m = pick_n_and_m()
	print(n)
	print(n)
	used_connections = set()
	for connection in range(n):
		tup = pick_connection(used_connections, False)
		used_connections.add(tup)
		print(str(tup[0]) + "," + str(tup[1]))
