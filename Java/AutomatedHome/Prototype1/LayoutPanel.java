//Layout Panel

import java.awt.*;

//Extends Panel, sets up some initial values
public class LayoutPanel extends Panel
{
   public LayoutPanel(int x, int y, int w, int h)
   {
         setLayout(null); //Turn off layout manager
         setBackground(new Color(150,150,250)); //Set background color
         setBounds(x,y,w,h); //Set position in m_Layout
         setFont(new Font("SansSerif", Font.BOLD, 18)); //Set font
   }
}
