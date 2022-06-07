public class Memento {
	private Swimmable state;
	private string saveTime = "Never";
			
	public Memento(Swimmable state){ 
		this.state = state;
	} 
	
	public Swimmable getState() { return state; } 
}