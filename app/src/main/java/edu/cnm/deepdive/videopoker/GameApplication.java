package edu.cnm.deepdive.videopoker;

import android.app.Application;
import edu.cnm.deepdive.videopoker.model.entity.Paytable;
import java.util.List;

public class GameApplication extends Application {

  static private GameApplication instance;
  private List<Paytable> localDb;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
  }

  static public GameApplication getInstance() {
    return instance;
  }

  public List<Paytable> getLocalDb() {
    return localDb;
  }

  public void setLocalDb(List<Paytable> localDb) {
    this.localDb = localDb;
  }

}
