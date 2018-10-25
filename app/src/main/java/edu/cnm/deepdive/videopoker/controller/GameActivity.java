package edu.cnm.deepdive.videopoker.controller;

import android.content.res.Resources;
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
  private static final int HAND_SIZE = 5;

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
  boolean images = false;

  Deck deck;
  Hand hand;
  int pot = 50;
  int bet = 0;
  int win = 0;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game);
    deck = new Deck(new SecureRandom());
    hand = deck.deal(HAND_SIZE);
    setupButtons();
    setupTextViews();
  }

  void setupButtons() {
    dealButton = findViewById(R.id.deal_button);
    dealButton.setEnabled(false);
    drawButton = findViewById(R.id.draw_button);
    drawButton.setEnabled(false);
    betOneButton = findViewById(R.id.bet1_button);
    betMaxButton = findViewById(R.id.bet5_button);
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
      setupDraw();
    });

    drawButton.setOnClickListener((v) -> {
      draw();
      updateWin();
      resetGame();
    });
  }

  void setupTextViews() {
    winNotifier = findViewById(R.id.win_notifier);
    winTotal = findViewById(R.id.win_total);
    betTotal = findViewById(R.id.bet_total);
    potTotal = findViewById(R.id.pot_total);
    winNotifier.setText("");
    winTotal.setText(Integer.toString(win));
    betTotal.setText(Integer.toString(bet));
    potTotal.setText(Integer.toString(pot));
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
      displayCards(i);
    }
    if (debug) System.out.println(deck.size());
    if (debug) System.out.println(deck);
  }

  void setupDraw() {
    dealButton.setEnabled(false);
    drawButton.setEnabled(true);
    betOneButton.setEnabled(false);
    betMaxButton.setEnabled(false);
    for (ToggleButton card : cardButtons) {
      card.setEnabled(true);
    }
  }

  void draw() {
    for (int i = 0; i < HAND_SIZE; i++) {
      if (!cardButtons[i].isChecked()) {
        deck.push(hand.get(i));
        hand.set(i, deck.remove(0));
        displayCards(i);
      }
    }
    if (debug) System.out.println(hand);
    if (debug) System.out.println(deck.size());
    if (debug) System.out.println(deck);
  }

  private void displayCards(int index) {
    String resourceId = hand.get(index).getResourceId();
    int identifier = getResources()
        .getIdentifier(resourceId, "drawable", "edu.cnm.deepdive.videopoker");
    System.out.println(resourceId);
    System.out.println(Integer.toString(identifier));
    if (images) {
      cardButtons[index].setBackgroundDrawable(getDrawable(identifier));
    }
    else {
      cardButtons[index].setTextOff(hand.get(index).toString());
      cardButtons[index].setTextOn(hand.get(index).toString());
      cardButtons[index].setChecked(false);
    }
  }

  void updateWin() {
    win = hand.getHandScore(bet);
    pot += win;
    winNotifier.setText(hand.getBestHand());
    winTotal.setText(Integer.toString(win));
    potTotal.setText(Integer.toString(pot));
  }

  void resetGame() {
    hand.clearWins();
    bet = 0;
    betTotal.setText(Integer.toString(bet));
    dealButton.setEnabled(false);
    drawButton.setEnabled(false);
    betOneButton.setEnabled(true);
    betMaxButton.setEnabled(true);
    for (ToggleButton card : cardButtons) {
      card.setEnabled(false);
    }
  }

}
