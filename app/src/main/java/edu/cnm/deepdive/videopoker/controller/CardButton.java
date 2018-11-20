package edu.cnm.deepdive.videopoker.controller;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;

public class CardButton extends android.support.v7.widget.AppCompatImageView implements Checkable {

  //https://stackoverflow.com/questions/33942116/state-checked-doesnt-toggle-imageview-on-and-off/34020870

  private boolean checked;

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
    setChecked(!checked);
  }

  public boolean isChecked() {
    return checked;
  }

  public void setChecked(boolean checked) {
    if (this.checked != checked) {
      this.checked = checked;
      refreshDrawableState();
    }
  }
}
