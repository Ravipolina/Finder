package finder;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.*;
import java.io.*;

public class Main {

    // The main module that reads a large text file
    public static void main(String[] args) {
        // Read the text file
        try {
        	URL myurl = new URL("http://norvig.com/big.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(myurl.openStream()));

            System.out.println("Started reading file");
            String line;
            // Read the text file in parts (e.g. 1000 lines in each part)
            int partSize = 1000;
            int count = 0;
            int lineCount=0;
            String textPart = "";
            HashMap<String, List<Offsets>> positionsMap = HashMap.newHashMap(500);
            // Read the text file line by line
            while ((line = reader.readLine()) != null) {
                textPart += line + "\n";
                count++;
                
                // If the current part is full, send it to the matcher
                if (count == partSize) {
     
                    // Send the text part to the matcher
                    Map<String, List<Offsets>> matches = matcher(textPart,lineCount);
                    aggregator(matches,positionsMap);
                    // Reset the count and the text part
                    count = 0;
                    textPart = "";
                    lineCount+=1000;
                }
            }
            
            //Handle remaining entries in the file
            
            // Send the last text part to the matcher if any
            Map<String, List<Offsets>> matches = matcher(textPart,lineCount);
            // Call the aggregator to combine and print the results
            aggregator(matches,positionsMap);
            
            //print the result
            for (Map.Entry<String, List<Offsets>> entry : positionsMap.entrySet()) {
            	System.out.print(entry.getKey()+"-->");
            	entry.getValue().forEach(e -> System.out.println(e.toString()));
             }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.print("finished");
    }

    // The matcher module that gets a text string as input and searches for matches of a given set of strings
    public static Map<String, List<Offsets>> matcher(String text, int lineOffset) {
        // Initialize a map to store the results
        // Search the text string for the given set of strings
        String[] words = {"James" , "John" , "Robert" , "Michael" , "William" , "David" ,
        				  "Richard" , "Charles" , "Joseph" , "Thomas" , "Christopher" , 
        				  "Daniel" , "Paul" , "Mark" , "Donald" , "George" , "Kenneth" , 
        				  "Steven" , "Edward" , "Brian" , "Ronald" , "Anthony" , "Kevin" , 
        				  "Jason" , "Matthew" , "Gary" , "Timothy" , "Jose" , "Larry" , 
        				  "Jeffrey" , "Frank" , "Scott" , "Eric" , "Stephen" , "Andrew" , 
        				  "Raymond" , "Gregory" , "Joshurjerry" , "Dennis" , "Walter" , 
        				  "Patrick" , "Peter" , "Harold" , "Douglas" , "Henry" , "Carl" , 
        				  "Arthur" , "Ryan" , "Roger" };
    	// Create a map to store the offsets      
        HashMap<String, List<Offsets>> positionsMap = HashMap.newHashMap(500);
        //split the text string into lines
        String[] lines = text.split("\n");
        for(String line : lines){
            int charOffset = 0;
            for(String word : words){
                if(line.contains(word)){
                    //create offset
                    Offsets offset = new Offsets(lineOffset, line.indexOf(word));
                    //positions.put(word, offsets);
                    if(positionsMap.containsKey(word))
                    {
                    	List<Offsets> l2 = positionsMap.get(word);
                    	l2.add(offset);
                    }
                    else {
                    	List<Offsets> l = new ArrayList<>();
                    	l.add(offset);
                    	positionsMap.put(word, l);
                    }
                }
                charOffset += word.length() + 1;
            }
       
            lineOffset++;
        }
        return positionsMap;
    }

    // The aggregator module that aggregates the results from all the matchers.
    public static void aggregator(Map<String, List<Offsets>> result,Map<String, List<Offsets>> combined) {
        // Print the results
		
    	result.forEach((k, v) -> combined.merge(k, v, (v1, v2) -> {
    	    List<Offsets> list = new ArrayList<>(v1);
    	    list.addAll(v2);
    	    return list;
    	}));
    }
}