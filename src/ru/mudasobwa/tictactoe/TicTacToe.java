/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.mudasobwa.tictactoe;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteConcern;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author am
 */
public class TicTacToe {

  protected static DBCollection coll;
  
  static {
    try {
      Mongo m = new Mongo();
      DB db = m.getDB("TicTacToe");
      db.setWriteConcern(WriteConcern.SAFE);
      coll = db.createCollection("scores", null);
    } catch (UnknownHostException ex) {
      Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public static void trainSelf() {
    // Let's train ourselves
    DB db = coll.getDB();
    coll.drop();
    coll = db.createCollection("scores", null);
    coll.createIndex(new BasicDBObject("hash", null));
    for(int i = 0; i < 5000; i++) {
      Game game = new Board().play();
      DBObject dbog = Helper.getMongoGame(game);

      DBCursor cursor = coll.find(new BasicDBObject("hash", game.hashCode()));
      try {
        if (cursor.hasNext()) {
          coll.update(cursor.next(), dbog);
        } else {
          coll.insert(dbog);
        }
      } finally {
        cursor.close();
      }
      
    }
  }
  
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
//    if (args.length > 0) { // Train self
      trainSelf();
//    }
    
    // Play the game 50 times
    int weWon = 0;
    int weLost = 0;
    for (int i = 0; i < 50; i++) {
      Board board = new Board();
      Game game = board.getGame();
      try {
        board.move(new Move(1, 0));
        while (true) {
          // Let stupid try || Do our best
          if (board.move() || board.move(suggestMove(game))) {
            Helper.draw(game);
            switch (game.getWinner()) {
              case O : weWon += 1; break;
              case X : weLost += 1; break;
            }
            break;
          }
        }
      } catch (IllegalMoveException ex) {
        Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
      }
    } 
    System.out.println ("Our score is: " + weWon + " won against " + weLost + " lost out of 50 times. Not so bad.");
  }
  
  /** This is main subject to change to improve AI.
   * Current implementation is not real implementation, it 
   *     is a dumb sceleton to show the “possibility” to 
   *     suggest moves. It's encapsulated in this one method 
   *     to show the main idea and a potential ability to do our best.
   */
  public static Move suggestMove(final Game game) {
    DBObject query = new BasicDBObject("winner", "O");
    int i = 0;
    for (final Move m : game) {
      BasicDBObject move = new BasicDBObject();
      move.put("h", m.h);
      move.put("v", m.v);
      query.put("move" + i++, move);
    }
    try (DBCursor cursor = coll.find(query)) {
      if (cursor.hasNext()) {
        DBObject nextMove = (DBObject) cursor.next().get("move" + i);
        return new Move((int) nextMove.get("h"), (int) nextMove.get("v"));
      } else {
        while (true) {
          Move m = new Move(); 
          if (game.isValidMove(m)) {
            return m;
          }
        }
      }
    }
  }
  
    
    
}
