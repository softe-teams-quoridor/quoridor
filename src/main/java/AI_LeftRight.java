public class AI_LeftRight implements MoveServer {
    private boolean direction; 
    public AI_LeftRight() {
        direction = false;
    }

    public String getNextMove(GameBoard board) {
        direction = (! direction);
        if (direction) {
            return "IV-A";
        } else {
            return "V-A";
        }
    }
}
