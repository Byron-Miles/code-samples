import java.awt.*;
import java.awt.event.*;

/**
* Comp320 Practial Assignment 4
* Byron Miles 220057347
**/

//GUI Pane for chat client
public class ChatClientUI extends Frame 
{
   //Fields and buttons for the GUI
   private Button btClear_ = new Button("Clear");
   private Button btQuit_ = new Button("Quit");
   private TextArea outText_ = new TextArea("", 5, 40, 
                               TextArea.SCROLLBARS_VERTICAL_ONLY);
   private TextArea inText_ = new TextArea("", 3, 40,
                               TextArea.SCROLLBARS_VERTICAL_ONLY);

   //Create new ChatClient window
   public ChatClientUI()
   {
      super("Java Chatclient");

      //Set look and feel
      outText_.setEditable(false);
      outText_.setFocusable(false);
      outText_.setBackground(new Color(255,255,255));

      //Create panels for holding the field.
      Panel outPanel = new Panel(new BorderLayout());
      Panel inPanel = new Panel(new BorderLayout());
      outPanel.add(outText_, BorderLayout.CENTER);
      inPanel.add(inText_, BorderLayout.CENTER);

      //Create a panel for the buttons and add listeners.
      Panel buttonPanel = new Panel(new GridLayout(1, 0));
      btClear_.addActionListener(new ClearListener());
      buttonPanel.add(btClear_); 
      buttonPanel.add(btQuit_);
	
      //Add, pack, and show.
      add(outPanel, BorderLayout.NORTH);
      add(inPanel, BorderLayout.CENTER);
      add(buttonPanel, BorderLayout.SOUTH);
      pack();
      setVisible(true); //Bug fix: show() deprecated
   }
 
   public TextArea outField()
   {
      return outText_;
   }

   public TextArea inField()
   {
      return inText_;
   }
  
   public Button quitBtn()
   {
      return btQuit_;
   }

   //Clear Chat field on the UI
   class ClearListener implements ActionListener 
   {
      public void actionPerformed(ActionEvent e) 
      {
	    inText_.setText("");
      }
   }

}

