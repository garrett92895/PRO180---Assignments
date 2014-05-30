package turn_taking;

public class MoveDirective extends Directive{
	private int row2;
	private int column2;
	private boolean isCapture;
	
	public MoveDirective(int column1, int row1, int column2, int row2, boolean isCapture)
	{
		super(column1, row1 - 1);
		this.row2 = row2 - 1;
		this.column2 = column2;
		this.isCapture = isCapture;
	}

	public int getRow2() {
		return row2;
	}

	public int getColumn2() {
		return column2;
	}

	public boolean isCapture() {
		return isCapture;
	}

	@Override
	public void execute(ChessBoard chessBoard, Piece[] darkPieces,
			Piece[] lightPieces) {
	}
}
