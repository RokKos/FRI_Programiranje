#! /bin/bash
# Script that runs my program trought all my test cases

RED='\033[0;31m' # Red
BB='\033[0;34m'  # Blue
NC='\033[0m' # No Color
BG='\033[0;32m' # Green

error() { >&2 echo -e "${RED}$1${NC}"; }
showinfo() { echo -e "${BG}$1${NC}"; }


showinfo "Running tests ..."

find $1 -mindepth 1 -maxdepth 1 -type d | while read -r dir
do
  
  # Moving in directory
  cd "$dir"  # note the quotes, which encapsulate whitespace
  #ls
  # takes substring from 3 to 6
  echo $dir
  program=$(echo $dir | cut -c3-6)

  # Copping program to run test on
  cp ../$program"_63160025.c" .
  
  # Setting thingst to run tests
  export name=$program"_63160025" 
  make test

  # Checking if test passed
  if [ $? -ne 0 ]; then
    error "Error: there are failed test!"
    make clean
  	rm $program"_63160025.c"
  	cd ..
	# Terminate script and outputs 1
    exit 1
  fi
  
  # Cleaning after test are done
  make clean
  rm $program"_63160025.c"
  # Moving out of directory
  cd ..
done

#showinfo "All tests compile and pass."