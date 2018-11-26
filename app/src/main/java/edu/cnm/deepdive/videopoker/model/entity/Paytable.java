package edu.cnm.deepdive.videopoker.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * A collection of poker hands and payouts for a specific variant of a Video Poker game.
 */
@Entity
public class Paytable {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "paytable_id")
  private long id;
  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String name;

  /**
   * @return the ID of this paytable.
   */
  public long getId() {
    return id;
  }

  /**
   * @param id an ID to assign to this paytable.
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * @return the name of the game this paytable is associated with.
   */
  public String getName() {
    return name;
  }

  /**
   * @param name a name to specify this paytable.
   */
  public void setName(String name) {
    this.name = name;
  }

}
