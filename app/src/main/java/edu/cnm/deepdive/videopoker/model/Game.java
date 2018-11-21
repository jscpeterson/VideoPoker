package edu.cnm.deepdive.videopoker.model;

import java.security.SecureRandom;
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

  private Deck deck;
  private PlayerHand playerHand;
  private double creditValue;
  private int purse;
  private int bet = 0;
  private int win = 0;

  /**
   * Creation of the game object sets the credits and their worth, creates a deck based on the
   * Random object given, and deals from that deck to a new hand.
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

}
