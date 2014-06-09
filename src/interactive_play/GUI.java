package interactive_play;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI {
	private Game game;
	private ChessPanel[][] panels;
	private Piece currentPiece; 
	private JPanel masterPanel;
	
	public GUI(Game game)
	{
		this.game = game;
		currentPiece = null;
		JFrame frame = new JFrame("Chess");
		masterPanel = new JPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(800, 800));
		masterPanel.setLayout(new GridLayout(8, 8));

		panels = new ChessPanel[8][8];
		int switchColor = 0;
		for(int i = 0; i < panels.length; i++)
		{
			for(int x = 0; x < panels[i].length; x++)
			{
				if(switchColor == 0)
				{
					panels[i][x] = new ChessPanel(new Position(i, x), Color.WHITE);
				}
				else
				{
					panels[i][x] = new ChessPanel(new Position(i, x), Color.GRAY);
				}
				switchColor = Math.abs(switchColor - 1);
				masterPanel.add(panels[i][x]);
			}
			switchColor = Math.abs(switchColor - 1);
		}

		frame.add(masterPanel);
		frame.setVisible(true);
	}
	
    public BufferedImage resizeImage(final Image image, int width, int height) 
    {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
 
        return bufferedImage;
    }
    
    public boolean pieceAlreadySelected()
    {
    	boolean isSelected = false;
    	
    	for(int i = 0; i < panels.length; i++)
    	{
    		for(int x = 0; x < panels[i].length; x++)
    		{
    			if(panels[i][x].isSelected)
    			{
    				isSelected = true;
    			}
    		}
    	}
    	
    	return isSelected;
    }
    
    public void unselectPiece()
    {
    	for(int i = 0; i < panels.length; i++)
    	{
    		for(int x = 0; x < panels[i].length; x++)
    		{
    			panels[i][x].isSelected = false;
    		}
    	}
    }
    
    public void repaintAll()
    {
    	for(int i = 0; i < panels.length; i++)
    	{
    		for(int x = 0; x < panels[i].length; x++)
    		{
    			panels[i][x].repaint();
    		}
    	}
    }
    
    public void resetAll()
    {
    	for(int i = 0; i < panels.length; i++)
    	{
    		for(int x = 0; x < panels[i].length; x++)
    		{
    			panels[i][x].setIsPossibleMove(false);
    		}
    	}
    }
    
    public ChessPanel getPanelOnPosition(Position position)
    {
    	ChessPanel panel = null;
    	
    	for(int i = 0; i < panels.length; i++)
    	{
    		for(int x = 0; x < panels[i].length; x++)
    		{
	    		if(panels[i][x].getPosition().compareTo(position) == 0)
	    		{
	    			panel = panels[i][x];
	    		}
    		}
    	}
    	
    	return panel;
    }
	    
	private class ChessPanel extends JPanel
	{
		private Position position;
		private Color color;
		private boolean isSelected;
		private boolean isHovered;
		private boolean isPossibleMove;
		private boolean hasPiece;
		
		private ChessPanel(Position position, Color color)
		{
			this.position = position;
			this.color = color;
			isSelected = false;
			isHovered = false;
			isPossibleMove = false;
			hasPiece = false;
			addMouseListener(new ChessListener());
		}
		
		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.setColor(color);
			Piece piece = ChessFunctions.findPiece(position, game.getChessBoard(), game.getDarkPieces(), game.getLightPieces());
			
			if(isSelected && piece != null)
			{
				g.setColor(new Color(0x64324B));
			}
			else if(isHovered)
			{
				g.setColor(new Color(0xCC6699));
			}
			else if(isPossibleMove)
			{
				g.setColor(new Color(0xCCFF66));
			}

			g.fillRect(0, 0, 100, 100);
			

			if(piece != null)
			{ 			
				hasPiece = true;
//				g.drawImage(resizeImage(PieceMap.returnImage(piece.getColorModifier(), piece.getPieceChar()).getImage(), this.getHeight(), this.getWidth()), 0, 0, null);
				g.drawImage(PieceMap.returnImage(piece.getColorModifier(), piece.getPieceChar()).getImage(), 0, 0, null);
				
				
				if(isHovered && ChessFunctions.isRightTurn(game.isDarkTurn(), piece))
				{
					ArrayList<Position> positions = game.getMovesForPiece(piece);
					for(Position p : positions)
					{
						ChessPanel possibleMovePanel = getPanelOnPosition(p);
						if(p != null)
						{
							possibleMovePanel.setIsPossibleMove(true);
							repaintAll();
						}
					}
				}
				
			}
		}
		
		public Position getPosition()
		{
			return position;
		}

		public void setIsPossibleMove(boolean b)
		{
			isPossibleMove = b;
		}
		
		private class ChessListener implements MouseListener, MouseMotionListener
		{

			@Override
			public void mouseDragged(MouseEvent e) {}

			@Override
			public void mouseMoved(MouseEvent e) {}

			@Override
			public void mouseClicked(MouseEvent e) 
			{
				Piece piece = ChessFunctions.findPiece(position, game.getChessBoard(), game.getDarkPieces(), game.getLightPieces());
				
				if(!pieceAlreadySelected())
				{
					if(piece != null && ChessFunctions.isRightTurn(game.isDarkTurn(), piece) && game.getPiecesWithMoves().contains(piece))
					{
						currentPiece = piece;
						isSelected = true;
					}
				}
				else if(isPossibleMove)
				{
					unselectPiece();
					if(piece == null)
					{
					game.executeDirective(new MoveDirective(currentPiece.getPosition().getColumn(), currentPiece.getPosition().getRow(),
											position.getColumn(), position.getRow()));
					}
					else
					{
						game.executeDirective(new CaptureDirective(currentPiece.getPosition().getColumn(), currentPiece.getPosition().getRow(),
								position.getColumn(), position.getRow()));
					}
					currentPiece = null;
					resetAll();
					
				}

				repaintAll();
			}

			@Override
			public void mouseEntered(MouseEvent e) 
			{
				if(!pieceAlreadySelected())
					isHovered = true;
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) 
			{
				isHovered = false;
				if(!pieceAlreadySelected())
				{
					resetAll();
				}
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}
			
		}
	}
	

}









