TicTacToe
=========

The TicTacToe java game library. Supports external AI plugging. Written as an example.

TicTacToe 
---------

> Tic-tac-toe, also called noughts and crosses (in the British Commonwealth countries), 
> X's and O's (in Ireland) and X and 0 (in India) is a pencil-and-paper game for two players, 
> X and O, who take turns marking the spaces in a 3×3 grid. The player who succeeds in placing 
> three respective marks in a horizontal, vertical, or diagonal row wins the game.
— [Wikipedia](http://en.wikipedia.org/wiki/Tic-tac-toe "Tic-Tac-Toe @wikipedia")

------------------------------------------------------

Purpose
-------

Create a library in Java language which will be a good starting point for 
* implementing TicTacToe frontends, two-player boards etc.
* making an AI to play the game with users.

Solution
--------

We'd produce all the infrastructure for TicTacToe support (board, moves, rules etc.), than we
will “teach” the artifitial player by playing against itself and find “good” practices.

All the games played “with random move choice” will be stored in the data storage.

From that point on we are ready to produce the simple AI: it will analyze the collected 
previously played positions and choose the best continuation.

Tools
-----

Java and [MongoDB](http://www.mongodb.org/ "MongoDB Official Site") as backend for stored “lessons.” 
Netbeans and ANT are appreciated.

[MongoDB Java driver](http://www.mongodb.org/display/DOCS/Java+Language+Center "") is required to compile code.
MongoDB should be surprisingly installed on the target machine.
