/* GameEngine.java - CIS405 - teams
 * Last Edit: April 3, 2015
 * ____________________________________________________________________________
 *
 * used for game rules and validation. capable of converting numeral/character
 *   to appropriate array index, validate proper player moves, check victory
 *   conditions, and rotate player turn-order.
 *
 * this class is used by both the client and the server!
 * 
 * --------------------------------- METHODS ----------------------------------
 *
 * PUBLIC:
 *
 * String toNumerals(int)    --> converts int to a numeral 
 * int fromNumerals(String)  --> converts string(numeral) to an int
 * char toLetters(int)       --> converts int to a letter ex 0 -> A
 * int fromLetters(char)     --> conversions between ints and numerals/letters
 * boolean validate(GameBoard, Player, String)  
 *                           --> returns if string represents a legal move
 * Player getWinner(GameBoard,Players[])
 *                           --> checks if a player has won the game 
 * PROTECTED:
 * 
 * Square parseMove(GameBoard, String) 
 *                           --> returns the square to move to if valid, null otherwise
 * Square[] parseWall(GameBoard, String) 
 *                           --> returns the squares of the wall if valid, null otherwise
 * boolean validateMove(GameBoard, Square, Square,int ,int) 
 *                           --> returns true if the move is valid
 * boolean validateWall(GameBoard, Square[])
 *                           --> returns true if the wall is valid
 *
 */

import java.util.Queue;
import java.util.Arrays;
import java.util.LinkedList;

public class GameEngine {

