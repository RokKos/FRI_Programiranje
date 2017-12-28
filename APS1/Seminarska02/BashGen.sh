#! /bin/bash
#Script for generating test cases

RED='\033[0;31m' #red
BB='\033[0;34m'  # blue
NC='\033[0m' # No Color
BG='\033[0;32m' #green

error() { >&2 echo -e "${RED}$1${NC}"; }
showinfo() { echo -e "${BG}$1${NC}"; }
workingprocess() { echo -e "${BB}$1${NC}"; }
allert () { echo -e "${RED}$1${NC}"; }

stTest=$2
showinfo "Compiling $1"
javac $1
fileName=$(echo $1|sed 's/\(.*\)\.java/\1/')
echo $fileName


showinfo "Making $1 inputs and making outputs $1 "

for c in $(seq 16 $stTest);
do
	echo "$c test"

	inputFile="Naloga06/vhod$(printf "%02d" $c).txt"
	outputFile="Naloga06/izhod$(printf "%02d" $c).txt"
	python TestCaseGenNal06.py > $inputFile
	java $fileName $inputFile $outputFile
done
