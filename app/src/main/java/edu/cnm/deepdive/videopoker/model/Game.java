package edu.cnm.deepdive.videopoker.model;

import java.util.Random;

/**
 * This class contains information for the state of the game being currently played. It does not
 * contain any information about the paytable - the specific hands and the rules to evaluate them.
 * It only contains information about the deck being used, the hand being played with, and the
 * credits the user is playing with.
 */
public class Game {

  //CONSTANTS
  private static final int HAND_SIZE = 5;

  /**
   * The deck in play for the game.
   */
  private Deck deck;
  /**
   * The user's hand in play for the game.
   */
  private PlayerHand playerHand;
  /**
   * The monetary value of a credit.
   */
  private double creditValue;
  /**
   * The amount of credits in the purse.
   */
  private int purse;
  /**
   * The current bet in play.
   */
  private int bet = 0;
  /**
   * The amount of credits last won from a game.
   */
  private int win = 0;

  /**
   * Creation of the game object sets the credits and their worth, creates a deck based on the
   * Random object given, and deals from that deck to a new hand.
   *
   * @param purse the amount of credits to start playing with.
   * @param creditValue the monetary worth of each credit, assumed to be in US dollars.
   * @param rng the random number generation object to be passed into the deck for shuffling.
   */
  public Game(int purse, double creditValue, Random rng) {
    this.purse = purse;
    this.creditValue = creditValue;
    this.deck = new Deck(rng);
    this.playerHand = deck.deal(HAND_SIZE);
  }

  /**
   * @return the deck used in the game in its current state.
   */
  public Deck getDeck() {
    return deck;
  }

  /**
   * @return the player's hand used in the game in its current state.
   */
  public PlayerHand getPlayerHand() {
    return playerHand;
  }

  /**
   * @param playerHand a new hand to assign to the hand currently used in the game.
   */
  public void setPlayerHand(PlayerHand playerHand) {
    this.playerHand = playerHand;
  }

  /**
   * @return the monetary value of a credit in the current game.
   */
  public double getCreditValue() {
    return creditValue;
  }

  /**
   * @return the current number of credits in the game.
   */
  public int getPurse() {
    return purse;
  }

  /**
   * @param purse the number of credits to change the purse to.
   */
  public void setPurse(int purse) {
    this.purse = purse;
  }

  /**
   * @return the current bet.
   */
  public int getBet() {
    return bet;
  }

  /**
   * @param bet the bet to store in the class.
   */
  public void setBet(int bet) {
    this.bet = bet;
  }

  /**
   * @return the amount won in a game.
   */
  public int getWin() {
    return win;
  }

  /**
   * @param win the amount to assign to the win value of the game.
   */
  public void setWin(int win) {
    this.win = win;
  }

}
