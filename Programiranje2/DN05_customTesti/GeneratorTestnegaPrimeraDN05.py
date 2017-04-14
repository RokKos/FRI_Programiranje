from random import randint as ri
MAX = 1000
N = ri(0,MAX)
print(N)
r = ""
for _ in range(N):
	r += str(ri(0,MAX**3)) + " "

print (r[:-1]) 
