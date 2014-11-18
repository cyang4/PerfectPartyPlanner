/******************************************************************************
  * PartiesPanel.java
  * Code for the parties tab of the PPPGGG program. 
  * Last Edited by Rebecca Scanlon and Cecille Yang on 5/19/14
  * 
  * Final Project
  * Created 5/6/14 by Cecille Yang
  * 
  * Notes: 
  * -When loading parties from a file, if any parties were previously
  * added, they will be deleted when the new file is opened.  Thus, the user
  * must save their parties before opening the file if they want to keep 
  * those files.
  * -When loading a new contacts file in the contacts panel, the parties
  * will be deleted in the party panel because there may be different 
  * persons as guests in the party file that aren't in the new contacts
  * list.
  * ******************************************************************/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.net.URI;
import java.io.*;

public class PartiesPanel extends JPanel
{
  
  private JComboBox partyName; 
  private JButton add, remove, delete;
  private JButton newPartyButton, savePartiesB, loadPartiesB;
  private JButton search;
  private JTextArea output;
  private JScrollPane scroll;
  private MyParties parties;
  private ModListener mlisten;
  
  /****************************************************
    * Constructs the PartiesPanel
    * @param MyParties party
    ***************************************************/
  public PartiesPanel (MyParties party){
    
    parties = party;
    
    JPanel pane = new JPanel();
    pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
    pane.setPreferredSize (new Dimension (600, 600)); 
    pane.setBackground(new Color (255, 254, 241));
    
    //Title panel
    JLabel imgAbout = new JLabel (new ImageIcon ("My Parties.png"));
    JPanel pane0= new JPanel();
    pane0.setBackground(new Color(255, 254, 241));
    pane0.add(imgAbout);
    pane.add(pane0);
    
    //Add buttons for three options-- save, load, create new party
    JPanel pane1a = new JPanel();
    pane1a.setBackground(new Color (255, 254, 241));  
    savePartiesB = new JButton("Save Parties To File");
    pane1a.add(savePartiesB);
    loadPartiesB = new JButton("Load Parties From File");
    pane1a.add(loadPartiesB);
    newPartyButton = new JButton("Create New Party");
    pane1a.add(newPartyButton);
    
    //Party entry panel
    JPanel pane1 = new JPanel();
    pane1.setBackground(new Color (255, 254, 241));
    String [] partyList = new String[parties.getParties().size()];
    for(int i = 0; i<parties.getParties().size(); i++){
      partyList[i]=parties.getParties().get(i).getName();
    }
    partyName = new JComboBox(partyList);
    pane1.add(partyName);
    pane.add(pane1);
    
 
    //Add buttons for three options-- add contact, remove contact delete party
    JPanel pane3 = new JPanel();
    add = new JButton("Add Guest ");
    remove = new JButton("Remove Guest ");
    delete = new JButton("Delete Party ");
    
    ButtonGroup group = new ButtonGroup();
    group.add(add);
    group.add(remove);
    group.add(delete);
    
    pane3.add(add);
    pane3.add(remove);
    pane3.add(delete);
    pane.add(pane3);
    pane3.setBackground(new Color (255, 254, 241));
       
    //Add button for searching
    search = new JButton("Search for a Recipe!");
    JPanel paneS = new JPanel();
    paneS.add(search);
    paneS.setBackground(new Color (255, 254, 241));
    pane.add(paneS);
    
    //listeners
    mlisten = new ModListener();
    add.addActionListener(mlisten);
    remove.addActionListener(mlisten);
    delete.addActionListener(mlisten);
    newPartyButton.addActionListener(mlisten);
    partyName.addActionListener(mlisten);
    search.addActionListener(mlisten);
    loadPartiesB.addActionListener(mlisten);
    savePartiesB.addActionListener(mlisten);
    
    //Displays list of contacts added
    JPanel pane2 = new JPanel();
    output = new JTextArea("The selected party info will appear here.");
    output.setLineWrap(true); //allow line wrapping so it doesn't get out of text area
    output.setWrapStyleWord(true); //wrap by word
    pane2.add(output);
    pane2.setBackground(new Color (255, 254, 241));
    pane.add(pane2);
    
    //Scrollbar for contacts
    scroll = new JScrollPane(output); //construct a scrollbar on the output text area
    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); //vertical scrollbar
    scroll.setPreferredSize(new Dimension(500, 250)); //set dimension
    pane2.add(scroll);
    pane.add(pane1a);
    //adds all panes to the AboutPanel
    add (pane); 
    
