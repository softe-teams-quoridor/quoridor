public class Square {
    private int file; // 0-8
    private int rank; // 0-8
    private Pawn pawn; 


    public Square(int file, int rank) {
        this.file = file;
        this.rank = rank;
        pawn = null;
    }
}
