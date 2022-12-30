import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import util.LimitDocumentFilter;

class gui {
    private static String targetWord;
    private static ArrayList<String> guesses = new ArrayList<String>();
    private static final int frameWidth = 500;
    private static final int frameHeight = 300;
    private static JFrame frame;
    private static JPanel dPanel; // Panel containing the difficulty selection UI
    private static JPanel wPanel; // Panel containing the word selection UI
    private static int firstActive = 0;
    private static int lastActive = 4;
    private static JTextField[] textFields;
    private static WordSelectionService wordSelectionService;
    private static boolean textFieldsCreated = false;
    public static void createGui() { 
        /*
        Creates the JFrame and calls methods to create the JPanels that will be used for the UI
        */
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
        
        frame.setContentPane(dPanel); // The application will show the difficulty selection screen initially
        frame.setVisible(true);
    }

    public static JPanel createDifficultyPanel() {
        /*
        Creates the difficulty selection screen UI

        Returns:
            difficultyPanel (JPanel) : Panel which contains the difficulty selection screen UI
        */
        JPanel difficultyPanel = new JPanel(new GridLayout(10, 1, 10, 5));
        difficultyPanel.setSize(frameWidth, frameHeight);

        // Adding Welcome and Instruction Labels
        Label welcomeL = new Label("Welcome to Extreme Wordle!");
        Label instructionL = new Label("Please select the difficulty level from the options below:");

        difficultyPanel.add(welcomeL);
        difficultyPanel.add(instructionL);

        // Adding difficulty buttons
        JButton easyB = new JButton("Easy");
        JButton mediumB = new JButton("Medium");
        JButton hardB = new JButton("Hard");
        JButton vHardB = new JButton("Very Hard");
        JButton extremeB = new JButton("Extreme");

        // Mapping the difficulty values to integer multipliers that will be used for selecting words from the word list sorted by frequency
        HashMap<String, Integer> difficultyValues = new HashMap<String, Integer>(5);
        difficultyValues.put("Easy", 1);
        difficultyValues.put("Medium", 2);
        difficultyValues.put("Hard", 3);
        difficultyValues.put("Very Hard", 4);
        difficultyValues.put("Extreme", 5);

        wordSelectionService = new WordSelectionService();
        easyB.addActionListener(new ActionListener(){
            // The following methods obtain the target word based on the selected difficulty, reset the word selection screen, and switch the content pane to the word selection screen
            public void actionPerformed(ActionEvent e) {
                targetWord = wordSelectionService.chooseWord(difficultyValues.get("Easy"));
                if(textFieldsCreated){
                    resetTextFields();
                }
                frame.setContentPane(wPanel);
                frame.setVisible(true);
                frame.repaint();
                frame.revalidate();
            }
        });
        mediumB.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                targetWord = wordSelectionService.chooseWord(difficultyValues.get("Medium"));
                if(textFieldsCreated){
                    resetTextFields();
                }
                frame.setContentPane(wPanel);
                frame.setVisible(true);
                frame.repaint();
                frame.revalidate();
            }
        });
        hardB.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(textFieldsCreated){
                    resetTextFields();
                }
                targetWord = wordSelectionService.chooseWord(difficultyValues.get("Hard"));
                frame.setContentPane(wPanel);
                frame.setVisible(true);
                frame.repaint();
                frame.revalidate();
            }
        });
        vHardB.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                targetWord = wordSelectionService.chooseWord(difficultyValues.get("Very Hard"));
                if(textFieldsCreated){
                    resetTextFields();
                }
                frame.setContentPane(wPanel);
                frame.setVisible(true);
                frame.repaint();
                frame.revalidate();
            }
        });
        extremeB.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                targetWord = wordSelectionService.chooseWord(difficultyValues.get("Extreme"));
                if(textFieldsCreated){
                    resetTextFields();
                }
                frame.setContentPane(wPanel);
                frame.setVisible(true);
                frame.repaint();
                frame.revalidate();
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
        JPanel wordSelectionPanel = new JPanel(); // Outer panel which will encompass the back button and the inputFieldsPanel
        wordSelectionPanel.setSize(frameWidth, frameHeight);
        
        // Adding the back button
        JButton backB = new JButton("Back");
        backB.addActionListener(new ActionListener(){
            // Back button will switch to the difficulty selection screen
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(dPanel);
                frame.setVisible(true);
                frame.repaint();
                frame.revalidate();
            }
        });
        GridBagConstraints gbc = new GridBagConstraints();            
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(16, 16, 16, 16);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        wordSelectionPanel.add(backB, gbc);
     
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = gbc.BOTH;
        gbc.insets = new Insets(24, 40, 40, 40);
        JPanel inputFieldsPanel = new JPanel(new GridLayout(6, 5, 10, 2)); // Inner panel which will contain the text fields
        textFields = new JTextField[30];
        for(int i=0; i<30; i++) {
            JTextField letField = new JTextField(1);
            ((AbstractDocument)letField.getDocument()).setDocumentFilter(new LimitDocumentFilter(1)); // Sets the filter for text inputs
            if(i>lastActive) {
                letField.setEnabled(false); // Disables all text fields that are below the current row
            }
            letField.addActionListener(new java.awt.event.ActionListener() {
                // Triggered when the user selects enter in a text box, this method validates the entered word in the row and updates the UI accordingly
                public void actionPerformed(ActionEvent event) {
                    String guess = "";
                    for(int i=firstActive; i<=lastActive; i++) { // Builds the word from the five text fields in the row
                        String let = textFields[i].getText();
                        guess = guess + let; 
                    }

                    if((guess.length()==5) && !(guesses.contains(guess)) && wordSelectionService.validateWord(guess)) { // If 5 letters were inputted, the word has not already been guessed, and it is a valid word
                        guesses.add(guess);
                        Color[] letterColors = wordSelectionService.getLetterColors(guess, targetWord); // Obtains color mappings for the letters in the selected word 
                        int c = 0;
                        for(int i=firstActive; i<=lastActive; i++) {
                            // Sets the text fields to the respective color and disables the text fields in the finished row
                            textFields[i].setBackground(letterColors[c]);
                            textFields[i].setEnabled(false);
                            c += 1;
                        }
                        if(guess.equals(targetWord)) {
                            JOptionPane.showMessageDialog(frame, "You won!"); // Produced if the user enters the target word
                        }
                        else if(guesses.size() == 6) { 
                            JOptionPane.showMessageDialog(frame, "Game Over. The word was " + targetWord + "."); // Produced if the user has used all 6 guesses without guessing the target word
                        }
                        else { 
                            firstActive += 5;
                            lastActive += 5;
                            if(lastActive<=29) {
                                for(int i=firstActive; i<=lastActive; i++) {
                                    textFields[i].setEnabled(true); // Enables the text fields in the next row down
                                }
                            }
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(frame, "Invalid Word");
                    }
                }
              });
            inputFieldsPanel.add(letField);
            textFields[i] = letField;
        }
        textFieldsCreated = true;
        wordSelectionPanel.add(inputFieldsPanel, gbc);
        return wordSelectionPanel;
        
    }
    public static void resetTextFields() {
        /*
        Resets the text fields and associated variables for the word selection screen
        */
        for(int i=firstActive; i<lastActive+1;i++) { // Disables the currently editable row of text fields
            textFields[i].setEnabled(false); 
        }
        firstActive = 0;
        lastActive = 4;
        guesses = new ArrayList<String>();
        for(int i=0; i<30; i++) {
            textFields[i].setText("");
            textFields[i].setBackground(Color.WHITE);
        }
        for(int i=firstActive; i<lastActive+1;i++) { // Enables the first row of text fields
            textFields[i].setEnabled(true); 
        }
    }
    public static void main(String args[]){
        createGui();
    }

}





    




