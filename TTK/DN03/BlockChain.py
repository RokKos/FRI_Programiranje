import hashlib as hl
from string import ascii_letters, digits

# Constants
kSetOfCharacters = ascii_letters + digits
kSetOfCharactersLen = len(kSetOfCharacters)
kNewLenSign = "#"
kMatchingLen = 11


def GenerateNextText(length, number):
    if (kSetOfCharactersLen ** length <= number):
        # This will be sing that we need larger string
        return kNewLenSign
    
    s = ""
    
    for i in range(length):
        chr_ind = number % kSetOfCharactersLen
        number //= kSetOfCharactersLen
        s += kSetOfCharacters[chr_ind]
    
    # Encoding because Python3 implementation of hashlib uses bytes-like object.
    return s.encode()


def HashText(text):
    # We define sha1 hashing algorithm
    sha1 = hl.sha1()
    # Passing in text for hashing
    sha1.update(text)
    # hashing
    return sha1.hexdigest()

def MatchingHash(hash_1, hash_2):
    return hash_1[:kMatchingLen] == hash_2[:kMatchingLen]



text_1 = b"ROK bo vsemu KOS"  # b is infront because Python3 implementation of hashlib uses bytes-like object.
hash_1 = HashText(text_1)
print (hash_1)

# Finding mathcing text
length = 1
number = 0
text_2 = ""
hash_2 = ""
while (True):
    text_2 = GenerateNextText(length, number)
    #print (text_2)
    if (text_2 == kNewLenSign):
        length += 1
        number = 0
        text_2 = GenerateNextText(length, number)

    # Each time we are creating new sha
    hash_2 = HashText(text_2)

    #print(hash_2)
    if (MatchingHash(hash_1, hash_2)):
        break

    number += 1


print (text_1, text_2)
print (hash_1, hash_2)
