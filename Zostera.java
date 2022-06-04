import java.awt.*;

public class Zostera extends Immobile{
	
	int size, x_pos, y_pos;
	Color col;
	
	public Zostera(int size, int x_pos, int y_pos) {
		this.size = size;
		this.x_pos = x_pos;
		this.y_pos = y_pos;
		this.col = Color.GREEN;
	}
	@Override
	public void drawCreature(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		g2.setColor(col);
		g.drawLine(x_pos, y_pos, x_pos, y_pos - size); 
		g.drawLine(x_pos - 2, y_pos, x_pos - 10, y_pos - size * 9/10);
		g.drawLine(x_pos + 2, y_pos, x_pos + 10, y_pos - size * 9/10); 
		g.drawLine(x_pos - 4, y_pos, x_pos - 20, y_pos - size * 4/5); 
		g.drawLine(x_pos + 4, y_pos, x_pos + 20, y_pos - size * 4/5); 
		g.drawLine(x_pos - 6, y_pos, x_pos - 30, y_pos - size * 7/10); 
		g.drawLine(x_pos + 6, y_pos, x_pos + 30, y_pos - size * 7/10); 
		g.drawLine(x_pos - 8, y_pos, x_pos - 40, y_pos - size * 4/7); 
		g.drawLine(x_pos - 8, y_pos, x_pos - 40, y_pos - size * 4/7); 
	}

}
