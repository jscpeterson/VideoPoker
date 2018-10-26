package edu.cnm.deepdive.videopoker.controller;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import edu.cnm.deepdive.videopoker.R;

public class CardButton extends android.support.v7.widget.AppCompatImageView implements Checkable {

  //https://stackoverflow.com/questions/33942116/state-checked-doesnt-toggle-imageview-on-and-off/34020870

  private boolean mChecked;

  private static final int[] CHECKED_STATE_SET = {
      android.R.attr.state_checked
  };

  @Override
  public int[] onCreateDrawableState(int extraSpace) {
    final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
    if (isChecked()) {
      mergeDrawableStates(drawableState, CHECKED_STATE_SET);
    }
    return drawableState;
  }

  public CardButton(Context context) {
    super(context);
  }

  public CardButton(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public CardButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public void toggle() {
    setChecked(!mChecked);
  }

  public boolean isChecked() {
    return mChecked;
  }

  public void setChecked(boolean checked) {
    if (mChecked != checked) {
      mChecked = checked;
      refreshDrawableState();
    }
  }
}
