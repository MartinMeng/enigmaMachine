package enigma;

/** Class that represents a reflector in the enigma.
 *  @author Lingwei Meng
 */
class Reflector extends Rotor {
    
    public Reflector(String[] reflectorData) {
        super(reflectorData);
    }
    
    @Override
    boolean hasInverse() {
        return false;
    }

    /** Returns a useless value; should never be called. */
    @Override
    int convertBackward(int unused) {
        throw new UnsupportedOperationException();
    }

}
