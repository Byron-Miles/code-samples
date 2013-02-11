//Settings bar

//Specialised scrollbar of adjusting settings

import java.awt.*;

public class SettingsBar extends Scrollbar
{
   public SettingsBar(int orentation, int initial, int slider, int min, int max)
   {
      //Setupbar, taking into account bigger sliders
      super(orentation, initial, slider, min, max + slider);
      setBackground(new Color(175,175,175));
   }
}

      
      