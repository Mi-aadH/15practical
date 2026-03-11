//start anagrams project

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Anagrams {
//creating a signature
    public static String signature(String word){
        char [] letters = word.toCharArray();
        Arrays.sort(letters);
        return new String(letters);
    }

     public static void main(String[]args) throws Exception{
        if (args.length != 1){
            System.out.println("Input file for java Anagrams");
            return;
        }
        
        String input_file = args[0];
        System.out.println("Reading:"+input_file);

        List<String>lines = Files.readAllLines(Paths.get(input_file));
        Map<String,Integer> words= new HashMap<>();

         for(String line : lines){
            String[]parts = line.split("\\s+");

            for(String w : parts){
                String word = w.replaceAll("[^A-Za-z']","").toLowerCase();
                if (word.length()==0) continue;
                words.put(word,words.getOrDefault(word,0)+1);
                
            }
        }
         Map<String,List<String>>anagrams= new HashMap<>();
        for (String word : words.keySet()){
            String sign = signature(word);
            anagrams.computeIfAbsent(sign,k->new ArrayList<>()).add(word);
        }
        List<String>output= new ArrayList<>();
        for(List<String>group: anagrams.values()){
            if (group.size()>1){
                String line = String.join(" ",group);
                for (int i = 0; i < group.size();i++){
                    output.add(line + "\\\\");

                    int space = line.indexOf("");
                    line = line.substring(space + 1)+" "+line.substring(0,space);
                }
            }
        }
          Collections.sort(output);
        Files.createDirectories(Paths.get("latex"));
        PrintWriter tex = new PrintWriter("latex/theAnagrams.tex");

        char current = 'X';
        for ( String lemma : output ){
            char first = lemma.charAt(0);
            if ( Character.toLowerCase(first)!=Character.toLowerCase(current)){
                current = first;
                tex.println();
                tex.println("\\vspace{14pt}");
                tex.println("\\noindent\\notextbf{\\Large"+Character.toUpperCase(first)+"}\\\\*[+12pt]");

            }
            tex.println(lemma);
        }
        tex.close();
    }
}
