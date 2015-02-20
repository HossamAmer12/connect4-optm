package ca.uwaterloo.connect4optm;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import ca.uwaterloo.connect4optm.GameUtils.PieceType;

public class Connect4Activity extends ActionBarActivity implements
		OnItemClickListener, IOnExitListener {

	BoardView mBoard;
	BoardViewAdapter mBoardGridAdapter;

	// Sliding piece
	private DropAreaView mDropAreaView;

	// 8x7, 9x7, 10x7, 8x8
	public static int nCols = 7;// 7 columns --> Only changes the spaces between
	public static int nRows = 6; // rows

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		initBoard();

	}

	private void initBoard() {

		// Initialize the screen dimensions
		getScreenDimensions();

		LinearLayout x = (LinearLayout) findViewById(R.id.board_LId);
		mBoard = (BoardView) x.findViewById(R.id.my_custom_grid_view);

		// Get activity input
		startGame();

		mBoard.init(Connect4Activity.nRows, Connect4Activity.nCols);
		mBoardGridAdapter = new BoardViewAdapter(this, R.layout.row_grid,
				new ArrayList<Piece>(), this.getResources(),
				Connect4Activity.nRows, Connect4Activity.nCols);

		mBoard.setAdapter(mBoardGridAdapter);

		mDropAreaView = (DropAreaView) findViewById(R.id.sliding_piece);

		// Register the listener
		mBoard.setOnItemClickListener(this);

		// OnPieceDropped listener
		mBoard.setOnPieceMovedListener(new OnPieceMovedListener(mBoard,
				mBoardGridAdapter, getApplicationContext(), mDropAreaView));

		// OnPieceDropped listener
		mBoard.setOnPieceDroppedListener(new OnPieceDroppedListener(mBoard,
				mBoardGridAdapter, getApplicationContext(), mDropAreaView));

	}

	private void startGame() {
		SharedPreferences settings = getSharedPreferences(GameUtils.PREFS_NAME,
				0);
		int d = settings.getInt(GameUtils.PREFS_DIFF, GameUtils.easyLevel);
		int p = settings.getInt(GameUtils.PREFS_PLAY, GameUtils.initPlayers);
		int t = settings.getInt(GameUtils.PREFS_TURN, GameUtils.goFst);
		int s = settings.getInt(GameUtils.PREFS_SIZE, GameUtils.szFst);
		int alg = settings.getInt(GameUtils.PREFS_ALG, GameUtils.algoFst);

		if (s == 0) {
			Connect4Activity.nRows = 6;
			Connect4Activity.nCols = 7;
		} else if (s == 1) {
			Connect4Activity.nRows = 7;
			Connect4Activity.nCols = 8;
		} else if (s == 2) {
			Connect4Activity.nRows = 7;
			Connect4Activity.nCols = 10;

		}

		mBoard.setRowsCols(Connect4Activity.nRows, Connect4Activity.nCols);
		mBoard.setmDifficultyLevel(d);
		mBoard.setPlayersNumber(p);
		mBoard.setPlayer1_goFst(t);
		mBoard.setmAlgorithm(alg);

		// gV.setDepth(AlgorithmConsts.getDefaultDepth());
		// gV.setDifficulty(d);
		// mBoard.setOnExitListener(this);
		// tV.setNumPlayers(p);

	}

	@Override
	public void onResume() {
		super.onResume();

	}

	public void getScreenDimensions() {
		WindowManager mWindowManager = (WindowManager) getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = mWindowManager.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);

		GameUtils.mScreenWidth = size.x;
		GameUtils.mScreenHeight = size.y;

	}

	// CallBack Just in case
	// http://stackoverflow.com/questions/25187400/updating-textview-on-activity-once-data-in-adapter-class-is-changed

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

		storeAbsolutePositions();

		// Change the bottom position of the droparea view
		// int[] location = new int[2];
		// mBoard.getChildAt(0).getLocationOnScreen(location);
		// int yAbs = location[1];

		// yAbs = mBoard.getChildAt(0).getTop();
		// mDropAreaView.setBottomPos(yAbs);

	}

	/**
	 * Stores absolute locations
	 */
	public void storeAbsolutePositions() {
		// System.out.println("Size: " + mBoard.getChildCount());

		int[] location = new int[2];
		for (int i = 0; i < mBoard.getChildCount(); i++) {

			mBoard.getChildAt(i).getLocationOnScreen(location);

			// int xAbs = mBoard.getChildAt(i).getLeft();
			// int yAbs = mBoard.getChildAt(i).getTop();

			int xAbs = location[0];
			int yAbs = location[1];

			mBoardGridAdapter.getItem(i).setxPos(xAbs);
			mBoardGridAdapter.getItem(i).setyPos(yAbs);

			// System.out.println("X Value everytime STORE Activity !!!!!  "
			// + xAbs);
			// System.out.println("Y Value everytime STORE Activity !!!!!  "
			// + yAbs);
			// System.out.println(i);
		}
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

		// Board enabled?
		// if (!mBoard.isBoardEnabled)
		if (!mBoard.isBoardEnabled())
			return;

		Piece click = mBoardGridAdapter.getItem(position);

		int xInitial = click.getxIndex();
		// int yInitial = click.getyIndex();

		int top = mBoardGridAdapter.mTopArray[xInitial];
		int xDest = click.getxIndex();
		int yDest = Connect4Activity.nRows - top - 1;
		int posDest = mBoardGridAdapter.getLinearPosition(xDest, yDest);
		int posSrc = mBoardGridAdapter.getLinearPosition(xInitial, top);

		// Avoid overfill
		if (posDest < 0)
			return;

		// Align with column!
//		if (Math.abs(mDropAreaView.getBallRect().left
//				- mBoard.getChildAt(posSrc).getLeft()) > 15)
//			return;

		// Move the sliding piece automatically
		onHintButton(xDest, yDest);

		

		// Align with column!
		/*
		 * if (Math.abs(mDropAreaView.getBallRect().left -
		 * mBoard.getChildAt(posSrc).getLeft()) > 15) return;
		 */

		int dy = mBoard.getChildAt(posDest).getTop();
		int posAnim = mBoardGridAdapter.getLinearPosition(xInitial, 0);

		PieceType newPieceType;

		if (mBoard.mPieceType == PieceType.Player1)
			newPieceType = PieceType.Player2;
		else
			newPieceType = PieceType.Player1;

		mBoard.animatePiece(posAnim, dy, posDest, xInitial);
		mDropAreaView.togglePieceColor(newPieceType);

		// mBoard.animatePiece(posAnim, dy);
		// mBoardGridAdapter.updatePieces(posDest, mBoard.mPieceType,
		// mBoard.newPieceBitmap);

		// mBoard.playDropSound();
		// mBoardGridAdapter.updateTopArray(xInitial);
		// mBoard.togglePieceColor();

	}

	public void onHintButton(int x, int y) {
		int position = mBoardGridAdapter.getLinearPosition(x, y);

		// Get the xAbs of the clicked position
		int xAbs = mBoardGridAdapter.getItem(position).getxPos();

		// Move the sliding piece to that position
		int xSlidingPiece = mDropAreaView.getBallRect().left;

		int dx = xAbs - xSlidingPiece;

		Log.v("Main Activity ", "xAbs: " + xAbs);
		Log.v("Main Activity ", "xSlidingPiece: " + xSlidingPiece);
		Log.v("Main Activity ", "dx: " + dx);
		Log.v("Main Activity ", "Window width : " + GameUtils.mScreenWidth);

		mDropAreaView.onMove(dx, 0);

		/*
		 * final int total = dx; final DropAreaView thisView = mDropAreaView;
		 * 
		 * 
		 * TranslateAnimation slide; if(xAbs > xSlidingPiece) slide = new
		 * TranslateAnimation(xAbs, xSlidingPiece, 0, 0); else slide = new
		 * TranslateAnimation(xSlidingPiece, xAbs, 0, 0);
		 * //slide.setInterpolator(new BounceInterpolator());
		 * slide.setDuration(GameUtils.ANIMATION_FALLING_TIME/3);
		 * slide.setAnimationListener(new AnimationListener() {
		 * 
		 * @Override public void onAnimationStart(Animation animation) { // TODO
		 * Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void onAnimationRepeat(Animation animation) { //
		 * TODO Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void onAnimationEnd(Animation animation) { // TODO
		 * Auto-generated method stub thisView.invalidate();
		 * 
		 * } });
		 * 
		 * thisView.startAnimation(slide); mDropAreaView.onMove(dx, 0);
		 */
		// final int offset = total/ 10;
		// new Thread(new Runnable() {
		// public void run() {
		// mDropAreaView.post(new Runnable() {
		// public void run() {
		// int x = 0;
		// mDropAreaView.onMove(offset + x, 0);
		// x += offset;
		// }
		// });
		// }
		// }).start();

		/*
		 * boolean post = mDropAreaView.post(new Runnable() { public void run()
		 * { mDropAreaView.onMove(dx, 0); } });
		 */
	}

	@Override
	public void exit() {
		finish();
	}

}
