import java.util.ArrayList;
import java.util.HashSet;


public class CareTaker { 
	private HashSet<Memento> statesList = new HashSet<>(); 
	public void addMemento(Memento m) { 
		statesList.add(m); 
	} 
	
	public Memento getMemento(int index) { 
		ArrayList<Memento> mList = new ArrayList<>(statesList);
		return mList.get(index); 
	} 
}