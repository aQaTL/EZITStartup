package com.aqatl.ezitstartup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
	private static final String TAG = "GameActivity";
	private static final String STATE_GAME_STATE = "STATE_GAME_STATE";

	RecyclerView recyclerView;
	EditText pointsInput;

	private GameState gameState;

	public GameActivity() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		Intent intent = getIntent();
		int playerCount = intent.getIntExtra(MainActivity.PLAYER_COUNT_EXTRA, 0);

		if (savedInstanceState != null) {
			gameState = (GameState) savedInstanceState.getSerializable(STATE_GAME_STATE);
		} else {
			gameState = new GameState(playerCount);
		}

		initRecyclerView();

		pointsInput = findViewById(R.id.pointsInput);
		pointsInput.setOnEditorActionListener(this::applyPointsInput);
		pointsInput.setOnFocusChangeListener((v, hasFocus) -> {
			if (hasFocus) {
				getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			}
		});
	}

	private void initRecyclerView() {
		recyclerView = findViewById(R.id.recyclerView);
		RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, gameState);
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
	}

	private boolean applyPointsInput(TextView textView, int keyCode, KeyEvent keyEvent) {
		if (keyCode == EditorInfo.IME_ACTION_DONE) {
			int points = getPoints();
			if (points >= 0) {
				addPoints(points);
				pointsInput.getText().clear();
			}
			return true;
		}

		return false;
	}

	private int getPoints() {
		try {
			return Integer.parseInt(pointsInput.getText().toString());
		}
		catch (Exception e) {
			return -1;
		}
	}

	private void addPoints(int points) {
		int turnIdx = gameState.currentTurnIdx;
		gameState.addPoints(points);

		if (turnIdx == gameState.currentTurnIdx) {
			recyclerView.getAdapter().notifyItemChanged(gameState.currentTurnIdx);
		} else {
			recyclerView.getAdapter().notifyItemInserted(gameState.currentTurnIdx);
		}
		recyclerView.scrollToPosition(gameState.currentTurnIdx);
	}

	public void backspaceButtonClicked(View view) {
		int turnIdx = gameState.currentTurnIdx;
		gameState.undoMove();
		if (turnIdx != gameState.currentTurnIdx) {
			recyclerView.getAdapter().notifyItemRemoved(turnIdx);
		} else {
			recyclerView.getAdapter().notifyItemChanged(turnIdx);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(STATE_GAME_STATE, gameState);
	}
}
