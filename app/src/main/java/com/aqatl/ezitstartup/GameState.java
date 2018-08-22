package com.aqatl.ezitstartup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameState implements Serializable {
	public List<GameTurn> turns;
	public int playerCount;

	public int currentTurnIdx;
	public int currentPlayerIdx;

	public GameState(int playerCount) {
		this.playerCount = playerCount;
		this.currentPlayerIdx = -1;
		this.currentTurnIdx = -1;

		this.turns = new ArrayList<>(20);
	}

	public void addPoints(int points) {
		GameTurn turn;
		currentPlayerIdx = (currentPlayerIdx + 1) % playerCount;
		if (currentPlayerIdx == 0) {
			turn = new GameTurn(currentTurnIdx++, playerCount);
			turns.add(turn);
		} else {
			turn = turns.get(currentTurnIdx);
		}

		int pointsFromPreviousRound = 0;
		if (currentTurnIdx != 0) {
			pointsFromPreviousRound = turns.get(currentTurnIdx - 1).points[currentPlayerIdx];
		}

		turn.points[currentPlayerIdx] = pointsFromPreviousRound + points;
	}

	public void undoMove() {
		if (currentTurnIdx < 0) {
			return;
		}

		if (currentPlayerIdx == 0) {
			turns.remove(currentTurnIdx--);
			currentPlayerIdx = playerCount - 1;
		} else {
			turns.get(currentTurnIdx).points[currentPlayerIdx--] = 0;
		}
	}
}

