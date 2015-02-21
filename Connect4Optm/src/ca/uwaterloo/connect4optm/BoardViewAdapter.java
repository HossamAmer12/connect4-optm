package ca.uwaterloo.connect4optm;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import ca.uwaterloo.connect4optm.GameUtils.PieceType;

public class BoardViewAdapter extends ArrayAdapter<Piece> {
	Context mContext;
	int layoutResourceId;
	Resources mResources;
	int nRows;
	int nCols;
	ArrayList<Piece> pieces = new ArrayList<Piece>();
	LayoutInflater mInflater;

	int[] mTopArray;

	// private OnPiecesAvailable OnPiecesAvailable;

	public BoardViewAdapter(Context context, int layoutResourceId,
			ArrayList<Piece> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.mContext = context;
		this.pieces = data;
	}

	public BoardViewAdapter(Context context, int layoutResourceId,
			ArrayList<Piece> data, Resources mResouces, int nRows, int nCols) {

		super(context, layoutResourceId, data);

		this.layoutResourceId = layoutResourceId;
		this.mContext = context;
		this.pieces = data; // // XXXXX why?!
		this.mResources = mResouces;
		this.nRows = nRows;
		this.nCols = nCols;

		mTopArray = new int[this.nCols];

		// Initialize the pieces
		this.initPieces();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;

		if (row == null) {
			mInflater = ((Activity) mContext).getLayoutInflater();
			row = mInflater.inflate(layoutResourceId, parent, false);

			holder = new RecordHolder();
			holder.imageItem = (ImageView) row.findViewById(R.id.item_image);

			row.setTag(holder);

		} else {
			holder = (RecordHolder) row.getTag();
		}

		Piece item = pieces.get(position);
		holder.imageItem.setImageBitmap(item.getImage());

		return row;

	}

	public void initPieces() {

		Bitmap emptyPiece = BitmapFactory.decodeResource(mResources,
				R.drawable.empty);

		// Fix size bug of Gridview
		double scaleFactor = 1.1;
		emptyPiece = Bitmap.createScaledBitmap(emptyPiece,
				(int) (emptyPiece.getWidth() * scaleFactor),
				(int) (emptyPiece.getWidth() * scaleFactor), true);

		int pos = this.nRows * this.nCols - 1;
		for (int y = nRows - 1; y >= 0; y--) {

			for (int x = 0; x < nCols; x++) {

				// this.pieces.add(new Piece(emptyPiece, PieceType.Empty, x, y,
				// pos));
				// Piece(Bitmap image, PieceType pieceType, int xPos, int yPos,
				// int position, int xIndex, int yIndex)
				this.pieces.add(new Piece(emptyPiece, PieceType.Empty, -1, -1,
						pos, x, y));

				pos--;
			}

		}

		this.notifyDataSetChanged();

	}

	public void resetPieces() {

		Bitmap emptyPiece = BitmapFactory.decodeResource(mResources,
				R.drawable.empty);

		// Fix size bug of Gridview
		double scaleFactor = 1.1;
		emptyPiece = Bitmap.createScaledBitmap(emptyPiece,
				(int) (emptyPiece.getWidth() * scaleFactor),
				(int) (emptyPiece.getWidth() * scaleFactor), true);

		for (int i = 0; i < this.getCount(); i++) {
			this.pieces.get(i).setImage(emptyPiece);

		}
		
		Arrays.fill(this.mTopArray, 0);
		this.notifyDataSetChanged();

	}

	public void updatePieces(int position, PieceType pieceType,
			Bitmap newPieceBitmap) {
		Piece tmp = pieces.get(position);
		int xIndPressed = tmp.getxIndex();

		// Overload the column
		if (1 + mTopArray[xIndPressed] > nRows)
			return;

		int xActual = xIndPressed;
		// int yActual = nRows - mTopArray[xIndPressed] - 1;
		// int yActual = mTopArray[xIndPressed];
		int yActual = (position - xActual) / this.nCols;
		int linearPos = getLinearPosition(xActual, yActual);

		// this.pieces.set(linearPos, new Piece(newPieceBitmap,
		// pieceType, xActual, yActual, linearPos));

		// Circle == Same diameter along X and Y direction
		int width = this.pieces.get(1).getxPos();
		int height = this.pieces.get(1).getxPos();// this.pieces.get(1).getyPos();;

		// width = diamter;
		// height = diamter;

		// width = this.pieces.get(0).getyPos() + 50;
		// height = width + 50;

		newPieceBitmap = Bitmap.createScaledBitmap(newPieceBitmap, width,
				height, true);
		this.pieces.get(linearPos).setImage(newPieceBitmap);

		// Update the top array
		// mTopArray[xIndPressed]++;

		// Notify!
		this.notifyDataSetChanged();
	}

	public int getLinearPosition(int x, int y) {
		int linearPos = y * nCols + x;

		return linearPos;

	}

	/**
	 * 
	 * @param pieceType
	 * @return
	 */
	public Bitmap getNewPieceType(PieceType pieceType) {
		Bitmap newPiece;
		if (pieceType == PieceType.Player1)
			newPiece = BitmapFactory.decodeResource(mResources,
					R.drawable.green);
		else if (pieceType == PieceType.Player2)
			newPiece = BitmapFactory.decodeResource(mResources, R.drawable.red);
		else
			newPiece = BitmapFactory.decodeResource(mResources,
					R.drawable.empty);

		return newPiece;

	}

	// Inner class
	static class RecordHolder {
		ImageView imageItem;

		View newPiece;

	}

	public void updateTopArray(int xIndPressed) {
		// Update the top array
		this.mTopArray[xIndPressed]++;
	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();

		// System.out.println(" Pieces available ");
		// System.out.println( this.pieces.get(30));

	}

}// End BoardView Adapter