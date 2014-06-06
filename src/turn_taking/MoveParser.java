package turn_taking;

public class MoveParser {
	private static PieceMap h = new PieceMap();
	
	public Directive parseMove(String[] chessMoves)
	{
		Directive returnDirective = null;
		//Output depends on the number of directives
		switch(chessMoves.length)
		{
		case 1:
			returnDirective = new PlaceDirective(
					chessMoves[0].charAt(0), 
					chessMoves[0].charAt(1), 
					chessMoves[0].charAt(2) - 'a',
					Math.abs(Character.getNumericValue(chessMoves[0].charAt(3)) - 8));
			break;
		case 2:
			if(chessMoves[1].endsWith("*"))
			{
				returnDirective = new CaptureDirective(
						chessMoves[0].charAt(0) - 'a',
						Math.abs(Character.getNumericValue(chessMoves[0].charAt(1)) - 8),
						chessMoves[1].charAt(0) - 'a',
						Math.abs(Character.getNumericValue(chessMoves[1].charAt(1)) - 8));
			}
			else
			{
				returnDirective = new MoveDirective(
						chessMoves[0].charAt(0) - 'a',
						Math.abs(Character.getNumericValue(chessMoves[0].charAt(1)) - 8),
						chessMoves[1].charAt(0) - 'a',
						Math.abs(Character.getNumericValue(chessMoves[1].charAt(1)) - 8));
			}
			break;
		case 4:
			returnDirective = new CastleDirective(
					chessMoves[0].charAt(0) - 'a',
					Math.abs(Character.getNumericValue(chessMoves[0].charAt(1)) - 8),
					chessMoves[1].charAt(0) - 'a',
					Math.abs(Character.getNumericValue(chessMoves[1].charAt(1)) - 8),
					new MoveDirective(
							chessMoves[2].charAt(0) - 'a',
							Math.abs(Character.getNumericValue(chessMoves[2].charAt(1)) - 8),
							chessMoves[3].charAt(0) - 'a',
							Math.abs(Character.getNumericValue(chessMoves[3].charAt(1)) - 8))
					);
		}
		return returnDirective;
	}
}
