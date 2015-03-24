package ca.uwaterloo.connect4optm;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.TextView;
import ca.uwaterloo.connect4optm.GameUtils.PieceType;

public class OnPieceMovedListener implements OnPieceMoved {

	BoardView mBoardView;
	BoardViewAdapter mBoardViewAdapter;
	Context mContext;
	DropAreaView mDropAreaView;
	boolean turn;

	// static PieceType srcPieceType;
	// static Bitmap srcBmp;
	TextView mCheckWin;

	
	public OnPieceMovedListener(BoardView mBoardView,
			BoardViewAdapter mBoardViewAdapter, Context mContext,
			DropAreaView mDropAreaView) {
		this.mBoardView = mBoardView;
		this.mBoardViewAdapter = mBoardViewAdapter;
		this.mContext = mContext;
		this.mDropAreaView = mDropAreaView;
		
	}
	
	public OnPieceMovedListener(BoardView mBoardView,
			BoardViewAdapter mBoardViewAdapter, Context mContext,
			DropAreaView mDropAreaView, TextView mCheckWin) {
		this.mBoardView = mBoardView;
		this.mBoardViewAdapter = mBoardViewAdapter;
		this.mContext = mContext;
		this.mDropAreaView = mDropAreaView;
		this.mCheckWin = mCheckWin;
		
	}

	@Override
	public void onMoved(boolean moved, int posAnim) {

		if (moved) {
			
			
			// Toggle DropView!
			toggleDropView();
			
			// Save source pieceType
			PieceType srcPieceType = mBoardViewAdapter.getItem(posAnim)
					.getPieceType();
			Bitmap srcBmp = mBoardViewAdapter.getItem(posAnim).getImage();
			mBoardView.setSrcPieceType(srcPieceType);
			mBoardView.setSrcBmp(srcBmp);

			// Update to animate
			mBoardViewAdapter.updatePieces(posAnim, mBoardView.mPieceType,
					mBoardView.newPieceBitmap);
			
			if (!mBoardView.isComputerPlayed() && mBoardView.isComputer()) {
				mCheckWin.setText("Thinking...");
			}
			
			// Play sound
			mBoardView.playDropSound();
			
			// Deactivate the listener
			mBoardView.setPieceMoved(false);
			// Debug
			// Bitmap emptyPiece = BitmapFactory.decodeResource(
			// mContext.getResources(), R.drawable.green);
			// Debug!
			// mBoardViewAdapter.updatePieces(0, PieceType.Player1, emptyPiece);
			
			// Deactivate the reset button
			mBoardView.setReset(false);
			
			
				

		}
	}
	
	public void toggleDropView()
	{
		PieceType newPieceType;
		if (mBoardView.mPieceType == PieceType.Player1)
			newPieceType = PieceType.Player2;
		else
			newPieceType = PieceType.Player1;
		mDropAreaView.togglePieceColor(newPieceType);
	}

}
