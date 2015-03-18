commit 530bf1b63bec0f5dc5b85fe9deb87d9c0d9d9882
Merge: 0e0d014 16d8f1a
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Sun Mar 15 15:49:38 2015 -0400

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams

commit 0e0d0149115d4c964200db8c56cc4cbbf1d5a23a
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Sun Mar 15 15:47:47 2015 -0400

    fixed bugs in GameEngine.checkVictory
    
    checkVictory no longer breaks in two-player games
    also it now verifies that there are still at least two players left
    also minor refactoring

commit 16d8f1a2cfc859b5cdaf6e298b19f4d901c587b8
Author: Tylor Lehman <lehmantd197@potsdam.edu>
Date:   Sun Mar 15 15:28:00 2015 -0400

    updated TODO to show changes made.

commit cd465327287557222abcd81b22887218b4eecd69
Author: Tylor Lehman <lehmantd197@potsdam.edu>
Date:   Sun Mar 15 15:22:13 2015 -0400

    Added a gross workaround in GameBoardFrame to prevent focusing after board update.

commit 101877d850894ae9346b89b7fd3135a5903a2642
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Sat Mar 14 19:15:21 2015 -0400

    little bit of documentation to Game.java

commit f2f587b49d7acbe70bb0edaa7c9e8887161860f2
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Sat Mar 14 18:39:47 2015 -0400

    Cleaned up GameEngine slightly
    - added more syntactic sugar
    - replaced one of the error checking sending a "@@@@@@@@@@" to a null
    - to/from letters/numerals now tests all letters and numerals in the range
        as well as a few arbitrary errors

commit 07c91b596fdd7065512204b666dd48e78123627f
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Sat Mar 14 18:10:12 2015 -0400

    Added documentation to GameEngine
    
    We should consider if GameEngine.getSquare could be a private method
    instead of public

commit c0934f821c937a4a513b2cbec19fb21b04ba59b4
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Sat Mar 14 17:43:43 2015 -0400

    GameBoardTest update

commit e6e38707c950cb40784dbe76ca307f38db8d3e85
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Sat Mar 14 17:38:49 2015 -0400

    Big commit, not intended
    
        GameBoard
        - setupInitialPositio now iteratively places each player on the board
            instead of line-by-line
        - addPlayer is now only passed a player, instead of also taking an x and y
        - I think I added another ternary operation in there somewhere
    
        GameEngine
        - cleaned up validate a little bit, the old way has been deleted
    
        Test Files
        - changes have been made to test files to account for addPlayer taking
            less arguments, they'll be included in the next push

commit 05b4ba185da29cf4670bc489fe6c1e33a680b9b8
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Fri Mar 13 23:30:23 2015 -0400

    included somethings in TODO for after release 1.0

commit 237d1a04baf68ee9fd6699d7074417becaa933f9
Merge: b5cf3f5 ccbd17a
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Fri Mar 13 23:27:25 2015 -0400

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams

commit b5cf3f5c63d6854db6ba02196e1051819268fc9f
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Fri Mar 13 23:26:02 2015 -0400

    Jumping improved!
      - players can successfully jump three players
      - players can even jump when all four players are clustered!
      - GameEngine.validate should read a bit more clearly now, too

commit ccbd17a33b41f39a079d114b457b2aca72872856
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Fri Mar 13 23:11:15 2015 -0400

    improved UserServer and protocols

commit e94ee8321c66268ce446e01a05e40914225f0367
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Fri Mar 13 21:48:43 2015 -0400

    improved git's hiding of debug files

commit 2af2901ec50bb822c509e822b292c712ed3021be
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Fri Mar 13 21:47:06 2015 -0400

    added very important TODO

commit 0db420628d9f67754cd6646eb27bad598104d8b8
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Fri Mar 13 21:42:20 2015 -0400

    I wanna see stuff jump

commit 3140a8eaf0549511191c0c70e7ce780ac20eed55
Merge: 2e955f5 ff80b30
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Fri Mar 13 21:37:47 2015 -0400

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams

commit 2e955f5c34d49fca50c21c2a6e63767254b65e3b
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Fri Mar 13 21:37:22 2015 -0400

    created UserServer so we can talk to client

commit ff80b3074778017dc5e9f92a230d93e2fc5e9c9b
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Fri Mar 13 21:30:54 2015 -0400

    Added some syntactic sugar to GameBoard
    
    Jump validation is still disabled
    
    Cleaned up the documentation in GameBoard
    
    GameBoard is now mostly tested

commit 8fa2bb2dcc53b5bba260c9aa2e342af4fd3ad886
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Fri Mar 13 20:11:47 2015 -0400

    deleted game_debug
    
    also added it to .gitignore

commit d08d9eb8ed31f76a2e93bcb3abd74cc3601fd9c3
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Fri Mar 13 18:34:38 2015 -0400

    Can successfully jump another player, however code is still
      commented out in this push until it is properly tested
    
    Player moves are validated as usual for now.
    
        GameEngine.java
        - significant progress on jumping (implementation is done,
            testing is needed)
        GameBoard.java
        - getSquare now checks to ensure that we're retrieving from
            a valid array location
        - cleaned up documentation a little

commit a28c671a3c8e186f3b54157adfa255c01834e208
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Fri Mar 13 06:54:02 2015 -0400

    added to TODO to do things later

commit ed927527bf3e486894a786177e9e2a6f916abc8b
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Fri Mar 13 06:47:30 2015 -0400

    Renamed GameboardFrame to GameBoardFrame because it was bothering me
    
    All instances of GameboardFrame have been adjusted to match this change
    
    It built, so I win.

