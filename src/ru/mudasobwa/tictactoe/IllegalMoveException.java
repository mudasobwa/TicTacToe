/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.mudasobwa.tictactoe;

/**
 *
 * @author am
 */
public class IllegalMoveException extends Exception {

  final Move m;
  
  public IllegalMoveException(final Move m) {
    super();
    this.m = m;
  }
  
}
