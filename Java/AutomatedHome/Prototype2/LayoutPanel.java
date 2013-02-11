//A Panel for general layout within a top panel, holds individual components,
//and has a section header.

//Also provides various functions to help with absolute positioning

import java.awt.*;

public class LayoutPanel extends Panel
{
   //Layout co-cordinates
   private int xPos, yPos;
   
   //Constructor
   public LayoutPanel(int x, int y, int w, int h, String section)
   {
         setLayout(null); //Turn off layout manager
         setBackground(new Color(150,150,250)); //Set background color
         setBounds(x,y,w,h); //Set position of the panel
         //Setup section label
         Label setup = new Label(section);
         setup.setBackground(new Color(255,255,255));
         setup.setBounds(0,0,w,25);
         setup.setAlignment(Label.CENTER);
         add(setup);
         //Initilise layout co-ordinates
         xPos = 5; yPos = 30;
   }

   //Add values to xPos and yPos
   public void xyAdd(int x, int y)
   {
      xPos += x;
      yPos += y;
   }

   //Set values of xPos and yPos
   public void xySet(int x, int y)
   {
      xPos = x;
      yPos = y;
   }

   //Add value to xPos
   public void xAdd(int x)
   {
      xPos += x;
   }

   //Set value of xPos
   public void xSet(int x)
   {
      xPos = x;
   }

   //Add value to yPos
   public void yAdd(int y)
   {
      yPos += y;
   }

   //Set value of yPos
   public void ySet(int y)
   {
      yPos = y;
   }

   //Get value of xPos
   public int x()
   {
      return xPos;
   }

   //Get value of yPos
   public int y()
   {
      return yPos;
   }
}
