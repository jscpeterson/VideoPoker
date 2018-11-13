package edu.cnm.deepdive.videopoker.model;

import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class Card implements Comparable <Card>{

  private Rank rank;
  private Suit suit;
  private boolean held = false;

  public Card(Rank rank, Suit suit) {
    this.rank = rank;
    this.suit = suit;
  }

  Rank getRank() {
    return rank;
  }

  Suit getSuit() {
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
    resId.append("image_");
    resId.append(rank.getSymbol().toLowerCase());
    switch (suit) {
      case CLUBS:
        resId.append("c");
        break;
      case HEARTS:
        resId.append("h");
        break;
      case DIAMONDS:
        resId.append("d");
        break;
      case SPADES:
        resId.append("s");
        break;
    }
    return resId.toString();
  }

  public void toggleHeld() {
    held = !held;
  }

  public boolean isHeld() {
    return held;
  }

  @Override
  public int compareTo(@NotNull Card other) {
    return this.rank.compareTo(other.rank);
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    Card card = (Card) other;
    return rank == card.rank &&
        suit == card.suit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(rank, suit);
  }

}

