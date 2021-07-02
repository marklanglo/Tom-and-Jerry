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
      //File name: CatMouseUI.java
      //Compile : javac CatMouseUI.java
      //Purpose: This is the UI module that defines the UI

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import java.text.DecimalFormat;

//==============================================================================
//Class: "CatMouseUI"
//==============================================================================
//Purpose:
// The CatMouseUI has the purpose of formatting the frame and panels of the UI
// into a single window.  It contains two handler classes to manage its buttons and
// clocks.
// This class controls how the graphics panel in the window of the program is being
// used, and it uses functions from the CatMouseGraphics class to carry out operations.
// Its an extension of JFrame.
//
//Functions:
// CatMouseUI: this function builds the UI
// See the individual function descriptions for more details on implementation
// and use.
//
//Additional:
// This class contains a handler class which gives the startPauseButton, clearButton, and
// quitButton function.  It is called 'buttonHandler' and is detailed further down.  Another
// class contains a handler class called 'clockHandler' which gives the clocks
// functionality.
//
//==============================================================================

public class CatMouseUI extends JFrame{

  //============================================================================
  //Panel 1 - This includes two JLabels to display the title and creator name.
  //============================================================================
  private JLabel systemName;  //"CS223 Assignment#4 - Tom and Jerry"
  private JLabel authorName;  //"by Mark Wiedeman"

  //Panel 2 has no additional variables because it is the graphics panel

  //============================================================================
  //Panel 3 - This panel contains all interactable items.
  //============================================================================

  //JLabels
  private JLabel catSpeedName;        //"Cat Speed (pix/tic)"
  private JLabel mouseSpeedName;      //"Mouse Speed (pix/tic)"
  private JLabel directionName;       //"Mouse Direction"

  //JButtons
  private JButton clearButton;        //button used to reset ball and textfields
  private JButton startPauseButton;   //button used to start and pause ball
  private JButton quitButton;         //button used to quit program

  //JTextField
  private JTextField enterCatSpeed;   //Text field used to enter cat speed
  private JTextField enterMouseSpeed; //Text field used to enter mouse speed
  private JTextField enterDirection;  //Text field used to enter direction in degrees

      //========================================================================
      //Panel 3a - This panel holds the distance between the cat and mouse
      //========================================================================

      //JPanel
      private JPanel panel3a;         //subpanel to hold the ball location

      //JLabels
      private JLabel distanceBetween; //"Distance Between"

      //JTextFields
      private JTextField displayDist; //Text field used to display distance between cat and mouse

  //============================================================================
  //Panels:
  // Panel 1: Display Panel
  // Panel 2: Graphic Panel
  // Panel 3: Control Panel
  //============================================================================
  private JPanel            panel1;
  private CatMouseGraphics  panel2;
  private JPanel            panel3;

  private JPanel            panel2Mask; //panel used to hide graphics panel

  //============================================================================
  //UI limits - these are constants to adjust the UI size of the JFrame
  //============================================================================
  private int UI_WIDTH  = 1850;    //User Interface Width
  private int UI_HEIGHT = 925;     //User Interface Height

  //============================================================================
  //Clocks - There are three clocks, one clock for the refresh and a clock for
  //         each moving object in the graphics panel (one cat clock and one mouse clock).
  //         There is also the variable 'distance' which is used to see how close
  //         the cat and  mouse are to eachother, if they're close enough then
  //         all clocks will stop
  //============================================================================
  private clockHandler clockHandler;

  private double milliPerSecond    = 1000.0;  //milliseconds per second

  //cat clock
  private Timer  catMotionClock;              //This clock refreshes the cat position
  private double cMotionClockRate  = 60;      //default motion clock rate in Hz for cat
  private int    cMotionClockDelay;           //used to instantiate cMotionClock

  //mouse clock
  private Timer  mouseMotionClock;            //This clock refreshes the mouse position
  private double mMotionClockRate  = 60;     //default motion clock rate in Hz for mouse
  private int    mMotionClockDelay;           //used to instantiate mMotionClock

  //refresh clock
  private Timer  refreshClock;                //This clock refreshes the entire panel
  private double refreshClockRate  = 100;      //default refresh rate in Hz
  private int    refreshClockDelay;           //used to instantiate refreshClock

