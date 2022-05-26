import javax.swing.*;
import java.util.Scanner;
import java.util.*;

class gui {
    public static void main(String args[]){
       JFrame frame = new JFrame("My First GUI");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(300,300);
       JButton button = new JButton("Press");
       frame.getContentPane().add(button); // Adds Button to content pane of frame
       frame.setVisible(true);

       String wordleWord = "hello".toLowerCase();
       enteredWord word = new enteredWord();
       word.outputWord(wordleWord);
       System.exit(0);

    }
}

class enteredWord {
    public String guess;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public Scanner wordScan = new Scanner(System.in);

    public void outputWord(String word) {
        /*
        Prints the guess inputted by the user but highlights letters that match with the word with 
        green and highlights letters that are in the wrong position with yellow
        */
        String retList[] = new String[5];
        int guessCount = 1;
        while (guessCount <= 6) {
            this.getWord();
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
            System.out.println("Game Over, the correct word was " + word);
        }
        wordScan.close();
    }

    public void getWord() {
        boolean validWord = false;
        while (!validWord) {
            validWord = true;
            System.out.println("Enter your guess: ");
            guess = wordScan.nextLine();
            if (!(guess.matches("[a-zA-Z]+"))) {
                validWord = false;
                System.out.println("Please enter a valid word");
            }
            else if (guess.length() != 5) {
                validWord = false;
                System.out.println("Entered word is not 5 letters");
            }

        }
    }
}




