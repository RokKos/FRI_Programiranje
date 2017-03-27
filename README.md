# FRI_Programiranje
[![Travis CI logo][travis-image]][travis-link]

[![Build Status][travis-badge]][travis-link]
[![MIT License][license-badge]](LICENSE.md)
Repository for test cases for homework programming tasks.

### Table of Contents

**[Structure of test cases](#structure)**  
**[Getting test cases](#get)**  
**[Installing Git](#installing)**  
**[Testing](#testing)**    
**[Contributing test cases](#contribution)**    
**[Reporting bad test cases](#reporting)**  
**[Additional information](#information)**  
**[Authors](#authors)**  
**[License](#license)** 

## <a name="structure"></a> Structure of test cases
First 10 test cases are public and taken from a task and then N custom tests.
Usually they are corner cases and huge tests that test lower and upper bound of program.
And in between there are some normal cases to check results by hand.

## <a name="get"></a> Getting test cases

### Non-git way

If you are not using git you can get whole repository by clicking clone or download button and then download zip.
See picture below for explanation.

![alt text][downloadZIP]

If you have some previuos folders with test cases and want to download only one (current) directory from repository you must:

1. Copy link from current directory

![alt text][copyLink]

2. Go to this page [DownGit](https://minhaskamal.github.io/DownGit/#/home "DownGit's Homepage")
3. Copy link in the field
4. Click download

### Using git

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

## <a name="installing"></a> Installing Git

### On Linux

If you’re on Fedora for example, you can use yum:
```bash
sudo yum install git-all
```
If you’re on a Debian-based distribution like Ubuntu, try apt-get:
```bash
sudo apt-get install git-all
```

If you’re on a Arch-based distribution like Manjaro, try pacman:
```bash
sudo pacman -S git
```

### On Windows or Mac
There is special GUI Tool for these two on this link [GitHub Desktop](https://desktop.github.com/ "GitHub Desktop's Homepage").
There is also a documentation for this GUI on this link [GitHub Desktop documentation](https://help.github.com/desktop/ "GitHub Desktop's Documentation")

[Official instructions](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git "Installing Git") for installing git on each OS 


## <a name="testing"></a> Testing
You can found all instruction for:
* `Java` testing (Programiranje1): [here](https://github.com/RokKos/FRI_Programiranje/blob/master/Programiranje1/README.md "Java testing").
* `C` testing (Programiranje2): [here](https://github.com/RokKos/FRI_Programiranje/blob/master/Programiranje2/README.md "C testing").

## <a name="contribution"></a> Contributing test cases
I will be very glad if someone contributed test cases. They can even be the most simple ones, because with more tests more mistakes can be found.
Please try to verify your every test case if that's a possibility (on big test cases this is almost impossible).

### Format of test case
Test case should consist of vhodXX.txt and izhodXX.txt, where XX is the number of test case. Please check that number of test case doesn't exist already.

### Sending test cases
You can fork this repository. All instructions are [here](https://help.github.com/articles/fork-a-repo/ "Fork A Repo"). Then: 

1. Create and commit your test cases to your forked repository
2. Create pull request to this repository
3. I will look at the test cases and approve or propose to fix to your test case.
4. I will pull your pull request to this repository

The other option is to send me e-mail with zipped test cases on e-mail: rk7344@student.uni-lj.si with subject: Test cases for DNxx.
And I will manualy include them into this repository.

## <a name="reporting"></a> Reporting bad test cases
If you find a test case with wrong input data or wrong output data (result). Create issue with name DNxx Test case yy and then add description of the problem. The [template](https://github.com/RokKos/FRI_Programiranje/blob/master/.github/ISSUE_TEMPLATE.md "Issue template") will tell you how the issue should be structured.
Or you can send me an e-mail with subject DNxx Test case yy.

## <a name="information"></a> Additional information
Any addiotional information about git, test cases, running program on test cases etc., you can contact me over Skype: Rok Kos or e-mail: rk7344@student.uni-lj.si.

## <a name="authors"></a> Authors

* **RokKos** - [RokKos](https://github.com/RokKos)

## <a name="license"></a> License

This project is licensed under the MIT License - see the [LICENSE.md](https://github.com/RokKos/FRI_Programiranje/blob/master/LICENSE) file for details.


[downloadZIP]:	   https://github.com/RokKos/FRI_Programiranje/blob/master/images/CloneDownload.png "Clone/Download"
[copyLink]:		   https://github.com/RokKos/FRI_Programiranje/blob/master/images/LinkDownload.png "Link"
[travis-badge]:    https://travis-ci.org/RokKos/FRI_Programiranje/.svg?branch=master "travis-badge"
[travis-link]:     https://travis-ci.org/RokKos/FRI_Programiranje/
[travis-image]:    https://github.com/RokKos/classes-c-/blob/master/images/TravisCI.png "travis-image"

