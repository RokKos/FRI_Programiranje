from string import ascii_uppercase

# Constants
kN = len(ascii_uppercase)
kStartAscii = ord(ascii_uppercase[0])
kFILE_NAME = "geffe.txt"
kPLAIN_TEXT = "CRYPTOGRAPHY"


# Helpers

"""
Gets Char
and outputs number between 0 and 25
"""
def AsciiValue(char):
    return ord(char) - kStartAscii

"""
Gets number between 0 and 25
and outputs char
"""
def CharValue(num):
    return chr(num + kStartAscii)


"""
Opens file with file name: kFILE_NAME
and return string that represents whats is in file
"""
def OpenFileWithCipherText():
    content = ""
    with open(kFILE_NAME) as f:
        content = f.readlines()

    return content


"""
Function that takes two string of bits
and return one string of bits of min length of both strings
this string is also XOR between this two strings
"""

def XOR_two_sequences(seq1, seq2):
    lenS = min(len(seq1), len(seq2)) # Max size that we will xor 
    XOR_Output = ""
    # Going throught all possible bits
    for i in range(lenS):
        seq1_bit = int(seq1[i])
        seq2_bit = int(seq2[i])
        xor_bit = seq1_bit ^ seq2_bit  # XOR two bits
        XOR_Output += str(xor_bit)
    return XOR_Output



"""
Gets Character
and outputs binary representation of char
"""
def Char_to_Bits(char):
    int_val_char = AsciiValue(char) # converting to integer value
    binary_representation = "{0:05b}".format(int_val_char)  # converting to binary value
    return binary_representation

"""
Gets binary repsesentation
and outputs char
"""
def Bits_to_Char(bits):
    int_val_bin = int(bits, 2)  # converting 5 bitst to int
    char = CharValue(int_val_bin)  # geting char value of int
    return char

def String_to_Bits(string):
    bits = ""
    for c in string:
        bits += Char_to_Bits(c)
    print (bits)
    return bits


def Decipher_With_Plain_Text(plain_text, cypher_text):
    # To get key we need to reverse enginer proces
    # We have plain text and cypher text now we want stream of keys
    # As was on slides we need to do: stream_keys = plain_text xor cypher_text
    stream_keys = XOR_two_sequences(plain_text, cypher_text)
    print(stream_keys)



if __name__ == "__main__" :
    cypher_text = OpenFileWithCipherText()
    plain_text_bits = String_to_Bits(kPLAIN_TEXT)
    Decipher_With_Plain_Text(plain_text_bits, cypher_text)