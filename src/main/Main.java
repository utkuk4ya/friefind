package main;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.*;
import service.*;

//Main.java
public class Main {
 public static void main(String[] args) throws FileNotFoundException {
    
	 FriefindSystem.readTxt();

     User.displayTotalUserCount(); 

     SubscriptionManager subManager = new SubscriptionManager();
     
     FriefindSystem.displayAllUsers();
     
     System.out.println("\n--- Statistics ---");
     FriefindSystem.calculateAverageAge();
     
     Scanner scanner = new Scanner(System.in);
     
     while(true) {
    	 System.out.print("\nDo you want to add user? (Y/N): ");
    	 String ans = scanner.nextLine();
    	 
    	 if(ans.equalsIgnoreCase("N")) {
    		 break;
    	 }
    	 else {
    		 User newUser = FriefindSystem.createNewUser();
    		 boolean result = FriefindSystem.addUser(newUser);
    	 }
     }
    
     FriefindSystem.displayAllUsers();
     
     
 }
}