package edu.cnm.deepdive.videopoker.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import edu.cnm.deepdive.videopoker.GameApplication;
import edu.cnm.deepdive.videopoker.R;
import edu.cnm.deepdive.videopoker.model.db.PaytableDatabase;

/**
 * This is the launcher screen activity that opens when the user opens the app. The app will call
 * the PaytableDatabase to populate if it does not exist yet. The user chooses options for a new
 * game from here, providing values for the purse and credit values. When the user selects "Play!"
 * it will open the GameSelectDialog.
 */
public class SplashActivity extends AppCompatActivity {

  //CONSTANTS
  private static final String PURSE_KEY = "purse";
  private static final String CREDIT_VALUE_KEY = "creditValue";
  private static final int PURSE_DEFAULT = 50;
  private static final double CREDIT_VALUE_DEFAULT = 0.25;
  private static final String GAME_SELECT_DIALOG_TAG = "dialog";

  //FIELDS
  /**
   * Dialog fragment to open when the user selects Play.
   */
  private GameSelectDialog gameSelectDialog;
  /**
   * Editable text field for credits.
   */
  private EditText purseEdit;
  /**
   * Editable text field for credit value.
   */
  private EditText creditValueEdit;

  @SuppressLint("SetTextI18n")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    new Thread(() -> PaytableDatabase.getInstance(this).getPaytableDao().select()).start();
    setContentView(R.layout.activity_splash);
    Button playButton = findViewById(R.id.splash_play_button);
    purseEdit = findViewById(R.id.splash_purse_edit);
    creditValueEdit = findViewById(R.id.splash_credit_edit);
    purseEdit.setText(Integer.toString(PURSE_DEFAULT));
    creditValueEdit.setText(Double.toString(
        CREDIT_VALUE_DEFAULT)); //TODO change decimal formatting (right now you can do $5.325)
    playButton.setOnClickListener((v) -> {
      gameSelectDialog = new GameSelectDialog();
      Bundle arguments = new Bundle();
      int purseInput = Integer.parseInt(purseEdit.getText().toString());
      double creditValueInput = Double.parseDouble(creditValueEdit.getText().toString());
      arguments.putInt(PURSE_KEY, purseInput);
      arguments.putDouble(CREDIT_VALUE_KEY, creditValueInput);
      gameSelectDialog.setArguments(arguments);
      gameSelectDialog.show(getSupportFragmentManager(), GAME_SELECT_DIALOG_TAG);
    });
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (gameSelectDialog != null) {
      gameSelectDialog.dismiss();
    }
  }

  /**
   * This method is called from the PaytableDatabase to confirm that the database does not need to
   * be created, and data can be extracted from it.
   */
  public void ready() {
    new SetupTask(this).execute();
  }

  /**
   * This asynchronous task sends information about the database to the GameApplication for local
   * use. It is called from the ready method within the PaytableDatabase class.
   */
  private static class SetupTask extends AsyncTask<Void, Void, Void> {

    GameApplication instance = GameApplication.getInstance();
    private Context context;

    private SetupTask(Context context) {
      this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
      instance.setLocalDb(PaytableDatabase.getInstance(context.getApplicationContext())
          .getPaytableDao().select());
      return null;
    }
  }
}
