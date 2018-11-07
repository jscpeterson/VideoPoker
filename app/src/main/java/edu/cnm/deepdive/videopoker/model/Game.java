package edu.cnm.deepdive.videopoker.model;

import edu.cnm.deepdive.videopoker.model.db.Paytable;
import java.security.SecureRandom;

public class Game {

  private static final int BET_MAX = 5;
  private static final int HAND_SIZE = 5;

  private Deck deck;
  private PlayerHand playerHand;
  private double creditValue;
  private int purse;

  private int bet = 0;
  private int win = 0;

  public Game(int purse, double creditValue) {
    this.purse = purse;
    this.creditValue = creditValue;
    this.deck = new Deck(new SecureRandom());
    this.playerHand = deck.deal(HAND_SIZE);
  }


  public Deck getDeck() {
    return deck;
  }

  public PlayerHand getPlayerHand() {
    return playerHand;
  }

  public double getCreditValue() {
    return creditValue;
  }

  public int getPurse() {
    return purse;
  }

  public void setPurse(int purse) {
    this.purse = purse;
  }

  public int getBet() {
    return bet;
  }

  public void setBet(int bet) {
    this.bet = bet;
  }

  public int getWin() {
    return win;
  }

  public void setWin(int win) {
    this.win = win;
  }

  public void addWinToPurse() {
    this.purse += this.win;
  }

  public enum State {
    START,
    DEAL,
    HOLD,
    DRAW,
    RESULT,
    BET
  }

}
