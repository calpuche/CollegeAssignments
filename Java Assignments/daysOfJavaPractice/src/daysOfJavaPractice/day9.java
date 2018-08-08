package daysOfJavaPractice;

import java.util.Scanner;
//Write a factorial function that takes a positive integer,
//as a parameter and prints the factorial
public class day9 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner in = new Scanner(System.in);
        int factorial = in.nextInt();
        System.out.println(factorial(factorial));
        
        
    }
    public static int factorial(int n){
        if(n!=0){
            n = factorial(n-1) *n;
        }
        else if(n==0){
        	return 1;
        }
        return n;
    }
	

}
