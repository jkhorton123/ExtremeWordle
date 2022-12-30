import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
import java.util.Arrays;
import java.awt.*;
public class WordSelectionService {
    String[] validWords = new String[12972]; // Stores all 12,972 valid 5-letter words in the Scrabble text file
    public void getValidWords() {
        /*
        Collects all valid 5-letter words from the 2019 Scrabble words list
        */

        // Loads in a file containing all valid scrabble words
        String currentDir = System.getProperty("user.dir");
        String collinsPath = currentDir + "/Collins_Scrabble_Words_(2019).txt";
        File collinsFile = new File(collinsPath);

        try {
            BufferedReader collinsBR = new BufferedReader(new FileReader(collinsFile));
            String str = "";
            int vArrCount = 0;
            try {
                while ((str = collinsBR.readLine()) != null) {
                    if(str.replaceAll("\\s+","").length() == 5) {
                        validWords[vArrCount] = str.replaceAll("\\s+",""); // Collects all valid 5-letter words from the Scrabble words file
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
    }

    public String chooseWord(int difficultyVal) {
        /*
        Chooses the hidden word based on the difficulty level selected by the user. The word is selected from a .txt file containing 
        English words sorted based on the number of times they occur in a large database of books. Each of the 5 difficulty levels chooses from 
        20% of the books, with the most common 20% selected for easy mode and the rarest 20% selected for extreme mode.
        
        Parameters: 
            difficultyVal (Integer) : Value (1-5) corresponding to the selected difficulty

        Returns:
            wordleWord (String) : Hidden word that the user will try to guess
        */
        String currentDir = System.getProperty("user.dir");

        // Loads in common words .txt file containing English Words sorted by frequency which was obtained from norvig.com
        String commonWordsPath = currentDir + "/google-books-common-words.txt";
        File commonWordsFile = new File(commonWordsPath);

        // Initialize variables for parsing and storing words
        String wordleWord = "";
        int commonCount5 = 5522; // total 5-letter words in the google books common words .txt file that are also in the Scrabble words .txt file
        int wordsPerDiffLevel = 1104; // total 5-letter words considered for each difficulty level
        if (validWords[0]==null) { // Only populates list of 5-letter Scrabble words if it has not already been populated
            getValidWords(); // Gets list of valid 5-letter Scrabble words
        }
        try {
            BufferedReader commonWordsBR = new BufferedReader(new FileReader(commonWordsFile));
            String str = "";
            int wordPos = ThreadLocalRandom.current().nextInt(wordsPerDiffLevel*(difficultyVal-1), wordsPerDiffLevel*difficultyVal); // Finds the position of the target word among all 5-letter words sorted based on frequency of use
            int cWordCount = 0;
            
            try {
                while ((str = commonWordsBR.readLine()) != null) {
                    char let = str.charAt(4);
                    char space = str.charAt(5);
                    // Checks that the word is 5 letters
                    if ((let >= 'a' && let <= 'z') || (let >= 'A' && let <= 'Z') && Character.isWhitespace(space)) { 
                        String candidateWord = str.substring(0,5);
                        if (Arrays.asList(validWords).contains(candidateWord)) { // Verifies the word is valid based off the 2019 Scrabble word list
                            if (cWordCount == wordPos) { // Checks if the word is at the correct position in the list of 5-letter words sorted based on frequency of use
                                wordleWord = candidateWord.toLowerCase(); 
                                break;
                            }
                            cWordCount += 1;
                        }
                    
                    }

                }
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
        Returns an array containing color indicators corresponding to the validity and positioning of the letters in the guessed word relative to the target word.
        White indicates the letter is not contained in the target word.
        Yellow indicates the letter is found in the target word but is not in the correct position.
        Green indicates the letter is found in the target word and is in the correct position.
        
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
        Verifies that the guess is a valid word using the 2019 Scrabble word list
        */
        return Arrays.asList(validWords).contains(guess.toUpperCase());
    }
}