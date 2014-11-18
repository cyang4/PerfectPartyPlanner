/******************************************************************************
  * ContactsPanel.java
  * Code for the contacts tab of the PPPGGG program. 
  * 
  * Final Project
  * Created 5/6/14 by Cecille Yang
  *
  * 
  * Notes:
  * -warning getSelectedValues()... has been deprecated
  *      It is deprecated in Java 7, which runs on our computers, 
  *      but the newer method is not available in Java 6, so we are 
  *      sticking with getSelectedValues instead of getSelectedValuesList
  * -when a new contacts file is loaded, the parties in the PartiesPanel
  * will be deleted because they may not contain the same persons that are
  * in the new contacts file
  * ****************************************************************************/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;


public class ContactsPanel extends JPanel
{
  private final String MAIN_FILE = "main_courses.txt";
  private final String VEG_FILE = "vegetables.txt";
  private final String DES_FILE = "desserts.txt";
  private final String ALL_FILE = "allergies.txt";
  
  private JButton saveFileB, loadFileB, newContactB, remove; //three buttons for 3 action events
  private JTextArea output; //area where contact list will be printed
  private String outputText; //to be altered to whatever contacts.toString() outputs
  private JScrollPane scroll; //for the scrolling capability
  private Contacts contacts;
  private MyParties my_parties;
  private PartiesPanel ppanel;
  private JTextField remName;
  
  
  public ContactsPanel (MyParties parties, PartiesPanel p){
    ppanel = p;
    my_parties = parties;
    contacts = new Contacts();
    JPanel pane = new JPanel();
    //Light beige background
    pane.setPreferredSize (new Dimension (600, 600)); 
    pane.setBackground(new Color (255, 254, 241));
    //pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
    add (pane);
    
    //Title panel
    JLabel imgAdd = new JLabel (new ImageIcon ("My Contacts.png"));
    JPanel pane0= new JPanel();
    pane0.setBackground(new Color(255, 254, 241));
    pane0.add(imgAdd);
    pane.add(pane0);
    
    //Info entry panel
    JPanel pane1 = new JPanel();
    pane1.setBackground(new Color (255, 254, 241));
    pane.add(pane1);
    
    //Add buttons for three options-- add, update, delete
    saveFileB = new JButton("Save To File");
    pane1.add(saveFileB);
    
    loadFileB = new JButton("Load From File");
    pane1.add(loadFileB);
    
    newContactB = new JButton("New Contact");
    pane1.add(newContactB);
    
    
    //Displays list of contacts added
    JPanel pane2 = new JPanel();
    output = new JTextArea("The added contacts will appear here.");
    output.setLineWrap(true); //allow line wrapping so it doesn't get out of text area
    output.setWrapStyleWord(true); //wrap by word
    pane2.add(output);
    pane2.setBackground(new Color (255, 254, 241));
    pane.add(pane2);
    
    
    //Scrollbar
    scroll = new JScrollPane(output); //construct a scrollbar on the output text area
    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); //vertical scrollbar
    scroll.setPreferredSize(new Dimension(500, 250)); //set dimension
    pane2.add(scroll);

    //Remove option
    JPanel pane3 = new JPanel();
    pane3.setBackground(new Color (255, 254, 241));
    remName = new JTextField(15);
    pane3.add(remName);
    remove = new JButton("Remove Contact");
    pane3.add(remove);
    pane.add(pane3);
    