commit b7f81547ccdfdcd00a6e50a3de9123be5ff0a17e
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Fri Mar 13 06:20:20 2015 -0400

    More progress on jumping. I have the code laid out, but it's all commented
    until I can get back into the lab and test it properly.
    
    Check out what I've done, it's pretty neat! (just keep in mind it still needs
    testing with our game)
    
    DON'T TOUCH IT

commit 3d728a43e992025ec23368a30ebe8026b39018d4
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Fri Mar 13 03:50:07 2015 -0400

    Added another .equals that takes in an X and Y integer
    Made more progress on the jump thing... sorta
    Also have tests for the two .equals
    
    yyyyaaaaaaaayyy?

commit 9ffb1cc05ba288e915d54f7e6204e7a6af903031
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Fri Mar 13 03:39:15 2015 -0400

    Added .equals to Square object. This is to be used for
    validation later. It will make figuring out jumps cleaner.
    
    Also some commented-out lines showing progress for jump checking
    it's 3:40am I'm sleepy aaahahahahhahhah

commit 95e1f895b2dfe3a25eca38708f7b2fc9364193ce
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Fri Mar 13 02:55:43 2015 -0400

    sorry

commit 9091311cbe3ccbbb2eaa5f77eb03ac75dc239f34
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Fri Mar 13 02:54:28 2015 -0400

    improved build.gradle to make jar files?
    
    also tracks version

commit 41fcb9fd8468e1dd8c4d2e4d4ed586f26bea056d
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Fri Mar 13 02:54:18 2015 -0400

    added TODO file

commit 0e8ada56aee0cef4a86c1e18e43c7f6a901469bd
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Fri Mar 13 02:53:21 2015 -0400

    rewrote GameEngine.toLetters and fromLetters
    
    also wrote tests for them!

commit b66ef190f3986f15bed3c268bc01fa6691b20730
Merge: 38fdc18 717cf54
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Fri Mar 13 02:16:50 2015 -0400

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams
    
    Conflicts:
    	src/main/java/Game.java
    	src/main/java/GameEngine.java

commit 38fdc188208e68f2a8f702867f61719f16d15314
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Fri Mar 13 02:11:08 2015 -0400

    totally revamped much of Game,GameEngine,Protocol

commit 717cf54d4837396eac4e04ffad9d04a73de211de
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Fri Mar 13 01:31:40 2015 -0400

    More documentation and removal of odd spacing...
       ...I'm pretty sure I did that at some point

commit 00933b899adb049cc49b07502fe2c78445c88276
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Fri Mar 13 01:27:30 2015 -0400

    created TODO

commit 79763431df7afab8fc0294e0900cad781f0d0456
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Fri Mar 13 01:19:37 2015 -0400

    Added documentation to isActive within Player.java

commit 518757168b089264915c589a6574e9a954a3a633
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Fri Mar 13 01:17:56 2015 -0400

    Added a boolean isActive field and method to Player in order
      to tell if a player is still actively playing the game or
      not

commit 450bac67c0504d0dd86c75af5701bdbed788285c
Merge: 1615f87 0ca2084
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Fri Mar 13 01:11:31 2015 -0400

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams

commit 1615f8768af64ec6e8f113135c7c74579a2ec4e2
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Fri Mar 13 01:09:42 2015 -0400

    Removed methods, added documentation, fixed odd spacing
    
        Player.java
        - REMOVED getX()
        - REMOVED getY()
        - the above two should be retrieved from getLoc()
        - renamed setLocation() to setLoc() for consistency
        - removed some strange line spacing
        - added documentation
    
        Made changes to GameEngine and Game to reflect changes
          in Player

commit 0ca208441b7de6bd82b03b0fe7e34f03f85b7a41
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Fri Mar 13 01:06:39 2015 -0400

    renamed getColor()

commit cb79d4d4faa94259678eff5d7a3baa42ddd56de1
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Fri Mar 13 00:58:10 2015 -0400

    changed simple stuff in Player

commit ffcc24986826c79b037c2fd7537b51a2cf5b1105
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Fri Mar 13 00:41:24 2015 -0400

    continuing my protocol work

commit ecaf863bf0080ac3bace8c8118f5a7c1d1986e19
Merge: d9d50f4 c0682a5
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Fri Mar 13 00:21:15 2015 -0400

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams
    
    fixed eric's general brokenness
    
    Conflicts:
    	src/main/java/Game.java

commit d9d50f493c8671ddd9002f68d822438272da0ace
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Fri Mar 13 00:16:59 2015 -0400

    working with Protocol

commit c0682a582cb54af4199f6af92580fa3375f8ec29
Merge: 7525b72 258cf8f
Author: Eric Cavanagh <cavanaed198@potsdam.edu>
Date:   Fri Mar 13 00:15:34 2015 -0400

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams

commit 7525b72ba5b11b23011c6a6ce649a7bc98731d76
Author: Eric Cavanagh <cavanaed198@potsdam.edu>
Date:   Fri Mar 13 00:14:51 2015 -0400

    Fixes parseArgs and player_NUM

commit 258cf8f1f47d7c531970b87ff7925af74d662d13
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Fri Mar 13 00:01:27 2015 -0400

    Added documentation to GameEngine.java

commit aca8977950a23be3400c41d222cfa4dd5716c3fa
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Thu Mar 12 23:07:30 2015 -0400

    I have no idea what I changed, probably a comment somewhere. It builds,
    so I win

commit be535f065f927e7194553bf33ac11ab21a09ec1c
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Thu Mar 12 21:52:05 2015 -0400

    .gitignore now includes *# and #*

