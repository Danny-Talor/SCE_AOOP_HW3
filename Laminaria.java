import java.awt.*;

public class Laminaria extends Immobile {
	int size, x_pos, y_pos;
	Color col;

	public Laminaria(int size, int x_pos, int y_pos) {
		this.size = size;
		this.x_pos = x_pos;
		this.y_pos = y_pos;
		this.col = Color.GREEN;
	}
	
	@Override
	public void drawCreature(Graphics g) {
		g.setColor(col);

		g.fillArc(x_pos - size / 20, y_pos - size, size / 10, size * 4 / 5, 0, 360);
		g.fillArc(x_pos - size * 3 / 20, y_pos - size * 13/15, size / 10, size * 2 / 3, 0, 360);
		g.fillArc(x_pos + size / 20, y_pos - size * 13/15, size / 10, size * 2 / 3, 0, 360);
		g.drawLine(x_pos, y_pos, x_pos , y_pos - size / 5);
		g.drawLine(x_pos, y_pos, x_pos - size / 10 , y_pos - size / 5);
		g.drawLine(x_pos, y_pos, x_pos + size / 10 , y_pos - size / 5);
	}

}
