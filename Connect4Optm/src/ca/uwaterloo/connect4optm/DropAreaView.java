package ca.uwaterloo.connect4optm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import ca.uwaterloo.connect4optm.GameUtils.PieceType;

public class DropAreaView extends View {

	private Drawable mBallImage;
	private int mCanvasWidth;
	private int mCanvasHeight;
	private float totalAnimDx;
	private float totalAnimDy;
	private Rect mBallRect;
	public int mColumns;
	private GestureDetector gestures;
	private static String TAG = "DropAreaView";
	private boolean post;
	private int mBallSize;
	
	// public 
	public int bottomPos;
	public int topPos;

	Context mContext;

	int screenWidth;
	int screenHeight;

	public DropAreaView(Context context, int mColumns) {
		super(context);
		gestures = new GestureDetector(context, new GestureListener(this));

		mBallImage = context.getResources().getDrawable(R.drawable.green);
		mBallRect = new Rect();

		// this.mColumns = mColumns;
		this.mColumns = Connect4Activity.nCols;

	}

	public DropAreaView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mContext = context;
		// Add background picture
		// setBackgroundResource(R.drawable.wall4);

	}

	public DropAreaView(Context c) {
		super(c);

		mContext = c;
		// Add background picture
		// setBackgroundResource(R.drawable.wall4);

	}

	public DropAreaView(Context c, AttributeSet a, int ds) {
		super(c, a, ds);

		mContext = c;
		// Add background picture
		// setBackgroundResource(R.drawable.wall4);

	}

	public void onAnimateMove(float dx, float dy) {
		totalAnimDx = dx;
		totalAnimDy = dy;
		post = post(new Runnable() {
			public void run() {
				onAnimateStep();
			}
		});

	}

	private void onAnimateStep() {
		drawImage((int) (totalAnimDx));
	}

	@Override
	protected void onDraw(Canvas canvas) {

		mBallImage.setBounds(mBallRect);
		mBallImage.draw(canvas);
	}

	public int getScreenWidth() {
		WindowManager mWindowManager = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = mWindowManager.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);

		return size.y;
	}

	public int getScreenHeight() {
		WindowManager mWindowManager = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = mWindowManager.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);

		return size.x;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		Log.v(TAG, "entered in onSizeChanged");
		mCanvasWidth = w;
		mCanvasHeight = h;

		if (mBallRect == null) {
			// mColumns = 7;
			mColumns = Connect4Activity.nCols;
			gestures = new GestureDetector(mContext, new GestureListener(this));

			mBallImage = mContext.getResources().getDrawable(R.drawable.green);
			mBallRect = new Rect();

			screenWidth = getScreenWidth();
			screenHeight = getScreenHeight();

			// screenWidth = GameUtils.mScreenWidth;
			// screenHeight = GameUtils.mScreenHeight;

		}

		mBallSize = (mCanvasWidth / mColumns);

		// System.out.println("mBall Size: " + mBallSize);

		// mBallRect.set(left, top, right, bottom)

		// mBallRect.set(2, mCanvasHeight - mBallSize, mBallSize,
		// mCanvasHeight);

		// mBallRect.set(2, 0, mBallSize, mBallSize);
		
		// Working!
		mBallRect.set(0, 0 , mBallSize, mBallSize );
		
//		mBallRect.set(0, 0 , mBallSize, bottomPos );
		
//		mBallRect.set(0, 0 + 195, mBallSize, mBallSize + 195);
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gestures.onTouchEvent(event);
	}

	public void drawImage(int iX) {

		// mBallRect.set(iX, mCanvasHeight - mBallSize, iX + mBallSize,
		// mCanvasHeight);

		// mBallRect.set(2, 0, mBallSize, mBallSize);

		// Working!
		mBallRect.set(iX, 0, mBallSize, mBallSize);
//		mBallRect.set(iX, 0, mBallSize, bottomPos);
		invalidate();
	}

	public boolean checkClickOnBall(MotionEvent e) {
		Rect ballCord = mBallImage.getBounds();
		int x = (int) e.getX();
		if (x > ballCord.left && x < ballCord.right)
			return true;
		return false;
	}

	public void onMove(float dx, float dy) {
		mBallRect = mBallImage.copyBounds();
		mBallRect.left += (int) dx;
		mBallRect.right += (int) dx;
		invalidate();
	}

	public Rect getBallRect() {
		return mBallRect;
	}

	public void togglePieceColor(PieceType mPieceType) {

		if (mPieceType == PieceType.Player2) {
			this.mBallImage = this.getResources().getDrawable(R.drawable.red);
		} else {
			this.mBallImage = this.getResources().getDrawable(R.drawable.green);
		}
		this.invalidate();

	}

	public int getBottomPos() {
		return bottomPos;
	}

	public void setBottomPos(int bottomPos) {
		this.bottomPos = bottomPos;
	}

	public int getTopPos() {
		return topPos;
	}

	public void setTopPos(int topPos) {
		this.topPos = topPos;
	}

}
