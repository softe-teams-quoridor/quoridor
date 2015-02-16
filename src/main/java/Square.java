public class Square {
    private int file; // 0-8
    private int rank; // 0-8
    private Pawn pawn; 
    private Wall horizontal;
    private Wall vertical;


    public Square(int file, int rank) {
        this.file = file;
        this.rank = rank;
        pawn = null;
    }

    public Pawn getPawn() {
	return this.pawn;
    }
}
