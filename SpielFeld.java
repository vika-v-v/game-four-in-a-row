import java.awt.Font;

enum typFigur {
	Circle, Rectangle
}

public class SpielFeld {
	static double groesse;
	static double x = 200.0;
	static double y = 200.0;
	static int amountX;
	static int amountY;

	static int[] yRow;
	static int[][] field;

	public static void drawFigur(int amountX, int amountY, typFigur figur) {
		SpielFeld.amountX = amountX;
		SpielFeld.amountY = amountY;

		yRow = new int[amountX];
		for (int i = 0; i < yRow.length; i++)
			yRow[i] = 0;

		field = new int[amountY][amountX];
		for (int i = 0; i < amountY; i++) {
			for (int j = 0; j < amountX; j++) {
				field[i][j] = 0;
			}
		}

		double abstandX = x / (amountX + 1);
		double abstandY = y / (amountY + 1);
		SpielFeld.groesse = Math.min(abstandX, abstandY);
		// System.out.println(groesse);

		StdDraw.setXscale(0, x);
		StdDraw.setYscale(0, y);

		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.005);

		for (int i = 1; i <= amountX; i++) {
			for (int j = 1; j <= amountY; j++) {
				if (figur == typFigur.Circle) {
					StdDraw.circle(i * abstandX, j * abstandY, groesse / 3);
				} else {

					// StdDraw.setPenColor((i + j) % 2 == 0 ? StdDraw.ORANGE : StdDraw.BLACK);
					// StdDraw.filledSquare(i * abstandX, j * abstandY, groesse / 2);
					if ((i + j) % 2 == 0)
						filledGradientSquare(i * abstandX, j * abstandY, groesse / 2);
				}

			}
		}
	}

	private static void filledGradientSquare(double x, double y, double r) {
		double step = 0.5;
		int col = 0;

		StdDraw.setPenRadius(0.01);
		for (double j = y - r; j < y + r; j += step) {
			col = (int) (j * 255.0 / 200.0);
			StdDraw.setPenColor(255 - col, 255 - col, col);
			StdDraw.line(x - r, j, x + r, j);
		}

	}

	public static void DrawCircle(double xPos, double yPos) {
		// StdDraw.circle(xPos, yPos, groesse / 3);
		if(checkWin()) return;
		
		double abstandX = x / (amountX + 1);
		double abstandY = y / (amountY + 1);
		groesse = Math.min(abstandX, abstandY);
		// System.out.println(groesse);

		StdDraw.setXscale(0, x);
		StdDraw.setYscale(0, y);

		if (StdDraw.getPenColor() == StdDraw.PINK)
			StdDraw.setPenColor(StdDraw.ORANGE);
		else
			StdDraw.setPenColor(StdDraw.PINK);

		for (int i = 1; i <= amountX; i++) {
			for (int j = 1; j <= amountY; j++) {
				if (xPos > i * abstandX - groesse / 3 && xPos < i * abstandX + groesse / 3
						&& yPos < j * abstandY + groesse / 3 && yPos > j * abstandY - groesse / 3
						&& yRow[i - 1] < amountX - 1) {
					yRow[i - 1]++;
					// System.out.println(yRow[i - 1]);
					StdDraw.filledCircle(i * abstandX, yRow[i - 1] * abstandY, groesse / 3);
					fillNextFieldPos(i - 1);

				}

			}
		}
	}

	static int value = 2;

	private static void fillNextFieldPos(int x) {
		for (int i = field.length - 1; i >= 0; i--) {
			if (field[i][x] == 0) {
				field[i][x] = value;
				value = value == 1 ? 2 : 1;
				break;
			}
		}

		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				// System.out.print(field[i][j]);
			}
			// System.out.println();
		}

		if (checkWin())
		{
			StdDraw.text(100, 190, "Player " + value + " wins!");
		}
	}

	private static boolean checkWin() {
		int currHorisontal = 0, currVertical = 1, rDiagonal = 1, lDiagonal = 1;

		for (int i = 0; i < field.length; i++) // row
		{
			currHorisontal = 0;
			for (int j = 1; j < field[0].length; j++) // cell
			{
				if (currHorisontal == 0 && field[i][j] != 0)
					currHorisontal = 1;
				if (field[i][j] == field[i][j - 1] && field[i][j] != 0)
					currHorisontal++;
				if (field[i][j - 1] == 0 || field[i][j] != field[i][j - 1])
					currHorisontal = 0;
				if (currHorisontal == 4)
					return true;
			}
		}

		for (int i = 0; i < field[0].length; i++) {
			for (int j = 1; j < field.length; j++) {
				// if(currVertical == 0 && field[i][j] != 0) currVertical = 1;
				if (field[j][i] == field[j - 1][i] && field[j][i] != 0)
					currVertical++;
				if (field[j - 1][i] == 0 || field[j - 1][i] != field[j][i])
					currVertical = 1;
				if (currVertical == 4)
					return true;
			}

		}

		for(int shift = 0; shift < field.length - 3; shift++)
		{
			rDiagonal = 1;
			for (int i = 1 + shift, j = 1; i < field.length && j < field[0].length; i++, j++) {
				// System.out.println(i + " " + j);
				if (field[i][j] == field[i - 1][j - 1] && field[i - 1][j - 1] != 0)
					rDiagonal++;
				if (field[i - 1][j - 1] == 0 || field[i - 1][j - 1] != field[i][j])
					rDiagonal = 1;
				if (rDiagonal == 4)
					return true;
			}
		}
		
		for(int shift = 0; shift < field[0].length - 3; shift++)
		{
			rDiagonal = 1;
			for (int i = 1, j = 1 + shift; i < field.length && j < field[0].length; i++, j++) {
				// System.out.println(i + " " + j);
				if (field[i][j] == field[i - 1][j - 1] && field[i - 1][j - 1] != 0)
					rDiagonal++;
				if (field[i - 1][j - 1] == 0 || field[i - 1][j - 1] != field[i][j])
					rDiagonal = 1;
				if (rDiagonal == 4)
					return true;
			}
		
		}
		
		for(int shift = 0; shift < field.length - 3; shift++)
		{
			rDiagonal = 1;
			for (int i = field.length - 2 - shift, j = 1; i >= 0 && j < field[0].length; i--, j++) {
				// System.out.println(i + " " + j);
				if (field[i][j] == field[i + 1][j - 1] && field[i + 1][j - 1] != 0)
					rDiagonal++;
				if (field[i + 1][j - 1] == 0 || field[i + 1][j - 1] != field[i][j])
					rDiagonal = 1;
				if (rDiagonal == 4)
					return true;
			}
		}
		
		for(int shift = 0; shift < field[0].length - 3; shift++)
		{
			rDiagonal = 1;
			for (int i = field.length - 2, j = 1 + shift; i >= 0 && j < field[0].length; i--, j++) {
				// System.out.println(i + " " + j);
				if (field[i][j] == field[i + 1][j - 1] && field[i + 1][j - 1] != 0)
					rDiagonal++;
				if (field[i + 1][j - 1] == 0 || field[i + 1][j - 1] != field[i][j])
					rDiagonal = 1;
				if (rDiagonal == 4)
					return true;
			}
		
		}

		return false;
	}
}
