package utils;

import java.security.SecureRandom;
import java.util.Random;

public class RandomPasswordGenerator {
    static char[] LOWERCASE = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    static char[] UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    static char[] NUMBERS = "0123456789".toCharArray();
    static char[] SPECIALCHARACTERS = "!@#$%^&*()/\\,.?".toCharArray();
    
    static Random rand = new SecureRandom();
    
    public String getPassword() {
    	char[] pass = new char[4];
    	
    	pass[0] = LOWERCASE[rand.nextInt(LOWERCASE.length)];
        pass[1] = UPPERCASE[rand.nextInt(UPPERCASE.length)];
        pass[2] = NUMBERS[rand.nextInt(NUMBERS.length)];
        pass[3] = SPECIALCHARACTERS[rand.nextInt(SPECIALCHARACTERS.length)];
        
        String password = new String(pass);
        System.out.println(password);
    	
    	return password;
    }
    
    public String getNormal() {
    	char[] pass = new char[2];
    	pass[0] = LOWERCASE[rand.nextInt(LOWERCASE.length)];
        pass[1] = UPPERCASE[rand.nextInt(UPPERCASE.length)];
        
        String password = new String(pass);
        return password;
    }
}
