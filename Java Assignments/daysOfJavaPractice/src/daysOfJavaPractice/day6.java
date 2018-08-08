package daysOfJavaPractice;

import java.util.Scanner;

//Given a string,s , of length n that is indexed from 0 to n-1, 
//print its even-indexed and odd-indexed characters as s space-separated strings on 
//a single line (see the Sample below for more detail).
public class day6 {
	public static void main(String[] args) {
		/*
		 * Enter your code here. Read input from STDIN. Print output to STDOUT.
		 * Your class should be named Solution.
		 */
		Scanner reader = new Scanner(System.in);
		int amountOfWords = reader.nextInt();
		if (amountOfWords > 0) {
			while (amountOfWords >= 0) {
				String word = reader.nextLine();
				String evenWord = "";
				String oddWord = "";
				int lengthOfWord = word.length();
				int start = 0;
				while (start < lengthOfWord) {
					if (start % 2 == 0) {
						evenWord = evenWord + word.charAt(start);
					} else {
						oddWord = oddWord + word.charAt(start);
					}
					start++;
				}
				evenWord = evenWord.trim();
				oddWord = oddWord.trim();
				if ((evenWord + "" + oddWord).isEmpty()) {

				} else {
					System.out.println(evenWord + " " + oddWord);
				}
				amountOfWords--;
			}

		}
	}
}
