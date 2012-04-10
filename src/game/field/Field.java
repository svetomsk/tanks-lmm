package game.field;
public class Field 
{
	private byte [][] pole; // собственно поле
	public int width, height; // ширина и высота
	
	public Field(byte[][] array)
	{
		pole = array;
		width = array.length;
		height = array[0].length;		
	}
	
	public int get(int x, int y) // возвращает содержимое определенной ячейки
	{
		return pole[x][y];
	}	
	public void explode(int x, int y, int power) // взрыв
	{
		if(pole[x][y] == 1) return;
		pole[x][y] = 0;
		return;
	}
	
	public boolean isExplodable(int x, int y) // проверка на ударяемость. не воздух
	{
		return pole[x][y] != 0;
	}
	
	public int getWidth() // ширина
	{
		return width;
	}
	
	public int getHeight() // высота
	{
		return height;
	}
}
