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
      //File name: CatMouseGraphics.java
      //Compile : javac CatMouseGraphics.java
      //Purpose: This is the graphics module that defines the graphical panel

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

//==============================================================================
//Class: "CatMouseGraphics"
//==============================================================================
//Purpose:
// The CatMouseGraphics has the purpose of creating the graphics and updating the
// player position (the ball).
//
//Functions:
// CatMouseGraphics : this function initializes the panel
// paintComponent   : draws the panel objects, the cat and mouse
// moveMouse        : moves the mouse
// moveCat          : moves the cat
// resetCatMouse    : resets cat and mouse to original positions
// getMouseCenterX  : sends mouseCenterX to CatMouseUI
// getMouseCenterY  : sends mouseCenterY to CatMouseUI
// getCatCenterX    : sends catCenterX to CatMouseUI
// getCatCenterY    : sends catCenterY to CatMouseUI
// getMouseRadius   : sends mouseRadius to CatMouseUI
// getCatRadius     : sends catRadius to CatMouseUI
// setMouseDeltaX   : sets mouseDeltaX from CatMouseUI
// setMouseDeltaY   : sets mouseDeltaY from CatMouseUI
// setCatDeltaX     : sets catDeltaX from CatMouseUI
// setCatDeltaY     : sets catDeltaY from CatMouseUI
//
//==============================================================================

public class CatMouseGraphics extends JPanel
{
  private int    panelWidth   = 1850;
  private int    panelHeight  = 600;
  private int    panelCenterX = 925;
  private int    panelCenterY = 275;

  //============================================================================
  //Ball Coordinates - This section includes all data related to cat and mouse
  //                   position and measurement.  The corner and center coordinates
  //                   are tracked for each ball as well as their size based on
  //                   width and height.  Their radius is calculated using the width
  //                   and height as well.
  //
  //                   Additional data here is the cat and mouse deltas which are
  //                   used to determine how far the cat and mouse will move each tic
  //============================================================================
  private int    mouseWidth   = 20;
  private int    mouseHeight  = 20;
  private int    mouseRadiusX = mouseWidth/2;
  private int    mouseRadiusY = mouseHeight/2;
  private double mouseCenterX = panelCenterX;
  private double mouseCenterY = panelCenterY;
  private double mouseCornerX = (panelCenterX - mouseWidth/2);
  private double mouseCornerY = (panelCenterY - mouseHeight/2);

  private int    catWidth   = 50;
  private int    catHeight  = 50;
  private int    catRadiusX = catWidth/2;
  private int    catRadiusY = catHeight/2;
  private double catCenterX = catWidth/2;
  private double catCenterY = catHeight/2;
  private double catCornerX = 0;
  private double catCornerY = 0;

  private double  mouseDeltaX;  //delta X for mouse movement
  private double  mouseDeltaY;  //delta Y for mouse movement

  private double  catDeltaX;  //delta X for cat movement
  private double  catDeltaY;  //delta Y for cat movement

  //============================================================================
  //Function: "paintComponent"
  //============================================================================
  // This function has the job of drawing the balls in thier initial positions and
  // then repainting them in order to animate movement.
  //============================================================================
  public void paintComponent(Graphics table)
  {

    super.paintComponent(table);
    setBackground(new Color(255, 220, 145)); //sets green background color to panel

    table.setColor(new Color(168, 22, 17));  //creates the mouse ball
    table.fillOval((int)mouseCornerX, (int)mouseCornerY, mouseWidth, mouseHeight);

    table.setColor(new Color(145, 124, 124)); //creates the cat ball
    table.fillOval((int)catCornerX, (int)catCornerY, catWidth, catHeight);

  }//END - public void paintComponent(Graphics table)

