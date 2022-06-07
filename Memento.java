import java.awt.Color;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Memento {
	private Swimmable state;
	private int size, horSpeed, verSpeed, x_front,y_front;
	private Color col;
	private String saveTime = "Never";
			
	public Memento(Swimmable state){ 
		this.state = state;
		this.horSpeed = state.horSpeed;
		this.verSpeed = state.verSpeed;
		this.size = state.getSize();
		this.col = state.getColor();
		this.x_front = state.getXpos();
		this.y_front = state.getYpos();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime localTime = LocalTime.now();
		saveTime = dtf.format(localTime);    
	} 
	
	public Swimmable getState() {
		return state; 
	} 
	
	public String getSaveTime() {
		return saveTime;
	}

	public int getSize() {
		return size;
	}

	public int getHorSpeed() {
		return horSpeed;
	}

	public int getVerSpeed() {
		return verSpeed;
	}

	public int getX_front() {
		return x_front;
	}

	public int getY_front() {
		return y_front;
	}
	
	public Color getColor() {
		return col;
	}
	
	public String getColorRGB() {
		return "" + col.getRed() +"," + col.getGreen() + "," + col.getBlue();
	}
}