package game.field;
public class Field 
{
	private byte [][] pole;
	public int width, height;
	
	public Field(byte[][] array)
	{
		pole = array;
		width = array[0].length;
		height = array.length;		
	}
	
	public int get(int x, int y)
	{
		return pole[y][x];
	}	
	public void explode(int x, int y)
	{
		pole[y][x] = 0;
		return;
	}
}
