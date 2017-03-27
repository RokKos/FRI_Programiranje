from random import randint as ri
MAX = 1000
predmeti = ri(0,MAX)
koraki = ri(0,MAX)
print(predmeti)
for i in range(predmeti):
	x = ri(0,200)
	y = ri(0,200)
	while (x == 0 and y == 0):
		x = ri(0,200)
		y = ri(0,200)

	print ("{0} {1} {2}".format(x, y, ri(1,3)))

print (koraki)
