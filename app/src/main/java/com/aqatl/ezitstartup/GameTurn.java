package com.aqatl.ezitstartup;

import java.io.Serializable;

public class GameTurn implements Serializable {
	public int turn;
	public int[] points;

	public GameTurn(int turn, int players) {
		this.turn = turn;
		points = new int[players];
	}

}
