//Auto button

//Button for turning automatic systems on / off

import java.awt.*;

public class AutoButton extends Button
{
   //Constructor
   public AutoButton(boolean value)
   {
      setBackground(new Color(170,170,170));
      updateValue(value);
   }

   //Update the value and redraw the button
   public void updateValue(boolean value)
   {
      if(value == true)
         setLabel("AUTO: ON");
      else
         setLabel("AUTO: OFF");

      repaint();
   }
}
