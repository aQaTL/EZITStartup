package com.aqatl.ezitstartup;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
	public static final String TAG = "RecyclerViewAdapter";

	private Context context;

	private GameState gameState;

	private int accentColor;

	public RecyclerViewAdapter(Context context, GameState gameState) {
		this.context = context;
		this.gameState = gameState;
		this.accentColor = context.getResources().getColor(R.color.myColorAccent, context.getTheme());
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

		ViewHolder holder = new ViewHolder(view);
		for (int i = 0; i < gameState.playerCount; i++) {
			TextView turnData = new TextView(view.getContext());

			turnData.setText("-");
			turnData.setTextSize(18);
			turnData.setTextColor(Color.BLACK);
			turnData.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

			turnData.setPadding(5, 0, 5, 0);

			holder.row.addView(turnData);

			LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT,
					1.0f
			);
			turnData.setLayoutParams(param);

		}
		return holder;
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		holder.turnIdx.setText("Turn " + (position + 1));

		if (holder.row.getChildCount() != gameState.playerCount) {
			throw new IllegalStateException("Row child count doesn't match player count");
		}

		for (int childIdx = 0; childIdx < holder.row.getChildCount(); childIdx++) {
			TextView turnData = (TextView) holder.row.getChildAt(childIdx);

			int points = gameState.turns.get(position).points[childIdx];

			if (position == gameState.currentTurnIdx && childIdx > gameState.currentPlayerIdx) {
				turnData.setText("-");
				turnData.setTextColor(Color.BLACK);
				continue;
			}
			turnData.setText(Integer.toString(points));

			if ((points != 0) && ((points & (points - 1)) == 0)) {
				turnData.setTextColor(accentColor);
			} else {
				turnData.setTextColor(Color.BLACK);
			}
		}
	}

	@Override
	public int getItemCount() {
		return gameState.turns.size();
	}


	public class ViewHolder extends RecyclerView.ViewHolder {
		RelativeLayout parent;
		TextView turnIdx;
		LinearLayout row;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			parent = itemView.findViewById(R.id.parent);
			turnIdx = itemView.findViewById(R.id.turnIdx);
			row = itemView.findViewById(R.id.row);
		}
	}
}
