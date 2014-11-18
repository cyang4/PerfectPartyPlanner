/******************************************************************************
  * Contacts.java
  * Code emulates a contact book with name, preferences (main, vegetable,
  * dessert) and allergies. Employs a hash table with name as key and
  * Person class as value.
  * 
  * Final Project
  * Created 5/6/14 by Cecille Yang
  * 
  * 
  * ******************************************************************/

import java.util.*;
import java.io.*;
public class Contacts{
  private Hashtable <String, Person> contacts;
  private int numContacts; //to be incremented each time a contact is added
  private final String END_ENTRY = "---";
  boolean fileIsCorrect;
  
  /*********************************************************************
    * Default constructor, constructs an empty hashtable.
    * *****************************************************************/
  public Contacts(){
    contacts = new Hashtable<String, Person>();
    numContacts = 0;
  }
  /*********************************************************************
    * Alternative constructor, reads in a file to load previously
    * saved contacts.
    * @param filename the name of the file to be read in
    * Catches the FileNotFoundException
    * *****************************************************************/
  public Contacts(String filename){
    numContacts = 0;
    try {
      
      File f = new File(filename);
      Scanner s = new Scanner(f);
      contacts = new Hashtable<String, Person>();
      
      while(s.hasNext()){
        String temp = s.nextLine();
        //System.out.println(temp);
        //if file is not signaling the end of a person...
        if(!temp.equals(END_ENTRY)){
          String favMain = s.nextLine();
          String favVeg = s.nextLine();
          String favDes = s.nextLine();
          String allergy = s.nextLine();
          Person tempP = constructPerson(temp, favMain, favVeg, favDes, allergy);
          add(temp, tempP);
          temp = s.nextLine();
        }
        //else the next is signaling the end of a person.
        else{
          temp = s.nextLine(); //need to go another line to skip over the separator.
        }
      }
    }     
    
    
    catch(FileNotFoundException e){
      fileIsCorrect = false;
      System.out.println("Sorry, filename is not correct");
    }
    
  }
  /*********************************************************************
    * Helper method for second constructor to make a person from a series
    * of Strings
    * @param name
    * @param favMain a string list with the ", " delimiter
    * @param favVeg a string list with the ", " delimiter
    * @param favDes a string list with the ", " delimiter
    * @param allergy a string list with the ", " delimiter
    * @return a person with the specified characteristics
    * *****************************************************************/
  private Person constructPerson(String name, String favMain, String favVeg, String favDes, String allergy){
    LinkedList<String> m = convertLinked(favMain);
    LinkedList<String> v = convertLinked(favVeg);
    LinkedList<String> d = convertLinked(favDes);
    LinkedList<String> a = convertLinked(allergy);
    
    return new Person(name, m, v, d, a);
  }
  /*********************************************************************
    * Helper method for constructPerson to convert a string to a linked
    * list
    * @param entry a very specific String list
    * @return the LinkedList version of the String entry originally read
    * from file
    * *****************************************************************/
  private LinkedList<String> convertLinked(String entry){
    LinkedList<String> temp = new LinkedList<String>();
    Scanner scan = new Scanner(entry);
    scan.useDelimiter(", "); //use ", " as the separator instead of space
    while(scan.hasNext()){
      temp.add(scan.next());
    }
    scan.close(); //close the scanner to avoid possible future troubles
    return temp;
    
  }
  
