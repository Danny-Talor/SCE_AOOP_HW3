import java.util.HashMap;


public class CareTaker { 
	public HashMap<Integer,Memento> statesList = new HashMap<>(); 
	
	public void addMemento(int id, Memento m) { 
		statesList.put(id,m);
	} 
	
	public Memento getMemento(int index) { 
		
		return statesList.get(index);
	} 
}