  //movement speeds
  private double catPixPerTic;                //speed used in CatMouseGraphics for cat
  private double catPixPerSecond;             //used to calculate pixPerTic for cat
  private double mousePixPerTic;              //speed used in CatMouseGraphics for mouse
  private double mousePixPerSecond;           //used to calculate pixPerTic for mouse

  //distance between cat and mouse
  private double distance;                    //calculated at runtime, used to stop clocks when cat
                                              //and mouse are close enough to eachother

  //============================================================================
  //Direction and Position - All of these are variables used to dictate the mouse's
  //                         and cat's position. 'directionAngle' is entered by the user
  //                         and it simply determines the mouse's
  //                         direction from the starting position. 'directionAngle'
  //                         is used to calculate the 'directionRadians', which
  //                         is just the direction in radians used for calculating
  //                         'mouseDeltaX' and 'mouseDeltaY'.
  //
  //                         'catDeltaX' and 'catDeltaY' are calculated at runtime
  //                         based on the mouse's position relative to itself.
  //============================================================================
  private double directionAngle;    //user inputted original ball angle
  private double directionRadians;  //converted ball angle for calculating deltas

  private double mouseCenterX;    //center of the mouseX
  private double mouseCenterY;    //center of the mouseY
  private double mouseDeltaX;     //delta X for mouse movement
  private double mouseDeltaY;     //delta Y for mouse movement

  private double catCenterX;      //center of the catX
  private double catCenterY;      //center of the catY
  private double catDeltaX;       //delta X for cat movement
  private double catDeltaY;       //delta Y for cat movement

  private double mouseRadius;     //mouse ball radius
  private double catRadius;       //cat ball radius

  //============================================================================
  //Errors - 'errorString' simply is a string that stores the errors to be sent
  //         to the optionpanes.
  //============================================================================
  private String errorString = "ERROR: An Entered Value was INVALID"; //default error

