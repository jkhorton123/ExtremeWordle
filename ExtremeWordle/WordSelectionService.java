import javax.swing.*;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.awt.*;
import java.awt.event.*;
public class WordSelectionService {
    String[] validWords;
    public void getValidWords(int difficultyVal) {
        /*
        Collects all valid 5-letter words based on the difficulty level selected by the user
        
        Parameters: 
            difficultyVal (Integer) : Value (1-5) corresponding to the selected difficulty 

        Returns:
            validWords (String[]) : List of words that the mystery word can be chosen from
        */
        // Choose a random word from the 2019 Collins Scrabble Words List
        String currentDir = System.getProperty("user.dir");
        String collinsPath = currentDir + "/Collins_Scrabble_Words_(2019).txt";
        File collinsFile = new File(collinsPath);

        // Initialize variables for storing valid words
        int count5 = 12972; // 12972 total 5-letter words in the scrabble words .txt file
        String allValidWords[] = new String[count5]; //All valid 5-letter words
        try {
            BufferedReader collinsBR = new BufferedReader(new FileReader(collinsFile));
            String str = "";
            int vArrCount = 0;
            try {
                while ((str = collinsBR.readLine()) != null) {
                    if(str.replaceAll("\\s+","").length() == 5) {
                        allValidWords[vArrCount] = str.replaceAll("\\s+","");
                        vArrCount += 1;
                    }                  
                }
            }
            catch(IOException i) {
                System.out.print(i);
            }
        }
        catch(FileNotFoundException e) {
            System.out.print(e);
        }
        validWords = allValidWords;
    }

    public String chooseWord(int difficultyVal) {
        /*
        Chooses the hidden word based on the difficulty level selected by the user
        
        Parameters: 
            difficultyVal (Integer) : Value (1-5) corresponding to the selected difficulty 

        Returns:
            wordleWord (String) : Hidden word that the user will try to guess
        */
        String currentDir = System.getProperty("user.dir");

        // List of most common English Words produced by Peter Norvig and obtained from norvig.com
        String commonWordsPath = currentDir + "/google-books-common-words.txt";
        File commonWordsFile = new File(commonWordsPath);

        // Initialize variables for parsing and storing words
        String wordleWord = "";
        int commonCount5 = 5522; // total 5-letter words in the google books common words .txt file that are also in the scrabble words .txt file
        int wordsPerDiffLevel = 1104; // total 5-letter words considered for each difficulty level
        getValidWords(difficultyVal); // Gets list of valid 5-letter scrabble words
        try {
            BufferedReader commonWordsBR = new BufferedReader(new FileReader(commonWordsFile));
            String str = "";
            int randNum = ThreadLocalRandom.current().nextInt(wordsPerDiffLevel*(difficultyVal-1), wordsPerDiffLevel*difficultyVal);
            int cWordCount = 0;
            
            try {
                while ((str = commonWordsBR.readLine()) != null) {
                    char let = str.charAt(4);
                    char space = str.charAt(5);
                    // Checks that the word is 5 letters
                    if ((let >= 'a' && let <= 'z') || (let >= 'A' && let <= 'Z') && Character.isWhitespace(space)) { 
                        String candidateWord = str.substring(0,5);
                        if (Arrays.asList(validWords).contains(candidateWord)) {
                            if (cWordCount == randNum) {
                                wordleWord = candidateWord.toLowerCase();
                                break;
                            }
                            cWordCount += 1;
                        }
                    
                    }

                }
                System.out.println(randNum);
                commonWordsBR.close();
            }
            catch(IOException i) {
                System.out.print(i);
            }
        }
        catch(FileNotFoundException e) {
            System.out.print(e);
        } 
        return wordleWord;

    }
    public Color[] getLetterColors(String guess, String targetWord) {
        /*
        Returns an array containing the order of the colors corresponding to the validity and positioning of the letters in the guessed word relative to the target word
        White indicates the letter is not contained in the target word
        Yellow indicates the letter is found in the target word but is not in the correct position
        Green indicates the letter is found in the target word and is in the correct position
        
        Parameters: 
            guess (String) : User-inputted guess
            targetWord (String) : Random word that the user is trying to guess

        Returns:
            letterColors (Color[]) : Array containing the color indicators for the letters in the guessed word
        */

            Color[] letterColors = new Color[5];
            for (int i = 0; i<5; i++) {
                String guessLet = Character.toString(guess.charAt(i));
                String targetWordLet = Character.toString(targetWord.charAt(i));
                if (guessLet.equals(targetWordLet)) {
                    letterColors[i] = Color.GREEN;
                }
                
                else if (targetWord.contains(guessLet)) {
                    letterColors[i] = Color.YELLOW;
                }
                else {
                    letterColors[i] = Color.WHITE;
                }
            }
            return letterColors;

    }

    public boolean validateWord(String guess) {
        /*
        Verifies that the guess is a valid word (using the Scrabble dictionary)
        */
        return Arrays.asList(validWords).contains(guess.toUpperCase());
    }
}