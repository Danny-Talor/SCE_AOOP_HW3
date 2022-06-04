import java.awt.Color;

public class PlantFactory implements AbstractSeaFactory {

	int size, x_pos, y_pos;
	Color col;

	public PlantFactory(int size, int x_pos, int y_pos) {
		this.size = size;
		this.x_pos = x_pos;
		this.y_pos = y_pos;
		this.col = Color.GREEN;
	}

	public SeaCreature produceSeaCreature(String immobile) {
		if (immobile.equals("Laminaria"))
			return new Laminaria(size, x_pos, y_pos);
		else if (immobile.equals("Zostera"))
			return new Zostera(size, x_pos, y_pos);

		return null;
	}
}
