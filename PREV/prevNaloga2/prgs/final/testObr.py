n = 300
print(n)
obrCifra = 1
while (n > 0):
    print (obrCifra)
    obrCifra *= 10 
    obrCifra += (n % 10)
    print (obrCifra)
    n //= 10

print ("-----------------")
print (obrCifra)
while (obrCifra > 1):
    print (obrCifra)
    print (str(obrCifra % 10))
    obrCifra //=10
