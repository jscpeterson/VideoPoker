package edu.cnm.deepdive.videopoker.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import edu.cnm.deepdive.videopoker.R;
import edu.cnm.deepdive.videopoker.model.Deck;
import edu.cnm.deepdive.videopoker.model.Hand;
import java.security.SecureRandom;

public class GameActivity extends AppCompatActivity {

  private static final int BET_MAX = 5;
  private static final int HAND_SIZE = 5;
  private static final String EMPTY_STRING = "";

  private CardButton[] cardButtons;
  private Button dealButton;
  private Button drawButton;
  private Button betOneButton;
  private Button betMaxButton;

  private TextView winningHandView;
  private TextView winView;
  private TextView betView;
  private TextView purseView;
  
  private boolean firstDeal = true;
  private boolean debug = true;
  private boolean viewAsDollars = false;
  
  private Deck deck;
  private Hand hand;
  private int purse = 50;
  private int bet = 0;
  private int win = 0;
  
  // TODO Implement settings menu with add to pot option

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game);
    deck = new Deck(new SecureRandom());
    hand = deck.deal(HAND_SIZE);
    setupButtons();
    setupTextViews();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.options, menu);
    return true;
  }

  public String getWinString(int win, boolean viewAsDollars) {
    if (viewAsDollars) return getString(R.string.win_text_dollar_format, (double) win*0.25);
    else return getString(R.string.win_text_credits_format, win);
  }

  public String getPurseString(int purse, boolean viewAsDollars) {
    if (viewAsDollars) return getString(R.string.purse_text_dollar_format, (double) purse*0.25);
    return getString(R.string.purse_text_credits_format, purse);
  }

  public String getBetString(int bet, boolean viewAsDollars) {
    if (viewAsDollars) return getString(R.string.bet_text_dollar_format, (double) bet*0.25);
    else return getString(R.string.bet_text_credits_format, bet);
  }

  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    boolean handled = true;
    switch (item.getItemId()) {
      case R.id.switch_currency_view:
        viewAsDollars = !viewAsDollars;
        winView.setText(getWinString(win, viewAsDollars));
        purseView.setText(getPurseString(purse, viewAsDollars));
        betView.setText(getBetString(bet, viewAsDollars));
        break;
      default:
        handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }

  private void setupButtons() {
    dealButton = findViewById(R.id.deal_button);
    dealButton.setEnabled(false);
    drawButton = findViewById(R.id.draw_button);
    drawButton.setEnabled(false);
    betOneButton = findViewById(R.id.bet1_button);
    betMaxButton = findViewById(R.id.bet_max_button);
    cardButtons = new CardButton[]{
        findViewById(R.id.card1),
        findViewById(R.id.card2),
        findViewById(R.id.card3),
        findViewById(R.id.card4),
        findViewById(R.id.card5),
    };
    // cards start off disabled and invisible
    for (CardButton card : cardButtons) {
      card.setVisibility(View.INVISIBLE);
      card.setEnabled(false);
      card.setOnClickListener((v) -> {
        card.toggle();
      });
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
        for (CardButton card : cardButtons) {
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
      collectWinnings();
      resetGame();
    });
  }

  private void setupTextViews() {
    winningHandView = findViewById(R.id.win_notifier);
    winView = findViewById(R.id.win_view);
    betView = findViewById(R.id.bet_view);
    purseView = findViewById(R.id.purse_view);
    winningHandView.setText(EMPTY_STRING);
    winView.setText(getWinString(win, viewAsDollars));
    winView.setVisibility(View.INVISIBLE);
    betView.setText(getBetString(bet, viewAsDollars));
    purseView.setText(getPurseString(purse, viewAsDollars));
  }

  private void betOne() {
    if (bet < BET_MAX) ++bet;
    if (bet == BET_MAX || bet >= purse) {
      betOneButton.setEnabled(false);
      betMaxButton.setEnabled(false);
    }
    betView.setText(getBetString(bet, viewAsDollars));
  }

  private void betMax() {
    bet = BET_MAX;
    betOneButton.setEnabled(false);
    betMaxButton.setEnabled(false);
    betView.setText(getBetString(bet, viewAsDollars));
  }

  private void deal() {
    winView.setVisibility(View.INVISIBLE);
    winningHandView.setText(EMPTY_STRING);
    purse -= bet;
    purseView.setText(getPurseString(purse, viewAsDollars));
    betView.setText(getBetString(bet, viewAsDollars));
    deck.shuffle();
    deck.dealAndReplace(hand);
    for (int i = 0; i < hand.size(); i++) {
      displayCards(i);
    }

    // Evaluate hand to display if the player was dealt a winning hand.
    hand.evaluateHand();
    String bestHand = hand.getBestHand();
    // Avoid returning bust string if the dealt hand is not a winning hand.
    if (!bestHand.equals(hand.getBustString())) winningHandView.setText(hand.getBestHand());
    hand.clearWins();
    if (debug) System.out.println(deck.size());
    if (debug) System.out.println(deck);
  }

  private void setupDraw() {
    dealButton.setEnabled(false);
    drawButton.setEnabled(true);
    betOneButton.setEnabled(false);
    betMaxButton.setEnabled(false);
    for (CardButton card : cardButtons) {
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
    cardButtons[index].setImageResource(identifier);
  }

  private void collectWinnings() {
    hand.evaluateHand();
    win = hand.getHandScore(bet);
    purse += win;
    winningHandView.setText(hand.getBestHand());
    if (win > 0) {
      winView.setText(getWinString(win, viewAsDollars));
      winView.setVisibility(View.VISIBLE);
    }
    purseView.setText(getPurseString(purse, viewAsDollars));
  }

  private void resetGame() {
    hand.clearWins();
    bet = 0;
    betView.setText(getBetString(bet, viewAsDollars));
    dealButton.setEnabled(false);
    drawButton.setEnabled(false);
    for (CardButton card : cardButtons) {
      card.setEnabled(false);
      card.setChecked(false);
    }
    if (purse >= BET_MAX) {
      betMaxButton.setEnabled(true);
      betOneButton.setEnabled(true);
    }
    else if (purse > 0) {
      betOneButton.setEnabled(true);
    }
    else {
      winningHandView.setText(R.string.purse_empty_text);
    }
  }

}
