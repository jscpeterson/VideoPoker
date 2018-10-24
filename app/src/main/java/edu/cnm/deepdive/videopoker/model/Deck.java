package edu.cnm.deepdive.videopoker.model;

import java.util.ArrayList;
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
    public Hand deal(int numberOfCards) {
      Hand hand = new Hand();
      for (int i=0; i<numberOfCards; i++){
        hand.push(this.remove(0));
      }
      return hand;
    }

  /**
   * Using an existing hand, adds cards from the start of the deck to end the hand while taking cards
   * from start of the hand and returning them to end of the deck.
   * @param hand an existing hand of cards.
   */
  public void dealAndReplace(Hand hand) {
      for (int i = 0; i < hand.size(); i++) {
        hand.push(this.remove(0));
        this.add(hand.remove(0));
      }
    }

    void print() {
      System.out.println(this.toString());
    }

    int getSize() {
      return this.size();
    }

  }