  //============================================================================
  //Function: "moveMouse"
  //============================================================================
  // This function has the job of moving the mouse and checking for collision with
  // the four walls of the graphics panel.
  //============================================================================
  public void moveMouse()
  {
    if(mouseCenterY <= mouseRadiusY) //checks north wall
    {
        mouseDeltaY *= -1; //swaps deltaY with negative deltaY
        repaint();
    }
    else if(panelWidth - mouseCenterX <= mouseRadiusX) //checks east wall
    {
        mouseDeltaX *= -1; //swaps deltaX with negative deltaX
        repaint();
    }
    else if(panelHeight - mouseCenterY <= mouseRadiusY) //checks south wall
    {
        mouseDeltaY *= -1; //swaps deltaY with negative deltaY
        repaint();
    }
    else if(mouseCenterX <= mouseRadiusX) //checks west wall
    {
        mouseDeltaX *= -1; //swaps deltaX with negative deltaX
        repaint();
    }

    //updates mouse position
    mouseCenterX += mouseDeltaX;
    mouseCenterY += mouseDeltaY;
    mouseCornerX += mouseDeltaX;
    mouseCornerY += mouseDeltaY;

  }//END - public void moveMouse()

  //============================================================================
  //Function: "moveCat"
  //============================================================================
  // This function has the job of moving the cat by simply adding its pre-calculated
  // deltas to the cat ball.
  //============================================================================
  public void moveCat()
  {

    //updates cat position
    catCenterX += catDeltaX;
    catCenterY += catDeltaY;
    catCornerX += catDeltaX;
    catCornerY += catDeltaY;

  }//END - public void moveMouse()

  //============================================================================
  //Function: "resetCatMouse"
  //============================================================================
  // This function has the job of resetting the balls to their original positions and
  // zeroing out the old deltas.
  //============================================================================
  public void resetCatMouse()
  {
    //resets the mouse position
    mouseCenterX = panelCenterX;
    mouseCenterY = panelCenterY;
    mouseCornerX = (panelCenterX - mouseWidth/2);
    mouseCornerY = (panelCenterY - mouseHeight/2);

    //resets the cat position
    catCenterX = catWidth/2;
    catCenterY = catHeight/2;
    catCornerX = 0;
    catCornerY = 0;

    //zeroes out old deltas
    catDeltaX   = 0;
    catDeltaY   = 0;
    mouseDeltaX = 0;
    mouseDeltaY = 0;

    repaint(); //repaints after values are reset

  }//END - public void resetCatMouse()

  //============================================================================
  //Getter Functions
  //============================================================================
  // These functions have the job of sending data to the UI or other classes
  //
  // getMouseCenterX  : sends mouseCenterX to CatMouseUI
  // getMouseCenterY  : sends mouseCenterY to CatMouseUI
  // getCatCenterX    : sends catCenterX to CatMouseUI
  // getCatCenterY    : sends catCenterY to CatMouseUI
  // getMouseRadius   : sends mouseRadius to CatMouseUI
  // getCatRadius     : sends catRadius to CatMouseUI
  //============================================================================
  public double getMouseCenterX()
  {
    return mouseCenterX;
  }//END - getMouseCenterX()

  public double getMouseCenterY()
  {
    return mouseCenterY;
  }//END - getMouseCenterY()

  public double getCatCenterX()
  {
    return catCenterX;
  }//END - getCatCenterX()

  public double getCatCenterY()
  {
    return catCenterY;
  }//END - getCatCenterY()

  public double getMouseRadius()
  {
    return mouseRadiusX;
  }//END - getMouseRadius()

  public double getCatRadius()
  {
    return catRadiusX;
  }//END - getCatRadius()

  //============================================================================
  //Setter Functions
  //============================================================================
  // These functions have the job of changing data within the graphics class using
  // outside classes.
  //
  // setMouseDeltaX   : sets mouseDeltaX from CatMouseUI
  // setMouseDeltaY   : sets mouseDeltaY from CatMouseUI
  // setCatDeltaX     : sets catDeltaX from CatMouseUI
  // setCatDeltaY     : sets catDeltaY from CatMouseUI
  //============================================================================

  public void setMouseDeltaX(double newDelta)
  {
    mouseDeltaX = newDelta;
  }//END - setMouseDeltaX(double newDelta)

  public void setMouseDeltaY(double newDelta)
  {
    mouseDeltaY = -newDelta;
  }//END - setMouseDeltaY(double newDelta)

  public void setCatDeltaX(double newDelta)
  {
    catDeltaX = newDelta;
  }//END - setCatDeltaX(double newDelta)

  public void setCatDeltaY(double newDelta)
  {
    catDeltaY = newDelta;
  }//END - setCatDeltaY(double newDelta)

}//END - public class CatMouseGraphics extends JPanel
