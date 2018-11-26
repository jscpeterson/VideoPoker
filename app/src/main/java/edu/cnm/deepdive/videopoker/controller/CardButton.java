package edu.cnm.deepdive.videopoker.controller;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;

/**
 * This button represents a playing card in a hand. It is a custom Button object based on an
 * ImageView that implements behavior of the Checkable class. While active, the user can toggle
 * this button to be checked (held) or unchecked and will update the drawable state. Drawable
 * resources are not actually retrieved here, this class primarily handles the functionality for
 * the toggle feature.
 */
public class CardButton extends android.support.v7.widget.AppCompatImageView implements Checkable {

  private static final int[] CHECKED_STATE_SET = {
      android.R.attr.state_checked
  };

  private boolean checked;

  @Override
  public int[] onCreateDrawableState(int extraSpace) {
    final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
    if (isChecked()) {
      mergeDrawableStates(drawableState, CHECKED_STATE_SET);
    }
    return drawableState;
  }

  /**
   * Constructor inherited from ImageView.
   */
  public CardButton(Context context) {
    super(context);
  }

  /**
   * Constructor inherited from ImageView.
   */
  public CardButton(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  /**
   * Constructor inherited from ImageView.
   */
  public CardButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  /**
   * Toggles the "checked" flag.
   */
  public void toggle() {
    setChecked(!checked);
  }

  /**
   * @return the current state of the "checked" flag.
   */
  public boolean isChecked() {
    return checked;
  }

  /**
   * Sets the checked flag to a desired state if it is not already in that state, and refreshes the
   * drawable view if not.
   * @param checked the intended state of the "checked" flag.
   */
  public void setChecked(boolean checked) {
    if (this.checked != checked) {
      this.checked = checked;
      refreshDrawableState();
    }
  }
}
