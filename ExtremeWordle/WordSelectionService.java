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
                // System.out.println(randNum + wordleWord);
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
    public void outputWord(String word, String validWords[]) {
        /*
        Prints the guess inputted by the user but highlights letters that match with the word with 
        green and highlights letters that are in the wrong position with yellow
        */
        
    //     public String guess;
    //     public static final String ANSI_RESET = "\u001B[0m";
    //     public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    //     public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    //     public Scanner wordScan = new Scanner(System.in);
    //     String retList[] = new String[5];
    //     String guesses[] = new String[6];
    //     int guessCount = 1;
    //     while (guessCount <= 6) {
    //         this.getWord(validWords, guesses, guessCount);
    //         int matches = 0;
    //         for (int i = 0; i<5; i++) {
    //             String guessLet = Character.toString(guess.charAt(i)).toLowerCase();
    //             String wordLet = Character.toString(word.charAt(i)).toLowerCase();
    //             if (guessLet.equals(wordLet)) {
    //                 retList[i] = ANSI_GREEN_BACKGROUND+guessLet+ANSI_RESET;
    //                 matches += 1;
    //             }
                
    //             else if (word.contains(guessLet)) {
    //                 retList[i] = ANSI_YELLOW_BACKGROUND+guessLet+ANSI_RESET;
    //             }
    //             else {
    //                 retList[i] = guessLet;
    //             }
    //         }
        
    //         for (int i = 0; i<5; i++) {
    //             System.out.print(retList[i]);
    //         }
    //         System.out.println("");
    //         guessCount+=1;
    //         if (matches == 5){
    //             System.out.println("You guessed the word correctly!");
    //             guessCount = 8;
    //         }
    //     }
    //     if (guessCount == 7) {
    //         System.out.println("Game Over, the correct word was " + word.toLowerCase());
    //     }
    //     wordScan.close();
    }

    public boolean validateWord(String guess) {
        /*
        Verifies that the guess is a valid word (using the Scrabble dictionary)
        */
        return Arrays.asList(validWords).contains(guess.toUpperCase());
    }
}