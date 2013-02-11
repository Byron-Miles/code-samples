//A panel for outside

import java.awt.*;
import java.awt.event.*;

public class OutsidePanel extends TopPanel
{
   //A shortcut to the outside settings object
   private Outside m_Owner;

   //Constructor
   public OutsidePanel()
   {
      //Call TopPanel constructor, passing it 3 for setting the navButtons
      super("", 3);
      //Get the outside settings object
      m_Owner = SettingsManager.getOutside();
      add(new WaterPanel());
      add(new TempPanel());
      add(new LightPanel());
      
   }

///////////////////////////////////////////////////////////////////////////////

   //Water Panel, shows rainfall and watering settings
   private class WaterPanel extends LayoutPanel implements ActionListener,
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

      public WaterPanel()
      {
         super(5, 30, 250, 460, "Water");
         //Add components
         DataLabel setup;
         //Rainfall in past 24hrs label
         setup = new DataLabel(DataLabel.SENSOR, "24hr Rainfall",
                                 m_Owner.getRainFall(), "mm", -1);
         setup.setBounds(x(),y(),240,40);
         add(setup);
         //Adjust position for next component
         yAdd(45);
         //Rain forecast label
         setup = new DataLabel(DataLabel.FORECAST, "Forecast",
                                 m_Owner.getRainChance(), "%", "of",
                                 m_Owner.getRainForecast(), "mm");
         setup.setBounds(x(),y(),240,40);
         add(setup);
         //Adjust position for next component
         yAdd(50);

         //Auto watering system button
         m_Auto = new AutoButton(m_Owner.getWaterAuto());
         m_Auto.addActionListener(this);
         m_Auto.setBounds(x(),y(),240,90);
         add(m_Auto);
         //Adjust position for next component
         yAdd(100);

         //Front watering setting label
         m_FrontLabel = new DataLabel(DataLabel.SETTING, "Front",
                                       m_Owner.getWaterFront(), "mm/h", 0);
         m_FrontLabel.setBounds(x(),y(),240,40);
         add(m_FrontLabel);
         //Adjust position for next component
         yAdd(40);
         //Front watering setting bar
         m_FrontBar = new SettingsBar(true, m_Owner.getWaterFront(), 5, 0, 40,
                                       "OFF", "40");
         m_FrontBar.setBounds(x(),y(),240,30);
         m_FrontBar.addAdjustmentListener(this);
         add(m_FrontBar);
         //Adjust position for next component
         yAdd(40);

         //Back watering setting label
         m_BackLabel = new DataLabel(DataLabel.SETTING, "Back",
                                       m_Owner.getWaterBack(), "mm/h", 0);
         m_BackLabel.setBounds(x(),y(),240,40);
         add(m_BackLabel);
         //Adjust position for next component
         yAdd(40);
         //Back watering setting bar
         m_BackBar = new SettingsBar(true, m_Owner.getWaterBack(), 5, 0, 40,
                                       "OFF", "40");
         m_BackBar.setBounds(x(),y(),240,30);
         m_BackBar.addAdjustmentListener(this);
         add(m_BackBar);
         //Adjust position for next component
         yAdd(40);

         //Garden watering setting label
         m_GardenLabel = new DataLabel(DataLabel.SETTING, "Garden",
                                       m_Owner.getWaterGarden(), "mm/h", 0);
         m_GardenLabel.setBounds(x(),y(),240,40);
         add(m_GardenLabel);
         //Adjust position for next component
         yAdd(40);
         //Front watering setting bar
         m_GardenBar = new SettingsBar(true, m_Owner.getWaterGarden(), 5, 0, 40,
                                       "OFF", "40");
         m_GardenBar.setBounds(x(),y(),240,30);
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

   //Temperature panel, shows weather and heater controls
   private class TempPanel extends LayoutPanel implements AdjustmentListener
   {
      //Scrollbar for setting outside heater
      SettingsBar m_HeaterBar;
      //Label for heater setting
      DataLabel m_HeaterLabel;

      public TempPanel()
      {
         super(260, 30, 530, 165, "Temperature");
         //Add components
         DataLabel setup;
         //Current outside Tempurature
         setup = new DataLabel(DataLabel.SENSOR, "Temp",
                                 m_Owner.getTempCurrent(), "C", -1);
         setup.setBounds(x(),y(),265,40);
         add(setup);
         //Adjust position for next component
         yAdd(45);
         //Tempurature forecast
         setup = new DataLabel(DataLabel.FORECAST, "Forecast",
                                 m_Owner.getTempForecastMin(), "C", "to",
                                 m_Owner.getTempForecastMax(), "C");
         setup.setBounds(x(),y(),265,40);
         add(setup);
         //Adjust position for next component
         xAdd(270); ySet(30);
         //Current outside heater setting
         m_HeaterLabel = new DataLabel(DataLabel.SETTING, "Heater",
                                       m_Owner.getTempLevel(), "C", 15);
         m_HeaterLabel.setBounds(x(),y(),250,85);
         add(m_HeaterLabel);
         //Adjust position for next component
         xySet(5, 120);
         //Settings bar for outside heater
         m_HeaterBar = new SettingsBar(true, m_Owner.getTempLevel(), 2, 15,
                                       36, "OFF", "36");
         m_HeaterBar.setBounds(x(),y(),520,40);
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



   //Lighting Panel, shows ambient light and settings for front and back lights
   private class LightPanel extends LayoutPanel implements AdjustmentListener
   {
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
         super(260,200,530,290, "Lighting");
         //Add components
         DataLabel setup;
         //Ambient light label
         setup = new DataLabel(DataLabel.SENSOR, "Ambient",
                                 m_Owner.getLightAmbient(), "%", -1);
         setup.setBounds(x(),y(),520,40);
         add(setup);
         //Adjust position for next component
         yAdd(50);

         //Front lighting label
         m_FrontLabel = new DataLabel(DataLabel.SETTING, "Front",
                                       m_Owner.getLightLevelFront(), "%", 0);
         m_FrontLabel.setBounds(x(),y(),365,45);
         add(m_FrontLabel);
         //Adjust position for next component
         yAdd(50);
         //Front lighting setting bar
         m_FrontBar = new SettingsBar(true, m_Owner.getLightLevelFront(),
                                       10, 0, 100, "OFF", "100");
         m_FrontBar.setBounds(x(),y(),365,45);
         m_FrontBar.addAdjustmentListener(this);
         add(m_FrontBar);
         //Adjust position for next component
         xAdd(370); ySet(80);
         //Auto button for front lighting
         m_AutoFront = new AutoButton(m_Owner.getLightAutoFront());
         m_AutoFront.addActionListener(new AutoFront());
         m_AutoFront.setBounds(x(),y(),150,95);
         add(m_AutoFront);
         //Adjust position for next component
         xySet(5, 185);

         //Back lighting label
         m_BackLabel = new DataLabel(DataLabel.SETTING, "Back",
                                       m_Owner.getLightLevelBack(), "%", 0);
         m_BackLabel.setBounds(x(),y(),365,45);
         add(m_BackLabel);
         //Adjust position for next component
         yAdd(50);
         //Back lighting setting bar
         m_BackBar = new SettingsBar(true, m_Owner.getLightLevelBack(),
                                       10, 0, 100, "OFF", "100");
         m_BackBar.setBounds(x(),y(),365,45);
         m_BackBar.addAdjustmentListener(this);
         add(m_BackBar);
         //Adjust position for next component
         xAdd(370); ySet(185);
         //Auto button for front lighting
         m_AutoBack = new AutoButton(m_Owner.getLightAutoBack());
         m_AutoBack.addActionListener(new AutoBack());
         m_AutoBack.setBounds(x(),y(),150,95);
         add(m_AutoBack);
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
   }
}
