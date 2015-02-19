package ca.uwaterloo.connect4optm;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

class GestureListener implements GestureDetector.OnGestureListener,
		GestureDetector.OnDoubleTapListener {

	DropAreaView view;
	private static String DEBUG_TAG = "GestureDetecter";

	private static final int yCut = 600;
	private static double theConstant = 4.5;

	public GestureListener(DropAreaView view) {
		this.view = view;
	}

	public boolean onDown(MotionEvent e) {
		Log.v(DEBUG_TAG, "onDown");
		// view.drawImage((int)(e.getX()));
		return view.checkClickOnBall(e);
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2,
			final float velocityX, final float velocityY) {
		Log.v(DEBUG_TAG, "onFling");
		return false;
	}

	public boolean onDoubleTap(MotionEvent e) {
		Log.v(DEBUG_TAG, "onDoubleTap");
		// view.onResetLocation();
		return true;
	}

	public void onLongPress(MotionEvent e) {
		Log.v(DEBUG_TAG, "onLongPress");
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		Log.v(DEBUG_TAG, "onScroll");

		float x = e2.getRawX();
		float y = e2.getRawY();

		float ballWidth = Math.abs(view.getBallRect().left
				- view.getBallRect().right);
		// float threshold = view.screenWidth - 2*ballWidth;
		// double threshold = view.screenWidth/view.mColumns*ballWidth -
		// 0.5*ballWidth;

		if (view.mColumns != 7)
			// theConstant = view.mColumns;// - 0.1;
			theConstant = 6;// - 0.1;

		double threshold = view.screenWidth - theConstant * ballWidth;
		;

		// double threshold = GameUtils.mScreenWidth - theConstant*ballWidth;;

		// Log.v(DEBUG_TAG, "onScroll " + x);
		// Log.v(DEBUG_TAG, "onScroll Screen Width " + view.screenWidth);
		// Log.v(DEBUG_TAG, "onScroll Screen Ball width " + ballWidth);
		// Log.v(DEBUG_TAG, "onScroll Screen Threshold " + threshold);

		if (y < yCut // && x < threshold
				&& x > ballWidth / 2)
			view.onMove(-distanceX, -distanceY);
		return true;
	}

	public void onShowPress(MotionEvent e) {
		Log.v(DEBUG_TAG, "onShowPress");
	}

	public boolean onSingleTapUp(MotionEvent e) {
		Log.v(DEBUG_TAG, "onSingleTapUp");
		return false;
	}

	public boolean onDoubleTapEvent(MotionEvent e) {
		Log.v(DEBUG_TAG, "onDoubleTapEvent");
		return false;
	}

	public boolean onSingleTapConfirmed(MotionEvent e) {
		Log.v(DEBUG_TAG, "onSingleTapConfirmed");
		return false;
	}

}