//Outside Frame

//The frame for outside

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class OutsideFrame extends TitledFrame
{
   //Rainfall Panel for outside frame
   private class RainPanel extends LayoutPanel implements ActionListener,
                                                      AdjustmentListener
   {
      //Button for turning auto watering on / off
      private AutoButton m_Auto;
      //Sliders for adjusting watering system
      private SettingsBar m_FrontBar;
      private SettingsBar m_BackBar;
      private SettingsBar m_GardenBar;
      //Label for watering system values
      private DataLabel m_FrontLabel;
      private DataLabel m_BackLabel;
      private DataLabel m_GardenLabel;

      public RainPanel()
      {
         super(5,55,250,415);
         //Add components
         DataLabel dLabel;
         //Rainfall label
         dLabel = new DataLabel("Rain","mm",
                           m_Owner.getRainFall(),
                           DataLabel.SETTING);
         dLabel.setBounds(5,5,240,30);
         add(dLabel);
         //Rain forcast label
         dLabel = new DataLabel(m_Owner.getRainChance() + "% of "
                                + m_Owner.getRainForcast() + "mm",
                                "",0, DataLabel.SPECIAL);
         dLabel.setBounds(5,40,240,30);
         add(dLabel);

         //Auto watering system button
         m_Auto = new AutoButton(m_Owner.getWaterAuto());
         m_Auto.addActionListener(this);
         m_Auto.setBounds(5,80,240,90);
         add(m_Auto);

         //Front watering setting label
         m_FrontLabel = new DataLabel("Front","mm/h",
                           m_Owner.getWaterFront(),
                           DataLabel.SETTING);
         m_FrontLabel.setBounds(5,180,240,30);
         add(m_FrontLabel);
         //Front watering setting bar
         m_FrontBar = new SettingsBar(Scrollbar.HORIZONTAL,
                                       m_Owner.getWaterFront(),5,0,40);
         m_FrontBar.setBounds(5,215,240,30);
         m_FrontBar.addAdjustmentListener(this);
         add(m_FrontBar);

         //Back watering setting label
         m_BackLabel = new DataLabel("Back","mm/h",
                           m_Owner.getWaterBack(),
                           DataLabel.SETTING);
         m_BackLabel.setBounds(5,255,240,30);
         add(m_BackLabel);
         //Back watering setting bar
         m_BackBar = new SettingsBar(Scrollbar.HORIZONTAL,
                                       m_Owner.getWaterBack(),5,0,40);
         m_BackBar.setBounds(5,290,240,30);
         m_BackBar.addAdjustmentListener(this);
         add(m_BackBar);

         //Garden watering setting label
         m_GardenLabel = new DataLabel("Garden","mm/h",
                           m_Owner.getWaterGarden(),
                           DataLabel.SETTING);
         m_GardenLabel.setBounds(5,330,240,30);
         add(m_GardenLabel);
         //Front watering setting bar
         m_GardenBar = new SettingsBar(Scrollbar.HORIZONTAL,
                                       m_Owner.getWaterGarden(),5,0,40);
         m_GardenBar.setBounds(5,365,240,30);
         m_GardenBar.addAdjustmentListener(this);
         add(m_GardenBar);
      }

      //Auto button action
      public void actionPerformed(ActionEvent e)
      {
         m_Owner.toggleWaterAuto();
         m_Auto.updateValue(m_Owner.getWaterAuto());
      }

      //Adjustment action
      public void adjustmentValueChanged(AdjustmentEvent e)
      {
         //Change Front water setting
         m_Owner.setWaterFront(m_FrontBar.getValue());
         m_FrontLabel.updateValue(m_Owner.getWaterFront());
         //Change Back water setting
         m_Owner.setWaterBack(m_BackBar.getValue());
         m_BackLabel.updateValue(m_Owner.getWaterBack());
         //Change Garden water setting
         m_Owner.setWaterGarden(m_GardenBar.getValue());
         m_GardenLabel.updateValue(m_Owner.getWaterGarden());
      }
   }

   //Temperature panel for outside frame
   private class TempPanel extends LayoutPanel implements AdjustmentListener
   {
      //Scrollbar for setting outside heater
      SettingsBar m_HeaterBar;
      //Laebl for heater setting
      DataLabel m_HeaterLabel;

      public TempPanel()
      {
         super(260,55,410,115);
         //Add components
         DataLabel dLabel;
         //Current outside Tempurature
         dLabel = new DataLabel("Temp","C",
                           m_Owner.getTempCurrent(),
                           DataLabel.SENSOR);
         dLabel.setBounds(5,5,195,30);
         add(dLabel);
         //Tempurature forecast
         dLabel = new DataLabel(m_Owner.getTempForcastMin() + "C"
                                + " to " + m_Owner.getTempForcastMax() + "C",
                                "", 0, DataLabel.SPECIAL);
         dLabel.setBounds(5,40,195,30);
         add(dLabel);
   
         //Current outside heater setting
         m_HeaterLabel = new DataLabel("Heater","%",
                           m_Owner.getTempLevel(),
                           DataLabel.SETTING);
         m_HeaterLabel.setBounds(210,5,195,65);
         add(m_HeaterLabel);
         //Settings bar for outside heater
         m_HeaterBar = new SettingsBar(Scrollbar.HORIZONTAL,
                                       m_Owner.getTempLevel(),10,0,100);
         m_HeaterBar.setBounds(5,80,400,30);
         m_HeaterBar.addAdjustmentListener(this);
         add(m_HeaterBar);
      }

      //Adjsutment action
      public void adjustmentValueChanged(AdjustmentEvent e)
      {
         //Change heater setting
         m_Owner.setTempLevel(m_HeaterBar.getValue());
         m_HeaterLabel.updateValue(m_Owner.getTempLevel());
      }
   }

   //Lighting Panel for outside frame
   private class LightPanel extends LayoutPanel implements AdjustmentListener
   {
      //Action listener for front auto lighting
      private class AutoFront implements ActionListener
      {
         public void actionPerformed(ActionEvent e)
         {
            m_Owner.toggleLightAutoFront();
            m_AutoFront.updateValue(m_Owner.getLightAutoFront());
         }
      }

      //Action listener for back auto lighting
      private class AutoBack implements ActionListener
      {
         public void actionPerformed(ActionEvent e)
         {
            m_Owner.toggleLightAutoBack();
            m_AutoBack.updateValue(m_Owner.getLightAutoBack());
         }
      }

      //Buttons for turning auto lighting on / off
      private AutoButton m_AutoFront;
      private AutoButton m_AutoBack;
      //Sliders for adjusting lighting level
      private SettingsBar m_FrontBar;
      private SettingsBar m_BackBar;
      //Label for watering lighting level
      private DataLabel m_FrontLabel;
      private DataLabel m_BackLabel;

      public LightPanel()
      {
         super(260,175,410,300);
         //Add components
         DataLabel dLabel;
         //Auto button for front lighting
         m_AutoFront = new AutoButton(m_Owner.getLightAutoFront());
         m_AutoFront.addActionListener(new AutoFront());
         m_AutoFront.setBounds(5,5,180,90);
         add(m_AutoFront);

         //Front lighting label
         m_FrontLabel = new DataLabel("Front","%",
                           m_Owner.getLightLevelFront(),
                           DataLabel.SETTING);
         m_FrontLabel.setBounds(190,5,215,45);
         add(m_FrontLabel);
         //Front lighting setting bar
         m_FrontBar = new SettingsBar(Scrollbar.HORIZONTAL,
                                      m_Owner.getLightLevelFront(),10,0,100);
         m_FrontBar.setBounds(190,60,215,30);
         m_FrontBar.addAdjustmentListener(this);
         add(m_FrontBar);

         //Ambient light label
         dLabel = new DataLabel("Ambient","%",m_Owner.getLightAmbient()
                                ,DataLabel.SENSOR);
         dLabel.setBounds(5,105,400,60);
         add(dLabel);

         //Auto button for back lighting
         m_AutoBack = new AutoButton(m_Owner.getLightAutoBack());
         m_AutoBack.addActionListener(new AutoBack());
         m_AutoBack.setBounds(5,175,180,90);
         add(m_AutoBack);

         //Back lighting label
         m_BackLabel = new DataLabel("Back","%",
                           m_Owner.getLightLevelBack(),
                           DataLabel.SETTING);
         m_BackLabel.setBounds(190,175,215,45);
         add(m_BackLabel);
         //Back lighting setting bar
         m_BackBar = new SettingsBar(Scrollbar.HORIZONTAL,
                                      m_Owner.getLightLevelBack(),10,0,100);
         m_BackBar.setBounds(190,230,215,30);
         m_BackBar.addAdjustmentListener(this);
         add(m_BackBar);
      }

      //Adjsutment action
      public void adjustmentValueChanged(AdjustmentEvent e)
      {
         //Change front light setting
         m_Owner.setLightLevelFront(m_FrontBar.getValue());
         m_FrontLabel.updateValue(m_Owner.getLightLevelFront());
         //Change back light setting
         m_Owner.setLightLevelBack(m_BackBar.getValue());
         m_BackLabel.updateValue(m_Owner.getLightLevelBack());
      }
   }

   //Shortcut to settings
   private Outside m_Owner;

   //Constructor
   public OutsideFrame()
   {
      super("Outside");

      m_Owner = SettingsManager.getOutside();
      m_Layout.add(new RainPanel());
      m_Layout.add(new TempPanel());
      m_Layout.add(new LightPanel());
      m_Layout.add(new MenuPanel(1,1,0,1,1));
   }
}
