package edu.cnm.deepdive.videopoker.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import edu.cnm.deepdive.videopoker.R;
import edu.cnm.deepdive.videopoker.model.dao.PokerHandDao;
import edu.cnm.deepdive.videopoker.model.db.PaytableDatabase;
import edu.cnm.deepdive.videopoker.model.entity.Paytable;
import edu.cnm.deepdive.videopoker.model.entity.PokerHand;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class PaytableActivity extends AppCompatActivity {

  private static final String PAYTABLE_ID_KEY = "paytableId";
  private static final String PAYTABLE_NAME_KEY = "paytableNameKey";
  private static final int INDEX_GAME_NAME = 0;
  private static final int INDEX_HAND_NAME = 1;
  private static final int INDEX_RULE_SEQUENCE = 2;
  private static final int INDEX_BET_ONE_VALUE = 3;
  private static final int INDEX_OVERLOADED_PARAM = 4;
  private long paytableId;
  private String paytableName;

  public long getPaytableId() {
    return paytableId;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle extras = getIntent().getExtras();
    setContentView(R.layout.activity_paytable);
    paytableId = extras.getLong(PAYTABLE_ID_KEY);
    paytableName = extras.getString(PAYTABLE_NAME_KEY);
    this.setTitle(paytableName);
    new GetPaytableData().execute(paytableId);
  }

  @Override
  protected void onPause() {
    super.onPause();
    finish();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.paytable_options, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
      return true;
    }
    if (item.getItemId() == R.id.revert_defaults) {
      new ResetDefaultsTask().execute();
      return true;
    }
    return (super.onOptionsItemSelected(item));
  }

  //TODO make static
  private class GetPaytableData extends AsyncTask<Long, Void, List<PokerHand>> {

    List<PokerHand> paytable;
    PaytableDatabase db;
    PokerHandDao dao;

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
    }

    @Override
    protected List<PokerHand> doInBackground(Long... longs) {
      db = PaytableDatabase.getInstance(getApplicationContext());
      dao = db.getPokerHandDao();
      paytable = dao.selectPokerHandsByBetOneFromPaytable(longs[0]);
      return null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onPostExecute(List<PokerHand> pokerHands) {
      TableLayout tableLayout = findViewById(R.id.paytable_layout);

      for (PokerHand hand : paytable) {
        if (!hand.isShowInTable()) {
          //Skip loop if hand has showInTable flag set to false (value is 0 or there is a duplicate)
          continue;
        }
        TableRow row = (TableRow)
            LayoutInflater.from(PaytableActivity.this).inflate(R.layout.paytable_row,
                findViewById(R.id.paytable_row_layout), false);
        TextView pokerHandView = (TextView) row.getChildAt(0);
        pokerHandView.setText(hand.getName());
        TextView bet1View = (TextView) row.getChildAt(1);
        bet1View.setText(Integer.toString(hand.getBetOneValue()));
        bet1View.setOnClickListener((v) -> {
          AlertDialog.Builder changePayoutDialog = new AlertDialog.Builder(PaytableActivity.this,
              R.style.alert_dialog);
          EditText editText = new EditText(
              new ContextThemeWrapper(PaytableActivity.this, R.style.change_payout_edit_text));
          changePayoutDialog.setMessage(String.format(getString(R.string.change_payout_format),
              hand.getName()));
          changePayoutDialog.setView(editText);
          changePayoutDialog
              .setPositiveButton(R.string.change_payout_pos, (dialog, whichButton) -> {
                int value = Integer.valueOf(editText.getText().toString());
                hand.setBetOneValue(value);
                hand.setBetFiveValue(value * 5);
                new ChangePayoutTask().execute(hand);
              });
          changePayoutDialog
              .setNegativeButton(R.string.change_payout_neg, (dialog, whichButton) -> {
                dialog.cancel();
              });
          changePayoutDialog.show();
        });
        TextView bet2View = (TextView) row.getChildAt(2);
        bet2View.setText(Integer.toString(hand.getBetOneValue() * 2));
        TextView bet3View = (TextView) row.getChildAt(3);
        bet3View.setText(Integer.toString(hand.getBetOneValue() * 3));
        TextView bet4View = (TextView) row.getChildAt(4);
        bet4View.setText(Integer.toString(hand.getBetOneValue() * 4));
        TextView bet5View = (TextView) row.getChildAt(5);
        bet5View.setText(Integer.toString(hand.getBetFiveValue()));
        bet5View.setOnClickListener((v) -> {
          AlertDialog.Builder changePayoutDialog = new AlertDialog.Builder(PaytableActivity.this,
              R.style.alert_dialog);
          EditText editText = new EditText(
              new ContextThemeWrapper(PaytableActivity.this, R.style.change_payout_edit_text));
          changePayoutDialog.setMessage(String.format(getString(R.string.change_payout_format),
              hand.getName()));
          changePayoutDialog.setView(editText);
          changePayoutDialog
              .setPositiveButton(R.string.change_payout_pos, (dialog, whichButton) -> {
                int value = Integer.valueOf(editText.getText().toString());
                hand.setBetFiveValue(value);
                new ChangePayoutTask().execute(hand);
              });
          changePayoutDialog
              .setNegativeButton(R.string.change_payout_neg, (dialog, whichButton) -> {
                dialog.cancel();
              });
          changePayoutDialog.show();
        });
        tableLayout.addView(row);
      }
      super.onPostExecute(pokerHands);
    }

  }

  private class ChangePayoutTask extends AsyncTask<PokerHand, Void, Void> {

    @Override
    protected Void doInBackground(PokerHand... pokerHands) {
      PokerHand hand = pokerHands[0];
      PaytableDatabase db = PaytableDatabase.getInstance(getApplicationContext());
      PokerHandDao dao = db.getPokerHandDao();
      dao.updateBetOneValue(PaytableActivity.this.getPaytableId(), hand.getName(),
          hand.getBetOneValue());
      dao.updateBetFiveValue(PaytableActivity.this.getPaytableId(), hand.getName(),
          hand.getBetFiveValue());
      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      Intent intent = getIntent();
      finish();
      startActivity(intent);
      super.onPostExecute(aVoid);
    }

  }

  private class ResetDefaultsTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
      PaytableDatabase db = PaytableDatabase
          .getInstance(PaytableActivity.this.getApplicationContext());
      PokerHandDao pokerHandDao = db.getPokerHandDao();
      InputStream paytablesInputStream = PaytableActivity.this.getResources()
          .openRawResource(R.raw.paytables);
      try {
        CSVParser paytablesCsvParser = new CSVParser(new InputStreamReader(paytablesInputStream),
            CSVFormat.DEFAULT);
        for (CSVRecord paytableRecord : paytablesCsvParser.getRecords()) {
          if (paytableRecord.get(INDEX_GAME_NAME).equals(paytableName)) {
            pokerHandDao.updateBetOneValue(paytableId, paytableRecord.get(INDEX_HAND_NAME), Integer.valueOf(paytableRecord.get(INDEX_BET_ONE_VALUE)));
            if (paytableRecord.size() == INDEX_OVERLOADED_PARAM + 1) {
              pokerHandDao.updateBetFiveValue(paytableId, paytableRecord.get(INDEX_HAND_NAME),
                  Integer.valueOf(paytableRecord.get(INDEX_OVERLOADED_PARAM)));
            }
            else {
              pokerHandDao.updateBetFiveValue(paytableId, paytableRecord.get(INDEX_HAND_NAME),
                  Integer.valueOf(paytableRecord.get(INDEX_BET_ONE_VALUE)) * 5);
            }
          }
        }
      } catch (IOException e) {
        System.out.println("Database unable to populate from CSV file.");
      }
      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      Intent intent = getIntent();
      finish();
      startActivity(intent);
      super.onPostExecute(aVoid);
    }

  }

}