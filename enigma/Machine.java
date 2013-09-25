package enigma;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.String;

/** Class that represents a complete enigma machine.
 *  @author Lingwei Meng
 */
class Machine {

    // This needs other methods or constructors.

    private Rotor[] normalRotors = new Rotor[3];
    private FixedRotor fixedRotor;
    private Reflector reflector;
    
    /** Set my rotors to (from left to right) ROTORS.  Initially, the rotor
     *  settings are all 'A'. */
    void replaceRotors(Rotor[] rotors) throws IOException {
        String[] rotorNames = new String[3];
        for (int i = 0; i < 3; i++) {
            String rotorName = rotors[i].getRotorIndex();
            if (Arrays.asList(rotorNames).contains(rotorName)) {
                throw new IOException("There are two instances of "
                        + rotorName);
            }
            normalRotors[i] = rotors[i]; 
        }
        fixedRotor = (FixedRotor) rotors[3];
        reflector = (Reflector) rotors[4];
    }

    /** Set my rotors according to SETTING, which must be a string of four
     *  upper-case letters. The first letter refers to the leftmost
     *  rotor setting.  */
    void setRotors(String setting) throws IOException {
        Pattern rotatingRotorConfig = Pattern.compile("\\A[A-Z]{4}\\Z");
        Matcher settingMatcher = rotatingRotorConfig.matcher(setting);
        if (!settingMatcher.find()) {
            throw new IOException(setting +
                    "is not a right configuration for rotors!");
        }
        
        fixedRotor.set(enigma.Rotor.toIndex(setting.charAt(3)));
        for (int i = 0; i < 3; i++) {
            normalRotors[i].set(enigma.Rotor.toIndex(setting.charAt(i)));
        }
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) throws IOException {
        char[] msgChar = msg.toCharArray();
        for (int i = 0; i < msgChar.length; i++) {
            char currChar = msgChar[i];
            if (currChar == ' ') {
                continue;
            }
            else if (!Character.isLetter(currChar)) {
                throw new IOException("Invalid character: " + currChar);
            }
            setRotors();
            int currInt = enigma.Rotor.toIndex(currChar);
            for (int j = 0; j < 3; j++) {
                currInt = normalRotors[j].convertForward(currInt);
            }
            currInt = fixedRotor.convertForward(currInt);
            currInt = reflector.convertForward(currInt);
            //currInt = reflector.convertBackward(currInt);
            currInt = fixedRotor.convertBackward(currInt);
            for (int j = 2; j >= 0; j--) {
                currInt = normalRotors[j].convertBackward(currInt);
            }
            msgChar[i] = enigma.Rotor.toLetter(currInt); 
        }
        return new String(msgChar);
    }
    
    /** Set the rotors to the correct configuration at each turn before converting
     *  a character. If both last and middle rotors are atNotch, then last and middle
     *  rotor will advnace twice.*/
    void setRotors() {
        //if (normalRotors[1].atNotch() && normalRotors[0].atNotch()) {
            //normalRotors[2].advance();
            //normalRotors[1].advance();
            //normalRotors[1].advance();
        //}
        if (normalRotors[1].atNotch()) {
            normalRotors[1].advance();
            normalRotors[2].advance();
        }
        if (normalRotors[0].atNotch()) {
            normalRotors[1].advance();
        }
        normalRotors[0].advance();        
    }
    
    /** Constructor for the Machine class. */
    public Machine() {       
    }
}
