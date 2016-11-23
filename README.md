# FRI_Programiranje
Repository for test cases for programs from homework and sharing programs after homework due date.

### Table of Contents

**[Structure of test cases](#structure)**  
**[Getting test cases](#get)**  
**[Installing Git](#installing)**  
**[Testing](#testing)**    
**[Contributing test cases](#contribution)**    
**[Reporting bad test cases](#reporting)** 
**[Authors](#authors)**  
**[License](#license)** 

##<a name="structure"></a> Structure of test cases
First 10 test cases are public test cases from task and then follows N custom test.
They are usually corner cases and huge test that test lower bounds and upper bound of program.
And in between there are some normal cases so that we can check results by hand.

##<a name="get"></a> Getting test cases

###Non-git way

If you are not using git you can get whole repository by clicking clone or download button and then download zip.
See picture below for explanation.
![alt text][downloadZIP]

If you have some previus folders with test cases and want to download only one(current) directory from repository you must:

1. Copy link from current directory
![alt text][copyLink]

2. Go to this page [DownGit](https://minhaskamal.github.io/DownGit/#/home "DownGit's Homepage")
3. Copy link in field
4. Click download

###Using git

If you don't have git installed first look at the section [Installing Git](#installing).
When you set up your git you can clone this repository to your local computer with this command:
```bash
git clone https://github.com/RokKos/FRI_Programiranje.git
```
Then you can go in repository by:
```bash
cd FRI_Programiranje/
```
To get new test cases from server run this comand:
```bash
git pull origin master
```
And now you can run commands from section  [Testing](#testing).

##<a name="installing"></a> Installing Git

###On Linux

If you’re on Fedora for example, you can use yum:
```bash
sudo yum install git-all
```
If you’re on a Debian-based distribution like Ubuntu, try apt-get:
```bash
sudo apt-get install git-all
```

###On Windows or Mac
There is special GUI Tool for this two OS on this link [GitHub Desktop](https://desktop.github.com/ "GitHub Desktop's Homepage").
There is also documentation for this GUI on this link [GitHub Desktop documentation](https://help.github.com/desktop/ "GitHub Desktop's Documentation")

Here are [official instructions](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git "Installing Git") for installing git on each OS 


##<a name="testing"></a> Testing
(Remark - you don't need git to run your program on test cases)
You can test on all test cases by runnig this comand:

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
Where xx is number of task and yyyyyyyy your Vpisna number. 

There are also other options like:

* Run only one test case
```bash
./tj.exe DNxx_yyyyyyyy.java DNxx_customTesti/ rezultati/ -p 15
```
* Run one range of test cases
```bash
./tj.exe DNxx_yyyyyyyy.java DNxx_customTesti/ rezultati/ -p 15-20
```
This command also work on Windows and Mac.

##<a name="contribution"></a> Contributing test cases
I will be very glad if someone contributes test cases. They can be even most simple ones, because more test more mistakes can be found.
Just please try to verify your every test case if this is possible (on big test cases is this almost imposible).

### Format of test case
Test case should consist from vhodXX.txt and izhodXX.txt, where XX is number of test case. Please check that number of test case doesn't exist.

### Sending test cases
You can fork this repository all instructions are [here](https://help.github.com/articles/fork-a-repo/ "Fork A Repo"). Then: 

1. Create and commit your test cases to your forked repository
2. Create pull request to this repository
3. I will look at the test cases and approve or propose fix to your test case.
4. I will pull your pull request to this repository

Other options is to send me e-mail with ziped test cases on e-mail: rokkos@student.uni-lj.si with subject: Test cases for DNxx.
And then I will manualy include them into this repository.

##<a name="reporting"></a> Reporting bad test cases
If you found test case that has wrong input data or wrong output data (result). Create issue with name DNxx Test case yy and then add description of problem. Or you can also send me e-mail with with subject DNxx Test case yy.

##<a name="authors"></a> Authors

* **RokKos** - [RokKos](https://github.com/RokKos)

##<a name="license"></a> License

This project is licensed under the MIT License - see the [LICENSE.md](https://github.com/RokKos/FRI_Programiranje/blob/master/LICENSE) file for details


[downloadZIP]: https://github.com/adam-p/markdown-here/raw/master/src/common/images/icon48.png "Logo Title Text 2"
[copyLink]: https://github.com/adam-p/markdown-here/raw/master/src/common/images/icon48.png "Logo Title Text 2"

