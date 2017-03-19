# Testing C programs
(Remark - you don't need git to run your programs on test cases)
You can test on all test cases by running this comand:

## On Linux and Mac:
* First you tell make file which program should compile:
```bash
export name=DNxx_yyyyyyyy
```
Where xx is number of task and yyyyyyyy your registration number (vpisna številka). 

* Then you could check if your program compiles :
```bash
make
```
* And then run program on test:
```bash
make test
```
* At the end you could run clean command to clean your directory (cleans all .res and .diff files):
```bash
make clean
```

There are also other options like:

* Run only one test case
```bash
./DNxx_yyyyyyyy < testZZ.in > testZZ.out
```
* Check the diffrences
```bash
cat testZZ.diff
```

* Check your result or official result or input file:
```bash
cat testZZ.res
cat testZZ.out
cat testZZ.in
```