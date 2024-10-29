import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.awt.*;

public class WordSelectionService {
    String[] validWords = new String[12653]; // Stores all 12,653 valid 5-letter words in the en.txt file

    public void getValidWords() {
        /*
         * Collects all valid 5-letter words from the en.txt file
         */

        // Loads in a file containing all valid English words
        String currentDir = System.getProperty("user.dir");
        String enWordsPath = currentDir + "/en.txt";
        File enWordsFile = new File(enWordsPath);

        try {
            BufferedReader enWordsBR = new BufferedReader(new FileReader(enWordsFile));
            String str = "";
            int vArrCount = 0;
            try {
                while ((str = enWordsBR.readLine()) != null) {
                    if (str.replaceAll("\\s+", "").length() == 5) {
                        validWords[vArrCount] = str.replaceAll("\\s+", ""); // Collects all valid
                        // 5-letter words from
                        // the en.txt file
                        vArrCount += 1;
                    }
                }
            } catch (IOException i) {
                System.out.print(i);
            }
        } catch (FileNotFoundException e) {
            System.out.print(e);
        }
    }

    public String chooseWord(int difficultyVal) {
        /*
         * Chooses the hidden word based on the difficulty level selected by the user.
         * The word is selected from a .txt file containing
         * English words sorted in descending order based on the number of times they
         * occur in a large English corpus (see README).
         * Each of the 5 difficulty levels chooses from a subset of the words.
         * 
         * Easy Mode: First 10% of the words in the list (the most common words in the
         * list because the list is sorted by word commonness in descending order)
         * Medium Mode: Next 10% of words
         * Hard Mode: Next 20% of words
         * Very Hard Mode: Next 30% of words
         * Extreme Mode: Last 30% of words (the least common words in the list)
         * 
         * Parameters:
         * difficultyVal (Integer) : Value (1-5) corresponding to the selected
         * difficulty
         * 
         * Returns:
         * wordleWord (String) : Hidden word that the user will try to guess
         */
        String currentDir = System.getProperty("user.dir");

        // Loads in common words .txt file containing English Words sorted by frequency
        // which was obtained from norvig.com
        String commonWordsPath = currentDir + "/google-books-common-words.txt";
        File commonWordsFile = new File(commonWordsPath);

        // Initialize variables for parsing and storing words
        String wordleWord = "";
        // There are 5483 total 5-letter words in the google books common words .txt
        // file that are also in the en.txt file
        int wordsPerDiv = 548; // ~10% of the the total 5-letter words being considered
        HashMap<Integer, Integer> nextDifficultyMultiples = new HashMap<Integer, Integer>(5); // Maps the difficulty
                                                                                              // values to a multiple
                                                                                              // that bounds the range
                                                                                              // of words considered
        nextDifficultyMultiples.put(0, 1); // First 10% of words for easy mode
        nextDifficultyMultiples.put(1, 2); // Next 10% of words for medium mode
        nextDifficultyMultiples.put(2, 4); // Next 20% of words for hard mode
        nextDifficultyMultiples.put(4, 7); // Next 30% of words for very hard mod
        nextDifficultyMultiples.put(7, 10); // Next 30% of words for extreme mode
        if (validWords[0] == null) { // Only populates list of valid words if it has not already been
                                     // populated
            getValidWords(); // Gets list of valid 5-letter English words
        }
        try {
            BufferedReader commonWordsBR = new BufferedReader(new FileReader(commonWordsFile));
            String str = "";
            int wordPos = ThreadLocalRandom.current().nextInt(wordsPerDiv * difficultyVal,
                    wordsPerDiv * nextDifficultyMultiples.get(difficultyVal)); // Finds the position of the target word
                                                                               // among all 5-letter words sorted based
                                                                               // on frequency of use
            int cWordCount = 0;
            try {
                while ((str = commonWordsBR.readLine()) != null) {
                    char let = str.charAt(4);
                    char space = str.charAt(5);
                    // Checks that the word is 5 letters
                    if ((let >= 'a' && let <= 'z') || (let >= 'A' && let <= 'Z') && Character.isWhitespace(space)) {
                        String candidateWord = str.substring(0, 5);
                        if (Arrays.asList(validWords).contains(candidateWord.toLowerCase())) { // Verifies the word is
                                                                                               // valid based off
                            // the English word list
                            if (cWordCount == wordPos) { // Checks if the word is at the correct position in the list of
                                                         // 5-letter words sorted based on frequency of use
                                wordleWord = candidateWord;
                                break;
                            }
                            cWordCount += 1;
                        }

                    }

                }
                commonWordsBR.close();
            } catch (IOException i) {
                System.out.print(i);
            }
        } catch (FileNotFoundException e) {
            System.out.print(e);
        }
        return wordleWord;

    }

    public Color[] getLetterColors(String guess, String targetWord) {
        /*
         * Returns an array containing color indicators corresponding to the validity
         * and positioning of the letters in the guessed word relative to the target
         * word.
         * White indicates the letter is not contained in the target word.
         * Yellow indicates the letter is found in the target word but is not in the
         * correct position.
         * Green indicates the letter is found in the target word and is in the correct
         * position.
         * 
         * Parameters:
         * guess (String) : User-inputted guess
         * targetWord (String) : Random word that the user is trying to guess
         * 
         * Returns:
         * letterColors (Color[]) : Array containing the color indicators for the
         * letters in the guessed word
         */

        Color[] letterColors = new Color[5];
        for (int i = 0; i < 5; i++) {
            String guessLet = Character.toString(guess.charAt(i));
            String targetWordLet = Character.toString(targetWord.charAt(i));
            if (guessLet.equals(targetWordLet)) {
                letterColors[i] = Color.GREEN;
            }

            else if (targetWord.contains(guessLet)) {
                letterColors[i] = Color.YELLOW;
            } else {
                letterColors[i] = Color.WHITE;
            }
        }
        return letterColors;

    }

    public boolean validateWord(String guess) {
        /*
         * Verifies that the guess is a valid word using the English word list
         */
        return Arrays.asList(validWords).contains(guess);
    }
}