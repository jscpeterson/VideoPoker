package edu.cnm.deepdive.videopoker.model;

import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public class Deck extends Stack<Card> {

    //use composition instead of inheritance

//  Stack<Card> deck = new Stack<Card>();
/*
  public Deck() {
    //ArrayList<>
    this = ArrayList<Card>();
  }*/

    private Random rng;

    public Deck(Random rng) {
      this.rng = rng;
      generateStandardDeck();
    }

    private void generateStandardDeck(){
      for (Suit suit : Suit.values()) {
        for (Rank rank : Rank.values()) {
          Card card = new Card(rank, suit);
          this.push(card);
        }
      }
    }

    public void shuffle() {
      Collections.shuffle(this, rng);
    }

  /**
   * Creates a new hand by dealing numberOfCards from the start of the deck.
   * @param numberOfCards the size of the hand to be dealt.
   * @return a new hand of numberOfCards length.
   */
    public PlayerHand deal(int numberOfCards) {
      PlayerHand playerHand = new PlayerHand();
      for (int i = 0; i < numberOfCards; ++i){
        playerHand.push(this.remove(0));
      }
      return playerHand;
    }

  /**
   * Using an existing playerHand, adds cards from the start of the deck to end the playerHand while taking cards
   * from start of the playerHand and returning them to end of the deck.
   * @param playerHand an existing playerHand of cards.
   */
  public void dealAndReplace(PlayerHand playerHand) {
      for (int i = 0; i < playerHand.size(); ++i) {
        playerHand.push(this.remove(0));
        this.add(playerHand.remove(0));
      }
    }

    void print() {
      System.out.println(this.toString());
    }

    int getSize() {
      return this.size();
    }

  }
