package com.aqatl.ezitstartup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
	public static final String PLAYER_COUNT_EXTRA = "playerCount";

	NumberPicker playerCountChooser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		playerCountChooser = findViewById(R.id.playerCountChooser);

		playerCountChooser.setMinValue(2);
		playerCountChooser.setMaxValue(16);
		playerCountChooser.setValue(2);
	}

	public void startButtonClicked(View view) {
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra(PLAYER_COUNT_EXTRA, playerCountChooser.getValue());
		startActivity(intent);
	}
}
