package ca.uwaterloo.connect4optm;

import android.content.Context;
import android.graphics.Bitmap;
import ca.uwaterloo.connect4optm.GameUtils.PieceType;

public class OnPieceMovedListener implements OnPieceMoved {

	BoardView mBoardView;
	BoardViewAdapter mBoardViewAdapter;
	Context mContext;
	DropAreaView mDropAreaView;
	boolean turn;

	// static PieceType srcPieceType;
	// static Bitmap srcBmp;

	public OnPieceMovedListener(BoardView mBoardView,
			BoardViewAdapter mBoardViewAdapter, Context mContext,
			DropAreaView mDropAreaView) {
		this.mBoardView = mBoardView;
		this.mBoardViewAdapter = mBoardViewAdapter;
		this.mContext = mContext;
		this.mDropAreaView = mDropAreaView;
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

			// Play sound
			mBoardView.playDropSound();
			
			// Deactivate the listener
			mBoardView.setPieceMoved(false);
			// Debug
			// Bitmap emptyPiece = BitmapFactory.decodeResource(
			// mContext.getResources(), R.drawable.green);
			// Debug!
			// mBoardViewAdapter.updatePieces(0, PieceType.Player1, emptyPiece);

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
