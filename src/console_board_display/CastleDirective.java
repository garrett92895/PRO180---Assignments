package console_board_display;

public class CastleDirective extends MoveDirective{
	private MoveDirective rookMove;
	
	public CastleDirective(int column1, int row1, int column2, int row2, MoveDirective directive2)
	{
		super(column1, row1, column2, row2, false);
		rookMove = directive2;
	}
	
	public MoveDirective getRookMove()
	{
		return rookMove;
	}
}
