import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.CyclicBarrier;

public abstract class Swimmable extends Thread implements SeaCreature, MarineAnimal, Cloneable{
	protected int horSpeed;
	protected int verSpeed;

	public Swimmable() {
		horSpeed = 0;
		verSpeed = 0;
	}

	public Swimmable(int hor, int ver) {
		horSpeed = hor;
		verSpeed = ver;
	}

	public int getHorSpeed() {
		return Math.abs(horSpeed);
	}

	public int getVerSpeed() {
		return Math.abs(verSpeed);
	}

	public void setHorSpeed(int hor) {
		horSpeed = hor;
	}

	public void setVerSpeed(int ver) {
		verSpeed = ver;
	}

	abstract public String getAnimalName();

	abstract public void drawCreature(Graphics g);

	abstract public void setSuspend();

	abstract public void setResume();

	abstract public void setBarrier(CyclicBarrier b);

	abstract public int getSize();

	abstract public void eatInc();

	abstract public int getEatCount();

	abstract public String getColorName();
	
	abstract public Color getColor();
	
	abstract public void PaintFish(Color c);
	
	abstract public int getHungerFreq();
	
	abstract public Swimmable clone();
	
	abstract public int getXpos();
	
	abstract public int getYpos();
	
	abstract public void setState(int size,int x_front,int y_front,int horSpeed,int verSpeed, Color col);
}
