/* AI_Link - teams
 *
 *
 */

public class AI_Link implements QuoridorAI {

    private int bias;

    public AI_Link() {
    
    }

    public String getMove(GameBoard board, Player player) {
        return "";
    }

    public void reset() {
        bias = 0;
    }

    public String toString() {
        return "Link";
    }

    private void getBias(Player p) {
        // nothing has really been implemented here yet
        switch (p.getPlayerNo()) {
            case 0: bias = 0; break;
            case 1: bias = 1; break;
            case 2: bias = 2; break;
            case 3: bias = 3; break;
            default: break; //error
        }
    }
}
