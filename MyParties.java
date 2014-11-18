/*****************************************************************
  * Last Edited by Rebecca and Cecille 5/19/14
  * 
  * Date Created: 5/10/14
  * Created by: Rebecca Scanlon
  * 
  * MyParties: contains a LinkedList of Party objects and a Contacts.
  * Allows for adding and removing parties from the list, retrieving
  * the contacts, and saving the parties to a file.
  * This class has four different constructors, one that has no parameters,
  * one with a file name to read in the parties from and a Contacts object,
  * one with just a file name, and one with just a contacts object.  All
  * of these constructors are needed at different times in the PartiesPanel.
  * 
  ***************************************************************/
import java.util.*;
import java.io.*;

public class MyParties{
  Contacts myContacts;
  private LinkedList<Party> myparties;
  private final String END_ENTRY = "---";
  private boolean notAdded;
  
  /*******************************************************************
    * Constructor #1:
    * MyParties: no parameters, sets empty contacts and an empty party list.
    ******************************************************************/
  public MyParties(){
    myContacts = new Contacts();
    myparties = new LinkedList<Party>(); 
    notAdded = false;
  }
  
  /*******************************************************************
    * Constructor #2:
    * MyParties: Reads in from a file
    * @param String filename that has the parties in the correct format
    * (the format when you save your parties to a file)
    * @param Contacts c
    ******************************************************************/  
  public MyParties(String filename, Contacts c){
    myContacts = c;
    notAdded = false;
    try {   
      File f = new File(filename);
      Scanner s = new Scanner(f);
      boolean name = true;
      myparties = new LinkedList<Party>();
      Party p = new Party("");
      while(s.hasNext()){   
        String temp = s.nextLine();
        //System.out.println(temp);
     //if file is not signaling the end of a party...
        if(!temp.equals(END_ENTRY)){
          if(name){ //if it is the first line after the END_ENTRY then it is the name
            p = new Party(temp); //create party with that name    
            name=false; //next line will not be a name
          } else{
            if(c.getPerson(temp)!=null){ //only adds guests in the contact book
              p.addGuest(c.getPerson(temp)); //adds the person from the contact list that corresponds to the name just read in
            //  System.out.println(temp);
            }else notAdded = true; //guest wasn't in contact book so can't add guest to the party          
          }        
        } else { //end of a party
          myparties.add(p); //all guests to party read in, so add party to myparties
          name = true;//next line will be a name if there is a next line
        }
      }    
    }    
    catch(FileNotFoundException e){
      System.out.println("Sorry, filename is not correct");
    }
  }
  
  /*******************************************************************
    * Constructor #3: 
    * @param Party p
    * Sets contact list to empty Contacts object
    ******************************************************************/
  public MyParties(Party p){
    myContacts = new Contacts();
    myparties = new LinkedList<Party>();
    myparties.add(p);
    notAdded = false;
  }
  
  /*******************************************************************
    * Constructor #4: 
    * @param Contacts c
    * Sets myparties to an empty LinkedList<Party> -- no parties have been added
    ******************************************************************/
  public MyParties(Contacts c){
    myContacts = c;
    myparties = new LinkedList<Party>();
    notAdded = false;
  }
  
  /*******************************************************************
    * getAllAdded:
    * @return boolean: true if all of the guests were added (i.e. all
    * guests were in the contact book so they could be added to the 
    * party), otherwise returns false
    ******************************************************************/ 
  public boolean getAllAdded(){
    return !notAdded;
  }
  
    /*******************************************************************
    * addParty:
    * @param Party p
    * @return boolean: true if adding a party with the same name as a previously,
    * false otherwise
    * If a party with the same name as a previous party is tried to be added,
    * it will not be added, instead a line will print out telling the user that
    * the party was not added.
    ******************************************************************/
  public boolean addParty(Party p){
    boolean sameName = false;
    for(int i = 0; i<myparties.size(); i++){ //for each party in myparties
      if(p.getName().equals(myparties.get(i).getName())){ //checks if the name already exists
        sameName=true;
      }
    }
    if(sameName){
      System.out.println("Party was not added: name of party already used.");
    } else{ //if the party name is not already used then add the party to the list
      myparties.add(p);
    }
    return sameName;
  }
  
   /*******************************************************************
    * deleteParty: removes a party from the myparties list
    * @param Party p
    ******************************************************************/
  public void deleteParty(Party p){
    myparties.remove(p); 
  } 
  
   /*******************************************************************
    * getParties: Returns the linkedlist of parties
    * @return LinkedList<Party> myparties
    ******************************************************************/
  public LinkedList<Party> getParties(){
    return myparties;
  }

   /*******************************************************************
    * getParty: 
    * @param int i, the index of the party that is retrieved
    * @return Party, the party at the index of i in the LinkedList<Party>
    ******************************************************************/
  public Party getParty(int i){
    return myparties.get(i);   
  }
  
   /*******************************************************************
    * setContacts: 
    * @param Contacts c
    ******************************************************************/
  public void setContacts(Contacts c){
    myContacts = c;
  }
  
   /*******************************************************************
    * getContacts:
    * @return Contacts myContacts
    ******************************************************************/
  public Contacts getContacts(){
    return myContacts;
  }
  
