package ru.mudasobwa.tictactoe;

import java.util.LinkedList;

/**
 * The representation of {@link Move}s collection, plus marker for game winner.
 * @author Alexei Matyushkin
 */
public class Game extends LinkedList<Move> {

  /** The game winner variants (me, you, nobody). */
  public static enum Winner { O, X, N };
  
  /** Who's game winner? */
  protected Winner winner = Winner.N;

  @Override
  public int hashCode() {
    return super.hashCode() + winner.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Game other = (Game) obj;
    if (this.winner != other.winner) {
      return false;
    }
    return super.equals(obj);
  }

  /** 
   * Checker for the current move possibility.
   * @return true, if the move is OK (cell is empty), false otherwise
   */
  public final boolean isValidMove(final Move m) {
    return !contains(m);
  }
  
  /**
   * Get the value of winner
   * @return the value of winner ({@link Winner.N} when Game is not finished
   *     and/or game is drawn.
   */
  public final Winner getWinner() {
    return this.winner;
  }

  /**
   * Set the value of winner
   * @param winner new value of winner
   */
  public void setWinner(final Winner winner) {
    this.winner = winner;
  }

  /** @return true if the winner of the game is determined, false otherwise */
  public final boolean hasWinner() {
    return this.winner != Winner.N;
  }

  /** The class constructor. */
  public Game() {
    super();
  }
  
}
