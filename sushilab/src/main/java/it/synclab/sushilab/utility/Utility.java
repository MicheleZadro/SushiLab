package it.synclab.sushilab.utility;

import java.time.LocalDateTime;

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

    public static boolean verifyDate(String giorno, int oraInizio, int oraFine){
        LocalDateTime now = LocalDateTime.now();
        //String date = dtf.format(now.getDayOfWeek());
        int giornoAttuale = now.getDayOfWeek().getValue();
        int oraAttuale = now.getHour();
        int minutoAttuale = now.getMinute();
        int giornoParametro = -1;
        if(giorno.compareTo("Lun") == 0) giornoParametro = 1;
        if(giorno.compareTo("Mar") == 0) giornoParametro = 2;
        if(giorno.compareTo("Mer") == 0) giornoParametro = 3;
        if(giorno.compareTo("Gio") == 0) giornoParametro = 4;
        if(giorno.compareTo("Ven") == 0) giornoParametro = 5;
        if(giorno.compareTo("Sab") == 0) giornoParametro = 6;
        if(giorno.compareTo("Dom") == 0) giornoParametro = 7;
        if(giorno.compareTo("All") == 0) giornoParametro = 8;
        if(giorno.compareTo("Week") == 0) giornoParametro = 9;
        if(giorno.compareTo("End") == 0) giornoParametro = 10;
        if(giornoParametro == -1) return false;
        if(!(giornoParametro == giornoAttuale || giornoParametro == 8 || (giornoParametro == 9 && giornoAttuale <= 5) || (giornoParametro == 10 && giornoAttuale >=6))) return false;
        if((oraAttuale * 60 + minutoAttuale) >= oraInizio && (oraAttuale * 60 + minutoAttuale) <= oraFine)
            return true;
        return false;
    }
}