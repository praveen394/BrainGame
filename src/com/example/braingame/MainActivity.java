/**************************************
 * MainActivity.java
 * Class to display the main activity page
 * Author: Praveen Naresh
 */
package com.example.braingame;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

public class MainActivity extends Activity implements OnClickListener {
	//instance of the game class's variable
	private int loop_counter = 0;
	private int diff = 0;
	private int myScore = 0;
	//instance of the current class
	private MainActivity act = this;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.settings:
			startActivity(new Intent(this, Prefs.class));
			return true;
		}
		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button game, con, abt, ex;//instances of buttons

		game = (Button) findViewById(R.id.new_game_button);//new game button
		game.setOnClickListener(this);
		con = (Button) findViewById(R.id.continue_button);//continue button
		con.setOnClickListener(this);
		abt = (Button) findViewById(R.id.about_game_button);//about button
		abt.setOnClickListener(this);
		ex = (Button) findViewById(R.id.exit_button);//exit button
		ex.setOnClickListener(this);

		//setting default values to the variables
		loop_counter = getIntent().getIntExtra("game_Counter", 0);
		diff = getIntent().getIntExtra("difficulty", 0);
		myScore = getIntent().getIntExtra("GameScore", 0);
		

	}

	private static final String TAG = "Brain Game";
	//function to start the new game
	private void openNewGameDialog() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.new_game_title)
				.setItems(R.array.difficulty,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int i) {
								// TODO Auto-generated method stub
								startGame(i);
							}
						}).show();
	}

	private void startGame(int i) {
		Intent in = new Intent(MainActivity.this, Game.class);
		in.putExtra(Game.KEY_DIFFICULTY, i);
		startActivity(in);
		finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.about_game_button://about
			Intent i = new Intent(this, About.class);
			startActivity(i);
			break;
		case R.id.exit_button://exit
			new AlertDialog.Builder(this)
					.setTitle("Save")
					.setMessage("Would you like to save current game?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// save and exit
									SharedPreferences sharedPref = act//new sharedPreferences
											.getPreferences(Context.MODE_PRIVATE);
									SharedPreferences.Editor editor = sharedPref
											.edit();//adding the variables to the sharedPreferences
									editor.putInt("difficulty", diff);
									editor.putInt("game_Counter", loop_counter);
									editor.putInt("GameScore", myScore);
									editor.commit();
									finish();
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									finish();
								}
							}).show();
			break;
		case R.id.new_game_button://new game
			openNewGameDialog();
			break;
		case R.id.continue_button://continue button
			SharedPreferences sharedPref = this
					.getPreferences(Context.MODE_PRIVATE);
			//getting the variables from the sharedPreferences
			this.diff = sharedPref.getInt("difficulty", 0);
			this.loop_counter = sharedPref.getInt("game_Counter", 0);
			this.myScore = sharedPref.getInt("GameScore", 0);
			//calling the activity from inside the continue
			Intent continue_game = new Intent(this, Game.class);
			//setting the variables back into the game class
			continue_game.putExtra("difficulty", diff);
			continue_game.putExtra("game_Counter", loop_counter);
			continue_game.putExtra("GameScore", myScore);
			startActivity(continue_game);
			finish();
			break;
		}
	}
}
