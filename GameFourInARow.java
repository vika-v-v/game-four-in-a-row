import java.awt.Color;

import edu.bielefeld.io.EAM;

public class GameFourInARow  {
	
	public static void main(String[] args) {
		
		SpielFeld.drawFigur(7, 6, typFigur.Circle);

		boolean pressed, allowNew = true;
		while(true) 
		{
			
			pressed = StdDraw.isMousePressed();
			if(pressed == false) allowNew = true;
			
				
			if(pressed && allowNew) SpielFeld.DrawCircle(StdDraw.mouseX(), StdDraw.mouseY());
			if(pressed) allowNew = false;
		}
		
	}
	
}
