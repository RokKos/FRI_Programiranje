from random import randint as ri
choice = ri(1,2)
length = ri(0,10000)
print (choice, length)
out = ""
if (choice == 1):
	while length > 0:
		seq = ri(1,length)
		if (seq > length):
			seq = length
		length -= seq
		number = str(ri(65,90))
		for i in range(0,seq):
			out += number + " "

else:
	for i in range(0,length):
		out += "{0} {1} ".format(ri(1,1000),ri(65,90))

print (out[:-1])
