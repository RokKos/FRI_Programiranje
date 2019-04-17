from string import ascii_uppercase
from itertools import product

# Constants
kN = len(ascii_uppercase)
kStartAscii = ord(ascii_uppercase[0])
kFILE_NAME = "geffe.txt"
kPLAIN_TEXT = "CRYPTOGRAPHY"
kPOSITIVE_MATCHING = 0.75


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
Creates all product elements of particiluar group for n-times (for example Z2xZ2x ... xZ2)
and returns array of strings
"""
def Product(group, n_times):
    elements = product(group, repeat = n_times) # creates all possible combination but they are not in string form
    
    str_elements = []
    # turnig them in string
    for el in elements:
        str_tuple = ""
        for tuple_element in el:
            str_tuple += str(tuple_element)
        str_elements.append(str_tuple)

    return str_elements

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
Function that takes two string of bits
and return one string of bits of min length of both strings
this string is also AND between this two strings
"""
def AND_two_sequences(seq1, seq2):
    lenS = min(len(seq1), len(seq2)) # Max size that we will and
    AND_Output = ""
    # Going throught all possible bits
    for i in range(lenS):
        seq1_bit = int(seq1[i])
        seq2_bit = int(seq2[i])
        and_bit = seq1_bit == seq2_bit  # AND two bits
        AND_Output += '1' if and_bit else '0'
    
    return AND_Output

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

"""
Gets string 
and outputs bits
"""
def String_to_Bits(string):
    bits = ""
    for c in string:
        bits += Char_to_Bits(c)
    print (bits)
    return bits

def Bits_to_Char(bits):
    string = ""
    for bit in bits:
        string += Bits_to_Char(bit)
    return string

def Sum_String_of_Bits(bits):
    suma = 0
    for bit in bits:
        suma = (suma + int(bit,10)) % 2
    return suma

# Polinom and Z stream key for each LFSR and solution
#           1  2  3  4  5
polinom1 = [0, 1, 0, 0, 1]
z1 = ""
solution_key_1 = ""
#           1  2  3  4  5  6  7
polinom2 = [1, 0, 0, 0, 0, 0, 1]
z2 = ""
solution_key_2 = ""
#           1  2  3  4  5  6  7  8  9 10  11
polinom3 = [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1]
z3 = ""
solution_key_3 = ""


"""
Creates initial z for specific LFSR
"""
def Create_Z_For_LFSR(polinom, key):
    z = ""
    for i in range(len(polinom)):
        ind = i % len(key)
        z += key[ind]
    return z

"""
Generates next bit of LFSR stream key
and outputs it out
"""
def Create_Next_Bit_of_LFSR(polinom, z):
    next_bit = str(Sum_String_of_Bits(AND_two_sequences(polinom, z)))  # anding two sequnces and getting bit
    z = next_bit + z # adding bit to the front
    z = z[:-1] # poping out last bit
    return z

"""
Generates next n_bit of LFSR stream key
and outputs it out
"""
def Generate_Next_Bits(polinom, z, n_bits):
    out_bits = ""
    for i in range(n_bits):
        z = Create_Next_Bit_of_LFSR(polinom, z)
        out_bits += z[0]
    return (out_bits, z)

def ProcentageMatch(seq1, seq2):
    matching_chars = 0
    lenS = len(seq1)
    for i in range(lenS):
        if (seq1[i] == seq2[i]):
            matching_chars += 1
    return float(matching_chars) / lenS

def Decipher_With_Plain_Text(plain_text, cypher_text):
    # To get key we need to reverse enginer proces
    # We have plain text and cypher text now we want stream of keys
    # As was on slides we need to do: stream_keys = plain_text xor cypher_text
    stream_keys = XOR_two_sequences(plain_text, cypher_text)
    #print(stream_keys)
    lfsr_stream_1 = ""
    lfsr_stream_2 = ""
    lfsr_stream_3 = ""

    """
    We will first try it to crack it with LFSR3 because it has 75% matching and then
    with LFSR1 because it has also has 75% matching and at the end
    with brute for on LFSR2. This is called Correlation_attack (more on that here: https://en.wikipedia.org/wiki/Correlation_attack)
    The procentage was figured out from this table:

    x1 | x2 | x3 | z  |
    0  | 0  | 0  | 0  |
    0  | 0  | 1  | 1  |
    0  | 1  | 0  | 0  |
    0  | 1  | 1  | 0  |
    1  | 0  | 0  | 0  |
    1  | 0  | 1  | 1  |
    1  | 1  | 0  | 1  |
    1  | 1  | 1  | 1  |

    """

    N = len(stream_keys)


    
    # breaking LFSR1
    for element in Product(range(2), len(polinom1)):
        # Initialize z and for LFSR1
        z1 = Create_Z_For_LFSR(polinom1, element)
        lfsr_stream_1, z1 = Generate_Next_Bits(polinom1, z1, N)
        print(lfsr_stream_1, z1)
        matching = ProcentageMatch(lfsr_stream_1, stream_keys)
        print(matching)
        if (matching >= kPOSITIVE_MATCHING):
            solution_key_1 = element
            break

    # breaking LFSR3
    for element in Product(range(2), len(polinom3)):
        # Initialize z and for LFSR3
        z3 = Create_Z_For_LFSR(polinom3, element)
        lfsr_stream_3, z3 = Generate_Next_Bits(polinom3, z3, N)
        #print (lfsr_stream_3)
        matching = ProcentageMatch(lfsr_stream_3, stream_keys)
        #print(matching)
        if (matching >= kPOSITIVE_MATCHING):
            solution_key_3 = element
            break

    # breaking LFSR1
    for element in Product(range(2), len(polinom2)):
        # No we can do brute force attact on LFSR2 with help of LFSR1 and LFSR3
        z2 = Create_Z_For_LFSR(polinom2, element)
        lfsr_stream_2, z2 = Generate_Next_Bits(polinom2, z2, N)

        and_1_2 = AND_two_sequences(lfsr_stream_1, lfsr_stream_2)
        and_2_3 = AND_two_sequences(lfsr_stream_2, lfsr_stream_3)
        xor_1 = XOR_two_sequences(and_1_2, and_2_3)
        helping_stream = XOR_two_sequences(xor_1, lfsr_stream_3)
        
        matching = ProcentageMatch(helping_stream, stream_keys)
        if (matching >= kPOSITIVE_MATCHING):
            solution_key_3 = element
            break


if __name__ == "__main__" :
    cypher_text = OpenFileWithCipherText()
    plain_text_bits = String_to_Bits(kPLAIN_TEXT)
    print("Deciphering with plain text: " + kPLAIN_TEXT)
    Decipher_With_Plain_Text(plain_text_bits, cypher_text)
    print("LFSR1 key: " + solution_key_1)
    print("LFSR2 key: " + solution_key_2)
    print("LFSR3 key: " + solution_key_3)

    # Putting it all together and getting the result
    lenC = len(cypher_text)
    final_stream_1, z1 = Generate_Next_Bits(polinom1, z1, lenC)
    final_stream_2, z2 = Generate_Next_Bits(polinom2, z2, lenC)
    final_stream_3, z3 = Generate_Next_Bits(polinom3, z3, lenC)

    and_1_2 = AND_two_sequences(final_stream_1, final_stream_2)
    and_2_3 = AND_two_sequences(final_stream_2, final_stream_3)
    xor_1 = XOR_two_sequences(and_1_2, and_2_3)
    xor_2 = XOR_two_sequences(xor_1, final_stream_3)
    solution = XOR_two_sequences(xor_2, cypher_text)
    
    solution_text = Bits_to_Char(solution)
    print(solution_text)
