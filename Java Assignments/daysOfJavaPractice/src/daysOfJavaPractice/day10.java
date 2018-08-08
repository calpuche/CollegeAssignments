package daysOfJavaPractice;

import java.util.Scanner;

//Given a integer convert it to binary (base2). 
//Then find and print the base- integer denoting the 
//maximum number of consecutive 1s in the binary
public class day10 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		String binary = "";
		binary = Integer.toBinaryString(n);
		// System.out.println(binary);
		int isAOne = 0;
		int max = 0;
		for (int i = 0; i < binary.length(); i++) {
			if (binary.charAt(i) == '1') {
				isAOne++;
			} else if (binary.charAt(i) == '0') {
				isAOne = 0;
			}
			if (isAOne > max) {
				max = isAOne;
			}
		}
		System.out.println(max);
	}

}
