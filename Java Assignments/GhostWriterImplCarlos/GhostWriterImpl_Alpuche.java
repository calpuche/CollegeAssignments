package writing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GhostWriterImpl_Alpuche implements GhostWriter {
	private String text;

	public GhostWriterImpl_Alpuche(String inputText) {
		this.text = inputText;
	}

	public String getInputText() {
		return this.text;
	}

	public String generate(String seed, List<Integer> selectionList) {
		// assertions
		assert seed != null : "seed is null!";
		assert seed.length() <= 10 : "seed.length() = " + seed.length() + " > 10!";
		assert selectionList != null : "selectionList is null!";
		assert getInputText().indexOf(seed) != -1 : "getInputText().indexOf(" + seed + ") is -1!";

		HashMap<String, List<Character>> stringToSuffixListMap = new HashMap<String, List<Character>>();

		String ghostText = seed;

		int lengthOfSeed = seed.length();
		StringBuffer tempString = new StringBuffer(text);
		
		for (int i = lengthOfSeed - 1; i < tempString.length() - 1; i++) {
			
			int start = i - (lengthOfSeed - 1);
			String processSentence = tempString.substring(start, i + 1);
			char value = tempString.charAt(i + 1);
			if (stringToSuffixListMap.get(processSentence) != null) {
				
				List<Character> currentSuffixList = stringToSuffixListMap.get(processSentence);
				currentSuffixList.add(value);
				Collections.sort(currentSuffixList);
				stringToSuffixListMap.put(processSentence, currentSuffixList);
				
			} else {
				
				List<Character> newCurrentSuffixList = new ArrayList<Character>();
				newCurrentSuffixList.add(value);
				stringToSuffixListMap.put(processSentence, newCurrentSuffixList);
			}
		}

		for (int i = seed.length() - 1; i < selectionList.size() - 1; i++) {
			String next = ghostText.substring(ghostText.length() - seed.length(), ghostText.length());
			int modder = stringToSuffixListMap.get(next).size();
			int index = selectionList.get(i + 1);
			int modResult = index % modder;
			List<Character> result = stringToSuffixListMap.get(next);
			char sentence = result.get(modResult);
			ghostText = ghostText + sentence;
		}

		return ghostText;
	}

	@Override
	public String getFirstNameOfSubmitter() {
		return "Carlos";
	}

	@Override
	public String getLastNameOfSubmitter() {
		return "Alpuche";
	}

	@Override
	public double getHoursSpentWorkingOnThisAssignment() {
		return 4;
	}

	@Override
	public int getScoreAgainstTestCasesSubset() {
		return 50/60;
	}
}