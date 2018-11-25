package edu.cnm.deepdive.videopoker.controller;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import edu.cnm.deepdive.videopoker.R;
import edu.cnm.deepdive.videopoker.model.dao.PokerHandDao;
import edu.cnm.deepdive.videopoker.model.db.PaytableDatabase;
import edu.cnm.deepdive.videopoker.model.entity.PokerHand;
import java.util.List;

public class PaytableActivity extends AppCompatActivity {

  private static final String PAYTABLE_ID_KEY = "paytableId";
  private static final String PAYTABLE_NAME_KEY = "paytableNameKey";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle extras = getIntent().getExtras();
    setContentView(R.layout.activity_paytable);
    long paytableId = extras.getLong(PAYTABLE_ID_KEY);
    String paytableName = extras.getString(PAYTABLE_NAME_KEY);
    this.setTitle(paytableName);
    new GetPaytableData().execute(paytableId);
  }

  @Override
  protected void onPause() {
    super.onPause();
    finish();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
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
        bet1View.setOnClickListener( (v) -> {
          //TODO Open ChangePayoutDialog
        });
        TextView bet2View = (TextView) row.getChildAt(2);
        bet2View.setText(Integer.toString(hand.getBetOneValue()*2));
        TextView bet3View = (TextView) row.getChildAt(3);
        bet3View.setText(Integer.toString(hand.getBetOneValue()*3));
        TextView bet4View =  (TextView) row.getChildAt(4);
        bet4View.setText(Integer.toString(hand.getBetOneValue()*4));
        TextView bet5View  = (TextView) row.getChildAt(5);
        bet5View.setText(Integer.toString(hand.getBetFiveValue()));
        tableLayout.addView(row);
      }
      super.onPostExecute(pokerHands);
    }

  }

  private class ChangePayout extends AsyncTask<Integer, Void, List<PokerHand>> {

    //TODO implement change paytable ability

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
    }

    @Override
    protected List<PokerHand> doInBackground(Integer... integers) {
      return null;
    }

    @Override
    protected void onPostExecute(List<PokerHand> pokerHands) {
      super.onPostExecute(pokerHands);
    }

  }

  private class ResetDefaultsTask extends AsyncTask<Integer, Void, Void> {

    //TODO implement reset default paytable button (from CSV)

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Integer... integers) {
      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);
    }

  }

}