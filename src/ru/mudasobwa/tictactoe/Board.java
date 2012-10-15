package ru.mudasobwa.tictactoe;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The implemenation of {@code Board} of TicTacToe game.
 * This class is responsible for game rules (e. g. board's dimension
 *    and the {@link Game} behind).
 * 
 * It as well determines the current state of the game, allows making moves,
 *    random moves and “playing” game in non-stop random manner. The latter
 *    is necessary to train callers (if any) to find the best next move.
 *
 * The winner is determined by collectioning the amount of same “symbols”
 *    on all the rows, columns and both diags.
 * 
 * @author Alexei Matyushkin
 */
public class Board {

  /** Surprisingly this is a logger for logging purposes. */
  static final Logger LOG = Logger.getLogger(Board.class.getName());
  static {
    LOG.setLevel(Level.INFO);
  }
  
  static final int dimension = 3;

  protected final Game moves = new Game();

  /** Cache for the match result.
   *  First index refers to whether the move belongs to the first player,
   *  second index is for vertical/horizontal and 
   *  the third is for row/column number.(plus one holder for diag)
   * 
   *  Integers are initialized to zeroes, hence no further init is needed
   */
  int [][][] moveResult = new int [2][2][dimension + 1];

  /**
   * Get the value of dimension
   *
   * @return the value of dimension
   */
  public static int getDimension() {
    return dimension;
  }
  
  public Game getGame() {
    return this.moves;
  }
  
  public boolean isValidMove(final Move m) {
    return moves.isValidMove(m);
  }
  
  public boolean move(final Move m) throws IllegalMoveException {
    if (!isValidMove(m)) {
      throw new IllegalMoveException(m);
    }
    // We have no capacity-restricted Deque, therefore it's not dangerous
    moves.addLast(m);
    int moveOwner = (moves.size() - 1) % 2; // 0 if first player, 1 otherwise
    moveResult[moveOwner][0][m.h] += 1; // count horz
    moveResult[moveOwner][1][m.v] += 1; // count vert
    if (m.h == m.v) { // straight diagonal
      moveResult[moveOwner][0][dimension] += 1;
    }
    if (m.h + m.v == dimension) { // backward diagonal
      moveResult[moveOwner][1][dimension] += 1;
    }
    // Ugly trick :-)
    // Built-in convert array to string and search for occurences
    //   of <code>dimension</code>, as it means that there are N-in-line
    if (Arrays.deepToString(moveResult).matches("(.*)[\\[\\s]" + dimension + "[,\\]](.*)")) {
      moves.setWinner(moveOwner == 0 ? Game.Winner.O : Game.Winner.X);
    }
    return 
        (moves.size() == dimension * dimension) || // drawn game
        moves.hasWinner(); // we have a winner
  }
  
  /** The real methos to be called for moves */
  public boolean move() {
    while (true) {
      try { 
        return move(new Move()); 
      } catch (IllegalMoveException e) {
        LOG.log(Level.FINEST, "Skipped illegal move: [{0};{1}]", new Object[]{e.m.h, e.m.v});
      }
    }
  }
  
  Game play() {
    while (!move()) {
      if (LOG.isLoggable(Level.FINE)) {
        Helper.draw(moves);
      }
    }
    if (LOG.isLoggable(Level.INFO)) {
      Helper.draw(moves);
    }
    return moves;
  }

}
