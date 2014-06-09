package interactive_play;

import javax.swing.ImageIcon;

public class PieceMap {
	private static final char[] PIECE_HASHES = new char[] {'k', 'q', 'b', 'n', 'r', 'p', 'l', 'd'};
	private static final String[] PIECES = new String[] {"King", "Queen", "Bishop", "Knight", "Rook", "Pawn", "Light", "Dark"};
	private static final String folderName = "PADPieces\\";
	private static final ImageIcon[] BLACK_IMAGES = new ImageIcon[] {new ImageIcon(folderName + "BlackKing.png"), new ImageIcon(folderName + "BlackQueen.png"),
		new ImageIcon(folderName + "BlackBishop.png"), new ImageIcon(folderName + "BlackKnight.png"), new ImageIcon(folderName + "BlackRook.png"), 
		new ImageIcon(folderName + "BlackPawn.png")};
	private static final ImageIcon[] WHITE_IMAGES = new ImageIcon[] {new ImageIcon(folderName + "WhiteKing.png"), new ImageIcon(folderName + "WhiteQueen.png"),
		new ImageIcon(folderName + "WhiteBishop.png"), new ImageIcon(folderName + "WhiteKnight.png"), 
		new ImageIcon(folderName + "WhiteRook.png"), new ImageIcon(folderName + "WhitePawn.png")};
	
	public static String returnPiece(char c)
	{
		int index = 0;
		for(int i = 0; i < PIECE_HASHES.length; i++)
		{
			if(PIECE_HASHES[i] == c)
			{
				index = i;
			}
		}
		
		return PIECES[index];
	}
	
	public static String returnPiece(int colorModifier)
	{
		char color = (colorModifier == 1) ? 'd' : 'l';
		return returnPiece(color);
	}
	
	public static ImageIcon returnImage(int colorModifier, char pieceType)
	{
		ImageIcon[] images = (colorModifier == 1) ? BLACK_IMAGES : WHITE_IMAGES;
		
		int index = 0;
		for(int i = 0; i < images.length; i++)
		{
			if(PIECE_HASHES[i] == pieceType)
			{
				index = i;
			}
		}
		return images[index];
	}
}

