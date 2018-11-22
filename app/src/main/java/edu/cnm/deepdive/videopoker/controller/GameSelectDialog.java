package edu.cnm.deepdive.videopoker.controller;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import edu.cnm.deepdive.videopoker.GameApplication;
import edu.cnm.deepdive.videopoker.R;
import edu.cnm.deepdive.videopoker.model.entity.Paytable;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Text;

public class GameSelectDialog extends DialogFragment {

  private static final String PURSE_KEY = "purse";
  private static final String CREDIT_VALUE_KEY = "creditValue";
  private static final String PAYTABLE_ID_KEY = "paytableId";
  private static final String PAYTABLE_NAME_KEY = "paytableNameKey";

  ListView gameList;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_game_select, null);
    getDialog().setTitle("Pick a Game");
    gameList = view.findViewById(R.id.game_select_list_view);
    ArrayList<String> items = new ArrayList<>();
    for (Paytable paytable : GameApplication.getInstance().getLocalDb()) {
      items.add(paytable.getName());
    }
    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
        android.R.layout.simple_list_item_1, items);
    gameList.setOnItemClickListener((parent, view1, position, id) -> {
      Intent intent = new Intent(getContext(), GameActivity.class);
      // TODO Get these values from edit text views.
      intent.putExtra(PURSE_KEY, 100);
      intent.putExtra(CREDIT_VALUE_KEY, 0.50);
      intent.putExtra(PAYTABLE_ID_KEY, (long) position + 1);
      intent.putExtra(PAYTABLE_NAME_KEY, GameApplication.getInstance().getLocalDb().get(position).getName());
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
      startActivity(intent);
    });
    gameList.setAdapter(adapter);
    return view;
  }
}
