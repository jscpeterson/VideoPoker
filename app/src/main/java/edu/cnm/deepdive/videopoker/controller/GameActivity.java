package edu.cnm.deepdive.videopoker.controller;

import android.graphics.drawable.Drawable;
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

  private Button dealButton;
  private Button drawButton;
  private ToggleButton[] cardButtons;
  private Button betOneButton;
  private Button betMaxButton;

  private TextView winningHandView;
  private TextView winView;
  private TextView betView;
  private TextView purseView;

  private boolean firstDeal = true;
  private boolean debug = true;
  private boolean images = false;

  private Deck deck;
  private Hand hand;
  private int purse = 50;
  private int bet = 0;
  private int win = 0;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game);
    deck = new Deck(new SecureRandom());
    hand = deck.deal(HAND_SIZE);
    setupButtons();
    setupTextViews();
  }

  private void setupButtons() {
    dealButton = findViewById(R.id.deal_button);
    dealButton.setEnabled(false);
    drawButton = findViewById(R.id.draw_button);
    drawButton.setEnabled(false);
    betOneButton = findViewById(R.id.bet1_button);
    betMaxButton = findViewById(R.id.bet_max_button);
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

  private void setupTextViews() {
    winningHandView = findViewById(R.id.win_notifier);
    winView = findViewById(R.id.win_view);
    betView = findViewById(R.id.bet_view);
    purseView = findViewById(R.id.purse_view);
    winningHandView.setText("");
    winView.setText(getString(R.string.win_text_format, win));
    betView.setText(getString(R.string.bet_text_format, bet));
    purseView.setText(getString(R.string.purse_text_format, purse));
  }

  private void betOne() {
    if (bet < BET_MAX) ++bet;
    if (bet == BET_MAX || bet >= purse) {
      betOneButton.setEnabled(false);
      betMaxButton.setEnabled(false);
    }
    betView.setText(getString(R.string.bet_text_format, bet));
  }

  private void betMax() {
    bet = BET_MAX;
    betOneButton.setEnabled(false);
    betMaxButton.setEnabled(false);
    betView.setText(getString(R.string.bet_text_format, bet));
  }

  private void deal() {
    winningHandView.setText("");
    purse -= bet;
    purseView.setText(getString(R.string.purse_text_format, purse));
    betView.setText(getString(R.string.bet_text_format, bet));
    deck.shuffle();
    deck.dealAndReplace(hand);
    for (int i = 0; i < hand.size(); i++) {
      displayCards(i);
    }
    if (debug) System.out.println(deck.size());
    if (debug) System.out.println(deck);
  }

  private void setupDraw() {
    dealButton.setEnabled(false);
    drawButton.setEnabled(true);
    betOneButton.setEnabled(false);
    betMaxButton.setEnabled(false);
    for (ToggleButton card : cardButtons) {
      card.setEnabled(true);
    }
  }

  private void draw() {
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
    Drawable cardImage = getDrawable(identifier);
    if (images) {
      cardButtons[index].setBackground(cardImage);
    }
    else {
      cardButtons[index].setTextOff(hand.get(index).toString());
      cardButtons[index].setTextOn(hand.get(index).toString());
      cardButtons[index].setChecked(false);
    }
  }

  private void updateWin() {
    win = hand.getHandScore(bet);
    purse += win;
    winningHandView.setText(hand.getBestHand());
    winView.setText(getString(R.string.win_text_format, win));
    purseView.setText(getString(R.string.purse_text_format, purse));
  }

  private void resetGame() {
    hand.clearWins();
    bet = 0;
    betView.setText(getString(R.string.bet_text_format, bet));
    dealButton.setEnabled(false);
    drawButton.setEnabled(false);
    for (ToggleButton card : cardButtons) {
      card.setEnabled(false);
    }
    if (purse >= BET_MAX) {
      betMaxButton.setEnabled(true);
      betOneButton.setEnabled(true);
    }
    else if (purse > 0) {
      betOneButton.setEnabled(true);
    }
    else {
      winningHandView.setText("Game over, loser");
    }
  }

}
