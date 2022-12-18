import javax.swing.*;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.awt.*;
import java.awt.event.*;
class gui {
    public static void createGui() { //JFrame Setup
        JFrame frame = new JFrame("Extreme Wordle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int frameWidth = 500;
        int frameHeight = 300;
        frame.setSize(frameWidth, frameHeight);
        frame.setLayout(null);
        frame.setVisible(true);
        JPanel difficultyPanel = createDifficultyPanel();
        frame.add(difficultyPanel);
        // JPanel wordSelectionPanel = createWordSelectionPanel();
        // frame.add(wordSelectionPanel);
        frame.setContentPane(difficultyPanel);
        frame.repaint();
        frame.revalidate();
    }

    public static JPanel createDifficultyPanel() {
        int frameWidth = 500;
        int frameHeight = 300;
        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setSize(frameWidth, frameHeight);

        //Adding Welcome and Instruction Labels
        Label welcomeL = new Label("Welcome to Extreme Wordle!");
        Label instructionL = new Label("Please select the difficulty level from the options below:");
        int welcomeLWidth = 200;
        int instructionLWidth = 400;
        welcomeL.setBounds((frameWidth-welcomeLWidth)/2, 10, welcomeLWidth, 50);
        instructionL.setBounds((frameWidth-instructionLWidth)/2, 40, instructionLWidth, 50);
        difficultyPanel.add(welcomeL);
        difficultyPanel.add(instructionL);

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

        HashMap<String, Integer> difficultyValues = new HashMap<String, Integer>(5);
        difficultyValues.put("Easy", 1);
        difficultyValues.put("Medium", 2);
        difficultyValues.put("Hard", 3);
        difficultyValues.put("Very Hard", 4);
        difficultyValues.put("Extreme", 5);

        WordSelectionService wordSelectionService = new WordSelectionService();
        easyB.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                wordSelectionService.chooseWord(difficultyValues.get("Easy"));
                
            }
        });
        mediumB.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                wordSelectionService.chooseWord(difficultyValues.get("Medium"));
            }
        });
        hardB.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                wordSelectionService.chooseWord(difficultyValues.get("Hard"));
            }
        });
        vHardB.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                wordSelectionService.chooseWord(difficultyValues.get("Very Hard"));
            }
        });
        extremeB.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                wordSelectionService.chooseWord(difficultyValues.get("Extreme"));
            }
        });
        difficultyPanel.add(easyB); 
        difficultyPanel.add(mediumB); 
        difficultyPanel.add(hardB); 
        difficultyPanel.add(vHardB); 
        difficultyPanel.add(extremeB); 
        return difficultyPanel;
    }

    public static void main(String args[]){
        createGui();
    }
        
}





    




