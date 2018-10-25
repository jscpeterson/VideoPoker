package edu.cnm.deepdive.videopoker.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import edu.cnm.deepdive.videopoker.R;
import edu.cnm.deepdive.videopoker.model.Deck;
import edu.cnm.deepdive.videopoker.model.Hand;
import java.security.SecureRandom;

public class GameActivity extends AppCompatActivity {

  private static final int BET_MAX = 5;

  //    https://deckofcardsapi.com/api/deck/new/
  TableLayout payoutTable;

  Button dealButton;
  Button drawButton;
  ToggleButton[] cardButtons;
  Button betOneButton;
  Button betMaxButton;

  TextView winNotifier;
  TextView winTotal;
  TextView betTotal;
  TextView potTotal;

  boolean firstDeal = true;
  boolean debug = true;

  Deck deck;
  Hand hand;
  int pot = 50;
  int bet = 0;
  int win = 0;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Separate methods
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game);
    dealButton = findViewById(R.id.deal_button);
    dealButton.setEnabled(false);
    drawButton = findViewById(R.id.draw_button);
    drawButton.setEnabled(false);
    betOneButton = findViewById(R.id.bet1_button);
    betMaxButton = findViewById(R.id.bet5_button);
    deck = new Deck(new SecureRandom());
    hand = deck.deal(5);

    winNotifier = findViewById(R.id.win_notifier);
    winTotal = findViewById(R.id.win_total);
    betTotal = findViewById(R.id.bet_total);
    potTotal = findViewById(R.id.pot_total);

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

    // initializes blank winNotifier
    winNotifier.setText("");
    // initializes as 0, assuming nothing else changes...
    winTotal.setText(Integer.toString(win));

    betOneButton.setOnClickListener((v) -> {
      betOne();
      dealButton.setEnabled(true);
    });

    betMaxButton.setOnClickListener((v) -> {
      betMax();
      dealButton.setEnabled(true);
    });

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

  void betOne() {
    if (bet < BET_MAX) ++bet;
    if (bet == BET_MAX) {
      betOneButton.setEnabled(false);
      betMaxButton.setEnabled(false);
    }
    betTotal.setText(Integer.toString(bet));
  }

  void betMax() {
    bet = BET_MAX;
    betOneButton.setEnabled(false);
    betMaxButton.setEnabled(false);
    betTotal.setText(Integer.toString(bet));
  }

  void deal() {
    winNotifier.setText("");
    pot -= bet;
    potTotal.setText(Integer.toString(pot));
    betTotal.setText(Integer.toString(bet));

    deck.shuffle();
    deck.dealAndReplace(hand);
    for (int i = 0; i < hand.size(); i++) {
//      TODO implement cardImage drawables
//      String resourceId = hand.get(i).getResourceId();
      cardButtons[i].setTextOff(hand.get(i).toString());
      cardButtons[i].setTextOn(hand.get(i).toString());
      cardButtons[i].setChecked(false); //probably poor way to update view
    }
    if (debug) System.out.println(deck.size());
    if (debug) System.out.println(deck);
    dealButton.setEnabled(false);
    drawButton.setEnabled(true);
    betOneButton.setEnabled(false);
    betMaxButton.setEnabled(false);
    for (ToggleButton card : cardButtons) {
      card.setEnabled(true);
    }
  }

  void draw() {
    for (int i = 0; i < hand.size(); i++) {
      if (!cardButtons[i].isChecked()) {
        deck.push(hand.get(i));
        hand.set(i, deck.remove(0));
//        TODO implement cardImage drawables
//        String resourceId = hand.get(i).getResourceId();
        cardButtons[i].setTextOff(hand.get(i).toString());
        cardButtons[i].setTextOn(hand.get(i).toString());
        cardButtons[i].setChecked(false);
      }
    }
    if (debug) System.out.println(hand);
    if (debug) System.out.println(deck.size());
    if (debug) System.out.println(deck);
    dealButton.setEnabled(false);
    drawButton.setEnabled(false);
    betOneButton.setEnabled(true);
    betMaxButton.setEnabled(true);
    for (ToggleButton card : cardButtons) {
      card.setEnabled(false);
    }

    win = hand.getHandScore(bet);
    pot += win;
    winNotifier.setText(hand.getBestHand());
    winTotal.setText(Integer.toString(win));
    potTotal.setText(Integer.toString(pot));
    hand.clearWins();

    bet = 0;
    betTotal.setText(Integer.toString(bet));

  }


}
