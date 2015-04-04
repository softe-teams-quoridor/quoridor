/* this QuoridorAI alternates between stepping left and right */
public class AI_LeftRight implements QuoridorAI {
    private boolean goLeft; 
    public AI_LeftRight() {
        goLeft = false; // true means step left, false means step right
    }

    public String getMove(GameBoard _, Player player) {
        goLeft = (! goLeft);
        if (player.getPlayerNo() == 0) {
            if (goLeft) {
                return "IV-A";
            } else {
                return "V-A";
            }
        } else if (player.getPlayerNo() == 1) {
            if (goLeft) {
                return "IV-I";
            } else {
                return "V-I";
            }
        } else if (player.getPlayerNo() == 2) {
            if (goLeft) {
                return "II-E"; // can't go left, that's a border
            } else {
                return "I-E";
            }
        } else {
            assert (player.getPlayerNo() == 3);
            if (goLeft) {
                return "VIII-E";
            } else {
                return "IX-E";
            }
        }
    }

    public void reset() {
        goLeft = false;
    }
}
