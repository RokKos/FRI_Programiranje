import random

LENGTH = 18

first = ""
second = ""
first_len = random.randint(1,LENGTH)
for i in range(first_len):
	if (i == 0):
		first += str(random.randint(1,9))
	else:
		first += str(random.randint(0,9))

c = 0
while c < first_len:
	t = random.randint(1,9)
	if (t + c > first_len):
		t = first_len - c
		c = first_len
	second += str(t)
	c += t

print (first + " " + second)
	