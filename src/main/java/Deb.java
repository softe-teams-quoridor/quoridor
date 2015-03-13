import java.util.*;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Deb {
    public static PrintStream ug;

    public static void initialize() {
        // initialize debug stream
        try {
            ug = new PrintStream("game_debug");
        } catch (FileNotFoundException e) {
            ug = System.err;
        }
    }
}