    //sets size and color for aboutPanel
    setPreferredSize (new Dimension (700, 620));
    setBackground(new Color (178, 177, 159));
  }
  
  /********************************************************
    * getUpdatedParties: use to reload parties after a change
    * has been made
    * @return MyParties
    *******************************************************/
  public MyParties getUpdatedParties(){
    return parties;
  }
  
  /********************************************************
    * emptyPCombo: use to reset combobox to have no parties
    * once new contacts are loaded in the contacts panel
    *******************************************************/
  public void emptyPCombo(){
    partyName.removeActionListener(mlisten);
    partyName.removeAllItems();
    partyName.addActionListener(mlisten);
  }
  
  /********************************************************
    * emptyTextArea: use to reset JTextArea when new contacts
    * are loaded in the contacts panel
    *******************************************************/
  public void emptyTextArea(){
    output.setText("");
  }
  
  /********************************************************
    * emptyParty: use to reset parties when new contacts are 
    * loaded in the contacts panel
    * @param Contacts c
    *******************************************************/
  public void emptyParty(Contacts c){
    parties = new MyParties(c);
  }
  
  public void setParties(MyParties p){
    parties = p;
  }
  private class ModListener implements ActionListener{
    public void actionPerformed(ActionEvent event){
      Object source = event.getSource();
      
      if(source==savePartiesB){  
        final JFileChooser choose = new JFileChooser();
        int returnVal = choose.showSaveDialog(savePartiesB);
        String filename = "";
        System.out.println("Chose to save file");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
          //System.out.println("Got to line before getName");
          filename = choose.getSelectedFile().getName();
          parties.saveToFile(filename);
        }
        else{
          System.out.println("Save command cancelled by user");
        }       
      } else if(source==loadPartiesB){ //will only add guests in the contact book  
        final JFileChooser choose = new JFileChooser();
        int returnVal = choose.showOpenDialog(loadPartiesB);
        String filename = "";
        System.out.println("Chose to load file");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
          //System.out.println("Got to line before getName");
          try{
          filename = choose.getSelectedFile().getName();
            Contacts cTemp = parties.getContacts();
            
            parties = new MyParties(filename, cTemp); //create new MyParties from file
            if (!parties.getAllAdded()) //if guests were in the parties that weren't in the contact book
            {
              JOptionPane.showMessageDialog(null,"Some of your guests were not in your contacts book, and were not added to the party :(");
            }            
            partyName.removeActionListener(mlisten);
            partyName.removeAllItems(); //reset combobox to have no parties
            for(int i=0; i<parties.getParties().size(); i++){
              partyName.addItem(parties.getParties().get(i).getName()); //add new parties to combobox
            }
            partyName.addActionListener(mlisten);
            //display the party info and the recommended menu for the selected party
            partyName.setSelectedIndex(0);
          }catch(NoSuchElementException e){
            output.setText("Sorry, file format is incorrect.");
          }
        } 
        else{
          System.out.println("Load command cancelled by user");
        }
      } else{
        //if the party list is empty, don't want to allow users to add/remove guests
        if(parties.getParties().isEmpty()){ 
          //do nothing except create new parties
          if(source == newPartyButton){   
            String name = JOptionPane.showInputDialog("Party Name: ");
            if(name!=null){ //if they don't click exit/cancel
              Party newParty = new Party(name);
              String [] contacts = parties.getNamesofContacts();
              if(contacts.length==0){
                JOptionPane.showMessageDialog(null, "You have no contacts to add to your party. "+
                                              "Go to the contacts tab to add more contacts to your list."); 
              } else { //they have contacts that they haven't added to their party yet
                JList jlist = new JList (contacts); 
                JOptionPane.showMessageDialog(null, jlist);
                int[] indexOfGuest = jlist.getSelectedIndices();
                for(int i = 0; i<indexOfGuest.length; i++){
                  newParty.addGuest(parties.getContacts().getHash().get(contacts[indexOfGuest[i]])); //add the guests to the new party
                }
                partyName.addItem(newParty.getName()); //add party name to list in combobox
                parties.addParty(newParty); //add party name to parties
                partyName.setSelectedIndex(0); //update JTextArea to have new party info and menu
              }
            }
          }
        } else { //there is a party in myparties     
          if (source == search){
            Party p = parties.getParty(partyName.getSelectedIndex());
            ArrayList<String> allFoods = p.getAllCommon(); //get all the recommended food for the party
            
            String [] allCourse = allFoods.toArray(new String[allFoods.size()]); //make food list into an array
            
            JList jlist = new JList (allCourse); //display recommended food in a JList
            jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JOptionPane.showMessageDialog(null, jlist, "Select a food to search: ", 1);
            try{
              String s = jlist.getSelectedValue().toString(); //get the food they want to search for a recipe for
              s = s.replace(" ", "+");
            //  System.out.println(s);
              String searchTerm = "http://www.google.com/search?q="+s+"+recipe";
            //  System.out.println(searchTerm);
              
              try{
                Desktop.getDesktop().browse(new URI(searchTerm));
              }catch(java.net.URISyntaxException e){
                System.out.println("Oops! bad url.");
              }
              catch(java.io.IOException e1){
                System.out.println("Oops! IOException!");
              }
            } catch(java.lang.NullPointerException e2){ 
              JOptionPane.showMessageDialog(null, "Please select a food in order to search for a recipe.");
            }
          }else if (source == add){
            String [] contacts = parties.getNamesofContacts();
            System.out.println(contacts.length);
            //get the guests of the currently selected party
            LinkedList<Person> guests = parties.getParty(partyName.getSelectedIndex()).getGuests();
            if(guests.size()==contacts.length){ //all the contacts are guests of the party
              JOptionPane.showMessageDialog(null, "You have no more contacts to add to your party. "+
                                            "Go to the contacts tab to add more contacts to your list.");
            } else {
              //System.out.println("Guests are: " + p);
              String[] guestNames = new String[guests.size()];
              for(int i = 0; i<guests.size(); i++){
                guestNames[i]=guests.get(i).getName(); //retrieve all names of guests
              }
              String[] temp = new String[contacts.length];
              //makes so that JList of people to add to the party contains only persons in the contact list that
              //are not already guests
              int k=0;
              for(int i =0; i<contacts.length;i++){ //for each contact
                
                //check if the name of the contact is the same as the name of the guest
                if(!parties.getParty(partyName.getSelectedIndex()).isGuest(contacts[i])){
                  temp[k]=contacts[i]; //if it's not the same (i.e. the contact is not a guest) then add the contact to the array
                  k++;
                }
              }
              
              JList jlist = new JList (temp);
              
              JOptionPane.showMessageDialog(null, jlist);
              int[] indexOfGuest = jlist.getSelectedIndices();
              if(indexOfGuest.length==0){
                //do nothing - no guests added
              } else{
                for(int i = 0; i<indexOfGuest.length; i++){
                  //adds corresponding guest that was selected to the party
                  parties.getParty(partyName.getSelectedIndex()).addGuest(parties.getContacts().getHash().get(temp[indexOfGuest[i]])); 
                }
                //print out new party info with updated menu recommendations
                partyName.setSelectedIndex(partyName.getSelectedIndex());            
              }  
            }
          } else if(source == remove){
            //get the guests of the selected party
            LinkedList<Person> guests = parties.getParty(partyName.getSelectedIndex()).getGuests();
            if(guests.isEmpty()){ //no guests in the party, so can't remove any
              JOptionPane.showMessageDialog(null, "There are no guests in your party.");
            } else{//there are guests to remove
              String[] guestNames = new String[guests.size()];
              //display only the names of the people that are guests of the party
              for(int i = 0; i<guests.size(); i++){
                guestNames[i]=guests.get(i).getName();  
              }
              JList jlist = new JList(guestNames);
              JOptionPane.showMessageDialog(null, jlist);
              int[] indexOfGuest = jlist.getSelectedIndices();
              for(int i = 0; i<indexOfGuest.length; i++){
                //remove all guests that were seleceted
                parties.getParty(partyName.getSelectedIndex()).removeGuest(parties.getContacts().getHash().get(guestNames[indexOfGuest[i]])); 
              }
              //update the JTextArea with the new party info and the updated menu recommendations
               partyName.setSelectedIndex(partyName.getSelectedIndex());
            }     
          } else if(source == newPartyButton){
            String name = JOptionPane.showInputDialog("Party Name: ");
            if(name!=null){ //if they don't press cancel
              Party newParty = new Party(name);
              String [] contacts = parties.getNamesofContacts();
              if(contacts.length==0){ //no contacts in contact book
                // System.out.println("at contacts ==0");
                JOptionPane.showMessageDialog(null, "You have no contacts to add to your party. "+
                                              "Go to the contacts tab to add more contacts to your list."); 
              } else { //there are contacts in the contact book
                //  System.out.println("Got here!");
                JList jlist = new JList (contacts);
                JOptionPane.showMessageDialog(null, jlist);
                int[] indexOfGuest = jlist.getSelectedIndices();
                for(int i = 0; i<indexOfGuest.length; i++){
                  //add each selected guest to the party
                  newParty.addGuest(parties.getContacts().getHash().get(contacts[indexOfGuest[i]])); 
                }
               //checks if the party has the same name as a previously added party
                if(parties.addParty(newParty)){
                  JOptionPane.showMessageDialog(null, "Parties with the same name are not allowed.  Please create the party with a new name."); 
                }else{
                  output.setText("You Just Created a New Party!\n" + newParty.toString());
                  partyName.addItem(newParty.getName()); //add name to list in combobox  
                  partyName.setSelectedIndex(parties.getParties().size()-1);
                }
                //    System.out.println("Got Here :(");
              }
              // System.out.println("Got Here :)");
            }
          }else if(source == delete){
            Party p = parties.getParty(partyName.getSelectedIndex()); //get party to remove    
            int n = JOptionPane.showConfirmDialog(delete,
                                                  "Are you sure you want to delete " + p.getName() + "?",
                                                  "Delete Party",
                                                  JOptionPane.YES_NO_OPTION);
           if (n==0){ //confirm that they want to delete
              parties.deleteParty(p);
              output.setText("You have deleted " + p.getName() + ".");
              partyName.removeItem(p.getName()); //remove party from combobox
            }
            
          } else if(source == partyName){
            //the party info: name and guests, along with the updated menu will be
            //displayed each time the user clicks on a party in the combobox.
            Party p = parties.getParty(partyName.getSelectedIndex());
            String temp = p.toString()+"\n\n";
            p.commonMainDish();
            p.commonVegetable();
            p.commonDessert();
            temp += "***RECOMMENDED FOOD***\n";
            temp += "Favorite Main Dishes: " + p.getmaxHappyMain()+" out of "+p.getGuests().size()+" guests like:\n"+p.commonMainDish()+"\n\n";
            temp += "Favorite Vegetables: " +p.getmaxHappyVeg()+" out of "+p.getGuests().size()+" guests like:\n"+p.commonVegetable()+"\n\n";
            temp += "Favorite Desserts: " +p.getmaxHappyDes()+" out of "+p.getGuests().size()+" guests like:\n"+p.commonDessert()+"\n\n";
            temp += (p.guestAllergies().equals("") ? "" : "WARNING!\n" + p.guestAllergies());
            
            output.setText(temp);
          }
        }
      }
    }
  }
}