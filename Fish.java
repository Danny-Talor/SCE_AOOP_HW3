import java.awt.*;
import java.util.concurrent.CyclicBarrier;

public class Fish extends Swimmable {
	private int EAT_DISTANCE = 4;
	private int size;
	private Color col;
	private int eatCount;
	private int x_front, y_front, x_dir, y_dir;
	private boolean isSuspended = false;

	public Fish() {
		super();
		this.size = 20;
		this.col = new Color(30, 60, 70);
		this.x_front = 0;
		this.y_front = 0;
		this.eatCount = 0;
		this.x_dir = 1;
		this.y_dir = 1;
	}

	/**
	 * Creates a new fish
	 *
	 * @param size     as {@code int} to be given.
	 * @param x_front  as {@code int} to be given.
	 * @param y_front  as {@code int} to be given.
	 * @param horSpeed as {@code int} to be given.
	 * @param verSpeed as {@code int} to be given.
	 * @param col      as {@code int} to be given.
	 */
	public Fish(int size, int x_front, int y_front, int horSpeed, int verSpeed, Color col) {
		super(horSpeed, verSpeed);
		this.size = size;
		this.col = col;
		this.x_front = x_front;
		this.y_front = y_front;
		this.eatCount = 0;
		this.x_dir = 1;
		this.y_dir = 1;
	}

	public Fish(Fish other) {
		super.horSpeed = other.horSpeed;
		super.verSpeed = other.verSpeed;
		this.size = other.size;
		this.col = other.col;
		this.x_front = other.x_front;
		this.y_front = other.y_front;
		this.eatCount = other.eatCount;
		this.x_dir = other.x_dir;
		this.y_dir = other.y_dir;
	}

	/**
	 * @return String with the name of the animal
	 */
	public String getAnimalName() {
		return "Fish";
	}

	public void setSuspend() {
		isSuspended = true;
	}

	public void setResume() {
		synchronized (this) {
			isSuspended = false;
			notify();
		}
	}

	/**
	 * @return the fish size
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * this function check if the fish eat enough to go bigger
	 * 
	 * @see changefish
	 */
	public void eatInc() {
		this.eatCount++;
		if (this.eatCount == this.EAT_DISTANCE) {
			this.eatCount = 0;
			changeFish();
		}
	}

	/**
	 * @return int with the number of time the fish ate before he chage his size
	 */
	public int getEatCount() {
		return this.eatCount;
	}

	/**
	 * @return String with the color of the fish
	 */
	public String getColor() {
		String rgb = String.valueOf(this.col.getRed()) + "," + String.valueOf(this.col.getGreen()) + ","
				+ String.valueOf(this.col.getBlue());
		if (rgb.equals("255,0,0"))
			return "Red";
		else if (rgb.equals("0,0,255"))
			return "Blue";
		else if (rgb.equals("0,255,0"))
			return "Green";
		else if (rgb.equals("0,255,255"))
			return "Cyan";
		else if (rgb.equals("255,165,0"))
			return "Orange";
		else if (rgb.equals("255,255,0"))
			return "Yellow";
		else if (rgb.equals("255,0,255"))
			return "Magenta";
		else if (rgb.equals("255,105,180"))
			return "Pink";
		else
			return "Black";
	}

	/**
	 * this function increse the size of the fish by one
	 */
	public void changeFish() {
		this.size++;
	}

	public void drawAnimal(Graphics g) {
		g.setColor(col);
		if (x_dir == 1) // fish swims to right side
		{
			// Body of fish
			g.fillOval(x_front - size, y_front - size / 4, size, size / 2);

			// Tail of fish
			int[] x_t = { x_front - size - size / 4, x_front - size - size / 4, x_front - size };
			int[] y_t = { y_front - size / 4, y_front + size / 4, y_front };
			Polygon t = new Polygon(x_t, y_t, 3);
			g.fillPolygon(t);

			// Eye of fish
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(255 - col.getRed(), 255 - col.getGreen(), 255 - col.getBlue()));
			g2.fillOval(x_front - size / 5, y_front - size / 10, size / 10, size / 10);

			// Mouth of fish
			if (size > 70)
				g2.setStroke(new BasicStroke(3));
			else if (size > 30)
				g2.setStroke(new BasicStroke(2));
			else
				g2.setStroke(new BasicStroke(1));
			g2.drawLine(x_front, y_front, x_front - size / 10, y_front + size / 10);
			g2.setStroke(new BasicStroke(1));
		} else // fish swims to left side
		{
			// Body of fish
			g.fillOval(x_front, y_front - size / 4, size, size / 2);

			// Tail of fish
			int[] x_t = { x_front + size + size / 4, x_front + size + size / 4, x_front + size };
			int[] y_t = { y_front - size / 4, y_front + size / 4, y_front };
			Polygon t = new Polygon(x_t, y_t, 3);
			g.fillPolygon(t);

			// Eye of fish
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(255 - col.getRed(), 255 - col.getGreen(), 255 - col.getBlue()));
			g2.fillOval(x_front + size / 10, y_front - size / 10, size / 10, size / 10);

			// Mouth of fish
			if (size > 70)
				g2.setStroke(new BasicStroke(3));
			else if (size > 30)
				g2.setStroke(new BasicStroke(2));
			else
				g2.setStroke(new BasicStroke(1));
			g2.drawLine(x_front, y_front, x_front + size / 10, y_front + size / 10);
			g2.setStroke(new BasicStroke(1));
		}
	}

	public void setBarrier(CyclicBarrier b) {
		// TODO Auto-generated method stub
	}

	public void run() {

		if (isSuspended) {
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		if (x_front >= AquaFrame.panel.getWidth() || x_front < 0) {
			x_dir = x_dir * -1;
			super.horSpeed = super.horSpeed * -1;
		}

		x_front += super.horSpeed;

		if (y_front >= AquaFrame.panel.getHeight() || y_front < 0) {
			y_dir = y_dir * -1;
			super.verSpeed = super.verSpeed * -1;
		}

		y_front += super.verSpeed;
		AquaFrame.panel.repaint();
		
	}

}
