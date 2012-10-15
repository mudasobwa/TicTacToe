package ru.mudasobwa.tictactoe;

import java.util.Random;

/**
 * The implementation of one move in the TicTacToe game.
 * Move has coordinates (horizontal and vertical) on the board.
 * This class is responsible for creation of random moves within board
 *   boundaries as well and bounds check for coords values when given.
 * 
 * @author Alexei Matyushkin
 */
public class Move {
  /** horizontal posision */
  public final int h;
  /** vertical posision */
  public final int v;

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 47 * hash + this.h;
    hash = 47 * hash + this.v;
    return hash;
  }

  @Override
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }
    if (! (other instanceof Move)) {
      return false;
    }
    Move m = (Move)other;
    return m.h == this.h && m.v == this.v;
  }

  /**
   * Creation of <code>Move</code> instance, initialized with <i>random</i> values.
   */  
  public Move() {
    Random r = new Random();
    this.h = r.nextInt(Board.dimension);
    this.v = r.nextInt(Board.dimension);
  }
  
  /**
   * Creation of <code>Move</code> instance, initialized with given values.
   * @param h the horizontal coord of the move
   * @param v the vertical coord of the move
   * @throws ArrayIndexOutOfBoundsException if the given value(s) for coords
   *    are out of board bounds
   */
  
  public Move(int h, int v) throws ArrayIndexOutOfBoundsException {
    if (h < 0 || h >= Board.dimension || v < 0 || v >= Board.dimension) {
      // FIXME report wrong value precisely (or even reset to boundaries)
      throw new ArrayIndexOutOfBoundsException( 
        // FIXME I18N !!!!
        "The index of move + [" + h + "; " + v + "] + should be in range [0; " + Board.dimension + "]!"); 
    }
    this.h = h;
    this.v = v;
  }

}
