package model;

import java.util.Scanner;

public class Activity {
    private String type;       
    private double price;      
    private int duration;      
    private final int appDiscount = 10;   //Kupon kullanilirsa %10'luk indirim uygulanır

    public Activity(String type, double price, int duration) {
        this.type = type;
        this.price = price;
        this.duration = duration;
    }

    public Activity() {
	 
	}


	public void getInput() {
    	Scanner inp = new Scanner(System.in);
        System.out.println("Enter activity details: ");
        System.out.println("Enter type: ");
        this.type = inp.nextLine();
        System.out.println("Enter price: ");
        this.price = inp.nextDouble();
        System.out.println("Enter duration: ");
        this.duration = inp.nextInt();
    }

    public void applyDiscount(boolean hasCoupon) {
        if (hasCoupon) {
            double discountAmount = (price * appDiscount) / 100.0;
            price -= discountAmount;
            System.out.println("Coupon applied for " + type + "! New Price: " + price + " TL");
        } else {
            System.out.println("No coupon. Price for " + type + " remains: " + price + " TL");
        }
    }

    @Override
    public String toString() {
        return type + " (" + duration + " mins) - " + price + " TL";
    }

	
}