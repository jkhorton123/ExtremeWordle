# ExtremeWordle Background
This is a [Wordle](https://www.nytimes.com/games/wordle/index.html) variant that has various difficulty levels and was built 
using Java 18.0.1 for the back-end and Java Swing for the front-end. Information regarding Wordle can be found [here](https://en.wikipedia.org/wiki/Wordle).

The wordle answer chosen by the program (which the user will then try to guess) is a random five letter word which is selected 
from a list of English words sorted by the number of times they occur in a large English corpus. This list was created by Peter 
Norvig (who served as the director of research at Google) who wrote an [article](http://norvig.com/mayzner.html) about the 
research. Norvig's list, which was imported as this [.txt file](https://norvig.com/google-books-common-words.txt), is based on 
the Google books Ngrams data set, which compiles word counts from all books scanned by Google. Each of the 5 difficulty levels 
chooses from a subset of the 5-letter words from Norvig's list. The subsets associated with each of the modes are described below:

Easy Mode: First 10% of the words in the list (the most common words in the list because the list is sorted by word commonness in descending order) <br />
Medium Mode: Next 10% of words <br />
Hard Mode: Next 20% of words <br />
Very Hard Mode: Next 30% of words <br />
Extreme Mode: Last 30% of words (the least common words in the list)

<<<<<<< HEAD
Each time the user selects a new difficulty, a new word will be selected by the program which will correspond to the selected difficulty. Each selected word, as well as each guess entered by the user, will be validated against an [English word list repository](https://github.com/lorenbrichter/Words/blob/master/Words/en.txt).
=======
Each time the user selects a new difficulty, a new word will be selected by the program which will correspond to the selected 
difficulty. Each selected word, as well as each guess entered by the user, will be validated against the [2019 Collins Scrabble 
Words list](https://drive.google.com/file/d/1oGDf1wjWp5RF_X9C7HoedhIWMh5uJs8s/view).


# Appliction Setup
1. Ensure [Java 18.0.1](https://www.oracle.com/java/technologies/javase/jdk18-archive-downloads.html) or a compatible version is installed.
2. Download and unzip the package "ExtremeWordle".
3. In the file "WordleGUI.java", run the main method in an IDE of choice (Visual Studio Code is preferred).
4. Close the pop-up java GUI window before re-running the application.


# Application Walkthrough
On application startup, the user is presented with a welcome screen which contains the difficulty options the user can choose from.

Medium mode
>>>>>>> 82a10ae (Update README.md)
