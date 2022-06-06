import java.awt.*;
import java.util.concurrent.*;

public class Jellyfish extends Swimmable {
	private int EAT_DISTANCE = 4;
	private int size;
	private Color col;
	private int eatCount;
	private int x_front, y_front, x_dir, y_dir;
	private boolean isSuspended = false;
	private CyclicBarrier barrier = null;

	/**
	 * Creates a new Jellyfish
	 *
	 * @param size     as {@code int} to be given.
	 * @param x_front  as {@code int} to be given.
	 * @param y_front  as {@code int} to be given.
	 * @param horSpeed as {@code int} to be given.
	 * @param verSpeed as {@code int} to be given.
	 * @param col      as {@code int} to be given.
	 */
	public Jellyfish(int size, int x_front, int y_front, int horSpeed, int verSpeed, Color col) {
		super(horSpeed, verSpeed);
		this.size = size;
		this.col = col;
		this.x_front = x_front;
		this.y_front = y_front;
		this.eatCount = 0;
		this.x_dir = 1;
		this.y_dir = 1;
	}

	public Jellyfish(Jellyfish other) {
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
		return "Jellyfish";
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
	 * @return the Jellyfish size
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * this function check if the Jellyfish eat enough to go bigger
	 * 
	 * @see changeJellyfish
	 */
	public void eatInc() {
		this.eatCount++;
		if (this.eatCount == this.EAT_DISTANCE) {
			this.eatCount = 0;
			changeJellyfish();
		}
	}

	/**
	 * @return int with the number of time the Jellyfish ate before he chage his
	 *         size
	 */
	public int getEatCount() {
		return this.eatCount;
	}

	/**
	 * @return String with the color of the Jellyfish
	 */
	public String getColorName() {
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
			return rgb;
	}
	
	@Override
	public Color getColor() {
		return this.col;
	}
	
	@Override
	public void setColor(Color c) {
		this.col = c;
	}

	/**
	 * this function increse the size of the Jellyfish by one
	 */
	public void changeJellyfish() {
		this.size++;
	}

	public void drawCreature(Graphics g) {
		int numLegs;
		if (size < 40)
			numLegs = 5;
		else if (size < 80)
			numLegs = 9;
		else
			numLegs = 12;

		g.setColor(col);
		g.fillArc(x_front - size / 2, y_front - size / 4, size, size / 2, 0, 180);

		for (int i = 0; i < numLegs; i++)
			g.drawLine(x_front - size / 2 + size / numLegs + size * i / (numLegs + 1), y_front,
					x_front - size / 2 + size / numLegs + size * i / (numLegs + 1), y_front + size / 3);
	}

	public void setBarrier(CyclicBarrier b) {
		this.barrier = b;
	}

	public void run() {

		try {
			Thread.sleep(200); // slow the jellyfish down
			if (isSuspended) {
				if (barrier != null)
					barrier.await();
				synchronized (this) {
					wait();
				}
			} else
				move();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}

		AquaFrame.panel.repaint();
	}

	public void move() {
		this.x_front += horSpeed * x_dir;
		this.y_front += verSpeed * y_dir;
		if (x_front >= AquaFrame.panel.getWidth())
			x_dir = -1;
		if (y_front >= AquaFrame.panel.getHeight())
			y_dir = -1;
		if (x_front <= 0)
			x_dir = 1;
		if (y_front <= 0)
			y_dir = 1;

		synchronized (this) {
		if ((Math.abs(AquaFrame.panel.getWidth() / 2 - x_front) <= 50) && (Math.abs(AquaFrame.panel.getHeight() / 2 - y_front) <= 50) && (AquaPanel.wormInstance != null)) {
			AquaFrame.panel.wormEatenBy(this);
			Singleton.set();
			AquaPanel.wormInstance = null;
			AquaFrame.btnFood.setEnabled(true);
			AquaFrame.panel.repaint();
			AquaFrame.updateJTable();
		}
		}
	}

	public int getHungerFreq() {
		return -1;
	}
}
