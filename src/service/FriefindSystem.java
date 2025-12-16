package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import model.*;

public class FriefindSystem {

	static ArrayList<User> userList = new ArrayList<>();;
	private static HashSet<Integer> userIdSet = new HashSet<>();

	public static void readTxt() throws FileNotFoundException {

		File f = new File("friefindTxt.txt");

		Scanner sc = new Scanner(f);

		while (sc.hasNextLine()) {

			String line = sc.nextLine();
			String[] data = line.split(";");

			if (data.length != 7) {
				System.out.println("Invalid line skipped: " + line);
				continue;
			}

			try {
				int id = Integer.parseInt(data[0]);
				userIdSet.add(id);
				String name = data[1];
				int age = Integer.parseInt(data[2]);
				String city = data[3];
				String bio = data[4];
				String hobbies = data[5];
				String subscription = data[6];

				ProfileDetails details = new ProfileDetails(bio, hobbies);

				User user;
				if (subscription.equalsIgnoreCase("Standard")) {
					user = new StandardUser(id, name, age, details, city);
				} else {
					user = new PremiumUser(id, name, age, details, city, true);
				}

				userList.add(user);

			} catch (NumberFormatException e) {
				System.out.println("Invalid number format: " + line);
			}
		}

	}

	public static boolean addUser(User user) {
		if (userIdSet.contains(user.getId())) {
			System.out.println("Error: User with ID " + user.getId() + " already exists.");
			return false;
		}
		userList.add(user);
		userIdSet.add(user.getId());
		System.out.println("User added: " + user.getName());
		return true;
	}

	public static void displayAllUsers() {
		System.out.println("\n--- All Friefind Users ---");
		if (userList.isEmpty()) {
			System.out.println("No users found.");
		} else {
			for (User u : userList) {
				u.showProfileType();
				System.out.print(u + "\n");
			}
		}
	}

	public static Meeting setMeeting(User U1, User U2, Location location) {
		Scanner scanner = new Scanner(System.in);

		Activity activity = new Activity();

		activity.getInput();

		System.out.print("Enter meeting time (e.g., 09/09/2025): ");
		String time = scanner.nextLine();
		Meeting meeting = new Meeting(U1, U2, location, activity, time);

		System.out.println("\n--- Meeting Created Successfully ---");
		System.out.println(meeting.toString());

		System.out.print("\nDo you want to change the time? (Y/N): ");
		String choice = scanner.nextLine();

		if (choice.equalsIgnoreCase("Y")) {
			System.out.print("Enter new time: ");
			String newTime = scanner.nextLine();
			meeting.setTime(newTime);

			System.out.println("\nTime updated.");
			System.out.println("Updated Meeting Details:");
			System.out.println(meeting.toString());
		} else {
			System.out.println("Meeting confirmed.");
		}
		return meeting;
	}

	public static User searchUser(String name) {
		for (User u : userList) {
			if (u.getName().equalsIgnoreCase(name)) {
				System.out.println("User Found: " + u.getName());
				return u;
			}
		}
		System.out.println("User " + name + " not found.");
		return null;
	}

	public static void deleteUser(int id) {
		Iterator<User> iterator = userList.iterator();
		while (iterator.hasNext()) {
			User u = iterator.next();
			if (u.getId() == id) {
				iterator.remove();
				userIdSet.remove(id);
				System.out.println("User with ID " + id + " has been deleted.");
				User.totalUsers--;
				return;
			}
		}
		System.out.println("Deletion failed. ID " + id + " not found.");
	}

	public static void calculateAverageAge() {
		if (userList.isEmpty()) {
			System.out.println("Average Age: 0");
			return;
		}
		int sum = 0;
		for (User u : userList) {
			sum += u.getAge();
		}
		double avg = (double) sum / userList.size();
		System.out.println("Average Age of Users: " + String.format("%.2f", avg));
	}
	
	public static User createNewUser() {
	    Scanner sc = new Scanner(System.in);

	    System.out.println("\n--- Create New User ---");

	    System.out.print("Enter ID: ");
	    int id = sc.nextInt();
	   
	    sc.nextLine(); 

	    System.out.print("Enter name: ");
	    String name = sc.nextLine();

	    System.out.print("Enter age: ");
	    int age = sc.nextInt();
	    sc.nextLine(); 

	    System.out.print("Enter the city: ");
	    String city = sc.nextLine();

	    System.out.print("Enter a bio: ");
	    String bio = sc.nextLine();

	    System.out.print("Enter your hobbies: ");
	    String hobbies = sc.nextLine();

	  
	    ProfileDetails details = new ProfileDetails(bio, hobbies);

	    System.out.print("Which packet do you want to subscribe? (Standard/Premium): ");
	    String packet = sc.nextLine();

	    User newUser = null;

	    if(packet.equalsIgnoreCase("Standard")) {
	        newUser = new StandardUser(id, name, age, details, city);
	    }
	    else if(packet.equalsIgnoreCase("Premium")) {
	        newUser = new PremiumUser(id, name, age, details, city, false);
	    } else {
	        System.out.println("Invalid packet type!");
	        return null; 
	    }
	    
	    return newUser;
	}
}