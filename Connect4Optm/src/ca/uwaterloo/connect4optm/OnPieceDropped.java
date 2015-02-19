package ca.uwaterloo.connect4optm;

public interface OnPieceDropped {

	public abstract void onDropped(boolean dropped, int posAnim, int dy,
			int posDest, int xInitial);

}
