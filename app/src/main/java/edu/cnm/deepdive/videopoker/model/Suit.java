package edu.cnm.deepdive.videopoker.model;

/**
 * This class represents the suit value in the order Clubs, Diamonds, Hearts, Spades. These are
 * assigned with a color for the suit of BLACK or RED and the unicode symbol representing the suit.
 */
public enum Suit {
  CLUBS(Color.BLACK, '\u2663'),
  DIAMONDS(Color.RED, '\u2662'),
  HEARTS(Color.RED, '\u2661'),
  SPADES(Color.BLACK, '\u2660');

  private Color color;
  private char symbol;

  Suit(Color color, char symbol) {
    this.color = color;
    this.symbol = symbol;
  }

  /**
   * Suit colors are not needed in many variants of draw poker, but included for comprehension
   * @return the color BLACK or RED for the suit.
   */
  public Color getColor() {
    return color;
  }

  /**
   * @return the unicode symbol representing the suit. Not all fonts may support.
   */
  public char getSymbol() {
    return symbol;
  }

  /**
   * A store of the color state of the suit in BLACK or RED.
   */
  public enum Color {
    BLACK, RED;
  }

}
