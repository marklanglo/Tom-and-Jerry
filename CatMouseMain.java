//****************************************************************************************************************************
//Program name: "Tom and Jerry".  This program animates a cat and mouse based on user inputs.                                         *
//Copyright (C) 2021 Mark Wiedeman.   This program is free software: you can redistribute it and/or modify it under the terms*
//of the GNU General Public License version 3 as published by the Free Software Foundation.                                  *
//This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied         *
//warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.     *
//A copy of the GNU General Public License v3 is available here:  <https://www.gnu.org/licenses/>.                           *
//****************************************************************************************************************************

//Author information:
  //Author: Mark Wiedeman
  //Mail: markwiedeman5@csu.fullerton.edu

  //Program information:
    //Program name: Tom and Jerry
    //Programming language: Java
    //Files: CatMouseMain.java, CatMouseUI.java, CatMouseGraphics.java
    //       ,CatMouseRun.sh
    //
    //Date project began:  2021-April-26   (version 0.0).
    //Date of last update: 2021-May-18  (version 1.0).
    //Purpose: This program animates a can and mouse based on user input.
    //Nice Feature: Error checking is simplified in this program.
    //              It also still has tooltips for textfields and some JLabels
    //Base test system: Linux system with Bash shell and openjdk-14-jdk

    //This module
      //File name: CatMouseMain.java
      //Compile : javac CatMouseMain.java
      //Purpose: This is the top level module that activates the UI

import javax.swing.JFrame;
import javax.swing.JOptionPane;

//==============================================================================
//Class: "CatMouseMain"
//==============================================================================
//Purpose:
// The CatMouseMain instantiates a CatMouseUI and runs it.
//
//Functions:
// main: this function builds the UI and sets it visible to the user
//
//Additional:
// This class is the top level, the CatMouseUI is the next class.  The CatMouseUI
// uses the CatMouseGraphics class within it.
//
// CatMouseMain -> CatMouseUI -> CatMouseGraphics
//
//==============================================================================
class CatMouseMain{
  //============================================================================
  //Function: "main"
  //============================================================================
  // This function has the job of being main. Instantiates and runs the whole deal.
  //============================================================================
  public static void main(String[] args){

    CatMouseUI mainUI = new CatMouseUI(); //instantiates CatMouseUI object
    mainUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //sets close operation

    mainUI.setVisible(true);  //sets frame visible to the user

    //outputs a welcome message as a pop-up
    String welcomeMessage = "          Welcome to my Tom and Jerry Program!          "
                   + "\n\n"
                   + "A few notes before you run the program: \n"
                   + "- At higher speeds the cat and mouse tend to \n"
                   + "  overlap when the clocks are stopped\n"
                   + "- Errorchecking is less sophisticated this time, \n"
                   + "  but as a result the code is significantly easier\n"
                   + "  to read.  It will still catch most normal errors.\n"
                   + "- My angle now goes the correct direction with 45 degrees\n"
                   + "  going to the top right instead of the bottom left. I \n"
                   + "  restricted the angles to anything between and including\n"
                   + "  0 to 360.\n\n"
                   + "Besides that everything should hopefully be as \n"
                   + "expected, and you can press 'ok' to begin testing\n"
                   + "my program.";
    JOptionPane.showMessageDialog(null, welcomeMessage);

  }//END - public static void main

}//END - CatMouseMain
