package piece_movement;

public class Directive {
	private int row1;
	private int column1;
	
	public Directive(int column1, int row1)
	{
		this.row1 = row1;
		this.column1 = column1;
	}
	
	public int getRow1() {
		return row1;
	}
	public void setRow1(int row1) {
		this.row1 = row1;
	}
	public int getColumn1() {
		return column1;
	}
	public void setColumn1(int column1) {
		this.column1 = column1;
	}
}
