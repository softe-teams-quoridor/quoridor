public class AI_LeftRight implements QuoridorAI {
    private boolean direction; 
    public AI_LeftRight() {
        direction = false;
    }

    public String getMove(GameBoard _, Player p) {
        direction = (! direction);
        if (direction) {
            return "IV-A";
        } else {
            return "V-A";
        }
    }

    public void reset() {
        direction = false;
    }
}
