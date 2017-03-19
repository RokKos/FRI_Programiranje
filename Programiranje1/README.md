# Testing Java programs
(Remark - you don't need git to run your programs on test cases)
You can test on all test cases by running this comand:

* On Linux
```bash
./tj.exe DNxx_yyyyyyyy.java DNxx_customTesti/ rezultati/
```
* On Windows
```bash
tj DNxx_yyyyyyyy.java DNxx_customTesti/ rezultati/
```
* On Mac
```bash
mono tj.exe DNxx_yyyyyyyy.java DNxx_customTesti/ rezultati/
```
Where xx is number of task and yyyyyyyy your registration number (vpisna številka). 

There are also other options like:

* Run only one test case
```bash
./tj.exe DNxx_yyyyyyyy.java DNxx_customTesti/ rezultati/ -p 15
```
* Run one range of test cases
```bash
./tj.exe DNxx_yyyyyyyy.java DNxx_customTesti/ rezultati/ -p 15-20
```
This commands also work on Windows and Mac.