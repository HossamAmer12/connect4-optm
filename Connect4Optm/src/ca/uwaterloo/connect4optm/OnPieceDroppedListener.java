package ca.uwaterloo.connect4optm;

import android.content.Context;
import android.widget.TextView;

public class OnPieceDroppedListener implements OnPieceDropped {

	BoardView mBoardView;
	BoardViewAdapter mBoardViewAdapter;
	Context mContext;
	DropAreaView mDropAreaView;
	// static boolean isPlayed = false;

	TextView mCheckWin;

	public OnPieceDroppedListener(BoardView mBoardView,
			BoardViewAdapter mBoardViewAdapter, Context mContext,
			DropAreaView mDropAreaView) {
		this.mBoardView = mBoardView;
		this.mBoardViewAdapter = mBoardViewAdapter;
		this.mContext = mContext;
		this.mDropAreaView = mDropAreaView;
	}

	public OnPieceDroppedListener(BoardView mBoardView,
			BoardViewAdapter mBoardViewAdapter, Context mContext,
			DropAreaView mDropAreaView, TextView mCheckWin) {

		this.mBoardView = mBoardView;
		this.mBoardViewAdapter = mBoardViewAdapter;
		this.mContext = mContext;
		this.mDropAreaView = mDropAreaView;
		this.mCheckWin = mCheckWin;

	}

	@Override
	public void onDropped(boolean dropped, int posAnim, int dy, int posDest,
			int xInitial) {

		if (dropped) {

			// Restore the source piece type
			mBoardViewAdapter.updatePieces(posAnim,
					mBoardView.getSrcPieceType(), mBoardView.getSrcBmp());

			// Add the piece!
			mBoardViewAdapter.updatePieces(posDest, mBoardView.mPieceType,
					mBoardView.newPieceBitmap);
			mBoardViewAdapter.updateTopArray(xInitial);

			// Update the Engine
			mBoardView.mEngine.PlayerMove_updated(xInitial);

			// Toggle!
			mBoardView.togglePieceColor();

			// Check win!
			if (mBoardView.checkWin() != 0) {
				System.out
						.println("Player: " + mBoardView.checkWin() + " won!");

				mCheckWin.setText("Player " + mBoardView.checkWin() + " won!");

				if (mBoardView.checkWin() == 1) {

					if (mBoardView.isComputer()) {

						if (mBoardView.isComputerFirst())
							mBoardView.playFailSound();
						else
							mBoardView.playSuccessSound();
					} else
						mBoardView.playSuccessSound();

				} else {
					if (mBoardView.isComputer()) {
						if (mBoardView.isComputerFirst())
							mBoardView.playSuccessSound();
						else
							mBoardView.playFailSound();
					} else
						mBoardView.playSuccessSound();
				}

				mBoardView.setEnabled(false);
				// Stop!
				return;
			} 
			//XXXXXX BUG
			else if (mBoardView.checkWin() == 0 && mBoardView.isFull()) {
				mCheckWin.setText("A Tie! :=)");
				mBoardView.setEnabled(false);

				// Stop!
				return;

			}
			

			// Check the turn and computer player!
			// computer player is 2nd
			// if(mBoardView.mComputerEnabled && mBoardView.player1_goFst == 0)
			// if (!turn)
			// if(!mBoardView.isComputerPlayed() && mBoardView.isComputer() &&
			// !mBoardView.isComputerFirst() )
			if (!mBoardView.isComputerPlayed() && mBoardView.isComputer()) {
				mBoardView.playComputer();
				// mBoardView.setComputerPlayed(true);
				// mBoardView.isComputerPlayed = true;
			}

			// Disable the listener
			mBoardView.setPieceDropped(false);
			// Debug
			// Bitmap emptyPiece = BitmapFactory.decodeResource(
			// mContext.getResources(), R.drawable.green);
			// Debug!
			// mBoardViewAdapter.updatePieces(0, PieceType.Player1, emptyPiece);

		}
	}
	/*
	 * private void playComputer() {
	 * 
	 * System.out.println("OnPieceDropped: Computer Move!");
	 * 
	 * // Get the Engine position // int tmp =
	 * mBoardView.mEngine.NextMoveHint_MC_Scoring(GameUtils.NUMBER_OF_PATHS);
	 * 
	 * int posAnim = 0; int posDest = 35; int xInitial = 0; int dy =
	 * mBoardView.getChildAt(posDest).getTop();
	 * 
	 * mBoardView.animatePiece(posAnim, dy, posDest, xInitial);
	 * 
	 * }
	 */

}
