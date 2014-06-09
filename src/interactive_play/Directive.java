package interactive_play;

import java.util.ArrayList;

public abstract class Directive {
	protected int row1;
	protected int column1;

	public Directive(int column1, int row1)
	{
		this.row1 = row1;
		this.column1 = column1;
	}

	public abstract boolean execute(ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces, boolean darkTurn);

	public int getRow1() {
		return row1;
	}
	public void setRow1(int row1) {
		this.row1 = row1;
	}
	public int getColumn1() {
		return column1;
	}
	public void setColumn1(int column1) {
		this.column1 = column1;
	}
}
