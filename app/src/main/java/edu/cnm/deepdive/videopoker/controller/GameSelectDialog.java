package edu.cnm.deepdive.videopoker.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.cnm.deepdive.videopoker.GameApplication;
import edu.cnm.deepdive.videopoker.R;
import edu.cnm.deepdive.videopoker.model.entity.Paytable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This DialogFragment opens from the SplashActivity and provides a list of games in the database
 * for the user to select from. It does not directly interact with the database, it assumes that
 * local data has already been provided in GameApplication.
 */
public class GameSelectDialog extends DialogFragment {

  //CONSTANTS
  private static final String PURSE_KEY = "purse";
  private static final String CREDIT_VALUE_KEY = "creditValue";
  private static final String PAYTABLE_ID_KEY = "paytableId";
  private static final String PAYTABLE_NAME_KEY = "paytableNameKey";

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_game_select, null);
    ListView gameList = view.findViewById(R.id.game_select_list_view);
    ArrayList<String> items = new ArrayList<>();
    for (Paytable paytable : GameApplication.getInstance().getLocalDb()) {
      items.add(paytable.getName());
    }
    ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
        android.R.layout.simple_list_item_1, items);
    gameList.setOnItemClickListener((parent, view1, position, id) -> {
      Intent intent = new Intent(getContext(), GameActivity.class);
      Bundle arguments = this.getArguments();
      assert arguments != null;
      intent.putExtra(PURSE_KEY, arguments.getInt(PURSE_KEY));
      intent.putExtra(CREDIT_VALUE_KEY, arguments.getDouble(CREDIT_VALUE_KEY));
      intent.putExtra(PAYTABLE_ID_KEY, (long) position + 1);
      intent.putExtra(PAYTABLE_NAME_KEY,
          GameApplication.getInstance().getLocalDb().get(position).getName());
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
      startActivity(intent);
    });
    gameList.setAdapter(adapter);
    return view;
  }
}
