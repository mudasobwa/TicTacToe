package ru.mudasobwa.tictactoe;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import java.util.Arrays;
import java.util.logging.Level;

/**
 * All the helpers (MongoDB and drawing).
 * @author Alexei Matyushkin
 */
public class Helper {
  
  /** We don't need lazy instantiation here. */
  protected final static Helper theInstance = new Helper(null);

  /** The game beyond to operate with. */
  protected Game game;
  /**
   * Sets the game for further mongo serializing
   * @param game
   * @return
   */
  protected Helper setGame(final Game game) {
    this.game = game;
    return this;
  }

  /**
   * Get the value of game
   *
   * @return the value of game
   */
  protected DBObject getMongoGame() {
    DBObject result = new BasicDBObject();

    result.put("hash", game.hashCode());
    result.put("winner", game.getWinner().toString());

    int i = 0;
    for ( final Move m : game ) {
      BasicDBObject move = new BasicDBObject();
      move.put("h", m.h);
      move.put("v", m.v);
      result.put("move" + i++, move);
    }
    
    return result;
  }

  public void draw() {
    Character [][] b = new Character[Board.dimension][Board.dimension];
    for (int i = 0; i < Board.dimension; i++) {
      for (int j = 0; j < Board.dimension; j++) {
        b[i][j] = ' ';
      }
    }
    int i = 0;
    for (Move m : game) {
      b[m.h][m.v] = (++i % 2 == 0) ? 'x' : 'o';
    }
    for (int j = 0; j < Board.dimension; j++) {
      System.out.println(Arrays.deepToString(b[j]));
    }
    System.out.println("WINNER: " + game.getWinner());
  }
  
  private Helper() {
    this(null);
  }
  
  private Helper(final Game game) {
    this.game = game;
  }
  
  public static DBObject getMongoGame(final Game game) {
    return theInstance.setGame(game).getMongoGame();
  }
  
  public static void draw(final Game game) {
    if (Board.LOG.isLoggable(Level.INFO)) {
      theInstance.setGame(game).draw();
    }
  }
  
}