  //============================================================================
  //Function: "CatMouseUI"
  //============================================================================
  // This function has the job of completely formatting all of the UI objects.
  // It has a handler class for its buttons, and a handler class for its
  // clocks.
  //============================================================================
  public CatMouseUI(){

    super("Program 4");             //Titles window of program as "Program 4"

    //This sets the UI size and disables resizing the UI
    setLayout(null);
    setSize(UI_WIDTH, UI_HEIGHT);
    setResizable(false);            //locks the window size

    //==========================================================================
    //creates first Panel
    panel1 = new JPanel();
    panel1.setLayout(new BorderLayout());
    panel1.setBackground(new Color(242, 70, 51));

    //initializes first panel object
    systemName = new JLabel("CS223 Assignment#4 - Tom and Jerry");
    authorName = new JLabel("by Mark Wiedeman");
    systemName.setToolTipText("CS223 Assignment#4 - Tom and Jerry");
    authorName.setToolTipText("by Mark Wiedeman");

    //adds JLabel to the center of the panel
    systemName.setHorizontalAlignment(JLabel.CENTER);
    authorName.setHorizontalAlignment(JLabel.CENTER);
    panel1.add(systemName, BorderLayout.PAGE_START);
    panel1.add(authorName, BorderLayout.CENTER);
    //==========================================================================

    //==========================================================================
    //creates second Panel
      panel2 = new CatMouseGraphics();
      panel2.setVisible(false);
      panel2Mask = new JPanel();
      panel2Mask.setBackground(new Color(255, 220, 145));

    //==========================================================================

    //==========================================================================
    //creates third panel
    panel3 = new JPanel();
    panel3.setLayout(null);
    panel3.setBackground(new Color(252, 38, 30));

    //subpanel instantiation
    panel3a = new JPanel();
    panel3a.setLayout(null);
    panel3a.setBackground(new Color(255, 220, 145));

    //initializes third panel objects and subobjects
    //JLabels
    catSpeedName     = new JLabel("Cat Speed (pix/tic)");
    mouseSpeedName   = new JLabel("Mouse Speed (pix/tic)");
    directionName    = new JLabel("Mouse Direction");
    distanceBetween  = new JLabel("Distance Between");

    //JButtons
    clearButton      = new JButton("Clear");
    startPauseButton = new JButton("Start");
    quitButton       = new JButton("Quit");

    //JTextFields
    enterCatSpeed    = new JTextField(10);
    enterMouseSpeed  = new JTextField(10);
    enterDirection   = new JTextField(10);
    displayDist      = new JTextField(10);

    //Tooltips for hovering cursor
    enterCatSpeed.setToolTipText("Please enter a speed for the cat to run between 0 and 10000 non-inclusive");
    enterMouseSpeed.setToolTipText("Please enter a speed for the mouse to run between 0 and 10000 non-inclusive");
    enterDirection.setToolTipText("Please enter an angle from 0 to 360");
    displayDist.setToolTipText("This is the current distance between the cat and mouse");

    //DisplayDist field
    displayDist.setEditable(false);
    displayDist.setText("N/A");

    //creates button handler class for 'clear', 'startpause', and 'quit' buttons
    buttonHandler myHandler = new buttonHandler();
    clearButton.addActionListener(myHandler);
    startPauseButton.addActionListener(myHandler);
    quitButton.addActionListener(myHandler);

        //======================================================================
        //set panel JComponent coordinates
        //======================================================================

        //row 1 of JButtons
        clearButton.setBounds(200, 30, 150, 50);
        startPauseButton.setBounds(500, 30, 150, 50);
        quitButton.setBounds(800, 30, 150, 50);

        //row 2 with JLabels and JTextFields
        catSpeedName.setBounds(200, 100, 150, 50);
        mouseSpeedName.setBounds(500, 100, 200, 50);
        directionName.setBounds(800, 100, 150, 50);
        enterCatSpeed.setBounds(200, 140, 150, 30);
        enterMouseSpeed.setBounds(500, 140, 150, 30);
        enterDirection.setBounds(800, 140, 150, 30);

        //panel 3a
        distanceBetween.setBounds(160, 20, 150, 10);
        displayDist.setBounds(155, 50, 150, 30);
        //======================================================================

    //adds objects to panel 3
    panel3.add(clearButton);
    panel3.add(startPauseButton);
    panel3.add(quitButton);
    panel3.add(catSpeedName);
    panel3.add(mouseSpeedName);
    panel3.add(directionName);
    panel3.add(enterCatSpeed);
    panel3.add(enterMouseSpeed);
    panel3.add(enterDirection);

    //adds objects to panel 3a
    panel3a.add(distanceBetween);
    panel3a.add(displayDist);

    panel3.add(panel3a);
    panel3a.setBounds(1050, 15, 500, 200);
    //==========================================================================


    //==========================================================================
    //combines panels on the frame
    add(panel1);
    add(panel2);
    add(panel2Mask);
    add(panel3);

    panel1.setBounds(0,   0, UI_WIDTH, 50);
    panel2.setBounds(0,  50, UI_WIDTH, 600);
    panel2Mask.setBounds(0, 50, UI_WIDTH, 600);
    panel3.setBounds(0, 650, UI_WIDTH, (UI_HEIGHT - 650));
    //==========================================================================

    //creates clockhandler
    clockHandler = new clockHandler();

    //instantiates catMotionClock
    cMotionClockDelay  = (int)Math.round(milliPerSecond / cMotionClockRate);
    catMotionClock     = new Timer(cMotionClockDelay, clockHandler);

    //instantiates mouseMotionClock
    mMotionClockDelay  = (int)Math.round(milliPerSecond / mMotionClockRate);
    mouseMotionClock   = new Timer(mMotionClockDelay, clockHandler);

    //instantiates refreshClock
    refreshClockDelay  = (int)Math.round(milliPerSecond / refreshClockRate);
    refreshClock       = new Timer(refreshClockDelay, clockHandler);

  }

  //============================================================================
  //Handler Class: "buttonHandler"
  //============================================================================
  // This handler class is used to create functionality for the Start/Pause
  // button and Quit button.
  //
  // Functions:
  //  'actionPerformed': controls the buttons in the JFrame
  //
  //============================================================================
  private class buttonHandler implements ActionListener{

