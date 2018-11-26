package edu.cnm.deepdive.videopoker.model;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents a playing card in a deck or a hand. It has a Rank and a Suit value
 * associated with it, and uses the unicode values associated with this rank when it needs to be
 * displayed as a string. This class can alternatively retrieve a drawable resource to be used by a
 * controller class to display to the user.
 */
public class Card implements Comparable<Card> {

  /**
   * The rank of a card.
   */
  private Rank rank;
  /**
   * The suit of a card.
   */
  private Suit suit;

  /**
   * @param rank the Rank value of the card to be created.
   * @param suit the Suit value of the card to be created.
   */
  public Card(Rank rank, Suit suit) {
    this.rank = rank;
    this.suit = suit;
  }

  /**
   * @return this card's rank value.
   */
  Rank getRank() {
    return rank;
  }

  /**
   * @return this card's suit value.
   */
  Suit getSuit() {
    return suit;
  }

  /**
   * Overridden toString to display the symbol values of Rank and Suit, respectively. Suit symbols
   * may not appear correctly on all fonts.
   *
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
   *
   * @return a String that corresponds to a resource in res/drawable.
   */
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

  /**
   * Overridden compareTo only accounts for the rank value. Aces are assumed to be low.
   */
  @Override
  public int compareTo(@NotNull Card other) {
    return this.rank.compareTo(other.rank);
  }

  /**
   * Two cards are equivalent to each other if the rank and the suit matches each other.
   */
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

