package edu.cnm.deepdive.videopoker.model;

import java.security.SecureRandom;

/**
 * This class contains information for the state of the game being currently played. It does not
 * contain any information about the paytable - the specific hands and the rules to evaluate them.
 * The Random object used to shuffle the deck is determined here.
 */
public class Game {

  private static final int BET_MAX = 5;
  private static final int HAND_SIZE = 5;

  private Deck deck;

  private String gameName;
  private PlayerHand playerHand;
  private double creditValue;
  private int purse;

  private int bet = 0;
  private int win = 0;

  /**
   * The constructor for this . It currently uses SecureRandom as the random number generator.
   * @param purse an amount of credits to start with.
   * @param creditValue the worth of each credit assumed to be in dollars.
   */
  public Game(int purse, double creditValue) {
    this.gameName = gameName;
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

  public void setPlayerHand(PlayerHand playerHand) {
    this.playerHand = playerHand;
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

  public String getGameName() {
    return gameName;
  }

  public void setGameName(String gameName) {
    this.gameName = gameName;
  }
}