    //==========================================================================
    //Function: "actionPerformed"
    //==========================================================================
    // 'startPauseButton'  - This button handles starting and stopping the Clocks
    //                       it will check whether the button says "Start",
    //                       "Pause", or "Resume".
    //
    //                       For "Start" it will pull from the JTextFields and
    //                       parse those numbers.
    //
    //                       For "Pause", all clocks will stop and the button
    //                       text will change to "Resume".
    //
    //                       For "Resume" all clocks will start and the button
    //                       text will change to "Pause".
    //
    // 'clearButton'       - resets the ball position and clears the text fields
    //
    // 'quitButton'        - closes the program in one click
    //==========================================================================
    public void actionPerformed(ActionEvent event){

      //if statement that checks which button is being pressed
      if(event.getSource() == startPauseButton)
      {
        //checks whether the button text says "Start", "Pause", or "Resume"
        if(startPauseButton.getText() == "Start")
        {
          //simplified try-catch to check if any inputs are invalid as doubles
          try{

            //calculates the angle into radians
            directionAngle = Double.parseDouble(enterDirection.getText());
            directionRadians = Math.toRadians(directionAngle);

            //gets the positions of the mouse and cat from the graphics panel
            mouseCenterX = panel2.getMouseCenterX();
            mouseCenterY = panel2.getMouseCenterY();
            mouseRadius  = panel2.getMouseRadius();
            catCenterX   = panel2.getCatCenterX();
            catCenterY   = panel2.getCatCenterY();
            catRadius    = panel2.getCatRadius();

            //calculates mouse speed
            mousePixPerSecond = Double.parseDouble(enterMouseSpeed.getText());
            mousePixPerTic = mousePixPerSecond / mMotionClockRate;

            //calculates cat speed
            catPixPerSecond = Double.parseDouble(enterCatSpeed.getText());
            catPixPerTic = catPixPerSecond / cMotionClockRate;

            //Errorchecks for out of bounds entries
            if((catPixPerSecond > 0    && catPixPerSecond < 10000)
            && (mousePixPerSecond > 0  && mousePixPerSecond < 10000)
            && (directionAngle >= 0 && directionAngle <= 360))
            {
              //initial distance between cat and mouse
              distance = Math.sqrt(Math.pow((catCenterX - mouseCenterX),2) + Math.pow((catCenterY - mouseCenterY),2));
              DecimalFormat numberFormat = new DecimalFormat("0.00");
              displayDist.setText(numberFormat.format(distance - (mouseRadius + catRadius)));

              //mouse delta is calculated
              mouseDeltaX = Math.cos(directionRadians)*mousePixPerTic;
              mouseDeltaY = Math.sin(directionRadians)*mousePixPerTic;
              panel2.setMouseDeltaX(mouseDeltaX);
              panel2.setMouseDeltaY(mouseDeltaY);

              //initial cat delta calculated
              catDeltaX = catPixPerTic * (mouseCenterX - catCenterX)/distance;
              catDeltaY = catPixPerTic * (mouseCenterY - catCenterY)/distance;
              panel2.setCatDeltaX(catDeltaX);
              panel2.setCatDeltaY(catDeltaY);

              panel2.setVisible(true);

              //starts clocks and changed button name to pause
              refreshClock.start();
              catMotionClock.start();
              mouseMotionClock.start();
              startPauseButton.setText("Pause");
            }
            else
            {
              //out of bounds error
              errorString = "ERROR: A value is outside of the accepted bounds for this program,"
                          + "\n Please double check the following values: "
                          + "\n  "
                          + "\n \"Cat Speed\" must be between 0 and 10000, non-inclusive,"
                          + "\n \"Mouse Speed\" must be between 0 and 10000, non-inclusive,"
                          + "\n \"Mouse Direction\" must be any angle from 0 to 360. "
                          + "\n  "
                          + "\n non-inclusive: 0 and 10000 are not valid, but all numbers between them are valid.";

              JOptionPane.showMessageDialog(null, errorString);
            }
          }
          catch(Exception InvalidInput)
          {
            //invalid input error
            errorString = "ERROR: An invalid input has been entered AND/OR a field has been left empty,"
                        + "\n please avoid symbols (!@#$%^&) and scientific"
                        + "\n notation.  Values entered as '123.12' are valid"
                        + "\n while values such as '1/2', '5*10^3', or alternate"
                        + "\n number formats will not work.";

            JOptionPane.showMessageDialog(null, errorString);
          }
        }
        else if(startPauseButton.getText() == "Pause")
        {
          //stops clocks if they arent stopped already
          refreshClock.stop();
          catMotionClock.stop();
          mouseMotionClock.stop();
          startPauseButton.setText("Resume");
        }
        else if(startPauseButton.getText() == "Resume")
        {
          //starts clocks and changed button name to pause
          refreshClock.start();
          catMotionClock.start();
          mouseMotionClock.start();
          startPauseButton.setText("Pause");
        }
        else
        {
          //create error code
        }
      }
      else if(event.getSource() == clearButton) //checks if "Clear" button was pressed
      {
        //resets text fields
        enterCatSpeed.setText("");
        enterMouseSpeed.setText("");
        enterDirection.setText("");

        //stops clocks if they arent stopped already
        refreshClock.stop();
        catMotionClock.stop();
        mouseMotionClock.stop();

        //resets cat and mouse to original positions
        panel2.resetCatMouse();

        //gets mouse and cat position
        mouseCenterX = panel2.getMouseCenterX();
        mouseCenterY = panel2.getMouseCenterY();
        mouseRadius  = panel2.getMouseRadius();
        catCenterX   = panel2.getCatCenterX();
        catCenterY   = panel2.getCatCenterY();
        catRadius    = panel2.getCatRadius();

        //sets display to current distance between cat and mouse
        distance = Math.sqrt(Math.pow((catCenterX - mouseCenterX),2) + Math.pow((catCenterY - mouseCenterY),2));
        DecimalFormat numberFormat = new DecimalFormat("0.00");
        displayDist.setText(numberFormat.format(distance - (mouseRadius + catRadius)));

        //sets graphics panel to visible
        panel2.setVisible(true);

        //sets 'startPauseButton' to "Start" in case it isnt set to that already
        startPauseButton.setText("Start");
      }
      else if(event.getSource() == quitButton) //checks if "Quit" button was pressed
      {
        System.exit(0); //simply closes the program
      }//END - if else button actions

    }//END - public void actionPerformed(ActionEvent event)

  }//END - private class buttonHandler implements ActionListener

