/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;


public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 5;
    private Random random = new Random();
    HashSet<String> wordSet = new HashSet<>();
    HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>();
    HashMap<Integer, ArrayList<String>> sizeToWords = new HashMap<>();
    ArrayList<String> wordList = new ArrayList<>();

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while ((line = in.readLine()) != null) {
            String word = line.trim().toLowerCase();
            wordList.add(word);
            wordSet.add(word);
            //Log.d("Carlos","In the AnagramDictionary");
            String sortedWord = sortLetters(word);
            int length = sortedWord.length();
            if (sizeToWords.containsKey(length)) {
                ArrayList<String> wordSizeList = sizeToWords.get(length);
                wordSizeList.add(word);
            } else {
                ArrayList<String> newWordSizeList = new ArrayList<>();
                newWordSizeList.add(word);
                sizeToWords.put(length, newWordSizeList);
            }
            ArrayList<String> sortedWordList;
            if (!lettersToWord.containsKey(sortedWord)) {
                sortedWordList = new ArrayList<>();
                sortedWordList.add(word);
                lettersToWord.put(sortedWord, sortedWordList);
                //  Log.d("Carlos","New List Created");

            } else {
                sortedWordList = lettersToWord.get(sortedWord);
                sortedWordList.add(word);
                lettersToWord.put(sortedWord, sortedWordList);
            }
        }


    }

    public boolean isGoodWord(String word, String base) {
        return wordSet.contains(word) && !(word.contains(base));
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> resultList = new ArrayList<>();
        String sortedWord = sortLetters(targetWord);
        //Log.v("Dictionary", "sorted word is " + sortedWord);
        if (lettersToWord.containsKey(sortedWord)) {
            //Log.v("Dictionary", "containskey");
            return lettersToWord.get(sortedWord);
        } else {
            //Log.v("Dictionary", "size of letters to word is " + lettersToWord.size());
            return resultList;
        }

    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 97; i < 123; i++) {
            String newword = word + (char) i;
            //Log.v("getAnagrams", "word is " + newword);
            result.addAll(getAnagrams(newword));
        }


        return result;
    }

    public String pickGoodStarterWord() {
        String starterWord = wordList.get(random.nextInt(wordList.size()-1));
        Log.v("Starter", "Starter Word is " + starterWord);
        while((lettersToWord.get(sortLetters(starterWord)).size() < MIN_NUM_ANAGRAMS) && starterWord.length() > MAX_WORD_LENGTH ){
            starterWord = wordList.get(random.nextInt(wordList.size()-1));
            Log.v("Starter", "Starter Word is " + starterWord);
        }
        return starterWord;
    }

    public String sortLetters(String base) {
        char[] charArray = base.toCharArray();
        Arrays.sort(charArray);
        //Log.d("Carlos", "In the sortLetters");
        String sortedString = new String(charArray);
        //Log.v("sortedString", "sortedString:" + sortedString + " base is " + base);
        return sortedString;
    }
}



