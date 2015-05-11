
    /* used for testing Graph algorithm */

import java.util.*;

public class GraphDemo {

    public static void main(String[] orgs) {
        
        /* initial preparation */
        Graph graph = new Graph(GameBoard.ROWS * GameBoard.COLUMNS);
        Queue<Player> players = new LinkedList<Player>();
        for(int i = 0; i < 4; i++)
            players.add(new Player(i, 10));
        GameBoard board = new GameBoard(players);

//*       // Player 0
        graph.buildPath(board, players.peek());
        graph.printGraph();
        System.out.println();
/*
        // Player 1
        players.add(players.remove());
        graph.buildPath(board, players.peek());
        graph.printGraph();
        System.out.println();

/*        // Player 2
        players.add(players.remove());
        players.add(players.remove());
        graph.buildPath(board, players.peek());
        graph.printGraph();
        System.out.println();
/*
        // Player 3
        players.add(players.remove());
        players.add(players.remove());
        players.add(players.remove());
        graph.buildPath(board, players.peek());
        graph.printGraph();
        System.out.println();
*/

        // Test board with walls for player 1
        System.out.println("---Wall test---\n");
        
        // Place a horizontal wall from I-D to VIII-D
        Square[] hwalls1 = {new Square(0,3),new Square(1,3)};
        board.placeWall(hwalls1);
        Square[] hwalls2 = {new Square(2,3),new Square(3,3)};
        board.placeWall(hwalls2);
        Square[] hwalls3 = {new Square(4,3),new Square(5,3)};
        board.placeWall(hwalls3);
        Square[] hwalls4 = {new Square(6,3),new Square(7,3)};
        board.placeWall(hwalls4);
        
        // Place a vertical wall from IV-A to IV-H
        Square[] vwalls1 = {new Square(3,0),new Square(3,1)};
        board.placeWall(vwalls1);
        Square[] vwalls2 = {new Square(3,2),new Square(3,3)};
        board.placeWall(vwalls2);
        Square[] vwalls3 = {new Square(3,4),new Square(3,5)};
        board.placeWall(vwalls3);
        Square[] vwalls4 = {new Square(3,6),new Square(3,7)};
        board.placeWall(vwalls4);
        
        // Output results
        graph.buildPath(board, players.peek());
        graph.printGraph();
        System.out.println();
    }

}
