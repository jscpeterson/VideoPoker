package edu.cnm.deepdive.videopoker.model;

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

  public Color getColor() {
    return color;
  }

  public char getSymbol() {
    return symbol;
  }

  public enum Color {
    BLACK, RED;
  }

}
