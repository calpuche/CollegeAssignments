import java.io.*;
import java.util.Scanner;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class WPEncoder {
	// initiate new map instance
	static Map<String, Integer> keyWords = new HashMap();
	private static int totalWordCount = 0;

	public static void main(String[] args) {

		int whichValue = 1;
		// grab location of file
		Scanner text = new Scanner(System.in);
		// Asks for name of file
		System.out.println("What is the name of the file?");
		// gets location for file
		String fileLocation = args[0];
		// goes through and gets keyWords filled
		fillMap(fileLocation);
		// prints out total word count
		System.out.println(totalWordCount + " Total words");
		// prints out unique word count
		System.out.println(keyWords.size() + " Unique Words");
		// sorts keyWords from smallest to largest
		keyWords = sortByValue(keyWords);
		System.out.println("Top 5 words: ");
		// turns keys into string list
		List<String> wordsList = new ArrayList<String>(keyWords.keySet());
		// turns values into an int list
		List<Integer> numbersList = new ArrayList<Integer>(keyWords.values());
		for (int i = wordsList.size() - 1; i > wordsList.size() - 6; i--) {

			System.out.println(whichValue + ") " + wordsList.get(i) + ": " + numbersList.get(i));
			whichValue++;
		}

	}

	// start grabing words from file
	public static void fillMap(String fileName) {
		Scanner in;
		try {
			in = new Scanner(new File(fileName));
			while (in.hasNext()) {
				// gets rid of trash from words
				String word = in.next();
				word = word.replaceAll("[- . , \n]+", "");
				word = word.toLowerCase();
				word = word.trim();

				// if word is empty, skips
				if (word.isEmpty()) {

				}
				// if word already in, ups the count
				else if (keyWords.containsKey(word)) {
					totalWordCount++;

					int orignalKeyValue = keyWords.get(word);
					orignalKeyValue++;
					keyWords.replace(word, orignalKeyValue++);
				}
				// if no word, adds to list
				else {

					totalWordCount++;
					keyWords.put(word, 1);

				}

			}
		}
		// catch for if file does not exist
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("File not Found!");
		}

	}

	// sorting algorithm for sorting the map
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		Map<K, V> result = new LinkedHashMap<>();
		Stream<Entry<K, V>> st = map.entrySet().stream();

		st.sorted(Comparator.comparing(e -> e.getValue())).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));

		return result;
	}

}