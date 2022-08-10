package it.synclab.sushilab.utility;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;

public class Utility {
    public static String generateString(int minLength, int maxLength, Boolean numbers, Boolean lowercaseLetters, Boolean uppercaseLetter){
        RandomStringGenerator randomStringGenerator;
        if(numbers && !lowercaseLetters && !uppercaseLetter){
            randomStringGenerator = new RandomStringGenerator.Builder()
                            .withinRange('0', '9')
                            .filteredBy(CharacterPredicates.DIGITS)
                            .build();
        }
        else if(!numbers){
            randomStringGenerator = new RandomStringGenerator.Builder()
                            .withinRange('A', 'z')
                            .filteredBy(CharacterPredicates.LETTERS)
                            .build();
        }
        else{
            randomStringGenerator = new RandomStringGenerator.Builder()
                            .withinRange('0', 'z')
                            .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
                            .build();
        }
        String s = randomStringGenerator.generate(minLength, maxLength);
        if(lowercaseLetters && !uppercaseLetter)
            return s.toLowerCase();
        if(!lowercaseLetters && uppercaseLetter)
            return s.toUpperCase();
        return s;
    }
}
