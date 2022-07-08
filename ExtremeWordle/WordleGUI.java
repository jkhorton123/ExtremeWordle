import javax.swing.*;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
import java.util.Arrays;

class gui {
    public static void main(String args[]){
       JFrame frame = new JFrame("My First GUI");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(300,300);
       JButton button = new JButton("Press");
       frame.getContentPane().add(button); // Adds Button to content pane of frame
       frame.setVisible(true);

       // Choosese a random word from the 2019 Collins Scrabble Words List
       String currentDir = System.getProperty("user.dir");
       String collinsPath = currentDir + "/Collins_Scrabble_Words_(2019).txt";
       File collinsFile = new File(collinsPath);
       // List of most common English Words produced by Peter Norvig and obtained from norvig.com
       String commonWordsPath = currentDir + "/google-books-common-words.txt";
       File commonWordsFile = new File(commonWordsPath);
       String wordleWord = "";
       int count5 = 12972; // 12972 total 5-letter words in the scrabble words .txt file
       int commonCount5 = 5522; // total 5-letter words in the google books common words .txt file that are also in the scrabble words .txt file
       String validWords[] = new String[count5]; //All valid 5-letter words
       //Read the .txt file of words
       try {
            BufferedReader collinsBR = new BufferedReader(new FileReader(collinsFile));
            BufferedReader commonWordsBR = new BufferedReader(new FileReader(commonWordsFile));
            String str = "";
            int randNum = ThreadLocalRandom.current().nextInt(0, commonCount5-1);
            int vArrCount = 0;
            int cWordCount = 0;
            
            try {
                
                while ((str = collinsBR.readLine()) != null) {
                    if(str.replaceAll("\\s+","").length() == 5) {
                        validWords[vArrCount] = str.replaceAll("\\s+","");
                        vArrCount += 1;
                    }                  
                }
                while ((str = commonWordsBR.readLine()) != null) {
                    char let = str.charAt(4);
                    char space = str.charAt(5);
                    // Checks that the word is 5 letters
                    if ((let >= 'a' && let <= 'z') || (let >= 'A' && let <= 'Z') && Character.isWhitespace(space)) { 
                        String candidateWord = str.substring(0,5);
                        if (Arrays.asList(validWords).contains(candidateWord)) {
                            if (cWordCount == randNum) {
                                wordleWord = candidateWord.toLowerCase();
                            }
                            cWordCount += 1;
                        }
                    
                    }

                }
                commonWordsBR.close();
                collinsBR.close();
            }
            catch(IOException i) {
                System.out.print(i);
            }
       }
       catch(FileNotFoundException e) {
            System.out.print(e);
       }
       
       enteredWord word = new enteredWord();
       word.outputWord(wordleWord, validWords);
       System.exit(0);

    }
}

class enteredWord {
    public String guess;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public Scanner wordScan = new Scanner(System.in);

    public void outputWord(String word, String validWords[]) {
        /*
        Prints the guess inputted by the user but highlights letters that match with the word with 
        green and highlights letters that are in the wrong position with yellow
        */
        String retList[] = new String[5];
        String guesses[] = new String[6];
        int guessCount = 1;
        while (guessCount <= 6) {
            this.getWord(validWords, guesses, guessCount);
            int matches = 0;
            for (int i = 0; i<5; i++) {
                String guessLet = Character.toString(guess.charAt(i)).toLowerCase();
                String wordLet = Character.toString(word.charAt(i)).toLowerCase();
                if (guessLet.equals(wordLet)) {
                    retList[i] = ANSI_GREEN_BACKGROUND+guessLet+ANSI_RESET;
                    matches += 1;
                }
                
                else if (word.contains(guessLet)) {
                    retList[i] = ANSI_YELLOW_BACKGROUND+guessLet+ANSI_RESET;
                }
                else {
                    retList[i] = guessLet;
                }
            }
        
            for (int i = 0; i<5; i++) {
                System.out.print(retList[i]);
            }
            System.out.println("");
            guessCount+=1;
            if (matches == 5){
                System.out.println("You guessed the word correctly!");
                guessCount = 8;
            }
        }
        if (guessCount == 7) {
            System.out.println("Game Over, the correct word was " + word.toLowerCase());
        }
        wordScan.close();
    }

    public void getWord(String validWords[], String guesses[], int guessCount) {
        /*
        Obtains the guess from the user and verifies that it is a valid 5-letter word
        */
        boolean validWord = false;
        while (!validWord) {
            validWord = true;
            System.out.print("Guesses remaining: ");
            System.out.print(7-guessCount);
            System.out.println("\nEnter your 5-letter word guess: ");
            guess = wordScan.nextLine().replaceAll("\\s+","");
            if (!(guess.matches("[a-zA-Z]+"))) {
                validWord = false;
                System.out.println("The entered word contains non-alphabetical characters");
            }
            else if (guess.length() != 5) {
                validWord = false;
                System.out.println("The entered word is not 5 letters");
            }
            else if (Arrays.asList(guesses).contains(guess.toLowerCase())) {
                validWord = false;
                System.out.println("The entered word has already been guessed");
            }
            else if (!Arrays.asList(validWords).contains(guess.toUpperCase())) {
                validWord = false;
                System.out.println("The entered word is not valid");
            }

        }
        guesses[guessCount-1] = guess.toLowerCase();
    }
}




