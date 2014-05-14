package file_io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MoveReader {
	private BufferedReader br;
	PieceMap h;
	private final String PIECES = "(q|k|b|n|r|p)";
	private final String COLORS = "(l|d)";
	private final String RANKS = "([a-h])";
	private final String COLUMNS = "([1-8])";
	
	public MoveReader(String fileName)
	{
		File file = new File(fileName);	
		h = new PieceMap();
		try
		{
			br = new BufferedReader(new FileReader(fileName));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found");
			System.exit(0);
		}
	}
	
	public void run()
	{
		String line;
		String[] directives;
		try 
		{
			while((line = br.readLine()) != null)
			{
				directives = line.toLowerCase().split("\\s"); //Splits each line into an array of directives
				boolean matches = false;
				
				//Loops through the array of strings and checks to see if they match the requirements, if they don't then the
				//for loop is finished
				for(int i = 0; i < directives.length; i++)
				{
					if(directives[i].matches("(" + (RANKS + COLUMNS) + "|" + ( PIECES + COLORS + RANKS + COLUMNS) + ")" + "\\*?")) //Regex
					{
						matches = true;
					}
					else
					{
						matches = false;
						i = directives.length;
					}
				}
				
				if(matches)
				{
					parseMove(directives);
				}
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		//Closes the stream
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void parseMove(String[] chessMoves)
	{
		//Output depends on the number of directives
		switch(chessMoves.length)
		{
		case 1:
			String s1 = "Places a " + h.returnPiece(chessMoves[0].charAt(0))
					+ " of color " + h.returnPiece(chessMoves[0].charAt(1))
					+ " at column " + chessMoves[0].charAt(2) 
					+ " and row " + chessMoves[0].charAt(3);
			System.out.println(s1);
			break;
		case 2:
			String s2 = "";
			if(!chessMoves[1].endsWith("*"))
			{
				s2 = "Takes the piece at column " + chessMoves[0].charAt(0)
				+ " row " + chessMoves[0].charAt(1) 
				+ " and places it at column" + chessMoves[1].charAt(0)
				+ " row " + chessMoves[1].charAt(0);
			}
			else
			{
				s2 = "Takes the piece at column " + chessMoves[0].charAt(0)
				+ " row " + chessMoves[0].charAt(1) 
				+ " and captures the piece at column" + chessMoves[1].charAt(0)
				+ " row " + chessMoves[1].charAt(0);
			}
			System.out.println(s2);
			break;
		case 4:
			String s3 = "Moves the King from column " + chessMoves[0].charAt(0)
			+ " row " + chessMoves[0].charAt(1)
			+ " to column " + chessMoves[1].charAt(0)
			+ " row " + chessMoves[1].charAt(1)
			+ " and the Rook from column " + chessMoves[2].charAt(0)
			+ " row " + chessMoves[2].charAt(1)
			+ " to column " + chessMoves[3].charAt(0)
			+ " row " + chessMoves[3].charAt(1);
			System.out.println(s3);
		}
	}

}
