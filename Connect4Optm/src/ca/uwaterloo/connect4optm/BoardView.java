package ca.uwaterloo.connect4optm;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import ca.uwaterloo.connect4optm.GameUtils.PieceType;

public class BoardView extends GridView implements
		android.widget.AdapterView.OnItemClickListener {

	private Listener mListener;
	int nRows;
	int nCols;
	private Resources mResources;

	// 2D array of piece Views
	// View [][] mPiece2DAnimate;
	// View newPiece;
	PieceType mPieceType;
	Bitmap newPieceBitmap;

	int mDifficultyLevel;
	int mAlgorithm;
	int playersNumber; // number of players (0: computer, 2 players otherwise)
	int player1_goFst; // 0 player 1 goes first, 1 player 2 goes first
	boolean mComputerEnabled;

	// OnPieceDroppedListener
	private OnPieceDropped onPieceDropped;

	// Engine instance
	public ConnectFourEngine mEngine;

	// Piece Dropped?
	private boolean isPieceDropped;

	// Piece Moved?
	private boolean isPieceMoved;

	// OnPieceMovedListener
	private OnPieceMoved onPieceMoved;

	// Source piece
	private PieceType srcPieceType;
	private Bitmap srcBmp;

	// Board Enabled?
	private boolean isBoardEnabled;

	public BoardView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// Add background picture
		// setBackgroundResource(R.drawable.wall4);

		init();
	}

	public BoardView(Context c) {
		super(c);

		// Add background picture
		// setBackgroundResource(R.drawable.wall4);

		init();

	}

	public BoardView(Context c, AttributeSet a, int ds) {
		super(c, a, ds);

		// Add background picture
		// setBackgroundResource(R.drawable.wall4);

		init();

	}

	public void init() {
		// Inflate the board_View
		mResources = this.getContext().getApplicationContext().getResources();

		mPieceType = PieceType.Player1;

		newPieceBitmap = BitmapFactory.decodeResource(mResources,
				R.drawable.green);

	}

	public void init(int nRows, int nCols) {

		// Register the listener
		setOnItemClickListener(this);

		this.setRowsCols(nRows, nCols);
		this.setNumColumns(nCols);

		// Inflate the board_View
		mResources = this.getContext().getApplicationContext().getResources();

		// Init Engine
		mEngine = new ConnectFourEngine(Connect4Activity.nRows,
				Connect4Activity.nCols, GameUtils.NUMBER_FINAL_DISK);

		// Enable board
		isBoardEnabled = true;

	}

	/**
	 * 
	 * @param nRows
	 * @param nCols
	 */
	public void setRowsCols(int nRows, int nCols) {
		this.nRows = nRows;
		this.nCols = nCols;
	}

	/**
	 * Sets the listener of the ItemClick
	 * 
	 * @param l
	 */
	public void setListener(Listener l) {
		mListener = l;
	}

	/**
	 * Overrides the itemClick and calls the onClick inside the MainActivity
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

		if (mListener != null) {
			mListener.onClick(position);
		}
	}

	/**
	 * Render the text
	 * 
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();

	}

	public void animatePiece(int positionToAnimate, int dy, int posDest,
			int xInitial) {

		int dx = 0;
		TranslateAnimation slide = new TranslateAnimation(0, dx, 0, dy);

		slide.setInterpolator(new BounceInterpolator());
		slide.setDuration(GameUtils.ANIMATION_FALLING_TIME);
		slide.setAnimationListener(new AnimationManager(positionToAnimate, dy,
				this, posDest, xInitial));

		Log.v("Board View ", " Childeren size" + this.getChildCount());
		Log.v("Board View ", " Dy inside view " + dy);
		Log.v("Board View ", " positionToAnimate inside view "
				+ positionToAnimate);

		this.getChildAt(positionToAnimate).startAnimation(slide);

	}

	public boolean animationStarted(int posAnim) {
		// TODO Auto-generated method stub
		System.out.println("animation Started ");
		this.isPieceMoved = true;

		onPieceMoved.onMoved(this.isPieceMoved, posAnim);
		return this.isPieceMoved;

	}

	public boolean animationEnded(int posAnim, int dy, int posDest, int xInitial) {
		// TODO Auto-generated method stub

		System.out.println("Check Win!");
		System.out.println("Computer Move if it exists! "
				+ this.getChildAt(0).getTop());
		this.isPieceDropped = true;
		onPieceDropped.onDropped(this.isPieceDropped, posAnim, dy, posDest,
				xInitial);
		return this.isPieceDropped;

	}

	public void setOnPieceDroppedListener(OnPieceDropped listener) {
		this.onPieceDropped = listener;
	}

	public void setOnPieceMovedListener(OnPieceMoved listener) {
		this.onPieceMoved = listener;
	}

	public void playDropSound() {
		playSound(R.raw.drop);
	}

	private void playSound(int i) {
		// SharedPreferences settings =
		// this.getContext().getSharedPreferences(Connect4App.PREFS_NAME, 0);
		// int s = settings.getInt(Connect4App.PREFS_SOUND, Players.SOUND_ON);
		// if(s==Players.SOUND_OFF){
		// return;
		// }
		MediaPlayer mp = MediaPlayer.create(this.getContext(), i);
		mp.start();
		mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		});
	}

	public int getPieceColor() {
		if (mPieceType == PieceType.Player1)
			return R.layout.greenpiece;
		else
			return R.layout.redpiece;
	}

	public void togglePieceColor() {
		if (mPieceType == PieceType.Player1) {
			mPieceType = PieceType.Player2;
			newPieceBitmap = BitmapFactory.decodeResource(mResources,

			R.drawable.red);
		} else {
			mPieceType = PieceType.Player1;
			newPieceBitmap = BitmapFactory.decodeResource(mResources,

			R.drawable.green);
		}
	}

	public int getmDifficultyLevel() {
		return mDifficultyLevel;
	}

	public void setmDifficultyLevel(int mDifficultyLevel) {
		this.mDifficultyLevel = mDifficultyLevel;
	}

	public int getmAlgorithm() {
		return mAlgorithm;
	}

	public void setmAlgorithm(int mAlgorithm) {
		this.mAlgorithm = mAlgorithm;
	}

	public int getPlayersNumber() {
		return playersNumber;
	}

	public void setPlayersNumber(int playersNumber) {
		this.playersNumber = playersNumber;
	}

	public int getPlayer1_goFst() {

		// Setting of mComputerEnabled
		mComputerEnabled = playersNumber == 0;

		return player1_goFst;
	}

	public void setPlayer1_goFst(int player1_goFst) {
		this.player1_goFst = player1_goFst;
	}

	public boolean isPieceDropped() {
		return isPieceDropped;
	}

	public void setPieceDropped(boolean isPieceDropped) {
		this.isPieceDropped = isPieceDropped;
	}

	public boolean isPieceMoved() {
		return isPieceMoved;
	}

	public void setPieceMoved(boolean isPieceMoved) {
		this.isPieceMoved = isPieceMoved;
	}

	public PieceType getSrcPieceType() {
		return srcPieceType;
	}

	public void setSrcPieceType(PieceType srcPieceType) {
		this.srcPieceType = srcPieceType;
	}

	public Bitmap getSrcBmp() {
		return srcBmp;
	}

	public void setSrcBmp(Bitmap srcBmp) {
		this.srcBmp = srcBmp;
	}

	public boolean isBoardEnabled() {
		return isBoardEnabled;
	}

	public void setBoardEnabled(boolean isBoardEnabled) {
		this.isBoardEnabled = isBoardEnabled;
	}

}

// new Thread(new Runnable() {
// public void run() {
// try {
// sender.sendMail("Sub", "Body", "from address",""+em[0])
// } catch (Exception e) {
// Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
// }