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

    void generateStandardDeck(){
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

    public Hand deal(int numberOfCards) {
      Hand hand = new Hand();
      for (int i=0; i<numberOfCards; i++){
        hand.push(this.pop());
      }
      return hand;
    }

    void print() {
      System.out.println(this.toString());
    }

    int getSize() {
      return this.size();
    }

    void repopulate(Hand cards) {
      for (Card card : cards) {
        this.push(card);
      }
    }
  }
