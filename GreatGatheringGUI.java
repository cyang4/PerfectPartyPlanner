/******************************************************************************
  * GreatGatheringGUI.java
  * Sets up the overarching layout for the program, Party Planning People's
  * Great Gathering GUI
  * 
  * Final Project
  * Created 5/6/14 by Cecille Yang
  *
  * 
  * KNOWN BUG: If we were to upload a Parties file on PartiesPanel without
  * any contacts, then update the contacts tab, we still cannot add these
  * new contacts in the PartiesPanel tab to the preloaded filed parties.
  * 
  * TRIED: ChangeListener that updates the MyParties class in PartiesPanel
  * every time we switch tabs in hopes of updating the Contacts within
  * MyParties, but it did not work. This is an issue to look into more
  * if we had more time.
  * 
  * UPDATE: NO MORE KNOWN BUGS! This now works.
  * ******************************************************************/
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.event.*;

public class GreatGatheringGUI extends JPanel{
  private MyParties myParties;
  private JTabbedPane tabbedPane;
  private PartiesPanel parties;
  private ContactsPanel contacts;
  /*********************************************************************
    * Made a class for GreatGatheringGUI originally to try and update
    * tabs through ChangeListener, but it has yet to work.
    * *****************************************************************/
  public GreatGatheringGUI(){
    myParties = new MyParties();
    AboutPanel about = new AboutPanel();
    parties = new PartiesPanel(myParties);
    contacts = new ContactsPanel(myParties, parties);
    tabbedPane = new JTabbedPane();
    tabbedPane.addTab("About", about);
    tabbedPane.addTab("My Contacts", contacts);
    tabbedPane.addTab("My Parties", parties);
    
    tabbedPane.addChangeListener(new ChangeListener() { //make a change listener to monitor tab changes
      public void stateChanged(ChangeEvent changeEvent) {
        //contacts = new ContactsPanel(myParties);
        parties = new PartiesPanel(contacts.getParties()); //update parties... this doesn't work.
        
        System.out.println("Parties: "+ contacts.getParties());
      }
    });
  }


  
  public JTabbedPane getPane(){
    return tabbedPane;
  }
  public static void main(String [] args){
    
    JFrame frame = new JFrame ("Party Planning People's Great Gathering GUI");
    frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    
    GreatGatheringGUI g = new GreatGatheringGUI();
    
    
    
    frame.getContentPane().add(g.getPane());
    
    frame.pack();
    frame.setVisible(true);
  
  }
  
  
    
  
}