    //Add action listeners
    ContactListener listenC = new ContactListener();
    newContactB.addActionListener(listenC);
    loadFileB.addActionListener(listenC);
    saveFileB.addActionListener(listenC);
    remove.addActionListener(listenC);
    
    
    //Green background
    setPreferredSize (new Dimension (700, 620));
    setBackground(new Color (178, 177, 159));
  }
  
  
  public MyParties getParties(){
    return my_parties;
  }
  
  
  
  private class ContactListener implements ActionListener{
    public void actionPerformed (ActionEvent event){
      Object source = event.getSource();
      //Code for implementing new contact GUI
      if(source == newContactB){
        LinkedList<String>tempM = new LinkedList<String>();
        LinkedList<String>tempV = new LinkedList<String>();
        LinkedList<String>tempD = new LinkedList<String>();
        LinkedList<String>tempA = new LinkedList<String>();
        String name = JOptionPane.showInputDialog("Contact Name: ");
        if (name != null) { //if the action wasn't cancelled
        //main
        try{
        File f = new File(MAIN_FILE); //read in file of list of main dishes
        Scanner s = new Scanner(f);
        LinkedList<String> mc = new LinkedList<String>();
        
        while(s.hasNext()){ //systematically add all foods to list
          mc.add(s.nextLine());
        }
        s.close();
        System.out.println(mc);
        String [] mainCourse = mc.toArray(new String[mc.size()]); //convert linked list to array for use in JList
        
        JList jlist = new JList (mainCourse);
        
        JOptionPane.showMessageDialog(null, jlist, "Main Courses: ", 1); //set it so jlist pops up in a message dialog
        
        Object[] temp = jlist.getSelectedValues();//default array to take in values selected by user
        
        for(int i=0;i<temp.length;i++){ //loop through and convert each object to string and add it to LinkedList
          tempM.add(temp[i].toString());
          System.out.println(temp[i]);
        } 
        //System.out.println(name);
        }
        catch(FileNotFoundException e){
          System.out.println("Sorry, " + MAIN_FILE + " not found.");
        }
        //vegetable, idea similar to main
        try{
        File f1 = new File(VEG_FILE);
        Scanner s1 = new Scanner(f1);
        ArrayList<String> vc = new ArrayList<String>();
        
        while(s1.hasNext()){
          vc.add(s1.nextLine());
        }
        s1.close();
        System.out.println(vc);
        String [] vegCourse = vc.toArray(new String[vc.size()]);
        
        JList jlist = new JList (vegCourse);
        
        JOptionPane.showMessageDialog(null, jlist, "Vegetables: ", 1);
        
        Object[] temp2 = jlist.getSelectedValues();
        for(int i=0;i<temp2.length;i++){
          tempV.add(temp2[i].toString());
          System.out.println(temp2[i]);
        } 
        //System.out.println(name);
        }
        catch(FileNotFoundException e){
          System.out.println("Sorry, "+ VEG_FILE + "file not found.");
        }
      //dessert, idea similar to main
      try{
        File f2 = new File(DES_FILE);
        Scanner s2 = new Scanner(f2);
        ArrayList<String> dc = new ArrayList<String>();
        
        while(s2.hasNext()){
          dc.add(s2.nextLine());
        }
        s2.close();
        //System.out.println(dc);
        String [] desCourse = dc.toArray(new String[dc.size()]);
        
        JList jlist = new JList (desCourse);
        
        JOptionPane.showMessageDialog(null, jlist, "Desserts: ", 1);
        
        Object[] temp3 = jlist.getSelectedValues();
        for(int i=0;i<temp3.length;i++){
          tempD.add(temp3[i].toString());
          System.out.println(temp3[i]);
        } 
        //System.out.println(name);
        }
        catch(FileNotFoundException e){
          System.out.println("Sorry, " + DES_FILE + " not found.");
        }
        
        //allergy, idea similar to main
        try{
        File f3 = new File(ALL_FILE);
        Scanner s3 = new Scanner(f3);
        ArrayList<String> ac = new ArrayList<String>();
        
        while(s3.hasNext()){
          ac.add(s3.nextLine());
        }
        s3.close();
        //System.out.println(dc);
        String [] allCourse = ac.toArray(new String[ac.size()]);
        
        JList jlist = new JList (allCourse);
        
        JOptionPane.showMessageDialog(null, jlist, "Allergies: ", 0);
        
        Object[] temp4 = jlist.getSelectedValues();
        for(int i=0;i<temp4.length;i++){
          tempA.add(temp4[i].toString());
          System.out.println(temp4[i]);
        } 
        //System.out.println(name);
        }
        
        catch(FileNotFoundException e){
          System.out.println("Sorry, " + DES_FILE + " not found.");
        }
        
        //construct a new contact based on inputs and display it in textArea
        boolean f = contacts.add(name, new Person(name, tempM, tempV, tempD, tempA));
        if (!f){
          JOptionPane.showMessageDialog(null, "Sorry, you already added this person before. Please try again with a different name.");
        }
        else{
        my_parties.setContacts(contacts); //update my_parties with the latest contacts info
        output.setText(contacts.toString());
        }
        }
      }
      
      //Code for choosing a file to load
      else if (source==loadFileB){
        final JFileChooser choose = new JFileChooser();
        int returnVal = choose.showOpenDialog(saveFileB); //file chooser!
        String filename = "";
        System.out.println("Chose to load file");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
          //System.out.println("Got to line before getName");
          filename = choose.getSelectedFile().getName();
          try{
          contacts = new Contacts(filename);
          my_parties.setContacts(contacts); //update my_parties with the latest contacts info
          output.setText(contacts.toString());
          ppanel.emptyPCombo();
          ppanel.emptyTextArea();
          ppanel.emptyParty(contacts);
          }catch(NoSuchElementException e){
            output.setText("Sorry, file format is incorrect.");
          }
          
        } 
        else{
          System.out.println("Load command cancelled by user");
        }
        
      }
      //Code for saving a file
       else if (source==saveFileB){
        final JFileChooser choose = new JFileChooser();
        int returnVal = choose.showSaveDialog(loadFileB);
        String filename = "";
        System.out.println("Chose to save file");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
          //System.out.println("Got to line before getName");
          filename = choose.getSelectedFile().getName();
          contacts.saveContacts(filename);
        } 
        
        else{
          System.out.println("Save command cancelled by user");
        }
       }
       else if (source==remove){
         int t = JOptionPane.showConfirmDialog(null,"Please make sure you have saved your parties, as you will need to reload them. Do you want to continue?", "Your Contacts will be updated", JOptionPane.YES_NO_OPTION);
         if (t==0){
           String name = remName.getText();
           boolean temp = contacts.remove(name);
           my_parties.setContacts(contacts);
           output.setText(my_parties.getContacts().toString());
           ppanel.setParties(my_parties);
           if (temp){
             JOptionPane.showMessageDialog(null, "You have successfully removed " + name + " from your contacts. Please reload your Parties file.");
           }
           else{
             JOptionPane.showMessageDialog(null, "Oops,  " + name + " not found. Please try again.");
           }
         }
         else {
           System.out.println("Command cancelled.");
         }
         
       }
    }
   
  }
}