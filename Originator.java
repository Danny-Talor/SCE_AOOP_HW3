public class Originator { 
	private Swimmable state; 
	public void setState(Swimmable state) { this.state = state; } 
	public Swimmable getState() { return state; } 
	public Memento createMemento() { 
		return new Memento(state); 
	} 
	public void setMemento(Memento memento) { 
		state = memento.getState(); 
	} 
}