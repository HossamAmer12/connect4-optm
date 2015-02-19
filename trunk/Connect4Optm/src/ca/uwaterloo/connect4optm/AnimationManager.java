package ca.uwaterloo.connect4optm;

import android.view.animation.Animation;

public class AnimationManager implements Animation.AnimationListener {

	int posAnim;
	int dy;
	BoardView mBoardView;
	int posDest;
	int xInitial;

	public AnimationManager(int posAnim, int dy, BoardView mBoardView) {
		this.posAnim = posAnim;
		this.dy = dy;
		this.mBoardView = mBoardView;
	}

	public AnimationManager(int posAnim, int dy, BoardView mBoardView,
			int posDest, int xInitial) {
		// TODO Auto-generated constructor stub
		this.posAnim = posAnim;
		this.dy = dy;
		this.mBoardView = mBoardView;
		this.posDest = posDest;
		this.xInitial = xInitial;
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		// mBoardView.animationStarted(this.posAnim, this.dy);

		// mBoardView.animationStarted(this.posAnim, this.dy, this.posDest,
		// this.xInitial);

		// Disable board
		// mBoardView.isBoardEnabled = false;
		mBoardView.setBoardEnabled(false);
		mBoardView.animationStarted(this.posAnim);

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		mBoardView.invalidate();
		mBoardView.animationEnded(posAnim, dy, posDest, xInitial);

		// Enable board
		mBoardView.setBoardEnabled(true);
		// mBoardView.isBoardEnabled = true;

	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

}
