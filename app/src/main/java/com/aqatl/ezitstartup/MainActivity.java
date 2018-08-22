package com.aqatl.ezitstartup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
	public static final String PLAYER_COUNT_EXTRA = "playerCount";

	SeekBar playerCountChooser;
	TextView playerCountTextView;

	int playerCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		playerCountChooser = findViewById(R.id.playerCountChooser);
		playerCountTextView = findViewById(R.id.playerCountTextView);

		playerCountChooser.setOnSeekBarChangeListener(new AbstractOnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				playerCount = progress + 2;
				playerCountTextView.setText(Integer.toString(playerCount));
			}
		});
		playerCountChooser.setProgress(2 - 2);
	}

	public void startButtonClicked(View view) {
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra(PLAYER_COUNT_EXTRA, playerCount);
		startActivity(intent);
	}
}
