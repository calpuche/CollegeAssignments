package daysOfJavaPractice;

import java.util.Scanner;

//Given an array, A, of N integers, print A's elements in reverse order as a single line of space-separated numbers.
public class day7 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = in.nextInt();
		}
		in.close();
		for (int i = n - 1; i >= 0; i--) {
			System.out.print(arr[i] + " ");
		}
	}
}
