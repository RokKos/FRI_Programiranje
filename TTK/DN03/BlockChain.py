import hashlib as hl
from string import ascii_letters, digits

# Constants
kSetOfCharacters = ascii_letters + digits
kSetOfCharactersLen = len(kSetOfCharacters)
kNewLenSign = "#"
kMatchingLen = 11


# Globals
ComputedHashes_ = {}

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


###################
# TASK 1

# Finding mathcing text
length = 1
number = 0
text_1 = ""
hash_1 = ""
text_2 = ""
hash_2 = ""
while (True):
    text_2 = GenerateNextText(length, number)
    if (text_2 == kNewLenSign):
        length += 1
        number = 0
        text_2 = GenerateNextText(length, number)

    # Each time we are creating new sha
    hash_2 = HashText(text_2)

    hash_key = hash_2[:kMatchingLen]

    if (hash_key in ComputedHashes_):
        text_1, hash_1 = ComputedHashes_[hash_key]

        text_1 = text_1.decode()
        text_2 = text_2.decode()

        print ("Text1: " + text_1)
        print ("Text2: " + text_2)
        print ("hash1: " + hash_1)
        print ("hash2: " + hash_2)
        break

    # Adding tupple in hash
    ComputedHashes_[hash_key] = (text_2, hash_2)


    number += 1


##################################################
# RESULTS OF TASK 1:
# Text1: E4cp
# Text2: I6it
# hash1: 041af7f44dfc2aa50cd947131d57ecb771a24bca
# hash2: 041af7f44df9042408f0742f0f33560cbdbc8d35

###################
# TASK 2

# Constants
kMainText = text_1 + " " + text_2
kMainHash = HashText(kMainText)
kMainNum = int(kMainHash, 16)

# This prime number was generated from Java file PrimeGenerator which is in ZIP file
k160BitPrimerNumber = 1161920828799848229743882544373088734211888441911 