commit 8643699bbdac176590ca915dce730be65fdff616
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Thu Mar 12 21:40:38 2015 -0400

    - Game now supports up to 4 players
    - Errors are now output if 3 or more than 4 players try to connect
    - GameEngine.validate cleaned up slightly, still calls a possibly
       redundant parseMove check

commit c5f8e2519bfa6b245c944af9a9fe59450b45b6d1
Merge: 68d30e4 280dfb4
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Thu Mar 12 20:31:37 2015 -0400

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams
    
    Conflicts:
    	src/main/java/Game.java

commit 68d30e4e55aae87faf925e20a26bdde57613456c
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Thu Mar 12 20:29:33 2015 -0400

    Changed validate to check if the location is occupied. Still working on it

commit 280dfb4d1641f36fc51178913cae2d1ea4b588bf
Author: Eric Cavanagh <cavanaed198@potsdam.edu>
Date:   Thu Mar 12 19:43:37 2015 -0400

    Fixed my stupid merge errors

commit 74af5ff8699a7dcbc15ae9a80772a7ebb96cfc81
Merge: 9e00623 f4a6261
Author: Eric Cavanagh <cavanaed198@potsdam.edu>
Date:   Thu Mar 12 19:40:02 2015 -0400

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams
    
    Conflicts:
    	src/main/java/Game.java
    	src/main/java/Protocol.java

commit 9e006238f66514b2710488a5f9bc6cd16fd393ae
Author: Eric Cavanagh <cavanaed198@potsdam.edu>
Date:   Thu Mar 12 19:37:08 2015 -0400

    When a player enters an illegal move it now "Boots"
    the player still needs to remove it from the network though
    also changed it so now players 1 and 2 are in correct location, games now must have 2 players for the moment

commit f4a62614366b79603ecd92c9801ed71cf67db897
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Thu Mar 12 18:58:25 2015 -0400

    continuing work on the protocol

commit c7c5baa41e82949f06bc3472968aa2aa55459ba5
Merge: 990a15e 18e4a57
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Thu Mar 12 18:49:34 2015 -0400

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams

commit 18e4a573ed3eb292eb4d1b65540867bec779c10a
Author: Eric Cavanagh <cavanaed198@potsdam.edu>
Date:   Thu Mar 12 18:48:39 2015 -0400

    Fixed collins break

commit 990a15e899d783ab0c2b9aeeab20f49064e22ee5
Merge: 5092516 34c2ec5
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Thu Mar 12 18:48:33 2015 -0400

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams

commit 50925162a9f9a101d5cac1f2ad6897f791a9d8a3
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Thu Mar 12 18:48:23 2015 -0400

    deleted a blank line

commit 34c2ec5e350fba2dcbca185878439277ea8c065f
Merge: 7e03f96 0792fda
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Thu Mar 12 18:47:02 2015 -0400

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams

commit 7e03f9661d8cf91a88b396b366d9dc1b73a6f7b6
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Thu Mar 12 18:46:29 2015 -0400

    Fixed things SHUT UP

commit 0792fda09594c925558a48cf87279da15ee19222
Merge: 4cb785b de01371
Author: Eric Cavanagh <cavanaed198@potsdam.edu>
Date:   Thu Mar 12 18:41:32 2015 -0400

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams

commit 4cb785b16e1a8d005d18449bda53233186b14286
Author: Eric Cavanagh <cavanaed198@potsdam.edu>
Date:   Thu Mar 12 18:40:37 2015 -0400

    Added a checkVictory message and now checks for in loop for victor

commit de01371f950c6a2a997d66284f4cb28d66262db4
Merge: d145826 29419cb
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Thu Mar 12 18:39:36 2015 -0400

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams

commit d145826df7eb79ddeed366a959291a6bf4df98c3
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Thu Mar 12 18:38:39 2015 -0400

    Added a validation rule in GameEngine.java to check if the player
    will move to an adjacent square. Testing is needed.

commit 29419cb9d5cd85e3cf80dd30d3c375f790ee0dcb
Merge: 9181697 4418831
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Thu Mar 12 18:37:47 2015 -0400

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams

commit 918169716ed1be8b7e6893ca5769da978f363299
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Thu Mar 12 18:37:36 2015 -0400

    still working on protocol

commit 4418831b5a3c6397ddf4c7d0e633955f49ec9d43
Merge: 98b9357 e6928ee
Author: Eric Cavanagh <cavanaed198@potsdam.edu>
Date:   Thu Mar 12 18:11:33 2015 -0400

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams

commit 98b93572c0a032ae92e126b261576c9adfef1a59
Author: Eric Cavanagh <cavanaed198@potsdam.edu>
Date:   Thu Mar 12 18:10:36 2015 -0400

    removed stupid removing player fix

commit e6928ee80efaa1ed72141eb82d184494ec00f8b9
Merge: 75c8ed3 952f2ed
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Thu Mar 12 18:08:54 2015 -0400

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams

commit 75c8ed3391f4365e7c30ab66c630e4a6de3a93e5
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Thu Mar 12 18:08:34 2015 -0400

    started separating out protocol stuff

commit 952f2edca8d6501961a2341515cc9f088d81983b
Author: Eric Cavanagh <cavanaed198@potsdam.edu>
Date:   Thu Mar 12 18:03:44 2015 -0400

    Fixed getX() and getY() in Sqaure.java:

commit 6a44636814ee9ed91e14e691d493a3e435177bcd
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Thu Mar 12 17:47:59 2015 -0400

    forgot to add the test file

commit 7d83dc8f858c80f4664bb9eb73a2a36a260fdc04
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Thu Mar 12 17:46:04 2015 -0400

    many changes, most notably changed parseMove
    
    there are now two steps to move validation:
        check formatting of the string
        check legality of move
    
    also other small things

