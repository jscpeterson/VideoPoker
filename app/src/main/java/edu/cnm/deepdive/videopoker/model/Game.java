package edu.cnm.deepdive.videopoker.model;

public class Game {

  private Deck deck;
  private Hand hand;
  private int purse = 50;
  private double creditValue = 0.25;
  private int bet = 0;
  private int win = 0;


  public enum State {
    START,
    DEAL,
    HOLD,
    DRAW,
    RESULT,
    BET
  }

}
