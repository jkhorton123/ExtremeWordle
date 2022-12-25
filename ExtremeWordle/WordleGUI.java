import javax.swing.*;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import util.LimitDocumentFilter;

import javax.swing.text.DocumentFilter;
class gui {
    private String word;
    private String validWords[];
    private static final int frameWidth = 500;
    private static final int frameHeight = 300;
    private static JFrame frame;
    private static JPanel dPanel; // Panel containing the difficulty selection UI
    private static JPanel wPanel; // Panel containing the word selection UI
    public static void createGui() { // JFrame Setup
        frame = new JFrame("Extreme Wordle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameWidth, frameHeight);
        frame.setLayout(null);
        frame.setVisible(true);
        dPanel = new JPanel(new BorderLayout());
        dPanel.setBorder(new EmptyBorder(2, 3, 2, 3));
        JPanel dLayout = new JPanel(new GridBagLayout());
        dLayout.setBorder(new EmptyBorder(5, 5, 5, 5));
        JPanel difficultyPanel = createDifficultyPanel();
        dLayout.add(difficultyPanel);
        dPanel.add(dLayout, BorderLayout.CENTER);
        frame.add(dPanel);

        wPanel = new JPanel(new BorderLayout());
        dPanel.setBorder(new EmptyBorder(2, 3, 2, 3));
        JPanel wLayout = new JPanel(new GridBagLayout());
        wLayout.setBorder(new EmptyBorder(5, 5, 5, 5));
        JPanel wordSelectionPanel = createWordSelectionPanel();
        wLayout.add(wordSelectionPanel);
        wPanel.add(wLayout, BorderLayout.CENTER);
        frame.add(wPanel);
        
        frame.setContentPane(dPanel);
        frame.setVisible(true);
        // frame.repaint();
        // frame.revalidate();
    }

    public static JPanel createDifficultyPanel() {
        /*
        Creates the difficulty Selection screen UI

        Returns:
            difficultyPanel (JPanel) : Panel which contains the difficulty selection screen UI
        */
        JPanel difficultyPanel = new JPanel(new GridLayout(10, 1, 10, 5));
        difficultyPanel.setSize(frameWidth, frameHeight);

        //Adding Welcome and Instruction Labels
        Label welcomeL = new Label("Welcome to Extreme Wordle!");
        Label instructionL = new Label("Please select the difficulty level from the options below:");

        difficultyPanel.add(welcomeL);
        difficultyPanel.add(instructionL);

        JButton easyB = new JButton("Easy");
        JButton mediumB = new JButton("Medium");
        JButton hardB = new JButton("Hard");
        JButton vHardB = new JButton("Very Hard");
        JButton extremeB = new JButton("Extreme");

        HashMap<String, Integer> difficultyValues = new HashMap<String, Integer>(5);
        difficultyValues.put("Easy", 1);
        difficultyValues.put("Medium", 2);
        difficultyValues.put("Hard", 3);
        difficultyValues.put("Very Hard", 4);
        difficultyValues.put("Extreme", 5);

        WordSelectionService wordSelectionService = new WordSelectionService();
        easyB.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String wordleWord = wordSelectionService.chooseWord(difficultyValues.get("Easy"));
                frame.setContentPane(wPanel);
                frame.setVisible(true);
                frame.repaint();
                frame.revalidate();
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
    public static JPanel createWordSelectionPanel() {
        /*
        Creates the word selection screen UI
        
        Returns:
            wordSelectionPanel (JPanel) : Panel which contains the word selection screen UI
        */
        JPanel wordSelectionPanel = new JPanel(new GridLayout(6, 5, 10, 2));
        Label instructionL = new Label("Guess the Word");
        wordSelectionPanel.add(instructionL);
        wordSelectionPanel.setSize(frameWidth, frameHeight);

        JTextField letField = new JTextField(1);
        ((AbstractDocument)letField.getDocument()).setDocumentFilter(new LimitDocumentFilter(1));
        wordSelectionPanel.add(letField);

        return wordSelectionPanel;
    }
    public static void main(String args[]){
        createGui();
    }

}





    




