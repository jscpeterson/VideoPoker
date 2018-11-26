package edu.cnm.deepdive.videopoker;

import android.app.Application;
import edu.cnm.deepdive.videopoker.model.entity.Paytable;
import java.util.List;

/**
 * This is the master Application class, primarily used to reference local variables
 */
public class GameApplication extends Application {

  static private GameApplication instance;
  private List<Paytable> localDb;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
  }

  /**
   * @return the instance of this application.
   */
  static public GameApplication getInstance() {
    return instance;
  }

  /**
   * @return a local list of paytables in the database.
   */
  public List<Paytable> getLocalDb() {
    return localDb;
  }

  /**
   * @param localDb a list of paytables from the database to be recorded locally. 
   */
  public void setLocalDb(List<Paytable> localDb) {
    this.localDb = localDb;
  }

}
