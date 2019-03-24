from string import ascii_lowercase
from math import gcd

# Constants
kN = len(ascii_lowercase)
kStartAscii = ord(ascii_lowercase[0])
kFILE_NAME = "TheHitchhikersGuidetotheGalaxy.txt"

# Helpers
def AsciiValue(char):
    return ord(char) - kStartAscii

def CharValue(num):
    return chr(num + kStartAscii)

def TransformText(original, key, add_sub):
    text = ""
    for idx,val in enumerate(original):
        lowr = val.lower()
        ascii_num = AsciiValue(lowr)
        i = idx % len(key)
        ascii_num += add_sub * AsciiValue(key[i])
        ascii_num %= kN
        text += CharValue(ascii_num)
    return text

def Encrypt(b,k):
    return TransformText(b,k, 1)

def Decrypt(c, k):
    return TransformText(c,k, -1)

def AllDivisors(num):
    divisors = [3]
    i = 4
    while i*i <= num:
        if (num % i == 0):
            divisors.append(i)
        i += 1
    return divisors

def PossibleLen(num):
    return list(range (3, num // 2))

def FindLenghtOfKey(cripted_text):
    ct_len = len(cripted_text)
    possible_pattern_search = AllDivisors(ct_len)
    #print (possible_pattern_search)
    distances = []
    for patter_len in possible_pattern_search:
        upper_bound = ct_len if ct_len % patter_len == 0 else ct_len - patter_len
        for i in range(0,upper_bound, patter_len):
            for j in range(i + patter_len, upper_bound, patter_len):
                matching = True
                for k in range(patter_len):
                    #print (i,j,k, patter_len, ct_len)
                    if (cripted_text[i+k] != cripted_text[j+k]):
                        matching = False
                        break
                if (matching):
                    distances.append(j - i - patter_len)
    
    print (distances)
    l_d = len(distances)
    gcd_distances = distances[0]
    if (l_d >= 2):
        for d in range(1, l_d):
            gcd_distances = gcd(gcd_distances, distances[d])

    return gcd_distances


def BreakTextIntoBlocks(text, lengh_of_blocks):
    blocks = ["" for _ in range(lengh_of_blocks)]
    for ind, val in enumerate(text):
        blocks[ind % lengh_of_blocks] += val
    return blocks

def FrequencyAnalysis(cripted_text):
    frequencies = [0 for _ in range(kN)]
    for c in cripted_text:
        frequencies[AsciiValue(c)] += 1
    print (frequencies)
    return frequencies


def FindKey(cripted_text):
    key_len = FindLenghtOfKey(cripted_text)
    text_blocks = BreakTextIntoBlocks(cripted_text, key_len)
    key = ""
    for block in text_blocks:
        freq = FrequencyAnalysis(block)
        print (CharValue(freq.index(max(freq))))
        char_from_key =  abs(freq.index(max(freq)) - AsciiValue('t'))
        key += CharValue(char_from_key)
    
    return key

if __name__ == "__main__" :
    #print (Encrypt("abc", "abc"))
    #print (Encrypt("abc", "z"))
    #print (Encrypt("abc", "y"))

    #print (Decrypt("ace", "abc"))
    #print (Decrypt("zab", "z"))
    #print (Decrypt("yza", "y"))


    #print (FindLenghtOfKey(Encrypt("abcijkabcoplabc", "abc")))
    #print (FindLenghtOfKey(Encrypt("abcijkabcoplabce", "abcd")))

    #print (FindKey(Encrypt("abcijkabcoplabc", "abc")))

        
    content = ""
    with open(kFILE_NAME) as f:
        content = f.readlines()
    #print(content)

    cripted_text = Encrypt(content[0], "abc")
    key = FindKey(cripted_text)
    print (key)
    original = Decrypt(cripted_text, key)
    print (original)
    print (content[0].lower())
    print (content[0].lower() == original)

