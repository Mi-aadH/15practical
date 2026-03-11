//start anagrams project

import java.util.Arrays;

public class Anagrams {
//creating a signature
    public static String signature(String word){
        char [] letters = word.toCharArray();
        Arrays.sort(letters);
        return new String(letters);
    }
}
