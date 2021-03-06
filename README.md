Contributors
============

* Eric Cavanagh            - Testing & AI
* Montana Earle            - Graphics & AI
* Tylor Lehman             - Graphics
* Dan Soucy (ninedotnine)  - Back end & Networking
* Collin Walling (fuzzn8r) - Back end & AI

How to run the game
===================

    1. Build all class files with `./gradlew build`

        *note: see TIPS for gradle help


    1. Navigate to ./build/libs


    2. Start up at least two MoveServers (in different terminals), give
         them unique port numbers and a user type

            java -cp quoridorscmoridor-3.3.jar MoveServer 1234 user

        *note: you may have permission issues with this file. run
               chmod +x quoridorschmoridor-3.3.jar and voila!

         ========================================================
         =                   VALID USER TYPES                   =
         ========================================================
         = user         --> human player                        =
         = lr           --> simple AI that moves left and right =
         = rd           --> simple AI that makes random moves   =
         = fcrd         --> flip coin roll dice                 =
         = hal          --> 'intelligent' AI                    =
         ======================================================== 


    3. Run Game in another terminal. Give it the port-host of the
         participating MoveServers
                
            java -cp quoridorscmoridor-3.3.jar Game localhost:1234 \
                localhost:5678

         You can also start a 4-player game using a similar setup

                ... localhost 1234 Whiteface 5678 Gothics 9012 \
                    Redfield 3456

         *note: the \ just means to continue the command onto the
                next line. You do not need to do this.


    4. Play the game! You have to input moves via the terminal that is
         running the respective MoveServer

         An example move for player 0 is V-B (moves down)

         An example wall for any player is (IV-D,V-D), which places
         a horizontal wall starting at IV-D and ending at V-D

         Use the labels printed on the graphic display to guide your
         movements
