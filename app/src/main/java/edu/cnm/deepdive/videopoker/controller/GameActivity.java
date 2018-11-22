package edu.cnm.deepdive.videopoker.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import edu.cnm.deepdive.videopoker.R;
import edu.cnm.deepdive.videopoker.model.Converter;
import edu.cnm.deepdive.videopoker.model.Game;
import edu.cnm.deepdive.videopoker.model.PlayerHand;
import edu.cnm.deepdive.videopoker.model.db.PaytableDatabase;
import edu.cnm.deepdive.videopoker.model.entity.PokerHand;
import java.security.SecureRandom;

public class GameActivity extends AppCompatActivity {

  //CONSTANTS
  private static final int BET_MAX = 5;
  private static final int HAND_SIZE = 5;
  private static final String EMPTY_STRING = "";
  private static final String PURSE_KEY = "purse";
  private static final String CREDIT_VALUE_KEY = "creditValue";
  private static final String PAYTABLE_ID_KEY = "paytableId";
  private static final String PAYTABLE_NAME_KEY = "paytableNameKey";
  private static final String CURRENCY_VIEW_PREF = "viewAsDollars";

  //EXTRAS
  private long paytableId;
  private String paytableName;

  //BUTTONS
  private Button mainButton;
  private Button helpButton;
  private Button dealButton;
  private Button drawButton;
  private Button betOneButton;
  private Button betMaxButton;
  private CardButton[] cardButtons;

  //FLAGS
  private boolean firstDeal = true;
  private boolean viewAsDollars;

  //TEXT VIEWS
  private TextView winningHandView;
  private TextView winView;
  private TextView betView;
  private TextView purseView;

  //OBJECTS
  private Game game;
  private Converter converter = new Converter();
  private SharedPreferences sharedPref;

  private void betOne() {
    if (game.getBet() < BET_MAX) {
      game.setBet(game.getBet() + 1);
    }
    if (game.getBet() == BET_MAX || game.getBet() >= game.getPurse()) {
      betOneButton.setEnabled(false);
      betMaxButton.setEnabled(false);
    }
    betView.setText(getBetString(game.getBet(), game.getCreditValue(), viewAsDollars));
  }

  private void betMax() {
    game.setBet(BET_MAX);
    betOneButton.setEnabled(false);
    betMaxButton.setEnabled(false);
    betView.setText(getBetString(game.getBet(), game.getCreditValue(), viewAsDollars));
  }

  private void collectWinnings() {
    // TODO Slow point accumulation for an "animated" win
    winningHandView.setText(game.getPlayerHand().getBestHand().getName());
    if (game.getWin() > 0) {
      winView.setVisibility(View.VISIBLE);
      winView.setText(getWinString(game.getWin(), game.getCreditValue(), viewAsDollars));
      game.setPurse(game.getPurse() + game.getWin());
      purseView.setText(getPurseString(game.getPurse(), game.getCreditValue(), viewAsDollars));
    }
  }

  private void displayCard(int index) {
    String resourceId = game.getPlayerHand().get(index).getResourceId();
    int identifier = getResources()
        .getIdentifier(resourceId, "drawable", "edu.cnm.deepdive.videopoker");
    cardButtons[index].setImageResource(identifier);
  }

  private void gameOver() {
    winningHandView.setText(R.string.purse_empty_text);
    betOneButton.setEnabled(false);
    betMaxButton.setEnabled(false);
    dealButton.setEnabled(false);
    drawButton.setEnabled(false);
  }

  private String getWinString(int win, double creditValue, boolean viewAsDollars) {
    if (viewAsDollars) {
      return getString(R.string.win_text_dollar_format, (double) win * creditValue);
    } else {
      return getString(R.string.win_text_credits_format, win);
    }
  }

  private String getPurseString(int purse, double creditValue, boolean viewAsDollars) {
    if (viewAsDollars) {
      return getString(R.string.purse_text_dollar_format, (double) purse * creditValue);
    }
    return getString(R.string.purse_text_credits_format, purse);
  }

