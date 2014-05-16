package console_board_display;

public class PlaceDirective extends Directive{
	private char pieceType;
	private char pieceColor;
	
	public PlaceDirective(char pieceType, char pieceColor, int column, int row)
	{
		super(column, row - 1);
		this.pieceColor = pieceColor;
		this.pieceType = pieceType;
	}
	
	public char getPieceType()
	{
		return pieceType;
	}
	
	public char getPieceColor()
	{
		return pieceColor;
	}
}