commit 082614624439c6652b164ab607798a977b6add9f
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Thu Mar 12 15:43:45 2015 -0400

    Made changes to:
    
        GameEngine
        - this now uses the array.length for iterations
        - numeral X has been removed, we only need I through IX
        GameboardFrame
        - adjusted the top labels to account for the numerals array
           no longer starting with the empty string
        GameEngineTest
        - adjusted to reflect changes in GameEngine
        tips
        - added a CLASSPATH tip, thanks Dan!

commit 559fe5722d34072f94fdb0d124c484a386ae9ba6
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Thu Mar 12 14:58:27 2015 -0400

    Modified GameEngine and GameEngine test to properl return array indices
    
    ex: 'A' returns 0, instead of 1
        "III" returns 2, instead of 3

commit a9e8ab69c338ad7355ed556ada63ff6531d5b206
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Thu Mar 12 13:52:51 2015 -0400

    Reverted parseMove to old changes, will look into a better fix
    
    Also cleaned up Game.java to read better. Will look into fixing
    the removePlayer problem.

commit 5a2ffd8d426060158b5164860a2be8905559b8d6
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Thu Mar 12 00:42:46 2015 -0400

    ~~ please do a gradle build before you push ~~
    
    the code currently doesn't pass a test, so whoever broke
    it needs to fix it soon! the test might be wrong, fyi.
    
    this commit is still broken, :(
    
    anyway, the GameEngine is not an object to be created,
    more of a library with useful functions.
    
    also, the arguments passed to Game are of the form
    <host> <port> ...
    
    so there are actually twice as many args as there will be players.

commit ddb8fcd5792d24b34014f4ca65810f4cce4420a5
Merge: 52aa959 e3741de
Author: Eric Cavanagh <cavanaed198@potsdam.edu>
Date:   Wed Mar 11 16:22:51 2015 -0400

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams
    
    Conflicts:
    	src/main/java/Game.java

commit 52aa9596dc9ab7f866a0f6b0ee67c4bfc5b3d8dc
Author: Eric Cavanagh <cavanaed198@potsdam.edu>
Date:   Wed Mar 11 16:02:36 2015 -0400

    Made changes to Game to move players on board

commit e3741de08ec7624ad54ef8377aee892654eea035
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Wed Mar 11 15:59:55 2015 -0400

    responses from the client

commit f375a7f68d92957d7efe8ce9ce2029b0e352f358
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Wed Mar 11 15:49:18 2015 -0400

    added hostnames to argument parsing

commit 80fa6a1001ef7c83af379bfe7577ae01df82f3aa
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Wed Mar 11 15:23:46 2015 -0400

    made Game.java able to cycle through servers

commit abbcd4071ade1bbde95ae1ce31fd0db67d370895
Merge: 31ad835 4b31630
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Wed Mar 11 14:49:35 2015 -0400

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams

commit 31ad835c65aed6dfa843ccd1a6aed326cbda7446
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Tue Mar 10 20:45:57 2015 -0400

    updated GameEngine

commit 4b316308b768b0fbbd334b2a60aa073f6f8a6bc2
Author: Scott Forbes <forbesst196@potsdam.edu>
Date:   Tue Mar 10 20:08:57 2015 -0400

    Can move player in Demo
    
    Works now till I can figure out how to use arrows instead.
    In the terminal use:
    8=up, 6=right, 2=down, 4=left.

commit 4e93008e163788f71cc1ace2b90bff6755cb8a0f
Author: Scott Forbes <forbesst196@potsdam.edu>
Date:   Tue Mar 10 19:22:30 2015 -0400

    Pawn movement in Demo changed
    
    I changed how the pawn moves in the demo. Now moves in a straight line like an actual player.

commit 023964de7d0a9d447a9c3e11f6d02ca1d00b05e8
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Tue Mar 10 19:13:26 2015 -0400

    made tips better one last time

commit f42da704fd1fed45820391d3ef5a53e01a7880ce
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Tue Mar 10 19:02:34 2015 -0400

    fixed bug in tips file

commit 4f267d4a7b53a844ae897b7da825da1161ce2541
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Tue Mar 10 18:56:23 2015 -0400

    made a tips file so we can help each other out!

commit e55bdeacbfcffce8a1abddde651d780f5b453715
Merge: 814542e 5b255dc
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Tue Mar 10 18:31:47 2015 -0400

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams

commit 814542eda98ff07f11bb43ef0e33da0b70711b9b
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Tue Mar 10 18:31:12 2015 -0400

    Added "pseudocode" comments to Game.java. No changes are set in stone

commit 5b255dcf5312e25c72b7aa187400a6108e32705b
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Tue Mar 10 18:30:16 2015 -0400

    tiny changes to GameEngine, removed pointless main
    
    Main.java was literally only a hello world program anyway

commit 04e13d90b65e922cd1414c7561e9a5b0e918ae44
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Mon Mar 9 17:48:36 2015 -0400

    added parseMove in GameEngine
    
    also wrote a test and made a number of other small changes
    look at parseMove though, it's exciting!!!

commit afc971cf29c32c0322c276063923342174043c95
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Mon Mar 9 16:36:04 2015 -0400

    removed old, obsolete classes
    
    and all references to them
    
    deleted:    src/main/java/Fence.java
    deleted:    src/main/java/Pawn.java
    deleted:    src/main/java/Point.java
    deleted:    src/test/java/PawnTest.java

commit 9832e1845a7ff425fd48c9ad8da6d711e834bcaf
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Mon Mar 9 16:29:23 2015 -0400

    moved GameBoardTest to gradle tests directory
    
    now it is always run when you build gradle. it also removes testing from our
    core codebase

commit 8f87d51061b16e6f155afcfab2cf913f8b9a7ae1
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Mon Mar 9 16:24:35 2015 -0400

    fixed indentation in several files

commit 807e9925b162c9d3cf0d0c33c00374ee445dee8b
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Mon Mar 9 16:07:57 2015 -0400

    tested toNumerals and fromNumerals

commit aa558e978c1866354699e6bddfbe7d57df1b16c6
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Mon Mar 9 15:53:24 2015 -0400

    demonstration of netcat

commit 43af6f2cb481ed540e063afcccf621f1ff4c6ee7
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Mon Mar 9 15:49:18 2015 -0400

    sorry, forget to test on last change. fixed it!

commit 2aab29096863dbd2793170758f1207fd98174ea8
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Mon Mar 9 15:48:21 2015 -0400

    renamed stuff in GameEngine

commit bda7a9312ab1edb182a4b31a49b7dd8b654ab8f8
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Mon Mar 9 15:43:51 2015 -0400

    changed numerals and antinumerals in GameEngine

commit 8eb56ededa62252ed98c131cc1e8e30a2f312b12
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Mon Mar 9 15:29:36 2015 -0400

    added broken antiNumerals

commit 895fb4d456e2676d02866fe79b882f3c75ac8bdc
Merge: f58f293 f636e8a
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Mon Mar 9 15:24:57 2015 -0400

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams

commit f58f293a0d1a0a0a2df16200e51aac906426cdd8
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Mon Mar 9 15:24:41 2015 -0400

    sorry, i had to commit these to pull
    
    i don't know if they work

commit f636e8a3473a670a8a977a777772aa49efea5fdc
Author: Tylor Lehman <TylorLehman@gmail.com>
Date:   Mon Mar 9 13:38:11 2015 -0400

    Made it so GameboardFrame can print every cell of the board correctly.
    Changes are marked in the file.
    It was a problem with the arrays starting in different locations.
    Demo now prints a player in every cell of the array sequentially.
    Also Montana spelled a thing wrong.

commit 2e509f498e4783435c81adbee3f29a65f85eadd3
Author: Tylor Lehman <TylorLehman@gmail.com>
Date:   Mon Mar 9 13:15:27 2015 -0400

    Fixed Walling's spelling errors

commit bd5477749748a4260c2d2168c2e55beb97167f1d
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Sun Mar 8 23:11:14 2015 -0400

    removed older version of topLayer method

commit baef532ab1fa3b5e93bf674b28631bc4029a4171
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Sun Mar 8 17:48:04 2015 -0400

    fixed SquareTest to make the code build!
    
    our constructor had changed but our tests had not

commit 958b188842d86e3f51f69e45826c792b71c01497
Author: Tylor Lehman <TylorLehman@gmail.com>
Date:   Wed Feb 25 15:38:07 2015 -0500

    added getRow and getCol functions to Square class

commit 250d90e2dfc246b2409fd2d0fdb02338df3c781e
Author: dan <soucydi194@potsdam.edu>
Date:   Wed Feb 25 15:15:47 2015 -0500

    square knows its location on the board
    
    the square constructor takes its x and y location as parameters now.
    this change is pretty untested but i hope is fine

commit f2fd90b9108f26936d2da13cc18a1a11b683da9f
Author: dan <soucydi194@potsdam.edu>
Date:   Wed Feb 25 14:56:59 2015 -0500

    _UNTESTED_ refactored code in GameboardFrame into a loop
    
    please note that i do not have gradle installed on this computer
    so i have not extensively tested this change (but it is minor)

commit e7ea82fcbc2efdb4fbf471834079b5b38f1d476f
Author: Tylor Lehman <TylorLehman@gmail.com>
Date:   Wed Feb 25 13:59:42 2015 -0500

    Re-added removePlayer method to gameBoard. I hope nothing breaks. :(

commit 1205c8c9f922935fbf7bf05d82f3b05a59fde51f
Author: Tylor Lehman <lehmantd197@potsdam.edu>
Date:   Wed Feb 25 13:51:01 2015 -0500

    Added a demo file to main to show pawns moving around our gameboard

commit e183a137476ea09f4dd354c99810952bb2216df5
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 23 17:37:25 2015 -0500

    added the methods getName() and getColor()

commit b81bd0c8afa602f69054616f534a7a72aa48df74
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 23 17:36:54 2015 -0500

    now draws players different colors

commit 25e8567ef87f3d047e796ade657ef4dd81d96118
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 23 17:36:31 2015 -0500

    tests tht all players print different colors

commit 921c519a7f9d573087a6dcf5bc71c40b97b1b399
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 23 17:25:21 2015 -0500

    added the update method

commit 6503e6f249203548a31fd7e24b8e4ae3023d6e0a
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 23 17:24:44 2015 -0500

    added a test for the update method - ran successfully!!

commit 45bdd851fc7001447e7e014eabc92675152d4ca8
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Mon Feb 23 16:18:31 2015 -0500

    created crude GameEngine class

commit b1ac283b8192200cb14a63bb40d7be424648c781
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Mon Feb 23 16:14:25 2015 -0500

    fixed minor indentation problems

commit b7e91828b4c9dcb1131390dbb25e55f933013f17
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Sun Feb 22 22:36:01 2015 -0500

    tyler.txt

commit 695b4219505a5aa0d4a777f5b9f1f54b1f4d256c
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Sun Feb 22 22:34:35 2015 -0500

    added PlayerTest.java

commit c73f3c610614190cfb2fb6c59c2725e9c834e4b4
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Sun Feb 22 22:34:18 2015 -0500

    added (comments mostly) to Square.java

commit 334a4b89ca78ecdde098a1d7f3c7989ccec8eb01
Author: dansoucy <soucydi194@potsdam.edu>
Date:   Sun Feb 22 22:33:46 2015 -0500

    added to SquareTest.java

commit 8799c151d660870800ebc4850108d021be384535
Author: Daniel Soucy <soucydi194@potsdam.edu>
Date:   Sun Feb 22 22:25:42 2015 -0500

    added vacant method to square class

commit 787692f93ccbe74ddb755a8a3dd1d56ecdeffa83
Author: Daniel Soucy <soucydi194@potsdam.edu>
Date:   Sun Feb 22 22:25:23 2015 -0500

    added more tests to SquareTest

commit 10a851551f8af4df12dad728e1aefb22ef11e95e
Author: Daniel Soucy <soucydi194@potsdam.edu>
Date:   Sun Feb 22 22:19:08 2015 -0500

    added more to SquareTest.java

commit bdc75de6465875b79cada1ad6b3fc652987a33a9
Merge: 0f0c0b3 a1b7506
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Sun Feb 22 22:12:14 2015 -0500

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams

commit 0f0c0b3597908677d88543a97bce67d5405cbf7e
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Sun Feb 22 22:11:33 2015 -0500

    SquareTest was not uploaded with last push, re-comitting it

commit a1b750623d9432eb1ed8da68962dfb1ba69e1f8c
Author: Daniel Soucy <soucydi194@potsdam.edu>
Date:   Sun Feb 22 21:57:02 2015 -0500

    i made our first test!

commit 0446821f03cc19884027b769afbb507e1868870f
Merge: 403697a 15727b4
Author: Daniel Soucy <soucydi194@potsdam.edu>
Date:   Sun Feb 22 19:46:29 2015 -0500

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams
    
    Conflicts:
    	src/main/java/Square.java

commit 15727b4b5f4cfc329532a20f19f77dcf901785de
Author: Tylor Lehman <lehmantd197@potsdam.edu>
Date:   Fri Feb 20 17:05:29 2015 -0500

    "Fixed" GameBoard to return false instead of throwing an exception. Need a more permanent solution.

commit 5a39b27de01a4ab6b06ee1ee01d29ae5c19fd100
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Thu Feb 19 19:53:49 2015 -0500

    Added methods, made modifications and performed tests
    
        Methods Added:
            Square.java (getVertWallStatus, getHorzWallStatus)
            Wall.java   (getIsStart)
    
        Modifications:
            Player.java (changed byte fields to int)
    
        Tests:
            src/tests/java/SquareTest
                can add and retrieve players and walls as well as
                remove a player from the square

commit 6e3318846a3e5a86cb490d2c6c371c7be862d824
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Tue Feb 17 20:15:45 2015 -0500

    Added \*~ to .gitignore

commit 8256822cfceb8d58f2abaec69616a912813d8e25
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Tue Feb 17 20:08:19 2015 -0500

    Documentation, methods, removals
    
    Added documentation to:
        GameBoard.java
        Player.java
        Square.java
        Wall.java
    
    Added methods to:
        GameBoard.java (removePlayer, getBoard(might not be needed))
        Square.java    (removePlayer)
    
    Deleted:
        redundant teams directory within the teams.git root
        files with ~ appended to them; will add this to .gitignore
            in next push

commit a03eeae2507ccec97ff603bd248262abeddc1600
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Tue Feb 17 19:04:22 2015 -0500

    Added my "Trying things out" directory to the .gitignore

commit f10a06c4096803bbed0a56ed3bd505ef710ef651
Author: Eric Cavanagh <cavanaed198@potsdam.edu>
Date:   Tue Feb 17 18:06:33 2015 -0500

    Added Comments and cleaned up GameBoard.java

commit 8d4f6ff69d047b2ad00f74dea9b036c9008f6765
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 16 19:02:26 2015 -0500

    addplayer();

commit 06373b0c9e27e55a088f63baed1fe6167af80f2b
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 16 19:01:59 2015 -0500

    adds a player

commit 76cbff76dbeea13c9de96182a072fd086b2e5dbc
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 16 19:01:22 2015 -0500

    works!! changes occupied squares red

commit 94e68edc2c7516d565b808c69420bea64086db0e
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 16 19:00:37 2015 -0500

    initialized squares and addplayermethod works

commit c4b54b1e8ace174355e25ac600656f54d8896973
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 16 18:52:22 2015 -0500

    getLoc() returns a square

commit d459fd07388946f0388547cc5d035949fd9536c0
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 16 18:51:59 2015 -0500

    trying to build a game to test...

commit a451c572acea1cded745966b15be33243dec9eff
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 16 18:50:24 2015 -0500

    builds board. turns player squares red

commit 0a5eb1c4736e8ef61a46fe7c0d651ecd49d6dadd
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 16 18:49:14 2015 -0500

    getSquare();
    addPlayer(Player p);
    getPlayeratLoc(int x, int y); returns a player

commit f32173d9a7c04d35abf4c73bb2b8e41950611350
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Mon Feb 16 18:37:31 2015 -0500

    Fixed things SHUT UP MONTANA

commit c4e9209d78f1db3613aa6a7a3fc39d9a9aa0f77f
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 16 18:29:20 2015 -0500

    fix bug

commit 07f50c5daf43730b0de6ad3327cb9f4c885ae3dd
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 16 18:27:25 2015 -0500

    switched Pawn with Player

commit dbce0235c96812666021566ef2ff6196ce2560f7
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 16 18:25:59 2015 -0500

    added getPlayer();
    returns occupying player

commit 3eb4195ace283bba62482a6739c46adab68f2786
Merge: 6ee5b4c 5cf5be9
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Mon Feb 16 18:17:12 2015 -0500

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams
    
    Conflicts:
    	src/main/java/Square.java
    	src/main/java/Wall.java

commit 6ee5b4c18ff58706fdc01dcb979c920ac7b1dcb8
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Mon Feb 16 18:08:43 2015 -0500

    Modified Square.java to have Walls
    
    Added Player.java to contain player information
    Added Wall.java to act as a wall on a square
    
    Removed Pawn.java, its use has been combined with Player.java

commit 5cf5be9993957d459205001ab291445bec10835a
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 16 18:04:14 2015 -0500

    added a constructor, parameters: identifier and color (ints)

commit 35be417f2a550dd0ba51cfb3bec6f8d06d5d684d
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 16 18:01:36 2015 -0500

    added a getPawnatLoc method

commit aa8e4556f9841e4b04423c3b5ffc092718e3bc99
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 16 17:54:11 2015 -0500

    added a field to control color of pawn
    	used by Frame

commit 99dcabd6289be1b0d305ae6653add70119aa3c31
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 16 17:52:05 2015 -0500

    added a call to GameBoard into the constructor

commit 614791bbbff1000077e6fdb975d0da55df4dcedb
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 16 17:51:15 2015 -0500

    now has rows and collumns labeled and
    needs Gameboard object to construct

commit 69f1588a449fa0adbabbb52ff83b769365244fe4
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 16 17:14:23 2015 -0500

    wall class
    field: boolean isStart
    constructor: parameter: start boolean

commit 6c961157363fe9215119b5572231164a33493bec
Merge: 6d32be4 f022e6a
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 16 17:10:37 2015 -0500

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams

commit 6d32be473bf6b18d383b6570f9c983a3ed243a32
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 16 17:09:39 2015 -0500

    created class
    	-constructor pass in boolean start. (start = true; end = false)

commit 2b80b8da53dc55924c98bfbc310804acb415dcff
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 16 17:09:08 2015 -0500

    added horizontal and vertical wall pointers to fields

commit f022e6ab33294e676a98aebb38953ad7f327b5ce
Merge: 4b618aa 9cc1331
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Mon Feb 16 17:07:25 2015 -0500

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams

commit 4b618aa42ddbf6b032f091d263b235451a3a4c6f
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Mon Feb 16 17:06:59 2015 -0500

    Commit GameBoard to see how merging works

commit 9cc13312543813a566139f1778de83552a64ab2c
Merge: b7453c7 edb6ffe
Author: Eric Cavanagh <cavanaed198@potsdam.edu>
Date:   Mon Feb 16 17:04:01 2015 -0500

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams

commit edb6ffe438b55ff33ceccc474774534db6df9a09
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Mon Feb 16 17:02:50 2015 -0500

    now does not have buttons

commit b7453c74fadce9e3e17027b0ddb2ee90dba08bfe
Merge: e478f86 df7fc3a
Author: Eric Cavanagh <cavanaed198@potsdam.edu>
Date:   Mon Feb 16 17:01:44 2015 -0500

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams
    
    Conflicts:
    	Checkin.txt
    
    Added IsValidLoc() to gameboard.java

commit 0404c97449e2fc021f7f9feac50dbf004a9404ab
Author: Tylor Lehman <TylorLehman@gmail.com>
Date:   Thu Feb 12 16:35:37 2015 -0500

    Changed tyler text to see if I can push from my laptop

commit 403697a5db1d85c37c986737a36905632d137159
Author: Daniel Soucy <soucydi194@potsdam.edu>
Date:   Wed Feb 11 18:49:56 2015 -0500

    added classes for fence and point

commit 46e916c29a88cde998313162db691a8f2255b537
Author: Daniel Soucy <soucydi194@potsdam.edu>
Date:   Wed Feb 11 18:42:46 2015 -0500

    added Game class, tracks the game's state

commit a7eb20aa091c3c6ba538e6bd253cdac65a3d5e6b
Author: dan <soucydi194@potsdam.edu>
Date:   Wed Feb 11 15:29:20 2015 -0500

    finished moving code into src/
    
    apparently last time i only made a copy of the files instead of moving them.
    oh git, you so silly.

commit df7fc3a2f21e9a1ddff258e1733dea4e974354b9
Author: Daniel Soucy <soucydi194@potsdam.edu>
Date:   Wed Feb 11 13:46:47 2015 -0500

    moved java code into src/ directory

commit dac81674cf9e6af1dd310472e032fc01d408c449
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Tue Feb 10 18:54:17 2015 -0500

    basic gui

commit 9280f916446115ad3bae702cafed1379cfbd8ad0
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Tue Feb 10 18:53:59 2015 -0500

    test file for gui

commit 864e255096ac9bb73a125a4917cda1b0f27e6c9e
Author: Scott Forbes <forbesst196@potsdam.edu>
Date:   Mon Feb 9 18:34:58 2015 -0500

    First Commit
    
    Adding name to Checkin.txt text file. To show that I can push.

commit c98988f19b961417038d9df60357dfa3865fcb19
Author: Daniel Soucy <soucydi194@potsdam.edu>
Date:   Mon Feb 9 15:03:37 2015 -0500

    deleted tylEr's junk

commit c66170f5fec622ad9138d87b1ee506934684db60
Author: Tylor Lehman <lehmantd197@potsdam.edu>
Date:   Mon Feb 9 13:49:35 2015 -0500

    checked in as Tylor :)

commit e478f86f102ce810907572eb2fbfca7b764b2fb3
Author: Eric Cavanagh <cavanaed198@potsdam.edu>
Date:   Mon Feb 9 13:05:54 2015 -0500

    Added name to Checkin.txt

commit 4a6ad3bc1f3e59c7dd615561cf17591b5fda6275
Merge: 50debc1 1078251
Author: Tylor Lehman <lehmantd197@potsdam.edu>
Date:   Mon Feb 9 12:57:31 2015 -0500

    Merge cs-devel.potsdam.edu:cis405/teams

commit 1078251b1f39ccaa10243fb389e6172321f6e295
Author: Montana Earle <earlems197@potsdam.edu>
Date:   Sun Feb 8 13:50:50 2015 -0500

    added name

commit 50debc154594973aec6672abd2822797998fc7f0
Author: Tylor Lehman <lehmantd197@potsdam.edu>
Date:   Fri Feb 6 14:05:13 2015 -0500

    did it again. :( changed tyler.txt
    `

commit c2234e97bb5002d22d15dc832d00945d755593c5
Author: Tylor Lehman <lehmantd197@potsdam.edu>
Date:   Fri Feb 6 14:00:22 2015 -0500

    changed the Tyler file

commit 9ca1f4a4c7583c759a072e0e8051815ee81f5c8b
Author: Tylor Lehman <lehmantd197@potsdam.edu>
Date:   Fri Feb 6 13:24:16 2015 -0500

    *Tylor* WAS here and knows how to spell his name. :)

commit 5cadad5db0e2f46b3ce363e3f19391a0e295b6a2
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Thu Feb 5 18:55:25 2015 -0500

    dan signed in

commit b17157fcc51b348e96ff8c63011828715b11b192
Author: Daniel Soucy <soucydi194@potsdam.edu>
Date:   Thu Feb 5 18:52:55 2015 -0500

    checking in

commit 2962ae7e750d6d820501d0a91363c904f33a773d
Merge: f09ee7a ce94900
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Thu Feb 5 18:52:05 2015 -0500

    Merge branch 'master' of cs-devel.potsdam.edu:cis405/teams

commit f09ee7ae9341f046495f0e09d816827b57bf0ec0
Author: Collin Walling <wallincl198@potsdam.edu>
Date:   Thu Feb 5 18:50:39 2015 -0500

    Added a checkin file. Please check in

commit ce9490092942f11941def7333a9c538243d43569
Author: Daniel Soucy <soucydi194@potsdam.edu>
Date:   Thu Feb 5 18:49:17 2015 -0500

    deleted dumb .gradle/ stuff, hope we don't need it

commit 3c4342028239341ceeebea7ac16fddab4acac109
Author: Daniel Soucy <soucydi194@potsdam.edu>
Date:   Thu Feb 5 18:46:28 2015 -0500

    ignoring .gradle/

commit 79632eed415c4f717c3846c999ac18bf958a7d5b
Author: Daniel Soucy <soucydi194@potsdam.edu>
Date:   Thu Feb 5 18:34:28 2015 -0500

    added readme

commit 1618539c76c0e9425dc30a759de2326fc2ab139e
Author: Daniel Soucy <soucydi194@potsdam.edu>
Date:   Thu Feb 5 18:23:06 2015 -0500

    added very import .vimrc

commit ed490f23a4e86ff6cf47252ca8c1d635328218fa
Author: Daniel Soucy <soucydi194@potsdam.edu>
Date:   Thu Feb 5 18:10:54 2015 -0500

    included more necessary gradle stuff (.gradle/)

commit 6b07ee24305d3a8c9ce5188f891fa18bda69e5d5
Author: Daniel Soucy <soucydi194@potsdam.edu>
Date:   Thu Feb 5 18:02:09 2015 -0500

    did more work on square, gameboard

commit d5cf162a13eac544d75602523e9427a22654e75a
Author: Daniel Soucy <soucydi194@potsdam.edu>
Date:   Thu Feb 5 17:49:23 2015 -0500

    added reverse stuff

commit 5fd22d93111f32401a45ff1a8e33a8ded67af0c2
Author: Daniel Soucy <soucydi194@potsdam.edu>
Date:   Thu Feb 5 17:13:03 2015 -0500

    added a trivial pawn class

commit a923c7573f049c796cf6da8f0c4ebbd0809ccc39
Author: Daniel Soucy <soucydi194@potsdam.edu>
Date:   Thu Feb 5 17:09:02 2015 -0500

    added crude square, gameboard classes

commit 525638d3c81ac078301d903490b78d7284a8f704
Author: Daniel Soucy <soucydi194@potsdam.edu>
Date:   Thu Feb 5 17:02:28 2015 -0500

    improved gradlew script
    
    i'm trying to make it run the same regardless of the
    directory you invoke it from, but no luck so far

commit 8c4824c9b706a84b14dda7b108fa1cc14970ba01
Author: Daniel Soucy <soucydi194@potsdam.edu>
Date:   Thu Feb 5 16:15:36 2015 -0500

    added .gitignore

commit 5378548e2c7cb48f99eeacf2350388359d773f90
Author: Daniel Soucy <soucydi194@potsdam.edu>
Date:   Thu Feb 5 16:15:06 2015 -0500

    added hello world demo

commit eda473bad22a730fddaf689c6ee08499c9f31bf2
Author: Daniel Soucy <soucydi194@potsdam.edu>
Date:   Thu Feb 5 16:11:12 2015 -0500

    added gradle stuff

commit 00b3b6cbd9ff07acd7c220830baf129d3317b11b
Author: Daniel Soucy <soucydi194@potsdam.edu>
Date:   Thu Feb 5 16:04:40 2015 -0500

    added tyler.txt

commit 1919f88d15a1ac94141175f5f96cb9bba2956640
Author: Collin <collin.walling@gmail.com>
Date:   Thu Feb 5 15:54:55 2015 -0500

    Basic file so I can push this
