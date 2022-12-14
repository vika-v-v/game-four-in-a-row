package FourInARow;

import edu.princeton.cs.introcs.StdDraw;

enum typeFigure {
	Circle, Rectangle
}

public class GameField {
	
	public GameField(int amountX, int amountY, typeFigure figur) {
		super();
		this.amountX = amountX;
		this.amountY = amountY;
		this.figur = figur;
	}

	double x = 200.0; 
	double y = 200.0;
	
	// amount of circles in a field
	int amountX; 
	int amountY;
	
	typeFigure figur;
	
	int currentPlayer = 1;

	int[] yRow; // amount of filled circles in a row
	int[][] field; // field is filled with 0 - empty, 1 - first player and 2 - second player
	
	double size = 2.0; // size of the single item (circle on a field)
	boolean isEnded = false;

	public void drawField() {

		yRow = new int[amountX]; // all slots are empty
		for (int i = 0; i < yRow.length; i++)
			yRow[i] = 0;

		field = new int[amountY][amountX]; // field is empty
		for (int i = 0; i < amountY; i++) {
			for (int j = 0; j < amountX; j++) {
				field[i][j] = 0;
			}
		}

		double slothX = x / (amountX + 1); // amount of place for one sloth
		double slothY = y / (amountY + 1);
		
		size = Math.min(slothX, slothY); // size of circle

		StdDraw.setXscale(0, x); // prepare board
		StdDraw.setYscale(0, y);

		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.005);

		for (int i = 1; i <= amountX; i++) { // draw field
			for (int j = 1; j <= amountY; j++) {
				if (figur == typeFigure.Circle) {
					StdDraw.circle(i * slothX, j * slothY, size / 3);
				} 
				else {
					if ((i + j) % 2 == 0) filledGradientSquare(i * slothX, j * slothY, size / 2);
				}

			}
		}
	}

	public void moveMade(double xPos, double yPos) {
		
		
		// in order to calculate position to fill, field schould be iterated one more time
		double slothX = x / (amountX + 1);
		double slothY = y / (amountY + 1);
		size = Math.min(slothX, slothY);

		for (int i = 1; i <= amountX; i++) 
		{
			for (int j = 1; j <= amountY; j++) 
			{ 
				// check if position of mouse click is inside the circle
				if (xPos > i * slothX - size / 3 && xPos < i * slothX + size / 3
						&& yPos < j * slothY + size / 3 && yPos > j * slothY - size / 3
						&& yRow[i - 1] < amountX - 1 && !isEnded) 
				{
					if(currentPlayer == 1) StdDraw.setPenColor(StdDraw.PINK);
					else StdDraw.setPenColor(StdDraw.ORANGE);
					
					yRow[i - 1]++; // calculate at witch height to draw next move
					StdDraw.filledCircle(i * slothX, yRow[i - 1] * slothY, size / 3);
					fillBoardArray(i - 1); // fill array with board
					
					if (analyseWin())
					{
						StdDraw.text(100, 190, "Player " + currentPlayer + " wins!");
						isEnded = true;
					}
					
					currentPlayer = currentPlayer == 1 ? 2 : 1;
					break;
				}

			}
		}
		
	}

	private boolean analyseWin() {
		int points;
		
		// check horisontal
		for (int i = 0; i < field.length; i++) // row
		{
			points = 0;
			for (int j = 0; j < field[0].length; j++) // cell
			{
				if (field[i][j] == currentPlayer) points++;
				if (field[i][j] != currentPlayer) points = 0;
				if (points == 4) return true;
			}
		}
		
		// check vertical
		for (int i = 0; i < field[0].length; i++) // column
		{ 
			points = 0;
			for (int j = 0; j < field.length; j++) // cell
			{ 
				if (field[j][i] == currentPlayer) points++;
				if (field[j][i] != currentPlayer) points = 0;
				if (points == 4) return true;
			}

		}
		
		// check left diagonal
		// caclulated using the main diagonal and parallel, the parallel is defined by shift
		for(int shift = 0; shift < field.length - 3; shift++) // shift to the right
		{
			points = 0;
			for (int i = shift, j = 0; i < field.length && j < field[0].length; i++, j++) 
			{
				if (field[i][j] == currentPlayer) points++;
				if (field[i][j] != currentPlayer) points = 0;
				if (points == 4) return true;
			}
		}
		
		for(int shift = 0; shift < field[0].length - 3; shift++) // shift to the up
		{
			points = 0;
			for (int i = 0, j = shift; i < field.length && j < field[0].length; i++, j++) 
			{
				if (field[i][j] == currentPlayer) points++;
				if (field[i][j] != currentPlayer) points = 0;
				if (points == 4) return true;
			}
		
		}
		
		// check right diagonal
		// caclulated using the secondary diagonal and parallel, the parallel is defined by shift
		for(int shift = 0; shift < field.length - 3; shift++) // shift down
		{
			points = 0;
			for (int i = field.length - 1 - shift, j = 0; i >= 0 && j < field[0].length; i--, j++) 
			{
				if (field[i][j] == currentPlayer) points++;
				if (field[i][j] != currentPlayer) points = 0;
				if (points == 4) return true;
			}
		}
		
		for(int shift = 0; shift < field[0].length - 3; shift++) // shift to the left
		{
			points = 0;
			for (int i = field.length - 1, j = shift; i >= 0 && j < field[0].length; i--, j++) 
			{
				if (field[i][j] == currentPlayer) points++;
				if (field[i][j] != currentPlayer) points = 0;
				if (points == 4) return true;
			}
		}
	
		return false;
	}
	
	private void filledGradientSquare(double x, double y, double r) { 

		double step = 0.5;
		int col = 0;

		StdDraw.setPenRadius(0.01);
		for (double j = y - r; j < y + r; j += step) 
		{
			col = (int) (j * 255.0 / 200.0);
			StdDraw.setPenColor(255 - col, 255 - col, col);
			StdDraw.line(x - r, j, x + r, j);
		}
	}
	
	private void fillBoardArray(int x) {
		for (int i = field.length - 1; i >= 0; i--) {
			if (field[i][x] == 0) 
			{
				field[i][x] = currentPlayer;
				break;
			}
		}
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getAmountX() {
		return amountX;
	}

	public int getAmountY() {
		return amountY;
	}

	public typeFigure getFigur() {
		return figur;
	}
}
