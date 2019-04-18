/***********************************
 * Game.java
 * Class that controls the entire game
 * Author: Praveen Naresh
 */
package com.example.braingame;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends Activity implements OnClickListener {
	// instances of textviews
	TextView guess = null;
	TextView expression = null;
	TextView display = null;
	TextView answer = null;
	TextView timerDisplay = null;
	// **************************
	// instances of buttons
	Button bu0 = null;
	Button bu1 = null;
	Button bu2 = null;
	Button bu3 = null;
	Button bu4 = null;
	Button bu5 = null;
	Button bu6 = null;
	Button bu7 = null;
	Button bu8 = null;
	Button bu9 = null;
	Button buDEL = null;
	Button buHash = null;
	Button buMin = null;
	// **************************

	public static final String TAG = "Brain Game";
	public static final String KEY_DIFFICULTY = "org.example.braingame.difficulty";// location
																					// of
																					// the
																					// difficulty
																					// array
	public static final int DIFFICULTLY_NOVICE = 0;
	public static final int DIFFICULTLY_EASY = 1;
	public static final int DIFFICULTLY_MEDIUM = 2;
	public static final int DIFFICULTLY_GURU = 3;
	final Context context = this;// instance of the current class's context

	private Expression e = new Expression();// instance of the expression class
	private ExpSolver es = new ExpSolver();// instance of the expSolver class
	private santimer timer;// instance of the timer
	private final long startTime = 11000;// start time of the timer
	private final long interval = 1000;// interval of the timer
	private long timeElapsed;// long variable of the time remaining for each
								// question

	private int loop_count = 0;// counter to set the number of expressions to 10
	private int correct_ans;// counter to set the number of correct answers
	private int incorrect_ans;// counter to set the number of wrong answers

	private int diff;// store difficulty
	private String currentExp = "";// store current expression
	private int currentAns = 0;// store correct answer
	private int userAns = 0;// store user answer
	private int points = 0;// store the score
	private int hintsCount = 0;// counter for the hints
	private int hashCount = 0;// counter used for double tapping the hash key

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		diff = getIntent().getIntExtra(KEY_DIFFICULTY, DIFFICULTLY_NOVICE);// assigning
																			// the
																			// difficulty
																			// to
																			// diff
		setContentView(R.layout.game);

		// setting the counter to 0
		loop_count = 0;
		correct_ans = 0;
		incorrect_ans = 0;

		initControls();

		timer = new santimer(startTime, interval);// starting the timer
		timer.start();
		// if the game is continued, getting values from previously saved game
		loop_count = getIntent().getIntExtra("game_Counter", 0);
		diff = getIntent().getIntExtra("difficulty", diff);
		points = getIntent().getIntExtra("GameScore", 0);

	}

	@Override
	public void onBackPressed() {
		// if back key pressed, main activity is called
		Intent backIntent = new Intent(this, MainActivity.class);
		// sending the current game info
		backIntent.putExtra("difficulty", diff);
		backIntent.putExtra("game_Counter", loop_count);
		backIntent.putExtra("GameScore", points);

		startActivity(backIntent);
		finish();
	}

	// function to get an expression and set it to the textView
	public void myExpression(int difficulty) {
		loop_count++;
		if (loop_count < 10) {
			currentExp = e.getEx(difficulty);
			expression.setText(currentExp);
		}
	}

	// function to initialize the textview and button
	public void initControls() {
		guess = (TextView) findViewById(R.id.guess);
		expression = (TextView) findViewById(R.id.expression);
		display = (TextView) findViewById(R.id.display);
		answer = (TextView) findViewById(R.id.answer);
		timerDisplay = (TextView) findViewById(R.id.myTimer);

		bu0 = (Button) findViewById(R.id.keypad_0);
		bu1 = (Button) findViewById(R.id.keypad_1);
		bu2 = (Button) findViewById(R.id.keypad_2);
		bu3 = (Button) findViewById(R.id.keypad_3);
		bu4 = (Button) findViewById(R.id.keypad_4);
		bu5 = (Button) findViewById(R.id.keypad_5);
		bu6 = (Button) findViewById(R.id.keypad_6);
		bu7 = (Button) findViewById(R.id.keypad_7);
		bu8 = (Button) findViewById(R.id.keypad_8);
		bu9 = (Button) findViewById(R.id.keypad_9);
		buDEL = (Button) findViewById(R.id.keypad_del);
		buHash = (Button) findViewById(R.id.keypad_hash);
		buMin = (Button) findViewById(R.id.keypadMin);

		bu0.setOnClickListener(this);
		bu1.setOnClickListener(this);
		bu2.setOnClickListener(this);
		bu3.setOnClickListener(this);
		bu4.setOnClickListener(this);
		bu5.setOnClickListener(this);
		bu6.setOnClickListener(this);
		bu7.setOnClickListener(this);
		bu8.setOnClickListener(this);
		bu9.setOnClickListener(this);
		buDEL.setOnClickListener(this);
		buHash.setOnClickListener(this);
		buMin.setOnClickListener(this);

		myExpression(diff);
	}

	// function that occurs when hash key is pressed
	public void hashClick() {
		if (hashCount == 1) {// check if key is already pressed
			answer.setText("");
			myExpression(diff);
			timer.start();
			hashCount = 0;
		}
		if (loop_count < 10) {// check the expression loop
			if (answer.getText().length() == 0) {
				answer.setText("");
			} else if (answer.getText().length() == 1
					&& answer.getText().charAt(0) == '-') {
				answer.setText("");
			} else {
				currentAns = es.Solve(currentExp);
				String ansTemp = answer.getText().toString();
				userAns = Integer.parseInt(ansTemp);
				if (userAns == currentAns) {// check if correct answer

					display.setVisibility(View.VISIBLE);
					display.setText("CORRECT");
					display.setTextColor(Color.GREEN);

					if (timeElapsed == 10) {// calculating the score for each
											// expression if its correct
						points += 100;
					} else {
						points += 100 / (10 - timeElapsed);
					}
					correct_ans++;
					hashCount += 1;
					timer.cancel();

				} else {
					if (Prefs.getHints(getApplicationContext())) {
						// check if// hints is
						// enabled
						if (hintsCount != 4) {
							if (userAns > currentAns) {// check if user's answer
														// is less than the
														// correct answer
								expression.setText(currentExp);
								Toast.makeText(this, "less", Toast.LENGTH_SHORT)
										.show();
							}
							if (userAns < currentAns) {// check if user's answer
														// is greater than the
														// correct answer
								expression.setText(currentExp);
								Toast.makeText(this, "greater",
										Toast.LENGTH_SHORT).show();
							}
						}
					}
					// incorrect answer
					hintsCount++;
					display.setText("");
					expression.setText(currentExp);
					display.setText("INCORRECT");
					display.setVisibility(View.VISIBLE);
					display.setTextColor(Color.RED);
					answer.setText("");
					incorrect_ans++;
				}
			}
		} else {
			// expression counter is 10, show score
			showScore();
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.keypad_0:
			answer.append("0");
			break;
		case R.id.keypad_1:
			answer.append("1");
			break;
		case R.id.keypad_2:
			answer.append("2");
			break;
		case R.id.keypad_3:
			answer.append("3");
			break;
		case R.id.keypad_4:
			answer.append("4");
			break;
		case R.id.keypad_5:
			answer.append("5");
			break;
		case R.id.keypad_6:
			answer.append("6");
			break;
		case R.id.keypad_7:
			answer.append("7");
			break;
		case R.id.keypad_8:
			answer.append("8");
			break;
		case R.id.keypad_9:
			answer.append("9");
			break;
		case R.id.keypad_del:
			String temp = answer.getText().toString();
			if (temp.length() == 0) {
				break;
			} else {
				answer.setText(temp.substring(0, temp.length() - 1));// remove
																		// the
																		// last
																		// entered
																		// character
			}
			break;
		case R.id.keypadMin:
			answer.append("-");
			break;
		case R.id.keypad_hash:
			hashClick();// calling the hashkey function
			break;
		}
	}

	// function to show the score
	public void showScore() {
		new AlertDialog.Builder(this).setTitle("Game Over")
				.setMessage("Your score :" + points).show();
		timer.cancel();
	}

	// timer class
	private class santimer extends CountDownTimer {

		public santimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			if (loop_count < 10) {
				hashClick();
				myExpression(diff);
				timer.start();
			} else {
				timer.cancel();
			}
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			long time = millisUntilFinished / 1000;
			timerDisplay.setText("Time remaining :" + time);
			timeElapsed = millisUntilFinished / 1000;
		}
	}
}
