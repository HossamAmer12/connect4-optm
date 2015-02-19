package ca.uwaterloo.connect4optm;

import android.graphics.Bitmap;
import ca.uwaterloo.connect4optm.GameUtils.PieceType;

public class Piece {

	private Bitmap image;
	private PieceType pieceType;
	private int xIndex;
	private int yIndex;
	private int position;// linear position in the array
	private int xPos;
	private int yPos;

	public Piece(Bitmap image) {
		super();
		this.image = image;

	}

	public Piece(Bitmap image, PieceType pieceType) {
		super();
		this.image = image;
		this.pieceType = pieceType;

	}

	public Piece(Bitmap image, PieceType pieceType, int xPos, int yPos,
			int position) {
		super();
		this.image = image;
		this.pieceType = pieceType;
		this.xPos = xPos;
		this.yPos = yPos;
		this.position = position;

	}

	public Piece(Bitmap image, PieceType pieceType, int xPos, int yPos,
			int position, int xIndex, int yIndex) {
		super();
		this.image = image;
		this.pieceType = pieceType;
		this.xPos = xPos;
		this.yPos = yPos;
		this.position = position;
		this.xIndex = xIndex;
		this.yIndex = yIndex;

	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public PieceType getPieceType() {
		return pieceType;
	}

	public void setPieceType(PieceType pieceType) {
		this.pieceType = pieceType;
	}

	public int getxIndex() {
		return xIndex;
	}

	public void setxIndex(int xIndex) {
		this.xIndex = xIndex;
	}

	public int getyIndex() {
		return yIndex;
	}

	public void setyIndex(int yIndex) {
		this.yIndex = yIndex;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

}
