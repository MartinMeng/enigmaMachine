package enigma;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import javax.naming.InitialContext;

/** Enigma simulator.
 *  @author Lingwei Meng
 */
public final class Main {

    /** Process a sequence of encryptions and decryptions, as
     *  specified in the input from the standard input.  Print the
     *  results on the standard output. Exits normally if there are
     *  no errors in the input; otherwise with code 1. */
    
   /** Global rotor data waiting to be initiaized. */
    public static Rotor[] rotors = new Rotor[12];
    
    public static void main(String[] unused) {
        Machine M;
        BufferedReader input =
            new BufferedReader(new InputStreamReader(System.in));
        
        buildRotors();

        M = null;
        int lineNum = 0;
        try {
            while (true) {
                String line = input.readLine();
                lineNum++;
                if (line == null) {
                    break;
                }
                if (isConfigurationLine(line)) {
                    M = new Machine();
                    configure(M, line);
                } else {
                    if (lineNum == 1){
                        throw new IOException("The first line of input is not " +
                                "a configuration!");
                    }
                    printMessageLine(M.convert(standardize(line)));
                }
            }
        } catch (IOException excp) {
            System.err.printf("Input error: %s%n", excp.getMessage());
            System.exit(1);
        }
    }
    
    /** Return true iff LINE is an Enigma configuration line. */
    private static boolean isConfigurationLine(String line) {
        if (line.startsWith("*")) {
            return true;
        }
        return false;
    }

    /** Configure M according to the specification given on CONFIG,
     *  which must have the format specified in the assignment. */
    private static void configure (Machine M, String config) throws IOException {
        String [] rotorConfig = new String[7];
        rotorConfig = config.split("\\s+");

        Rotor[] machineRotors = new Rotor[5];        
        for (int i = 1; i < 6; i++) {
            boolean isCorrectRotorIndex = false;
            for (Rotor rotor : rotors) {
                if (rotor.getRotorIndex().equals(rotorConfig[i])) {
                    machineRotors[5 - i] = rotor;
                    isCorrectRotorIndex = true;
                }
            }
            if (!isCorrectRotorIndex) {
                throw new IOException( rotorConfig[i] + "is not a correct rotor!");
            }
        }      
        M.replaceRotors(machineRotors);
        M.setRotors(rotorConfig[6]);
    }

    /** Return the result of converting LINE to all upper case,
     *  removing all blanks and tabs.  It is an error if LINE contains
     *  characters other than letters and blanks. */
    private static String standardize(String line) {
        String newLine = line.replaceAll("\\s+", "");
        return newLine.toUpperCase();
    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private static void printMessageLine(String msg) {
        char[] msgChar = msg.toCharArray();
        for (int i = 0; i < msgChar.length; i++) {
            System.out.print(msgChar[i]);
            if ((i + 1) % 5 == 0) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    /** Create all the necessary rotors. */
    private static void buildRotors() {
        for (int i = 0; i < 8; i++) {
            rotors[i] = new Rotor(enigma.PermutationData.ROTOR_SPECS[i]);
        }
        for (int i = 8; i < 10; i++) {
            rotors[i] = new FixedRotor(enigma.PermutationData.ROTOR_SPECS[i]);
        }
        for (int i = 10; i < 12; i++) {
            rotors[i] = new Reflector(enigma.PermutationData.ROTOR_SPECS[i]);
        }
    }
}

