from random import randint as ri
MAX = 1000
porabljene_kordinate = set()
predmeti = ri(0,MAX)
koraki = ri(0,MAX)
print(predmeti)
for i in range(predmeti):
	x = ri(-100,100)
	y = ri(-100,100)
	while ((x == 0 and y == 0) or ((x,y) in porabljene_kordinate)):

		x = ri(0,200)
		y = ri(0,200)
	porabljene_kordinate.add((x,y))

	print ("{0} {1} {2}".format(x, y, ri(1,3)))
#print(porabljene_kordinate)
print (koraki)

