/* this is an "AI" that simply defers to a user 
 * essentially, starting a MoveServer with this option
 * implements what used to be UserServer
 */

import java.util.Scanner;

public class AI_AskUser implements QuoridorAI {
    private static final Scanner keyboard = new Scanner(System.in);

    public String getMove(GameBoard board, Player player) {
        System.out.print(">> ");
        String move = keyboard.nextLine().trim();
        Square[] squares = GameEngine.validate(board, player, move);
        if(squares != null) {
            return move;
        }
        System.out.println("that looks illegal; are you sure?");
        String confirm = keyboard.nextLine();
        if (confirm.equals("y")) {
            return move;
        }
        return getMove(board, player);
    }
    
    public void reset() {
        // does this need to do anything? 
    }

    public String toString() {
        System.out.print("what is your name? ");
        return keyboard.nextLine().trim();
    }
}
