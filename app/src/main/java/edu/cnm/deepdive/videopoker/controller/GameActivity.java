package edu.cnm.deepdive.videopoker.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
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
  Deck deck;

  Hand hand;
  ToggleButton[] handButtons;

  int credits;
  int bet;
  int win;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game);

    dealButton = findViewById(R.id.deal_button);
    drawButton = findViewById(R.id.draw_button);

    deck = new Deck(new SecureRandom());
    hand = deck.deal(5);

    handButtons = new ToggleButton[] {
        findViewById(R.id.card1),
        findViewById(R.id.card2),
        findViewById(R.id.card3),
        findViewById(R.id.card4),
        findViewById(R.id.card5),
    };

    dealButton.setOnClickListener((v) -> {
      deal();
    });

    drawButton.setOnClickListener((v) -> {
      draw();
    });

  }

  void createPayoutTable() {
    payoutTable = findViewById(R.id.payout_table);
    //iterate over hands used in current game
    //display hand name
    //display bet 1. bet 2, bet 3, bet 4 and separate bet 5 columns
  }

  void deal() {
    deck.shuffle();
    deck.dealAndReplace(hand);
    for (int i = 0; i < hand.size(); i++) {
      handButtons[i].setTextOff(hand.get(i).getResourceId());
      handButtons[i].setTextOn(hand.get(i).getResourceId());
      handButtons[i].setChecked(false);
    }
    System.out.println(deck.size());
    System.out.println(deck);
  }

  void draw() {
    for (int i = 0; i < hand.size(); i++) {
      if (!handButtons[i].isChecked()) {
        deck.push(hand.get(i));
        hand.set(i, deck.remove(0));
        handButtons[i].setTextOff(hand.get(i).getResourceId());
        handButtons[i].setTextOn(hand.get(i).getResourceId());
        handButtons[i].setChecked(false);
      }
    }
    System.out.println(deck.size());
    System.out.println(deck);
  }


}
