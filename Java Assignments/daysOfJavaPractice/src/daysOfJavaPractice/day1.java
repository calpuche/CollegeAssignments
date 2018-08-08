package daysOfJavaPractice;

import java.util.Scanner;
//Complete the code in the editor below. The variables , , and  are already declared and initialized for you. You must:

//Declare  variables: one of type int, one of type double, and one of type String.
//Read  lines of input from stdin (according to the sequence given in the Input Format section below) and initialize your  variables.
//Use the  operator to perform the following operations: 
//Print the sum of  plus your int variable on a new line.
//Print the sum of  plus your double variable to a scale of one decimal place on a new line.
//Concatenate  with the string you read as input and print the result on a new line.

public class day1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i = 4;
		double d = 4.0;
		String s = "HackerRank ";
		Scanner scan = new Scanner(System.in);
		/* Declare second integer, double, and String variables. */
		int secondInt;
		double secondDouble;
		String finishedSentence = "";
		secondInt = scan.nextInt();
		secondDouble = scan.nextDouble();
		scan.nextLine();
		finishedSentence = scan.nextLine();
		/* Print the sum of both integer variables on a new line. */
		System.out.println(secondInt + i);
		/* Print the sum of the double variables on a new line. */
		System.out.println(d + secondDouble);
		/*
		 * Concatenate and print the String variables on a new line; the 's'
		 * variable above should be printed first.
		 */
		System.out.println(s + finishedSentence);
	}

}
