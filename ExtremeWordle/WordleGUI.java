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

       String wordleWord = "hello";
       

    }
}

class enteredWord {
    public String guess;
    
    public void outputWord(String guess, String word) {
        /*
        Prints the guess inputted by the user but highlights letters that match with the word with 
        green and highlights letters that are in the wrong position with yellow
        */
        ArrayList<String> retList = new ArrayList<String>();
        for (int i = 0; i<5; i++) {
            Character guessChar = guess.charAt(i);
            Character wordChar = word.charAt(i);
            if (guessChar==wordChar) {
                retList.add("\u002B31;1m"+Character.toString(guessChar));
            }
        }
        for (int i = 0; i<5; i++) {
            System.out.println(retList.get(i));
        }
    }

    public void getWord() {
        Scanner wordScan = new Scanner(System.in);
        System.out.println("Enter your guess: ");
        guess = wordScan.nextLine();
        wordScan.close();
    }
}




