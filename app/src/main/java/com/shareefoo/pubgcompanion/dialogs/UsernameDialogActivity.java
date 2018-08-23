package com.shareefoo.pubgcompanion.dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.shareefoo.pubgcompanion.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UsernameDialogActivity extends AppCompatActivity {

    @BindView(R.id.editText_playerName)
    EditText editTextPlayerName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog_username);
        setFinishOnTouchOutside(false);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_search)
    public void search(View view) {
        String playerName = editTextPlayerName.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("player_name", playerName);
        setResult(RESULT_OK, intent);
        finish();
    }


}
