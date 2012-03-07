package game.input;
import java.io.*;
public class Separator 
{
	private BufferedReader bufreader;
	private byte [][] mas;
	public static int X, Y; // кол-во по ширине и высоте
		
	public byte[][] interpritation(String name) throws IOException // входные данные в массив byte
	{
		bufreader = new BufferedReader(new FileReader(name));
		String read [] = bufreader.readLine().split(" ");
		X = Integer.valueOf(read[0]);
		Y = Integer.valueOf(read[1]);
		mas = new byte[X][Y];
		for(int i = 0; i < X; i++)
		{
			String s = bufreader.readLine();
			for(int g = 0; g < Y; g++)
			{
				char a = s.charAt(g);
				switch(a)
				{
				case 'o': mas[i][g] = 0;
				case 'w': mas[i][g] = 1;
				}
			}
		}
		return mas;
	}
}
