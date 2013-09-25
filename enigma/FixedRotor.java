package enigma;

/** Class that represents a rotor that has no ratchet and does not advance.
 *  @author Lingwei Meng
 */
class FixedRotor extends Rotor {

    // This needs other methods or constructors.
    public FixedRotor(String[] fixedRotorData) {
        super(fixedRotorData);
    }
    
    @Override
    boolean advances() {
        return false;
    }

    @Override
    boolean atNotch() {
        return false;
    }

    /** Fixed rotors do not advance. */
    @Override
    void advance() {
    }

}
