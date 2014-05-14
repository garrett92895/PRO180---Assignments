package file_io;

public class HashPiece {
	private char[] pieceHashes = new char[] {'k', 'q', 'b', 'n', 'r', 'p', 'l', 'd'};
	private String[] pieces = new String[] {"King", "Queen", "Bishop", "Knight", "Rook", "Pawn", "light", "dark"};
	
	public String returnPiece(char c)
	{
		int index = 0;
		for(int i = 0; i < pieceHashes.length; i++)
		{
			if(pieceHashes[i] == c)
			{
				index = i;
			}
		}
		
		return pieces[index];
	}
}

