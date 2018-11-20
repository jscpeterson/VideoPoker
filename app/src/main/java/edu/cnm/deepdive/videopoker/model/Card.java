package edu.cnm.deepdive.videopoker.model;

import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class Card implements Comparable <Card>{

  private Rank rank;
  private Suit suit;

  /**
   * Constructor takes a rank and a suit to build the card with.
   */
  public Card(Rank rank, Suit suit) {
    this.rank = rank;
    this.suit = suit;
  }

  /**
   * @return the Rank value of this card.
   */
  public Rank getRank() {
    return rank;
  }

  /**
   * @return the Suit value of this card.
   */
  public Suit getSuit() {
    return suit;
  }

  /**
   * Overridden toString to display the symbol values of Rank and Suit, respectively.
   * @return a String such as "A♠"
   */
  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(rank.getSymbol());
    stringBuilder.append(suit.getSymbol());
    return stringBuilder.toString();
  }

  /**
   * Retrieve a filename for a drawable resource.
   * @return a String that corresponds to a resource in res/drawable.
   */
  public String getResource() {
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

  /**
   * Overridden compareTo only accounts for the rank value. Aces are assumed to be low.
   */
  @Override
  public int compareTo(@NotNull Card other) {
    return this.rank.compareTo(other.rank);
  }

  // TODO Document
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

  // TODO Document
  @Override
  public int hashCode() {
    return Objects.hash(rank, suit);
  }

}

