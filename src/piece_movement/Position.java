package piece_movement;

public class Position implements Comparable<Position>{
	private int row;
	private int column;
	
	public Position(int row, int column)
	{
		this.row = row;
		this.column = column;
	}

	@Override
	public int compareTo(Position position2) {
		int result = 0;
		
		if(row == position2.getRow() && column == position2.getColumn())
		{
			result = 0;
		}
		
		return result;
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

}
