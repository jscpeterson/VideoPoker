package edu.cnm.deepdive.videopoker.model;

/**
 * This class represents the rank value of a playing card. These are assigned a fixed value from
 * Rank 1 (Ace) to 13 (King) and single character String for the symbol representation.
 */
public enum Rank {
  ACE(1, "A"),
  TWO(2, "2"),
  THREE(3, "3"),
  FOUR(4, "4"),
  FIVE(5, "5"),
  SIX(6, "6"),
  SEVEN(7, "7"),
  EIGHT(8, "8"),
  NINE(9, "9"),
  TEN(10, "T"),
  JACK(11, "J"),
  QUEEN(12, "Q"),
  KING(13, "K");

  private int value;
  private String symbol;

  Rank(int value, String symbol) {
    this.value = value;
    this.symbol = symbol;
  }

  /**
   * @return a numeric value ranging from 1 (Ace) to 13 (King).
   */
  public int getValue() {
    return value;
  }

  /**
   * @return the symbol representation of the card. This is required to be a single character, so
   * 10 is represented as T.
   */
  public String getSymbol() {
    //TODO Change to char?
    return symbol;
  }
}


