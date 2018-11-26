package edu.cnm.deepdive.videopoker.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import edu.cnm.deepdive.videopoker.R;
import edu.cnm.deepdive.videopoker.model.Converter;
import edu.cnm.deepdive.videopoker.model.Game;
import edu.cnm.deepdive.videopoker.model.PlayerHand;
import edu.cnm.deepdive.videopoker.model.db.PaytableDatabase;
import edu.cnm.deepdive.videopoker.model.entity.PokerHand;
import java.security.SecureRandom;

/**
 * This is the primary activity for the application where the user can play the Video Poker game
 * they have selected. When the user selects Back from this screen, they will be prompted to exit
 * the program. The user can select a new game from the MAIN button on this screen. The user can
 * bet, deal, or draw as long as they have enough credits in their purse to continue playing. If
 * there are zero credits or the credit value passed in is zero the game will display "Game Over"
 * and only allow the user to navgiate off of the activity. The status of the purse is held in the
 * Game class.
 */
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
  private static final String DRAWABLE = "drawable";
  private static final String PACKAGE = "edu.cnm.deepdive.videopoker";

  //EXTRAS
  /**
   * The ID of the paytable being used for the current game.
   */
  private long paytableId;
  /**
   * The name of the game currently being played.
   */
  private String paytableName;

  //BUTTONS
  /**
   * The "Deal" button.
   */
  private Button dealButton;
  /**
   * The "Draw" button.
   */
  private Button drawButton;
  /**
   * A button to accumulate the user's bet by one.
   */
  private Button betOneButton;
  /**
   * A button to maximize the user's bet. (note: maximum bet is five)
   */
  private Button betMaxButton;
  /**
   * An array of buttons to display the cards in the user's hand.
   */
  private CardButton[] cardButtons;

  //FLAGS
  /**
   * A flag to indicate that the game is in it's initial state and there has not been a deal yet.
   * Initialized as a field intentionally.
   */
  private boolean firstDeal = true;
  /**
   * A flag to specify the display in credit view or currency view.
   */
  private boolean viewAsDollars;

  //TEXT VIEWS
  /**
   * A text view to display the name of a winning hand to the user, or any other important message.
   */
  private TextView winningHandView;
  /**
   * A text view to display the amount of credits won on a successful game.
   */
  private TextView winView;
  /**
   * A text view to display the user's current bet.
   */
  private TextView betView;
  /**
   * A text view to display the number of credits the user is currently playing with.
   */
  private TextView purseView;

  //OBJECTS
  /**
   * A class to hold information about the game, such as the purse, deck, and hand.
   */
  private Game game;
  /**
   * The converter class used to parse rule sequences from the database.
   */
  private Converter converter = new Converter();
  /**
   * Shared preferences for the application.
   */
  private SharedPreferences sharedPref;

  /**
   * This method provides functionality for the Bet One button. It is available as long as there are
   * credits in the Game purse and the user has not hit the maximum bet.
   */
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

  /**
   * This method provides functionality for the Bet Max button. The maximum bet is assumed to be
   * five. The button is available as long as there are at least five credits in the game purse to
   * maximize the bet with and the user has not placed any bet yet.
   */
  private void betMax() {
    game.setBet(BET_MAX);
    betOneButton.setEnabled(false);
    betMaxButton.setEnabled(false);
    betView.setText(getBetString(game.getBet(), game.getCreditValue(), viewAsDollars));
  }

  /**
   * This method displays the result of a winning hand to the user and accumulates the win into the
   * Game purse.
   */
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

  /**
   * This method retrieves the drawable resource corresponding to the a card within the player's
   * hand.
   *
   * @param index the index of the card in the player's hand.
   */
  private void displayCard(int index) {
    String resourceId = game.getPlayerHand().get(index).getResourceId();
    int identifier = getResources()
        .getIdentifier(resourceId, DRAWABLE, PACKAGE);
    cardButtons[index].setImageResource(identifier);
  }

  /**
   * This method disables all gameplay buttons and displays Game Over text to indicate the end of
   * the game.
   */
  private void gameOver() {
    winningHandView.setText(R.string.purse_empty_text);
    betOneButton.setEnabled(false);
    betMaxButton.setEnabled(false);
    dealButton.setEnabled(false);
    drawButton.setEnabled(false);
  }

  /**
   * This method formats the "WIN" string (referring to the number credits won from a winning hand,
   * not the name of the winning hand) to be passed into an appropriate text view.
   *
   * @param win the amount of credits won.
   * @param creditValue the monetary value of each credit.
   * @param viewAsDollars the state of the "viewAsDollars" flag to designate which display format to
   * use.
   * @return a formatted String to display the state to the user.
   */
  private String getWinString(int win, double creditValue, boolean viewAsDollars) {
    if (viewAsDollars) {
      return getString(R.string.win_text_dollar_format, (double) win * creditValue);
    } else {
      return getString(R.string.win_text_credits_format, win);
    }
  }

  /**
   * This method formats the "CREDITS" string to be passed into an appropriate text view.
   *
   * @param purse the amount of credits in the purse.
   * @param creditValue the monetary value of each credit.
   * @param viewAsDollars the state of the "viewAsDollars" flag to designate which display format to
   * use.
   * @return a formatted String to display the state to the user.
   */
  private String getPurseString(int purse, double creditValue, boolean viewAsDollars) {
    if (viewAsDollars) {
      return getString(R.string.purse_text_dollar_format, (double) purse * creditValue);
    }
    return getString(R.string.purse_text_credits_format, purse);
  }

  /**
   * This method formats the "BET" string to be passed into an appropriate text view.
   *
   * @param bet the amount of credits in the bet.
   * @param creditValue the monetary value of each credit.
   * @param viewAsDollars the state of the "viewAsDollars" flag to designate which display format to
   * use.
   * @return a formatted String to display the state to the user.
   */
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

  /**
   * Pressing the back button from this screen opens a dialog to confirm that the user would like to
   * exit the application.
   */
  @Override
  public void onBackPressed() {
    AlertDialog.Builder exitDialog = new AlertDialog.Builder(this, R.style.alert_dialog);
    exitDialog.setMessage(R.string.exit_dialog_text);
    exitDialog.setPositiveButton(R.string.exit_dialog_pos, (dialog, which) -> System.exit(0));
    exitDialog.setNegativeButton(R.string.exit_dialog_neg, (dialog, which) -> dialog.cancel());
    exitDialog.show();
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
        intent.putExtra(PAYTABLE_NAME_KEY, paytableName);
        startActivity(intent);
        break;
      case R.id.hand_analysis:
        Toast.makeText(this, R.string.not_yet_implemented, Toast.LENGTH_SHORT).show();
        break;
      case R.id.autoplay:
        Toast.makeText(this, R.string.not_yet_implemented, Toast.LENGTH_SHORT).show();
        break;
    }
    return handled;
  }

  /**
   * This method is called upon the completion of a draw and sets the buttons and TextViews to an
   * appropriate state for the user to continue playing based on the number of credits still
   * available.
   */
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

  /**
   * This method initializes the buttons and listeners for the activity and sets their status to the
   * initial state of the game.
   */
  private void setupButtons() {
    Button mainButton = findViewById(R.id.main_button);
    Button helpButton = findViewById(R.id.help_button);
    dealButton = findViewById(R.id.deal_button);
    dealButton.setEnabled(false);
    drawButton = findViewById(R.id.draw_button);
    drawButton.setEnabled(false);
    betOneButton = findViewById(R.id.bet1_button);
    betMaxButton = findViewById(R.id.bet_max_button);
    if (game.getPurse() < BET_MAX) {
      betMaxButton.setEnabled(false);
    }
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
      card.setOnLongClickListener((v) -> {
        //TODO Open ChangeCardDialog
        Toast.makeText(this, R.string.not_yet_implemented, Toast.LENGTH_SHORT).show();
        return true;
      });
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
    dealButton.setOnClickListener((v) -> new DealTask().execute(game.getPlayerHand()));
    drawButton.setOnClickListener((v) -> new DrawTask().execute(game.getPlayerHand()));
  }

  /**
   * This method initializes the TextViews for the activity.
   */
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
   * Asynchronous task for the first deck shuffle and card deal. Accepts a PlayerHand as a parameter
   * to deal to.  database accessed for the evaluation of any potential winning hand. Prepares the
   * UI for the "Draw" state on completion.
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
   * Asynchronous task for the subsequent card draw. Accepts a PlayerHand to evaluate, cards are
   * dealt from the deck to replace any cards that are not checked as held. The resulting hand is
   * evaluated against the database. Once evaluated, the game updates with any win, and the UI is
   * set to allow the user to continue playing if they still have credits to bet.
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

