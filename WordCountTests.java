/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jacynta Ryan
 */
public class WordCountTests {
    
    public static void main(String[] args) {
        String[] textFiles = new String[3];
        //path needs to be changed these were for my laptop
        textFiles[0] = "C:\\Users\\Jacynta\\Desktop\\DS_textfiles\\GreatExpectations.txt";
        textFiles[1] = "C:\\Users\\Jacynta\\Desktop\\DS_textfiles\\MobyDick.txt";
        textFiles[2] = "C:\\Users\\Jacynta\\Desktop\\DS_textfiles\\OliverTwist.txt";
        //array of strageies, loop through array to test each
        //then loop through the text file array for each strategy
        //run word counter and print statistics
        SimpleHashMap.CollisionStrategy[] strategies = new SimpleHashMap.CollisionStrategy[3];
        strategies[0] = SimpleHashMap.CollisionStrategy.LINEAR_PROBING;
        strategies[1] = SimpleHashMap.CollisionStrategy.QUADRATIC_PROBING;
        strategies[2] = SimpleHashMap.CollisionStrategy.DOUBLE_HASHING;
        
        for (int i = 0; i < strategies.length; i++) {
            System.out.println("=== " + strategies[i] + "===");
            
            for (int j = 0; j < textFiles.length; j++) {
                WordCounter wordCounter = new WordCounter(textFiles[j], strategies[i]);
                wordCounter.run();
                wordCounter.printStatistics();
            }
        }
    }
}