  private String getBetString(int bet, double creditValue, boolean viewAsDollars) {
    if (viewAsDollars) {
      return getString(R.string.bet_text_dollar_format, (double) bet * creditValue);
    } else {
      return getString(R.string.bet_text_credits_format, bet);
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game);
    Bundle extras = getIntent().getExtras();
    assert extras != null;
    game = new Game((int) extras.get(PURSE_KEY), (double) extras.get(CREDIT_VALUE_KEY),
        new SecureRandom());
    paytableId = extras.getLong(PAYTABLE_ID_KEY);
    paytableName = extras.getString(PAYTABLE_NAME_KEY);
    sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
    viewAsDollars = sharedPref.getBoolean(CURRENCY_VIEW_PREF, false);
    setupButtons();
    setupTextViews();
    this.setTitle(paytableName);
    if (game.getPurse() <= 0 || game.getCreditValue() <= 0) {
      gameOver();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.options, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    boolean handled = true;
    Intent intent;
    switch (item.getItemId()) {
      default:
        handled = super.onOptionsItemSelected(item);
        break;
      case android.R.id.home:
        // Ends the game.
        // TODO Ask "Are you sure you want to quit?"
        onBackPressed();
        break;
      case R.id.switch_currency_view:
        viewAsDollars = !viewAsDollars;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(CURRENCY_VIEW_PREF, viewAsDollars);
        editor.apply();
        winView.setText(getWinString(game.getWin(), game.getCreditValue(), viewAsDollars));
        purseView.setText(getPurseString(game.getPurse(), game.getCreditValue(), viewAsDollars));
        betView.setText(getBetString(game.getBet(), game.getCreditValue(), viewAsDollars));
        break;
      case R.id.view_payout_table:
        intent = new Intent(this, PaytableActivity.class);
        intent.putExtra(PAYTABLE_ID_KEY, paytableId);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra(PAYTABLE_NAME_KEY, paytableName);
        startActivity(intent);
        break;
    }
    return handled;
  }

  private void resetGame() {
    game.getPlayerHand().setBestHand(null);
    game.setBet(0);
    betView.setText(getBetString(game.getBet(), game.getCreditValue(), viewAsDollars));
    dealButton.setEnabled(false);
    drawButton.setEnabled(false);
    for (CardButton card : cardButtons) {
      card.setEnabled(false);
      card.setChecked(false);
    }
    if (game.getPurse() >= BET_MAX) {
      betMaxButton.setEnabled(true);
      betOneButton.setEnabled(true);
    } else if (game.getPurse() > 0) {
      betOneButton.setEnabled(true);
    } else {
      gameOver();
    }
  }

  private void setupButtons() {
    mainButton = findViewById(R.id.main_button);
    helpButton = findViewById(R.id.help_button);
    dealButton = findViewById(R.id.deal_button);
    dealButton.setEnabled(false);
    drawButton = findViewById(R.id.draw_button);
    drawButton.setEnabled(false);
    betOneButton = findViewById(R.id.bet1_button);
    betMaxButton = findViewById(R.id.bet_max_button);
    if (game.getPurse() < BET_MAX) betMaxButton.setEnabled(false);
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
      card.setOnClickListener((v) -> card.toggle());
      //TODO Change card ability on long press
    }

    mainButton.setOnClickListener((v) -> {
      // Returns the main screen to the user but does not end the game unless the user selects
      // a new game.
      Intent intent = new Intent(this, SplashActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
      startActivity(intent);
    });

    helpButton.setOnClickListener((v) -> {
      Intent intent = new Intent(this, HelpActivity.class);
      startActivity(intent);
    });

    betOneButton.setOnClickListener((v) -> {
      betOne();
      dealButton.setEnabled(true);
    });

    betMaxButton.setOnClickListener((v) -> {
      betMax();
      dealButton.setEnabled(true);
    });

    dealButton.setOnClickListener((v) -> {
      new DealTask().execute(game.getPlayerHand());
    });

    drawButton.setOnClickListener((v) -> new DrawTask().execute(game.getPlayerHand()));
  }

  private void setupTextViews() {
    winningHandView = findViewById(R.id.win_notifier);
    winView = findViewById(R.id.win_view);
    betView = findViewById(R.id.bet_view);
    purseView = findViewById(R.id.purse_view);
    winningHandView.setText(EMPTY_STRING);
    winView.setText(getWinString(game.getWin(), game.getCreditValue(), viewAsDollars));
    winView.setVisibility(View.INVISIBLE);
    betView.setText(getBetString(game.getBet(), game.getCreditValue(), viewAsDollars));
    purseView.setText(getPurseString(game.getPurse(), game.getCreditValue(), viewAsDollars));
  }

  /**
   * Asynchronous task for the first card deal and evaluation of any potential winning hand.
   */
  private class DealTask extends AsyncTask<PlayerHand, Void, Void> {

    @Override
    protected void onPreExecute() {
      game.getDeck().shuffle();
      game.getDeck().dealAndReplace(game.getPlayerHand());
    }

    @Override
    protected Void doInBackground(PlayerHand... playerHands) {
      for (PokerHand pokerHand : PaytableDatabase.getInstance(getApplicationContext())
          .getPokerHandDao().selectPokerHandsByBetOneFromPaytable(paytableId)) {
        if (converter.parseRuleSequence(pokerHand.getRuleSequence(), playerHands[0])) {
          playerHands[0].setBestHand(pokerHand);
          return null;
        }
      }
      //no best hand found
      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      if (firstDeal) {
        for (CardButton card : cardButtons) {
          card.setVisibility(View.VISIBLE);
          card.setEnabled(true);
        }
      }
      winView.setVisibility(View.INVISIBLE);
      winningHandView.setText(EMPTY_STRING);
      game.setPurse(game.getPurse() - game.getBet());
      purseView.setText(getPurseString(game.getPurse(), game.getCreditValue(), viewAsDollars));
      betView.setText(getBetString(game.getBet(), game.getCreditValue(), viewAsDollars));
      for (int i = 0; i < game.getPlayerHand().size(); i++) {
        //TODO Animate card display
        displayCard(i);
      }
      if (game.getPlayerHand().getBestHand().getBetOneValue() > 0) {
        winningHandView.setText(game.getPlayerHand().getBestHand().getName());
      }
      dealButton.setEnabled(false);
      drawButton.setEnabled(true);
      betOneButton.setEnabled(false);
      betMaxButton.setEnabled(false);
      for (CardButton card : cardButtons) {
        card.setEnabled(true);
      }
    }
  }

  /**
   * Asynchronous task for the subsequent card draw, winnings evaluated and game reset.
   */
  private class DrawTask extends AsyncTask<PlayerHand, Void, Void> {

    @Override
    protected void onPreExecute() {
      for (int i = 0; i < HAND_SIZE; i++) {
        if (!cardButtons[i].isChecked()) {
          game.getDeck().push(game.getPlayerHand().get(i));
          game.getPlayerHand().set(i, game.getDeck().remove(0));
        }
      }
    }

    @Override
    protected Void doInBackground(PlayerHand... playerHands) {
      for (PokerHand pokerHand : PaytableDatabase.getInstance(getApplicationContext())
          .getPokerHandDao().selectPokerHandsByBetOneFromPaytable(paytableId)) {
        if (converter.parseRuleSequence(pokerHand.getRuleSequence(), playerHands[0])) {
          playerHands[0].setBestHand(pokerHand);
          break;
        }
      }
      if (game.getBet() < BET_MAX) {
        game.setWin(game.getBet() * game.getPlayerHand().getBestHand().getBetOneValue());
      } else {
        game.setWin(game.getPlayerHand().getBestHand().getBetFiveValue());
      }
      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      for (int i = 0; i < game.getPlayerHand().size(); i++) {
        //TODO Animate card display
        displayCard(i);
      }
      collectWinnings();
      resetGame();
    }
  }
}

