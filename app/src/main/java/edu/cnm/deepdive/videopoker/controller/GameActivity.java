package edu.cnm.deepdive.videopoker.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.ToggleButton;
import edu.cnm.deepdive.videopoker.R;
import edu.cnm.deepdive.videopoker.model.Deck;
import edu.cnm.deepdive.videopoker.model.Hand;
import java.security.SecureRandom;

public class GameActivity extends AppCompatActivity {

  //    https://deckofcardsapi.com/api/deck/new/
  TableLayout payoutTable;

  Button dealButton;
  Button drawButton;
  ToggleButton[] cardButtons;

  boolean firstDeal = true;

  Deck deck;
  Hand hand;
  int credits;
  int bet;
  int win;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Separate methods
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game);
    dealButton = findViewById(R.id.deal_button);
    drawButton = findViewById(R.id.draw_button);
    drawButton.setEnabled(false);
    deck = new Deck(new SecureRandom());
    hand = deck.deal(5);

    cardButtons = new ToggleButton[]{
        findViewById(R.id.card1),
        findViewById(R.id.card2),
        findViewById(R.id.card3),
        findViewById(R.id.card4),
        findViewById(R.id.card5),
    };
    // cards start off disabled and invisible
    for (ToggleButton card : cardButtons) {
      card.setVisibility(View.INVISIBLE);
      card.setEnabled(false);
    }

    dealButton.setOnClickListener((v) -> {
      // special actions for the initial deal when the game first begins
      // activate and make cards visible
      if (firstDeal) {
        for (ToggleButton card : cardButtons) {
          card.setVisibility(View.VISIBLE);
          card.setEnabled(true);
        }
        firstDeal = false;
      }
      deal();
    });

    drawButton.setOnClickListener((v) -> {
      draw();
    });
  }

  void deal() {
    deck.shuffle();
    deck.dealAndReplace(hand);
    for (int i = 0; i < hand.size(); i++) {
      cardButtons[i].setTextOff(hand.get(i).getResourceId());
      cardButtons[i].setTextOn(hand.get(i).getResourceId());
      cardButtons[i].setChecked(false);
    }
    System.out.println(deck.size());
    System.out.println(deck);
    dealButton.setEnabled(false);
    drawButton.setEnabled(true);
    for (ToggleButton card : cardButtons) {
      card.setEnabled(true);
    }
  }

  void draw() {
    for (int i = 0; i < hand.size(); i++) {
      if (!cardButtons[i].isChecked()) {
        deck.push(hand.get(i));
        hand.set(i, deck.remove(0));
        cardButtons[i].setTextOff(hand.get(i).getResourceId());
        cardButtons[i].setTextOn(hand.get(i).getResourceId());
        cardButtons[i].setChecked(false);
      }
    }
    System.out.println(deck.size());
    System.out.println(deck);
    dealButton.setEnabled(true);
    drawButton.setEnabled(false);
    for (ToggleButton card : cardButtons) {
      card.setEnabled(false);
    }
  }


}
