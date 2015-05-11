/* GameEngine.java - CIS405 - teams
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
 * void playTurn(String move, Player player, GameBoard board)
 *                           --> abstracts the functionality of moving pawns and placing walls
 * boolean existsPath(Player player, GameBoard board)
 *                           --> returns true if the players path to objective is possible
 * Square [] reachableAdjacentSquares(GameBoard b, Square sq)
 *                           --> returns an array of adjacent squares
 * boolean checkAllPlayersPaths(GameBoard board, Square [] wallSquares)
 *                           --> returns true if a wall placement will not block any player 
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
import java.util.LinkedList;
import java.util.List;

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
        if(strs[1].length() != 1) {
            return null;   
        }
        // Reject any coordinate that is not within the game board
        if (x == -1 || y == -1) {
            return null;  
        }
        return board.getSquare ( x, y );
    }

    //*************************************************************************

    /**
      * Parses a Player input move-String to determine if it follows the 
      * appropriate format of a Wall placement. A proper Wall placement
      * must go as follows:
      *                           (x1-y1,x2-y2)
      *
      * Where x1-y1 is the coordinate pair for the starting piece, and
      * x2-y2 is the coordinate pair for the ending piece. The x values
      * must be a roman numeral, and the y values must be a character.
      *     @param board GameBoard to retrieve Squares from
      *     @param move the String to parse
      *     @return an array of Squares that denote where a Wall can be placed 
      *     @see GameBoard
      *     @see Square
      *     @see Wall
      */
    protected static Square[] parseWall ( GameBoard board, String move ) {
        move = move.replaceAll("\\s+", "");
        // (V-A,V-B)

        // Reject any move that does not start and end with parenthesis
        if ( !move.startsWith("(") || !move.endsWith(")") )
            return null;

        // Separate the string by any commas
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

        Square[] wallSquares = new Square[2];
        wallSquares[0] = parseMove(board,commaSep[0]);
        wallSquares[1] = parseMove(board,commaSep[1]);

        if ( wallSquares[0] == null || wallSquares[1] == null )
            return null;
    
        // Check:
        //  if the second location is to the RIGHT of or BELOW the first
        //  if horizontal, we don't place on the bottom-most row
        //  if vertical, we don't place on the right-most column
        if ( wallSquares[0].getX()+1 == wallSquares[1].getX() &&
             wallSquares[0].getY()   == wallSquares[1].getY() &&
             wallSquares[0].getY()   != 8                     ||
             wallSquares[0].getY()+1 == wallSquares[1].getY() &&
             wallSquares[0].getX()   == wallSquares[1].getX() &&
             wallSquares[0].getX()   != 8                       ) 
            return wallSquares;

        return null;
    }

    //*************************************************************************

    /** 
      * Validates a given Player's move String. A valid move-String for a pawn
      * advancement must follow the format of parseMove and must be a reachable
      * location. Likewise, a valid Wall-String must follow the format of
      * parseWall and both pieces must be touching the board and not
      * intersecting another Wall.
      *     @param board the board currently in play
      *     @param player the current players turn
      *     @param move the move-string sent from the game or move servers
      *     @return a square array of length 1 if a move or 2 if a wall
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
            if(GameEngine.validateWall(board, validSquares)) { 
                    if(checkAllPlayersPaths(board, validSquares))
                            return validSquares;
            }
        }

        // (Should never happen) unexpected string
        return null;
    }

    /**
      * Validates a user move by checking for walls that might be obstructing
      * the direction we want to jump to, checking if the destination is a
      * Square adjacent to the player's current location, or if the destination
      * is adjacent to another player (who is adjacent to the player making the 
      * move).
      *     @param board GameBoard to validate a move on
      *     @param currLoc the square we are checking adjacent squares from
      *     @param dest the square we wish to move to
      *     @param dontCheckMe flag to prevent recursing to a previous location
      *     @param numJumps flag to prevent a 4th jump, inc. of player clustering
      *     @return true if move is valid, false otherwise 
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
            //  ---------------------------------
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
                switch ( i ) {
                    // If we encounter a wall, continue to the next iteration
                    case 0: if ( currLoc.hasWallBottom() ) continue; break;
                    case 1: if ( currLoc.hasWallRight()  ) continue; break;
                    case 2: if ( checkLoc.hasWallBottom()) continue; break;
                    case 3: if ( checkLoc.hasWallRight() ) continue; break;
                    default: break; //assertion here maybe?
                }
                // If checkLoc is adjacent and where we want to go...
                if ( !checkLoc.isOccupied() && checkLoc == dest )
                    return true;
                // If the spot is occupied, this isn't our third jump, and the
                // adjacent spot to check isn't the spot we were just in, check
                // if our destination could possibly be adjacent to that player
                if ( checkLoc.isOccupied() && i != dontCheckMe && numJumps < 3
                    && validateMove (board,checkLoc,dest,(i+2)%4,numJumps+1) )
                    return true;
            }
       }
       return false;
    } // i don't understand this and i'm grumpy about it
      // I added more comments and I can offer to explain it if you'd like :k)

    /** 
      * Validates a Wall placement by checking that there is no Wall existing
      * conflicting with the placement of a new Wall and that the new Wall 
      * placement does not disable a Player from establishing a path from their
      * current location to their respective goal location. 
      *     @param board GameBoard to check for placement and pathing
      *     @param wallSquares the Squares where the Wall is being placed
      *     @return if the Wall placement was successful
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
                    wallSquares[1].getWallBottom() == null && 
                    wallSquares[0].getWallBottom() == null) {
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
                    wallSquares[1].getWallRight() == null && wallSquares[0].getWallRight() == null) {
                    return true;
                }
            }
        return false;
    }

    //*************************************************************************

    /**
      * Cycles through the Player Queue to check if any Player has reached
      * their respective goal location. If only one Player remains in the
      * queue, that Player has won by default. 
      *     @param board GameBoard to check
      *     @param players Queue of Players to check if they have won
      *     @return a Player has won the game or null if no one has won yet
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

        /*TODO: could we make this a boolean and do the following?
                - return false if destination == null (the moves were invalid)
                - make a Wall move if destination.length == 2 and return true
                - make a pawn move otherwise and return true

                the display client and/or move servers can handle the case
                where this method returns false
        */
        
        Square[] destination = GameEngine.validate(board, player, move);
        // See if this is a wall move
        if (move.startsWith("(")) {
            Deb.ug.println("playTurn: legal wall");
            assert (move.endsWith(")")); // this will be a problem if the
                                         // display client is non-conformant
            board.placeWall(destination);
            player.useWall();
        } else { // it is a player move
            Deb.ug.println("playTurn: legal move");
            board.move(player, board.getSquare(move));
        }
    }

    //*************************************************************************

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

            if (visited[x][y]) {
                continue;
            }
            if (pno == 0 && y == 8) {
                return true; // player 0 has reached the bottom rank!
            } else if (pno == 1 && y == 0) {
                return true; // player 1 has reached the top rank!
            } else if (pno == 2 && x == 8) {
                return true; // player 2 has reached right side!
            } else if (pno == 3 && x == 0) {
                return true; // player 3 has reached left side!
            }
            visited[x][y] = true;
            if (existsPathRecurse(board, pno, sq, visited)) {
                return true;
            };
        }
        return false;
    }

    //*************************************************************************

    /** return an array containing all the squares that are reachable
     * in one step.
     * @param player the player who is about to make the move 
     * @param board the board
     */
    public static Square [] reachableAdjacentSquares(GameBoard b, Square sq) {
        return reachableAdjacentSquares(b,sq,-1,0);
    }

    /**
      * Retrieves all locations that are reachable from the given location
      *     @param board GameBoard to retrieve Squares from
      *     @param currLoc the Square we are checking adjacencies from
      *     @param dontCheckMe flag to prevent recursing to a previous location
      *     @param numJumps flag to prevent a 4th jump
      *     @return an array of Squares adjacent to currLoc
     */
    private static Square[] reachableAdjacentSquares ( GameBoard board, 
        Square currLoc, int dontCheckMe, int numJumps ) {

        List<Square> squareList = new LinkedList<Square>();
        int direction = 86; // we use bit shifting to get the coordinates
        for ( int i = 0; i < 4; i++ ) {
            /* This is the order in which we check for adjacencies:
                   ITERATION        COORDINATES
                i = 0 -> down   |  x =  0; y =  1
                i = 1 -> right  |  x =  1; y =  0
                i = 2 -> up     |  x =  0; y = -1
                i = 3 -> left   |  x = -1; y =  0
                --------------------------------- */
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
                switch ( i ) {
                    // If we encounter a wall, continue to the next iteration
                    case 0: if ( currLoc.hasWallBottom() ) continue; break;
                    case 1: if ( currLoc.hasWallRight()  ) continue; break;
                    case 2: if ( checkLoc.hasWallBottom()) continue; break;
                    case 3: if ( checkLoc.hasWallRight() ) continue; break;
                    default: break; //assertion here maybe?
                }
                // If the spot is occupied, this isn't our third jump, and the
                // adjacent spot to check isn't the spot we were just in, add
                // those locations to the array
                if ( checkLoc.isOccupied() && i != dontCheckMe && numJumps < 3 ) {
                    // Get the squares from the adjacent player
                    Square[] adjToPlayer = reachableAdjacentSquares(board,
                                           checkLoc, (i+2)%4, numJumps++);
                    // Add the adjacent player's squares to the list
                    for ( int j = 0; j < adjToPlayer.length; j++ )
                        squareList.add(adjToPlayer[j]);
                } 
                // just add the square to the list
                else if (i != dontCheckMe)
                    squareList.add(checkLoc);
            }
       }//---END for loop---
        return squareList.toArray(new Square[squareList.size()]);
    }
    
    //*************************************************************************

    /**
      * This method will go through each player and make sure that they
      * all have a vaild path
      * @param board the gameBoard being looked at
      * @param wallSquares the wall that will be placed to see if it blocks
      *                     any player
      * @return true if all paths exits
      */
    private static boolean checkAllPlayersPaths(GameBoard board, Square [] wallSquares) { 
        // Place the theoritcal wall
        board.placeWall(wallSquares); 

        int playerCt = 0;
        int pno = 0;
        // Go through and check all playes remaining paths
        while(playerCt != board.numPlayersRemaining()) {
            if(board.isPlayerRemaining(pno)) {
                playerCt++;
                if(!GameEngine.existsPath(board.getPlayer(pno), board)) {
                    board.removeWall(wallSquares);
                    return false;
                }
            }
            pno++;
        }

        // Remove the theorical wall
        board.removeWall(wallSquares);
        return true;
        
    }

    //*************************************************************************
    
}//---END GameEngine---