  /*********************************************************************
    * Method to write the current contacts to a file of specified filename
    * @param filename the name of the file to write to
    * *****************************************************************/
  public void saveContacts(String filename){
    try {
      PrintWriter writer = new PrintWriter(new File(filename)); //used to write to a new file of this filename
      Enumeration<String> keys = contacts.keys(); //get keys
      while(keys.hasMoreElements()){
        String p = keys.nextElement(); //person to be used for the next few lines of write commands
        writer.println(contacts.get(p).getName()); //line for name
        writer.println(stringFile(contacts.get(p).getMain())); //convert lists to string format
        writer.println(stringFile(contacts.get(p).getVegetables()));
        writer.println(stringFile(contacts.get(p).getDessert()));
        writer.println(stringFile(contacts.get(p).getAllergies()));
        writer.println(END_ENTRY);
      }
      
      
      writer.close();
    } catch (IOException ex) {
      System.out.println("***(T)ERROR*** The file could nt be written: " + ex); //if for some reason the filename was bad
    }
  }
   /*********************************************************************
    * Helper method for saveContacts that converts a linked list to the 
    * appropriate string representation for file writing
    * @param s the linked list to convert to string format for file-writing
    * @return the string format of the linked list
    * *****************************************************************/
  private String stringFile(LinkedList<String> s){
    String temp = "";
    int t = 0;
    //loop through until second to last (to avoid the "," after the last entry)
    for (int i = 0; i<s.size(); i++){
      temp+= s.get(i)+", ";
      t = i;
    }
    //temp += s.get(t);
    return temp;
  }
  /*********************************************************************
    * Simple method to add a person to the contacts list
    * @param key the String used to file a person
    * @param value the actual Person object to be filed away
    * @return whether a person was succesfully added
    * *****************************************************************/
  public boolean add(String key, Person value){
    if(contacts.get(key)==null){ //if there isn't already a contact with that name
    contacts.put(key, value); //add it
    numContacts++; //increment contact counter
    return true;
    }
    else return false; //if it does exist, don't add it
  }
  /*********************************************************************
    * Simple method to remove a person to the contacts list
    * @param key the name of the person
    * @return whether a person was succesfully added
    * *****************************************************************/
  public boolean remove (String key){
    if (contacts.containsKey(key)){
      contacts.remove(key);
      return true;
    }
    return false;
  }
  /*********************************************************************
    * String representation of contacts book
    * @return the string representation of the contacts class
    * *****************************************************************/
  public String toString(){
    String temp = "My Contacts Book: \n";
    Enumeration<String> keys = contacts.keys();
    while(keys.hasMoreElements()){ //loop through the keys and retrieve all people
      temp+= contacts.get(keys.nextElement());
      temp+="\n";
    }
    return temp;
  }
  /*********************************************************************
    * Method to get number of contacts in contacts
    * @return the number of contacts that exist
    * *****************************************************************/
  public int getNumContacts(){
   return numContacts; 
  }
  /*********************************************************************
    * Method to return the actual hash table used
    * @return the hashtable used to store contacts
    * *****************************************************************/
  public Hashtable<String, Person> getHash(){
   return contacts; 
  }
  /*********************************************************************
    * Method to retrieve a specific person based on a key
    * @param n the key for going back into the hash table to retrieve the person
    * @return the person corresponding to the key
    * *****************************************************************/
  public Person getPerson (String n){
    return contacts.get(n);
  }
  /*********************************************************************
    * String representation of contacts book
    * *****************************************************************/
  public static void main(String[] args){
    System.out.println("Trying to load in a bad contact list.");
    Contacts test = new Contacts("SD");
    Contacts test1 = new Contacts("sample.txt");
    System.out.println(test1);
    LinkedList<String> mainReb = new LinkedList<String>();
    mainReb.add("chicken");
    mainReb.add("pasta");
    LinkedList<String> vegReb = new LinkedList<String>();
    vegReb.add("corn");
    vegReb.add("salad");
    vegReb.add("carrots");
    LinkedList<String> desReb = new LinkedList<String>();
    desReb.add("cake");
    desReb.add("brownies");
    desReb.add("cookies");
    desReb.add("ice cream");
    LinkedList<String> allReb = new LinkedList<String>();
    allReb.add("peanuts");
    Person angela = new Person("Angela", mainReb, vegReb, desReb, allReb);
    System.out.println("Adding Angela.");
    test1.add("Angela", angela);
    Person rebecca = new Person("Rebecca");
    System.out.println("Trying to add Rebecca again... " + test1.add("Rebecca", rebecca));
    System.out.println(test1);
    System.out.println("Removing Rebecca...");
    System.out.println(test1.remove("Rebecca"));
    System.out.println(test1);
    test1.saveContacts("sampleOut.txt");
      
  }
  
  
  
  
}