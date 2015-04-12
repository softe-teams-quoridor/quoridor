import java.util.*;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Deb {
    public static PrintStream ug;

    public static void initialize(String name) {
        // initialize debug stream
        try {
            ug = new PrintStream(name + "_debug");
        } catch (FileNotFoundException e) {
            ug = System.err;
        }
    }

       // alternate, maybe better implementation
    public static PrintStream stream;
    
    public Deb(String name) {
        try {
            stream = new PrintStream(name + "_debug");
        } catch (FileNotFoundException e) {
            stream = System.err;
        }
    }
    public void ug(String line) {
        stream.println(line);
    }
}