    private static final String [] numerals = 
        {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX" };

    //*************************************************************************

    /** 
      * converts an int to a string of roman numerals
      * @param x: integer to convert to a numeral
      * @return the string of the numeral
      */
    public static String toNumerals(int x) {
        return (x < 0 || 8 < x) ? "@@@@@@@@@@@@@@" : numerals[x];
    }

    //*************************************************************************

    /**
      * converts a roman numeral to an integer
      * @param str: string to convert
      * @return the number of the numeral
      */
    public static int fromNumerals(String str) {
        for (int i = 0; i < numerals.length; i++) {
            if (str.equals(numerals[i])) {
                return i;
            }
        }
        return -1; // this should never happen
    }

    //*************************************************************************

    /**
      * converts an int to a char A-I
      * @param x: integer to convert to a numeral
      * @return the char from the number of the column
      */
    public static char toLetters(int x) {
        return (x < 0 || 8 < x) ? 'Z' : ((char)(x + 'A'));
    }

    //*************************************************************************

    /**
      * converts a character to an integer value
      * @param ch: character to convert
      * @return the number of the column from the char
      */
    public static int fromLetters(char ch) {
        return (ch < 'A' || 'I' < ch) ? -1 : (ch - 'A');
    }

    //*************************************************************************

    /**
      * parses an input player move and returns a square from the board if it
      *   is valid 
      * @param board the board to get a square from
      * @param move a string representing a move
      * @return the square to move to
      */
    protected static Square parseMove ( GameBoard board, String move ) {
        move = move.trim();
        String [] strs = move.split("-");
        // Reject any string that has more than one "-"
        if (strs.length != 2)
            return null;   
        // Reject any string that has more than four numerals and/or more than
        //   one letter
        if (strs[0].length() > 4 && strs[1].length() != 1)
            return null;
        // Parse the string into the respective x and y coordinates
        int x = fromNumerals(strs[0]);
        int y = fromLetters(strs[1].charAt(0));
        // Reject any coordinate that is not within the game board
        if (x == -1 || y == -1) {
            return null;  
        }
        return board.getSquare ( x, y );
    }

    //*************************************************************************

    /**
      * parses a player input wall action and returns an array of two squares
      *   if the action is valid
      * @param board board to getSquares from
      * @param move the string to parse
      */
    protected static Square[] parseWall ( GameBoard board, String move ) {
        move = move.replaceAll("\\s+", "");
        // (V-A,V-B)

        // Reject any move that does not start and end with parenthesis
        if ( !move.startsWith("(") || !move.endsWith(")") )
            return null;

        String[] commaSep = move.split(",");
        // [0] == (V-A
        // [1] == V-B)

        // Make sure the string array has only 2 elements
        if ( commaSep.length != 2 )
            return null;

        // Remove parentheses
        commaSep[0] = commaSep[0].replace ( "(", "" );
        commaSep[1] = commaSep[1].replace ( ")", "" );
        // [0] == V-A
        // [1] == V-B

        String[] firstW = commaSep[0].split("-");
        // [0] == V
        // [1] == A
        String[] secndW = commaSep[1].split("-");
        // [0] == V
        // [1] == B

        // Make sure the two string arrays have only 2 elements
        if ( firstW.length != 2 && secndW.length != 2 )
            return null;

        int firstX = fromNumerals ( firstW[0] );
        int firstY = fromLetters  ( firstW[1].charAt(0) );
        // X == 4
        // Y == 0
        int secndX = fromNumerals ( secndW[0] );
        int secndY = fromLetters  ( secndW[1].charAt(0) );
        // X == 4
        // Y == 1

        // Check if the conversions returned an erroneous value
        if ( firstX == -1 || firstY == -1 || secndX == -1 || secndY == -1 )
            return null;

        // Check if the second location is to the RIGHT of the first,
        //  or if it BELOW the first
        // also make sure if horizontal, we don't place on the bottom row
        //  and make sure if vertical, we don't place on the right-most row
        if ( firstX+1 == secndX && firstY == secndY && firstY != 8 ||
             firstY+1 == secndY && firstX == secndX && firstX != 8) {
            Square[] wallSquares = new Square[2];
            wallSquares[0] = board.getSquare ( firstX, firstY );
            wallSquares[1] = board.getSquare ( secndX, secndY );
            return wallSquares; 
        }
        return null;
    }

    //*************************************************************************

    /**
      * validates a user move by checking for walls that might be obstructing
      *   the direction we want to jump to, checking if the destination is
      *   a square adjacent to the player's current location, or if the dest is
      *   adjacent to another player (who is adjacent to the player making the 
      *   move)
      * @param board GameBoard to validate a move on
      * @param currLoc the square we are checking adjacent squares from
      * @param dest the square we wish to move to
      * @param dontCheckMe flag to prevent recursing to a previous location
      * @param numJumps flag to prevent a 4th jump, inc. of player clustering
      * @return true if move is valid, false otherwise 
     */
    protected static boolean validateMove ( GameBoard board, Square currLoc, 
                                Square dest, int dontCheckMe, int numJumps ) {
        int direction = 86; // we use bit shifting to get the coordinates
        for ( int i = 0; i < 4; i++ ) {
            // This is the order in which we check for adjacencies:
            //    ITERATION         COORDINATES
            //  i = 0 -> down   |  x = 0;  y = 1
            //  i = 1 -> right  |  x = 1;  y = 0
            //  i = 2 -> up     |  x = 0;  y = -1
            //  i = 3 -> left   |  x = -1; y = 0

            // Calculate the x and y offsets
            int x = ((direction & 8)  >> 3) * Integer.signum(direction);
            int y = ((direction & 16) >> 4) * Integer.signum(direction);

            // Retrieve an adjacent square to compare
            Square checkLoc = board.getSquare(currLoc.getX() + x,
                                              currLoc.getY() + y);

            // Modify bits for the next iteration
            direction = Integer.rotateRight(direction,1);

            //It is possible to check for a square that is outside of the board
            if ( checkLoc != null ) {


                // NEEDS TESTING
                //* [Hint: remove one of the slashes to comment this piece out]
                // Confirms if there is a wall obstructing the direction we
                //   want to check for our destination
                // Note: 0 = down, 1 = right, 2 = up, 3 = left
               
                switch ( i ) {
                    // If we encounter a wall, continue to the next iteration
                    case 0: if ( currLoc.hasWallBottom() ) continue; break;
                    case 1: if ( currLoc.hasWallRight()  ) continue; break;
                    case 2: if ( checkLoc.hasWallBottom()) continue; break;
                    case 3: if ( checkLoc.hasWallRight() ) continue; break;
                    default: break; //assertion here maybe?
                }
                //


                // If checkLoc is adjacent and where we want to go...
                if ( checkLoc.vacant() && checkLoc == dest )
                    return true;
                // If the spot is occupied, this isn't our third jump, and the
                // adjacent spot to check isn't the spot we were just in, check
                // if our destination could possibly be adjacent to that player
                if ( !checkLoc.vacant() && numJumps !=3 && i != dontCheckMe  
                    && validateMove (board,checkLoc,dest,(i+2)%4,numJumps+1) )
                    return true;
            }
       }
       return false;
    } // i don't understand this and i'm grumpy about it
      // I added more comments and I can offer to explain it if you'd like :k)

    //*************************************************************************

    /** 
      * This is now the only validate method called by the game and the 
      *    move servers, it will find out if the move-string is a move
      *    or a wall placement.
      * @param board the board currently in play
      * @param player the current players turn
      * @param move the move-string sent from the game or move servers
      * @return a square array of length 1 if a move or 2 if a wall
      */
    public static Square [] validate( GameBoard board, Player player, String move) {

        // The square array to return
        Square [] validSquares;

        //Check for a move
        if(move.charAt(0) != '(') {
            validSquares = new Square[1];
            // Check the move-string to see if it is valid
            validSquares[0] = parseMove(board, move);
            if(validSquares == null) // the move-string was invalid
                return null;
            // Check to make sure the move is valid
            if(validateMove(board,board.getPlayerLoc(player),
                            validSquares[0],-1,0)) {
                return validSquares; 
            }
        }
        // Wall Placement 
        else {
            // Check to see if the player has enough walls
            if (! player.mayPlaceWall()) {
                return null;
            }
            // Check to see if the move-string was valid
            validSquares = parseWall(board, move);
            if(validSquares == null) {
                return null;
            }
            // Check to see if the wall placement is valid
            if(GameEngine.validateWall(board, validSquares))
                return validSquares;
        }

        // (Should never happen) unexpected string
        return null;
        
    }

    //*************************************************************************

    /** 
      * This will validate a wall placement it checks it make sure that there
      *   there is no wall conflicting the new wall, Will eventually make sure 
      *   that there is a valid path for all players 
      * @param board the gameboard in play
      * @param wallSquares the squares where the wall is being placed
      * @return true if the wall placement is valid, false otherwise
      */
    protected static boolean validateWall(GameBoard board, Square[] wallSquares ) {
        // Hoizontal wall
        if(wallSquares[0].getY() == wallSquares[1].getY()) {
            // If the first square is unoccupied (no walls)
            if(wallSquares[0].getWallBottom() == null &&
               wallSquares[0].getWallRight() == null) {
                // Check for intersect
                if(wallSquares[1].getWallBottom() == null) {
                    return true;
                } 
            }
            // the square has a wall but its vertical 
            else if(wallSquares[0].getWallRight() != null) {
                // Check for intersect
                if(!wallSquares[0].getWallRight().isStart() && 
                    wallSquares[1].getWallBottom() == null) {
                    return true;
                }
            }
        }
        // Vertical wall
        else
            // If the first square is unoccupied (no walls)
            if(wallSquares[0].getWallBottom() == null &&
               wallSquares[0].getWallRight() == null) {
                // Check for intersect
               if(wallSquares[1].getWallRight() == null) {
                   return true;
               }
            }
            // the square has a wall but its horziontal
            else if(wallSquares[0].getWallBottom() != null) {
                // Check for intersect
                if(!wallSquares[0].getWallBottom().isStart() &&
                    wallSquares[1].getWallRight() == null) {
                    return true;
                }
            }
        return false;
    }

    //*************************************************************************

    /**
     * find a possible winner 
     * @param board GameBoard to check
     * @param players array of players to check if they have won
     * @return a player if that player has won the game, null otherwise
     */
    public static Player getWinner(GameBoard board, Queue<Player> players) {
        // Check if there is only one player left
        if ( players.size() == 1 )
            return players.peek();

        // Check if one of the players have met the traditional victory
        // condition
        for ( Player p : players ) {
            if (p.getPlayerNo() == 0 && board.getPlayerLoc(p).getY() == 8)
                return p;
            if (p.getPlayerNo() == 1 && board.getPlayerLoc(p).getY() == 0)
                return p;
            if (p.getPlayerNo() == 2 && board.getPlayerLoc(p).getX() == 8)
                return p;
            if (p.getPlayerNo() == 3 && board.getPlayerLoc(p).getX() == 0)
                return p;
        }
        // No player has won, return null
        return null;
    }   

    //*************************************************************************

    /**
     * abstracts the functionality of moving pawns and placing walls
     * @param player the player who is about to make the move 
     * @param move a string representing a pawn move or wall placement
     * @param board the board
     */
    public static void playTurn(String move, Player player, GameBoard board) {
        Deb.ug.println("playTurn saw " + move);

        Square[] destination = GameEngine.validate(board, player, move);
        // See if this is a wall move
        if (move.startsWith("(")) {
            Deb.ug.println("playTurn: legal wall");
            assert (move.endsWith(")")); // this will be a problem if the
                                         // display client is non-conformant
            board.placeWall(destination[0], destination[1]);
            player.useWall();
        } else { // it is a player move
            Deb.ug.println("playTurn: legal move");
//             board.move(player, destination[0]);
            board.move(player, board.getSquare(move));
        }
    }

    /** should return true if there is any path from a player to their goal
     * @param player the player who is about to make the move 
     * @param board the board
     */
    public static boolean existsPath(Player player, GameBoard board) {
        // reachable keeps track of all squares that can be visited
        boolean[][] reachable = new boolean[GameBoard.COLUMNS][GameBoard.ROWS];
        for (int i = 0; i < GameBoard.COLUMNS; i++) {
            for (int j = 0; j < GameBoard.ROWS; j++) {
                reachable[i][j] = false;
            }
        }
        Square currentSquare = board.getPlayerLoc(player);
        reachable[currentSquare.getX()][currentSquare.getY()] = true;
        return existsPathRecurse(board, player.getPlayerNo(), 
                                 currentSquare, reachable);
    }

    /** inner recursive lopo for existsPath
     */
    private static boolean existsPathRecurse(GameBoard board, int pno,
                                             Square square,
                                             boolean[][] visited) {
        int x, y;
        Square [] squares = reachableAdjacentSquares(board, square);
        for (Square sq : squares) {
            x = sq.getX();
            y = sq.getY();
            System.out.println("checking square: (" + x + ", " + y + ")");
            if (pno == 0 && y == 8) {
                return true; // player 0 has reached the bottom rank!
            } else if (pno == 1 && y == 0) {
                return true; // player 1 has reached the top rank!
            } else if (pno == 2 && x == 8) {
                return true; // player 2 has reached right side!
            } else if (pno == 3 && x == 0) {
                return true; // player 3 has reached left side!
            }
            if (visited[x][y]) {
                System.out.println("been here!");
                continue;
            }
            visited[x][y] = true;
            if (existsPathRecurse(board, pno, sq, visited)) {
                return true;
            };
        }
        return false;
    }

    /** return an array containing all the squares that are reachable
     * in one step.
     * FIXME: it might make sense to add possible squares that can be reached 
     * in one step by jumping over another player, iono
     * @param player the player who is about to make the move 
     * @param board the board
     */
    public static Square [] reachableAdjacentSquares(GameBoard b, Square sq) {
        LinkedList<Square> squares = new LinkedList<Square>(); 
        int x = sq.getX();
        int y = sq.getY();

        // each square can be blocked by either of two things: borders && walls
        if (y != 0 && (! b.getSquare(x, y-1).hasWallBottom())) {
            squares.add(b.getSquare(x, y-1));
        }
        if (y != 8 && !sq.hasWallBottom()) {
            squares.add(b.getSquare(x, y+1));
        }
        if (x != 0 && (! b.getSquare(x-1, y).hasWallRight())) {
            squares.add(b.getSquare(x-1, y));
        }
        if (x != 8 && !sq.hasWallRight()) {
            squares.add(b.getSquare(x+1, y));
        }
        return squares.toArray(new Square[0]);
    }
}
