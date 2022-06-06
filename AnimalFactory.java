import java.awt.Color;

public class AnimalFactory implements AbstractSeaFactory {
	private int size, x_pos, y_pos, horSpeed, verSpeed, hungerFreq;
	private Color col;

	public AnimalFactory(int size, int x_pos, int y_pos, int horSpeed, int verSpeed, Color col, int hungerFreq) {
		this.size = size;
		this.x_pos=x_pos;
		this.y_pos = y_pos;
		this.horSpeed = horSpeed;
		this.verSpeed = verSpeed;
		this.col = col;
		this.hungerFreq = hungerFreq;
	}
	
	public AnimalFactory(int size, int x_pos, int y_pos, int horSpeed, int verSpeed, Color col) {
		this.size = size;
		this.x_pos=x_pos;
		this.y_pos = y_pos;
		this.horSpeed = horSpeed;
		this.verSpeed = verSpeed;
		this.col = col;
	}

	public SeaCreature produceSeaCreature(String swimmable) {
		if (swimmable.equals("Fish"))
			return new Fish(size, x_pos, y_pos, horSpeed, verSpeed, col, hungerFreq);
		else if (swimmable.equals("Jellyfish"))
			return new Jellyfish(size, x_pos, y_pos, horSpeed, verSpeed, col);

		return null;
	}

}