  //============================================================================
  //Handler Class: "clockHandler"
  //============================================================================
  // This handler class is used to create functionality for the refreshClock
  // catMotionClock and mouseMotionClock.
  //
  // Functions:
  //  'actionPerformed': controls all of the clocks in the JFrame
  //
  //============================================================================
  private class clockHandler implements ActionListener{
    //==========================================================================
    //Function: "actionPerformed"
    //==========================================================================
    // 'refreshClock'     - repaints the entire graphics panel on interval
    // 'catMotionClock'   - moves the grey ball meant to represent a cat (Tom)
    // 'mouseMotionClock' - moves the brown ball meant to represent a mouse (Jerry)
    //==========================================================================
    public void actionPerformed(ActionEvent event){

      //checks which clock is being used
      if(event.getSource() == refreshClock)
      {
        panel2.repaint(); //repaints the graphics panel

      }
      else if(event.getSource() == catMotionClock)
      {
        //retrieves new position of cat and mouse
        mouseCenterX = panel2.getMouseCenterX();
        mouseCenterY = panel2.getMouseCenterY();
        catCenterX   = panel2.getCatCenterX();
        catCenterY   = panel2.getCatCenterY();

        //calculates new deltas based on cat and mouse positions
        catDeltaX = catPixPerTic * ((double)mouseCenterX - (double)catCenterX)/distance;
        catDeltaY = catPixPerTic * ((double)mouseCenterY - (double)catCenterY)/distance;

        //sends new cat deltas to graphics panel class
        panel2.setCatDeltaX(catDeltaX);
        panel2.setCatDeltaY(catDeltaY);

        //moves the cat ball
        panel2.moveCat();

        //sets new distance
        distance = Math.sqrt(Math.pow((catCenterX - mouseCenterX),2) + Math.pow((catCenterY - mouseCenterY),2));
        DecimalFormat numberFormat = new DecimalFormat("0.00");
        displayDist.setText(numberFormat.format(distance - (mouseRadius + catRadius)));

        //if the cat and mouse are tangent to one another or closer then the clocks stop
        if(distance <= (mouseRadius + catRadius))
        {
          refreshClock.stop();
          catMotionClock.stop();
          mouseMotionClock.stop();
          displayDist.setText("0.00");
        }
      }
      else if(event.getSource() == mouseMotionClock)
      {
        //calls the function to move the mouse ball
        panel2.moveMouse();
      }//END - if else clock getsource

    }//END - public void actionPerformed(ActionEvent event)

  }//END - private class clockHandler implements ActionListener

}//END - class CatMouseUI
