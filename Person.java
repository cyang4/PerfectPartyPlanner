/* Last Edited by: Rebecca Scanlon on 5/10/14
 * 
 * Created by: Rebecca Scanlon
 * Created on: 5/6/14
 * 
 * Person Class: Represents an individual and their food preferences and allergies.
 * This class utilizes linked lists to keep track of preferred foods 
 * (one for each: main, vegetable, dessert) and a linked list to keep track of allergies, if there are any.
 * 
 */
import java.util.*;
public class Person{
  
 //instance variables
  private String name;
  private List<String>[] preferences;
  public final static int PREF_SIZE=4;
  
  
/***********************************************************************
 * 1st constructor: takes in only the name and sets all preferences to empty.
 * @param n the name of the person to be constructed
 * *********************************************************************/
  public Person(String n){
    name = n;
    preferences = new List[PREF_SIZE];
    
    for(int i = 0; i<preferences.length; i++){
    preferences[i] = new LinkedList<String>();
    }
  }
  /***********************************************************************
 * 2nd constructor: takes in the name and linked lists for each of the
 * preferences (main course, vegetable, and dessert) and allergies
 * @param n The name of the person to be constructed
 * @param m A LinkedList of favorite main courses
 * @param v A LinkedList of favorite vegetables
 * @param d A LinkedList of favorite desserts
 * @param a A LinkedList of allergies
 * *********************************************************************/
  public Person(String n, LinkedList<String> m, LinkedList<String> v, 
                LinkedList<String> d, LinkedList<String> a){
    name = n;
    preferences = new List[PREF_SIZE];
    preferences[0] = m;
    preferences[1] = v;
    preferences[2] = d;
    preferences[3] = a;    
  }
  /*******************************************************************
    * Getter that returns the preferred main dishes.
    * @return the LinkedList of favorite main dishes
    ******************************************************************/
  public LinkedList<String> getMain(){
    return (LinkedList<String>) preferences[0]; 
  }
   /*******************************************************************
    * Getter that returns the preferred vegetables.
    * @return the LinkedList of favorite vegetables
    ******************************************************************/
  public LinkedList<String> getVegetables(){
    return (LinkedList<String>) preferences[1]; 
  }
   /*******************************************************************
    * Getter that returns the preferred desserts.
    * @return the LinkedList of favorite desserts
    ******************************************************************/
  public LinkedList<String> getDessert(){
    return (LinkedList<String>) preferences[2]; 
  }
   /*******************************************************************
    * Getter that returns the person's allergies.
    * @return the LinkedList of allergies
    ******************************************************************/
  public LinkedList<String> getAllergies(){
    return (LinkedList<String>) preferences[3]; 
  }
   /*******************************************************************
    * Getter that returns the preferred main dishes.
    * @return the String representing the person's name
    ******************************************************************/
  public String getName(){
    return name;
  }
  
  /*******************************************************************
    * adds one favorite food or allegery to the coresponging list
    * @param food depending on the category, adds a food to either main,
    * vegetable, dessert, or allergy linked list
    ******************************************************************/
  public void addMain(String food){
    preferences[0].add(food);
  }
  
  public void addVegetable(String food){
    preferences[1].add(food);
  }
  
  public void addDessert(String food){
    preferences[2].add(food);
  }
  
  public void addAllergy(String food){
    preferences[3].add(food);
  }
  
  /****************************************************************
    * likes: input a food and a linkedList to check if it is in it.
    * @return true if the food is in the list and false otherwise
    * *************************************************************/
  public boolean likes(String food, LinkedList<String> checkList){
    return checkList.contains(food);    
  }
  
  public boolean likesMainDish(String food){
    return likes(food, (LinkedList)preferences[0]); 
  }

  public boolean likesVegetable(String food){
   return likes(food, (LinkedList)preferences[1]); 
  }
  
  public boolean likesDessert(String food){
   return likes(food, (LinkedList)preferences[2]); 
  }
  /***************************************************************
    * isAllergicTo:
    * parameter: String representing a food to check if someone is 
    * allergic to it
    * Returns true if the food is in the allergy list, false
    * otherwise
    * @param food the food to test for allergy
    * @return a boolean specifying whether food is in list
    **************************************************************/
  public boolean isAllergicTo(String food){
    return likes(food, (LinkedList)preferences[3]);
  }
  
  
  /* **************************************************************
   * toString()
   * returns a string containing all preferences and allergies 
   * @return a string representation of the Person class
   ******************************************************************/
  public String toString(){
    String temp = name + "\n";
    for(int i = 0; i<preferences.length;i++){
      switch(i){
        case 0: 
          temp += "Favorite Main: ";
          break;
        case 1: 
          temp += "Favorite Vegetables: ";
          break;
        case 2: 
          temp += "Favorite Dessert: ";
          break;
        case 3: 
          temp += "Allergies: ";
          break;
      }
      
      for(int j = 0; j<preferences[i].size(); j++){
        temp += preferences[i].get(j);
        if(!(j==preferences[i].size()-1))
          temp+=", ";
      }
      temp+="\n";
    }
    return temp;
  }
  
  
  /*****************************************************************
    * main()
    * Testing!
    ****************************************************************/
  public static void main(String[] args){
    Person cecille = new Person("Cecille");
    System.out.println(cecille);
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
    Person rebecca = new Person("Rebecca", mainReb, vegReb, desReb, allReb);
    System.out.println(rebecca);
    System.out.println("Rebecca is allegeric to peanuts (Expected: true) Got: "
                         + rebecca.isAllergicTo("peanuts"));
    System.out.println("Rebecca likes corn (likes).  (Expected: true) Got: " 
                         + rebecca.likes("corn", rebecca.getVegetables()));
    System.out.println("Rebecca likes corn (likesVegetable).  (Expected: true) Got: " 
                         + rebecca.likesVegetable("corn"));
    System.out.println("Rebecca doesn't like spinach (likesVegetable).  (Expected: false) Got: " 
                                       + rebecca.likesVegetable("spinach"));
  }
  
  
  
  
  
}