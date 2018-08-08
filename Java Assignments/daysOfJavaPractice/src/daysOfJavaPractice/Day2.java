package daysOfJavaPractice;

//Given the meal price (base cost of a meal), tip percent (the percentage of the meal price being added as tip), and tax percent (the percentage of the meal price being added as tax) for a meal, find and print the meal's total cost.
public class Day2 {
	public static void main(String[] args) {
		double mealCost = 12.0; // original meal price
		int tipPercent = 20; // tip percentage
		int taxPercent = 8; // tax percentage
		int percent = 100;
		// Write your calculation code here.
		System.out.println("tip Percent is " + tipPercent);
		System.out.println("meal cost is " + mealCost);
		double tip = mealCost * (tipPercent / percent);
		System.out.println("tip is " + tip);
		double total = tip;
		// cast the result of the rounding operation to an int and save it as
		// totalCost
		int totalCost = (int) Math.round(total);

		// Print your result
	}
}
