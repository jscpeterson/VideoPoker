package edu.cnm.deepdive.videopoker.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import edu.cnm.deepdive.videopoker.R;
import edu.cnm.deepdive.videopoker.model.db.Paytable;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

public class SplashActivity extends AppCompatActivity {


  private Button playButton;

  public SplashActivity() throws IOException {
  }

  @Override
  protected void onCreate(Bundle savedInstanceState){
     buildPaytable(R.raw.jacksorbetter);

    super.onCreate(savedInstanceState);

    //TODO allow options for additional games
    //TODO import CSV Files

    Paytable table1 = Paytable.getInstance(SplashActivity.this);


    setContentView(R.layout.activity_splash);
    playButton = findViewById(R.id.splash_play_button);
    playButton.setOnClickListener((v) -> {
      Intent intent = new Intent(SplashActivity.this, GameActivity.class);
      startActivity(intent);
    });
  }

  void buildPaytable(int gameCsvFileId) {
    InputStream csvInputStream = getResources().openRawResource(gameCsvFileId);
    CSVParser csvParser = null;
    try {
      csvParser = new CSVParser(new InputStreamReader(csvInputStream), CSVFormat.DEFAULT);
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(csvParser.iterator().next());
    //TODO build and return a paytable
  }



}
