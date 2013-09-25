package enigma;

/** Class that represents a rotor in the enigma machine.
 *  @author Lingwei Meng
 */
class Rotor {
    // This needs other methods, fields, and constructors.

    /** Size of alphabet used for plaintext and ciphertext. */
    static final int ALPHABET_SIZE = 26;

    private String rotorIndex;
    private String fowardPermutationData;
    private String reversePermutationData;
    private char[] rotorNotch;
    
    /** Access the name of the rotor to help assign rotors. */
    public String getRotorIndex() {
        return rotorIndex;
    }
    
    /** Constructor for the Rotor class. */
    public Rotor(String[] rotorData) {
        rotorIndex = rotorData[0];
        fowardPermutationData = rotorData[1];
        if (rotorData.length > 2){
            reversePermutationData = rotorData[2];
        }
        if (rotorData.length > 3) {
            rotorNotch = rotorData[3].toCharArray();
        }
    }
    
    /** Assuming that P is an integer in the range 0..25, returns the
     *  corresponding upper-case letter in the range A..Z. */
    static char toLetter(int p) {
        return (char) (p + (int)'A');
    }

    /** Assuming that C is an upper-case letter in the range A-Z, return the
     *  corresponding index in the range 0..25. Inverse of toLetter. */
    static int toIndex(char c) {
        return (int) c - (int)'A'; 
    }

    /** Returns true iff this rotor has a ratchet and can advance. */
    boolean advances() {
        return true;
    }

    /** Returns true iff this rotor has a left-to-right inverse. */
    boolean hasInverse() {
        return true;
    }

    /** Return my current rotational setting as an integer between 0
     *  and 25 (corresponding to letters 'A' to 'Z').  */
    int getSetting() {
        return _setting;
    }

    /** Set getSetting() to POSN.  */
    void set(int posn) {
        assert 0 <= posn && posn < ALPHABET_SIZE;
        _setting = posn;
    }

    /** Return the conversion of P (an integer in the range 0..25)
     *  according to my permutation. */
    int convertForward(int p) {
        p = (p + getSetting()) % ALPHABET_SIZE;
        char after = fowardPermutationData.charAt(p);
        p = (toIndex(after) - getSetting()) % ALPHABET_SIZE;
        if ( p < 0) { p += ALPHABET_SIZE; }
        //System.out.println("foward: " + p);
        return p;
    }

    /** Return the conversion of E (an integer in the range 0..25)
     *  according to the inverse of my permutation. */
    int convertBackward(int e) {
        e = (e + getSetting()) % ALPHABET_SIZE;
        char after = reversePermutationData.charAt(e);
        e = (toIndex(after) - getSetting()) % ALPHABET_SIZE;
        if ( e < 0) { e += ALPHABET_SIZE; }
        //System.out.println("backward: " + e);
        return e;
    }

    /** Returns true iff I am positioned to allow the rotor to my left
     *  to advance. */
    boolean atNotch() {
        for (char notch : rotorNotch) {
            if (getSetting() == toIndex(notch)) {
                return true;
            }
        }
        return false;
    }

    /** Advance me one position. */
    void advance() {
        _setting++;
    }

    /** My current setting (index 0..25, with 0 indicating that 'A'
     *  is showing). */
    private int _setting;

}