   /*******************************************************************
    * toString
    * return String, holding all of the parties in the party list
    ******************************************************************/
  public String toString(){
    String temp = "";
    for (int i = 0; i<myparties.size(); i++){
      temp+=myparties.get(i).toString();
      temp+="\n";
    }
    return temp;
  }
  
   /*******************************************************************
    * getNmaesofContacts:
    * @return String[] holding the names of each of the contacts in the
    * contacts book
    ******************************************************************/
  public String[] getNamesofContacts(){
    String[] names = new String[myContacts.getNumContacts()];
    //get all of the keys of the hashtable in myContacts beacuse
    //they correspond to the names of each of the contacts
    Enumeration<String> keys = myContacts.getHash().keys();
    int i = 0;
    while(keys.hasMoreElements()){
      names[i]=keys.nextElement(); //add each name to the array
      i++;
    }
    return names; //array of names of each contact   
  }
  
   /*******************************************************************
    * savePartyToString: 
    * @return String, this String contains a specific format so that 
    * the file can be read in to create a MyParties Class
    * The name of the first party is the first line, each line after that
    * has the name of each guest in the party on a seperate line, then on 
    * a seperate line is the END_ENTRY symbol and then the next party begins.
    ******************************************************************/
  public String savePartyToString(){
    String temp="";
    for(int i = 0; i<myparties.size(); i++){
      temp += myparties.get(i).getName()+"\n"; //get name of party
      //  System.out.println(myparties.get(i));
      LinkedList<Person> guests = myparties.get(i).getGuests();
      for(int j=0; j<guests.size(); j++){  
        temp+= guests.get(j).getName()+"\n"; //each guest in each party
      }
      temp+= END_ENTRY + "\n"; //break between parties
    }
    return temp;
  }
  
   /*******************************************************************
    * saveToFile: Writes the parties to a file
    * @param String filename, of which to save the file
    * Catches IOException if the file cannot be written.
    ******************************************************************/
  public void saveToFile(String filename){
    try {
      PrintWriter writer = new PrintWriter(new File(filename));      
      writer.print(this.savePartyToString());
      writer.close();
    } catch (IOException ex) {
      System.out.println("***(T)ERROR*** The file could nt be written: " + ex);
    } 
  }
  
   /*******************************************************************
    * main: Testing! 
    ******************************************************************/
  public static void main(String[] args){
    MyParties parties = new MyParties();
    Party a = new Party("a");
    Party b = new Party("b");

    System.out.println("Adding party \"a\" and party \"b\" to parties.");
    parties.addParty(a);
    parties.addParty(b);
    
    Contacts con = new Contacts();
    con.add("Cecille Yang", new Person("Cecille Yang"));
    parties.setContacts(con);
    
    System.out.println(parties.toString());
    Person cecille = new Person("Cecille Yang");
    System.out.println("Adding Cecille Yang to party \"a\" as a guest.");
    a.addGuest(cecille);
    System.out.println(parties.toString());
    System.out.println("Printing parties from savePartyToString():\n" + parties.savePartyToString());
    System.out.println("Saving this party to a file with saveToFile(\"saveToFileTEST.txt\").");
    parties.saveToFile("saveToFileTEST.txt");
    
    System.out.println("Creating a new MyParties using the second constructor with the"+
                       " file \"saveToFileTEST.txt\" and with adding Cecille Yang to the contact list:");
    Contacts s = new Contacts();
    s.add("Cecille Yang", new Person("Cecille Yang"));
    MyParties parties1 = new MyParties("saveToFileTEST.txt", s);
    System.out.println(parties1);
    System.out.println("getAllAdded() (Expected: true) Got: " + parties1.getAllAdded()+"\n\n");
    
    System.out.println("Creating a new MyParties using the second contstructor with the file \"saveToFileTEST.txt\""+
                       " but without adding Cecille Yang to the contact List." +
                       "\nExpected: The contact will not be added and there will be no guests:");
    Contacts s1 = new Contacts();
    MyParties parties1a = new MyParties("saveToFileTEST.txt", s1);
    System.out.println(parties1a);
    System.out.println("getAllAdded() (Expected: false) Got: " + parties1a.getAllAdded()+"\n\n");
    
    System.out.println("Creating a new MyParties using the third constructor. Created with \"a\" as an initial party.");
    MyParties parties2 = new MyParties(a);
    System.out.println(parties2);
    
    System.out.println("Creating a new MyParties using the fourth constructor.  Created with Contacts added (only Cecille Yang in contact book).");
    MyParties parties3 = new MyParties(s);
    System.out.println("Printing out this new MyParties, but will not print anything because no parties were added: " + parties3);
    parties3.addParty(a);
    System.out.println("Adding party \"a\" to this MyParties:\n" + parties3);
    parties3.deleteParty(a);
    System.out.println("Deleting party \"a\" from this MyParties:\n" + parties3);
    
    System.out.println("Adding Celia Honigberg to contact list.");
    s.add("Celia Honigberg", new Person("Celia Honigberg"));
    System.out.println("getNamesofContacts: (Expected:Cecille Yang, Celia Honigberg) Got: ");
    String[] names = parties3.getNamesofContacts();
    for(int i = 0; i<names.length; i++){
      System.out.println(names[i]); 
    }
    
    
  }
}