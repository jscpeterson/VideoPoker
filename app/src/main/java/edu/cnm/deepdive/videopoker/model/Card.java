package edu.cnm.deepdive.videopoker.model;

import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;

public class Card implements Comparable <Card>{

  // implement hashcode

  private Rank rank;
  private Suit suit;
  private boolean held = false;

  public Card(Rank rank, Suit suit) {
    this.rank = rank;
    this.suit = suit;
  }

  public Rank getRank() {
    return rank;
  }

  public Suit getSuit() {
    return suit;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(rank.getSymbol());
    stringBuilder.append(suit.getSymbol());
    if (held) stringBuilder.append('*');
    return stringBuilder.toString();
  }

  public String getResourceId() {
    StringBuilder resId = new StringBuilder();
    resId.append(rank.getSymbol());
    switch (suit) {
      case CLUBS:
        resId.append("C");
        break;
      case HEARTS:
        resId.append("H");
        break;
      case DIAMONDS:
        resId.append("D");
        break;
      case SPADES:
        resId.append("S");
        break;
    }
    resId.append(".png");
    return resId.toString();
  }

  public void toggleHeld() {
    held = !held;
  }

  public boolean isHeld() {
    return held;
  }

  @Override
  public int compareTo(Card other) {
    int comparison = this.suit.compareTo(other.suit);
    if (comparison == 0) {
      comparison = this.rank.compareTo(other.rank);
    }
    return comparison;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Card card = (Card) o;
    return rank == card.rank &&
        suit == card.suit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(rank, suit);
  }

}

