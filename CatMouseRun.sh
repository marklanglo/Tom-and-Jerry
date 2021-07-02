#!/bin/bash

#Program name: Tom and Jerry
#Author: Mark Wiedeman
#Email: markwiedeman5@fullerton.edu
#File name:  CatMouseRun.sh
#Preconditions:
#   1.  All source files are in one directory
#   2.  This file, CatMouseRun.sh, is in that same directory
#   3.  The shell is currently active in that same directory
#How to execute: Enter "sh CatMouseRun.sh" without the quotes.

echo Remove old byte-code files if any exist
rm *.class

echo View list of source files
ls -l *.java

echo Compile CatMouseGraphics.java
javac CatMouseGraphics.java

echo Compile CatMouseUI.java
javac CatMouseUI.java

echo Compile CatMouseMain.java
javac CatMouseMain.java

echo Execute the CatMouse program
java CatMouseMain

echo Program successfully terminated. Thank you for watching Tom and Jerry.
