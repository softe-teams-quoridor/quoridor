public class AI_LeftRight implements MoveServer {
    private boolean direction; 
    public AI_LeftRight() {
        direction = false;
    }

    public String getMove() {
        direction = (! direction);
        if (direction) {
            return "IV-A";
        } else {
            return "V-A";
        }
    }
}
