/******************************************************************************
  * AboutPanel.java
  * Code for the about tab of the PPPGGG program. 
  * 
  * Final Project
  * Created 5/6/14 by Cecille Yang
  * Last Updated on 5/6/14 by Cecille Yang
  * 
  * ******************************************************************/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class AboutPanel extends JPanel
{
  public AboutPanel (){
    
    JPanel pane = new JPanel();
    pane.setPreferredSize (new Dimension (600, 600)); 
    pane.setBackground(new Color (255, 254, 241));
    
    
    JLabel imgAbout = new JLabel (new ImageIcon ("header.png"));
    
    JLabel about = new JLabel ("Welcome to the Party Planning People's Great Gathering GUI!");
    about.setFont(new Font("Apple Chancery", Font.PLAIN, 14));
    
    JLabel about1 = new JLabel ("Use the 'Contacts' and 'My Parties' tabs to create parties, ");
    about1.setFont(new Font("Apple Chancery", Font.PLAIN, 14));
    
    JLabel about2 = new JLabel ("add people and their food preferences, and get food suggestions!");
    about2.setFont(new Font("Apple Chancery", Font.PLAIN, 14));
    
    JLabel about3 = new JLabel ("Never host a sad party again!");
    about3.setFont(new Font("Apple Chancery", Font.PLAIN, 14));
    
    JLabel about4 = new JLabel ("Created by Rebecca Scanlon and Cecille Yang, 2014.");
    about4.setFont(new Font("Apple Chancery", Font.PLAIN, 14));
    
    JLabel imgLogo = new JLabel (new ImageIcon ("flower.png"));


    
    //adds all panes to the AboutPanel
    add (pane);
    pane.add (imgAbout);
    pane.add (about);
    pane.add (about1);
    pane.add (about2);
    pane.add (about3);
    pane.add (about4);
    pane.add(imgLogo);
   
    
    //sets size and color for aboutPanel
    setPreferredSize (new Dimension (700, 620));
    setBackground(new Color (178, 177, 159));
  }
}