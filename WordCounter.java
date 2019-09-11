
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jacynta Ryan
 */
public class WordCounter {
    
    private SimpleHashMap<String, Integer> wordFrequencies;
    private Scanner wordStream;
    private String filePath;
    private int totalWordCount = 0;
    private int distinctWordCount = 0;
    private int probeCount = 0;
    private double avgProbeCount;
    
    public WordCounter(String filePath, SimpleHashMap.CollisionStrategy strategy)
    {
        try {
            this.filePath = filePath;
            wordFrequencies = new SimpleHashMap<>(strategy);
            wordStream = new Scanner(new File(filePath));
            wordStream.useDelimiter("-{2,}|[^\\p{IsAlphabetic}'-]+");
        } catch (FileNotFoundException ex) {
            System.err.println("File at " + filePath + " does not exist");
        }
    }
    
    public void run()
    {
        while (wordStream.hasNext())
        {
            //read word while theres words left and update the words frequency
            String word = wordStream.next();
            word = word.toLowerCase();
            updateFrequency(word);
        }
        //this doesnt work
        avgProbeCount = (double) probeCount / (double) totalWordCount;
    }
    
    public void printStatistics()
    {
        System.out.println("File: " + filePath);
        System.out.printf("Size: %d Capacity: %d Load Factor: %f \n", 
                wordFrequencies.size(), 
                wordFrequencies.capacity(),
                wordFrequencies.loadFactor);
        System.out.printf("Average no. of probes: %f \n", avgProbeCount);
        System.out.printf("Words: %d Distinct: %d \n", totalWordCount, distinctWordCount);
        System.out.println("--------------------------------------------------");
    }
    
    private void updateFrequency(String word)
    {
        //every time a word is passed in it gets the current frequency from the hashmap
        Integer currentFrequency = wordFrequencies.get(word);
        
        totalWordCount++;
        // if the word is not already in the hash map set the word frequency to 1
        if (currentFrequency == null)
        {
            wordFrequencies.put(word, 1);
            //every time a new word is found the distict word count is incremented
            distinctWordCount++;
        }
        else
        {
            //if word is found in hashmap then the frequency is incremented
            wordFrequencies.put(word, currentFrequency + 1);
        }
        //this doesnt work
        probeCount += wordFrequencies.probeCount;
    }
}
