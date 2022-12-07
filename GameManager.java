package FourInARow;

import edu.princeton.cs.introcs.StdDraw;

public class GameManager  {
	
	public static void main(String[] args) {
		
		GameField field = new GameField();
		field.drawField();

		boolean pressed = false, allowNew = true;
		while(true) 
		{
			pressed = StdDraw.isMousePressed(); // prozess mouse click
			if(pressed == false) allowNew = true; // don't allow double method call while long press
			if(pressed && allowNew) field.moveMade(StdDraw.mouseX(), StdDraw.mouseY());
			if(pressed) allowNew = false;
		}
		
	}
	
}
