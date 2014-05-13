package file_io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MoveReader {
	private BufferedReader br;
	private final String PIECES = "(q|k|b|n|r|p)";
	private final String COLORS = "(l|d)";
	private final String RANKS = "([a-h])";
	private final String COLUMNS = "([1-8])";
	
	public MoveReader(String fileName)
	{
		File file = new File(fileName);
		
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
		String[] line;
		
		try 
		{
			while((line = br.readLine().toLowerCase().split("\\s")) != null)
			{
				boolean matches = false;
				
				//Loops through the array of strings and checks to see if they match the requirements, if they don't then the
				//for loop is finished
				for(int i = 0; i < line.length; i++)
				{
					if(line[i].matches((RANKS + COLUMNS) + "|" + ( PIECES + COLORS + RANKS + COLUMNS)))
					{
						matches = true;
					}
					else
					{
						matches = false;
						i = line.length;
					}
				}
				
				if(matches)
				{
					parseMove(line);
				}
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void parseMove(String[] chessMoves)
	{
		switch(chessMoves.length)
		{
		case 1:
			
		}
	}
	
	private String parseAbbr(char )
}
