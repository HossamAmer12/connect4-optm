package ca.uwaterloo.connect4optm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class UserInputActivity extends ActionBarActivity implements
		View.OnClickListener, RadioGroup.OnCheckedChangeListener {

	private Button setButton;
	private Button playButton;
	private Button helpButton;
	private RadioGroup diffGroup;
	private RadioGroup turnGroup;
	private RadioButton diff0, diff1, diff2;
	private RadioGroup playGroup;
	private RadioButton play0, play1;
	private RadioButton turn0, turn1;
	private RadioGroup algGroup;
	private RadioButton alg0, alg1;
	private RadioGroup szGroup;
	private RadioButton size0, size1, size2;

	public static final String MyPREFERENCES = "MyPrefs";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		load();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_input, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setDiff(int i) {

		if (i == 0) {
			diff0.setChecked(true);
		} else if (i == 1) {
			diff1.setChecked(true);
		} else {
			diff2.setChecked(true);
		}
	}

	private void setPlayers(int i) {

		if (i == 0)
			play0.setChecked(true);

		else
			play1.setChecked(true);

	}

	private void setTurn(int i) {
		if (i == 0)
			turn0.setChecked(true);

		else
			turn1.setChecked(true);
	}

	private int getTurn() {
		if (turnGroup.getCheckedRadioButtonId() == R.id.radio_turn0) {
			return 0;
		} else {
			return 1;
		}
	}

	private int getDiff() {
		if (diffGroup.getCheckedRadioButtonId() == R.id.radio_diff0) {
			return 0;
		} else if (diffGroup.getCheckedRadioButtonId() == R.id.radio_diff1) {
			return 1;
		} else {
			return 2;
		}
	}

	private int getPlay() {
		if (playGroup.getCheckedRadioButtonId() == R.id.radio_play0) {
			return 0;
		} else {
			return 1;
		}

	}

	private int getAlgorithm() {
		if (algGroup.getCheckedRadioButtonId() == R.id.radio_alg0) {
			return 0;
		} else {
			return 1;
		}

	}

	private void setAlgorithm(int i) {
		if (i == 0) {
			alg0.setChecked(true);
		} else {
			alg1.setChecked(true);
		}

	}

	private int getBoardSize() {
		if (szGroup.getCheckedRadioButtonId() == R.id.radio_size0) {
			return 0;
		} else if (diffGroup.getCheckedRadioButtonId() == R.id.radio_size1) {
			return 1;
		} else {
			return 2;
		}
	}

	private void setBoardSize(int i) {
		if (i == 0) {
			size0.setChecked(true);
		} else if (i == 1) {
			size1.setChecked(true);
		} else {
			size2.setChecked(true);
		}
	}

	private void store() {

		SharedPreferences settings = getSharedPreferences(GameUtils.PREFS_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();

		int d = getDiff();
		int p = getPlay();
		int t = getTurn();
		int s = getBoardSize();
		int alg = getAlgorithm();
		editor.putInt(GameUtils.PREFS_DIFF, d);
		editor.putInt(GameUtils.PREFS_PLAY, p);
		editor.putInt(GameUtils.PREFS_TURN, t);
		editor.putInt(GameUtils.PREFS_ALG, alg);
		editor.putInt(GameUtils.PREFS_SIZE, s);
		editor.commit();

		/*
		 * SharedPreferences settings =
		 * getSharedPreferences(Connect4App.PREFS_NAME, 0);
		 * SharedPreferences.Editor editor = settings.edit(); int d = getDiff();
		 * int p = getPlay(); int t = getTurn(); int pw = getPower();
		 * editor.putInt(Connect4App.PREFS_DIFF,d);
		 * editor.putInt(Connect4App.PREFS_PLAY,p);
		 * editor.putInt(Connect4App.PREFS_TURN,t);
		 * editor.putInt(Connect4App.PREFS_POWER,pw); editor.commit();
		 */
	}

	private void load() {

		SharedPreferences settings = getSharedPreferences(GameUtils.PREFS_NAME,
				Context.MODE_PRIVATE);

		int d = settings.getInt(GameUtils.PREFS_DIFF, GameUtils.easyLevel);
		int p = settings.getInt(GameUtils.PREFS_PLAY, GameUtils.initPlayers);
		int t = settings.getInt(GameUtils.PREFS_TURN, GameUtils.goFst);
		int s = settings.getInt(GameUtils.PREFS_SIZE, GameUtils.szFst);
		int alg = settings.getInt(GameUtils.PREFS_ALG, GameUtils.algoFst);

		showTurn();
		setDiff(d);
		setPlayers(p);
		setTurn(t);
		setBoardSize(s);
		setAlgorithm(alg);

		/*
		 * SharedPreferences settings =
		 * getSharedPreferences(Connect4App.PREFS_NAME, 0); int d =
		 * settings.getInt(Connect4App.PREFS_DIFF, Players.DIFF_EASY); int p =
		 * settings.getInt(Connect4App.PREFS_PLAY, Players.ONE_PLAYER); int t =
		 * settings.getInt(Connect4App.PREFS_TURN, Players.GO_FIRST); int pw =
		 * settings.getInt(Connect4App.PREFS_POWER, Players.POWER_OFF);
		 * showTurn(); setDiff(d); setPlayers(p); setTurn(t); setPower(pw);
		 * this.showTurn();
		 */
	}

	private void init() {
		playButton = (Button) (this.findViewById(R.id.play_button));
		playButton.setOnClickListener(this);
		
		/*
		setButton = (Button) (this.findViewById(R.id.open_settings));
		setButton.setOnClickListener(this);
		helpButton = (Button) (this.findViewById(R.id.open_help));
		helpButton.setOnClickListener(this);
		*/
		
		diffGroup = (RadioGroup) findViewById(R.id.radio_diff);
		playGroup = (RadioGroup) findViewById(R.id.radio_play);
		turnGroup = (RadioGroup) findViewById(R.id.radio_turn);
		diff0 = (RadioButton) findViewById(R.id.radio_diff0);
		diff1 = (RadioButton) findViewById(R.id.radio_diff1);
		diff2 = (RadioButton) findViewById(R.id.radio_diff2);
		play0 = (RadioButton) findViewById(R.id.radio_play0);
		play1 = (RadioButton) findViewById(R.id.radio_play1);

		turn0 = (RadioButton) findViewById(R.id.radio_turn0);
		turn1 = (RadioButton) findViewById(R.id.radio_turn1);
		playGroup.setOnCheckedChangeListener(this);

		algGroup = (RadioGroup) findViewById(R.id.radio_alg);
		alg0 = (RadioButton) findViewById(R.id.radio_alg0);
		alg1 = (RadioButton) findViewById(R.id.radio_alg1);

		szGroup = (RadioGroup) findViewById(R.id.radio_size);
		size0 = (RadioButton) findViewById(R.id.radio_size0);
		size1 = (RadioButton) findViewById(R.id.radio_size1);
		size2 = (RadioButton) findViewById(R.id.radio_size2);

	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	public void onRestart() {
		super.onRestart();
	}

	public void onStart() {
		super.onStart();
	}

	public void onResume() {
		super.onResume();
	}

	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	public void onPause() {
		super.onPause();
	}

	public void onStop() {
		super.onStop();
	}

	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.play_button) {
			store();
			Intent intent = new Intent(getBaseContext(), Connect4Activity.class);
			 this.startActivity(intent);
		}
		
		/*
		if (v.getId() == R.id.open_settings) {
			// Intent intent = new Intent(this, SettingsActivity.class);
			// startActivity(intent);
		} else if (v.getId() == R.id.play_button) {
			store();
//			 Intent intent = new Intent(this, Connect4Activity.class);
			Intent intent = new Intent(getBaseContext(), Connect4Activity.class);
			 this.startActivity(intent);
		} else if (v.getId() == R.id.open_help) {
			store();
			// Intent intent = new Intent(this, HelpActivity.class);
			// this.startActivity(intent);
		}
	*/
	}

	private void showTurn() {
		// ****
		// boolean show = (getPlay()==Players.ONE_PLAYER);
		// boolean show = (getPlay() == GameUtils.players_Num);
		boolean show = (getPlay() == 0);
		turnGroup.setEnabled(show);
		turn0.setEnabled(show);
		turn1.setEnabled(show);
		diff0.setEnabled(show);
		diff1.setEnabled(show);
		diff2.setEnabled(show);
		diffGroup.setEnabled(show);

		algGroup.setEnabled(show);
		alg0.setEnabled(show);
		alg1.setEnabled(show);

		// Two player game
		if (!show) {
			GameUtils.players_Num = getPlay();
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		showTurn();
	}

}
