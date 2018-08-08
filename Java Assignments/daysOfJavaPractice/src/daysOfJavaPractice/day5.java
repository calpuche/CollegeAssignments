package daysOfJavaPractice;

import java.util.Scanner;

//Given an integer, , print its first  multiples. Each multiple  (where ) should be printed on a new line in the form: n x i = result.
public class day5 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		in.close();
		int i = 1;
		while (i < 11) {
			System.out.println(n + " x " + i + " = " + (n * i));
			i++;
		}
	}
}
