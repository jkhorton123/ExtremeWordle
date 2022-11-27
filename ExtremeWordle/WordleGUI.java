import javax.swing.*;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.awt.*;
import java.awt.event.*;
class gui {
    public static void main(String args[]){
        //JFrame Setup
        JFrame frame = new JFrame("Extreme Wordle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int frameWidth = 500;
        int frameHeight = 300;

        //Adding Welcome and Instruction Labels
        Label welcomeL = new Label("Welcome to Extreme Wordle!");
        Label instructionL = new Label("Please select the difficulty level from the options below:");
        int welcomeLWidth = 200;
        int instructionLWidth = 400;
        welcomeL.setBounds((frameWidth-welcomeLWidth)/2, 10, welcomeLWidth, 50);
        instructionL.setBounds((frameWidth-instructionLWidth)/2, 40, instructionLWidth, 50);
        frame.add(welcomeL);
        frame.add(instructionL);

        //Adding Difficulty Buttons
        int buttonWidth = 150;
        int buttonHeight = 30;
        int buttonSpacing = 30;
        int diffVertStart = 70;

        JButton easyB = new JButton("Easy");
        JButton mediumB = new JButton("Medium");
        JButton hardB = new JButton("Hard");
        JButton vHardB = new JButton("Very Hard");
        JButton extremeB = new JButton("Extreme");
        

        easyB.setBounds((frameWidth-buttonWidth)/2, diffVertStart + buttonSpacing, buttonWidth, buttonHeight);
        mediumB.setBounds((frameWidth-buttonWidth)/2, diffVertStart + buttonSpacing*2, buttonWidth, buttonHeight);
        hardB.setBounds((frameWidth-buttonWidth)/2, diffVertStart + buttonSpacing*3, buttonWidth, buttonHeight);
        vHardB.setBounds((frameWidth-buttonWidth)/2, diffVertStart + buttonSpacing*4, buttonWidth, buttonHeight);
        extremeB.setBounds((frameWidth-buttonWidth)/2, diffVertStart + buttonSpacing*5, buttonWidth, buttonHeight);

        final SystemWordChoice systemWordChoice = new SystemWordChoice();
        HashMap<String, Integer> difficultyValues = new HashMap<String, Integer>(5);
        difficultyValues.put("Easy", 1);
        difficultyValues.put("Medium", 2);
        difficultyValues.put("Hard", 3);
        difficultyValues.put("Very Hard", 4);
        difficultyValues.put("Extreme", 5);

        easyB.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                systemWordChoice.chooseWord(difficultyValues.get("Easy"));
            }
        });
        mediumB.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                systemWordChoice.chooseWord(difficultyValues.get("Medium"));
            }
        });
        hardB.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                systemWordChoice.chooseWord(difficultyValues.get("Hard"));
            }
        });
        vHardB.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                systemWordChoice.chooseWord(difficultyValues.get("Very Hard"));
            }
        });
        extremeB.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                systemWordChoice.chooseWord(difficultyValues.get("Extreme"));
            }
        });
       frame.add(easyB); 
       frame.add(mediumB); 
       frame.add(hardB); 
       frame.add(vHardB); 
       frame.add(extremeB); 

       frame.setSize(frameWidth, frameHeight);
       frame.setLayout(null);
       frame.setVisible(true);

    }
}

class SystemWordChoice {
    // Choose a random word from the 2019 Collins Scrabble Words List
    String currentDir = System.getProperty("user.dir");
    String collinsPath = currentDir + "/Collins_Scrabble_Words_(2019).txt";
    File collinsFile = new File(collinsPath);
    // List of most common English Words produced by Peter Norvig and obtained from norvig.com
    String commonWordsPath = currentDir + "/google-books-common-words.txt";
    File commonWordsFile = new File(commonWordsPath);

    // Initialize variables for parsing and storing words
    String wordleWord = "";
    int count5 = 12972; // 12972 total 5-letter words in the scrabble words .txt file
    int commonCount5 = 5522; // total 5-letter words in the google books common words .txt file that are also in the scrabble words .txt file
    int wordsPerDiffLevel = 1104; // total 5-letter words considered for each difficulty level
    String validWords[] = new String[count5]; //All valid 5-letter words
    public void chooseWord(int difficultyVal) {
        /*
        Chooses the hidden word based on the difficulty level selected by the user
        
        Parameters: 
            difficultyVal (Integer) : Value (1-5) corresponding to the selected difficulty 

        Returns:
            wordleWord (String) : Hidden word that the user will try to guess
        */

        try {
            BufferedReader collinsBR = new BufferedReader(new FileReader(collinsFile));
            BufferedReader commonWordsBR = new BufferedReader(new FileReader(commonWordsFile));
            String str = "";
            int randNum = ThreadLocalRandom.current().nextInt(wordsPerDiffLevel*(difficultyVal-1), wordsPerDiffLevel*difficultyVal);
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
                                break;
                            }
                            cWordCount += 1;
                        }
                    
                    }

                }
                System.out.println(randNum + wordleWord);
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
        //Look up how to create a new gui screen for selecting the word using JFrame
        WordSelectionScreen wordSelectionScreen = new WordSelectionScreen();
        wordSelectionScreen.outputWord(wordleWord, validWords);
        System.exit(0);

    }
}


class WordSelectionScreen {
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




