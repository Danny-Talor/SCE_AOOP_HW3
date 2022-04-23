import java.awt.*;
import javax.swing.*;
import java.util.HashSet;

@SuppressWarnings("serial")
public class AquaPanel extends JPanel {

	public static HashSet<Swimmable> sealife = new HashSet<>();

	/**
	 * Create the panel.
	 */
	public AquaPanel() {
		setOpaque(false);
		setLayout(new GridLayout(1, 0, 0, 0));

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Swimmable animal : sealife) {
			if (animal instanceof Fish) {
				((Fish) animal).drawAnimal(g);
				new Thread(animal).start();
			} else {
				((Jellyfish) animal).drawAnimal(g);
				new Thread(animal).start();
			}
		}
	}

	public void sleepFish() {
		for (Swimmable animal : sealife) {
			if (animal instanceof Fish) {
				((Fish)animal).setSuspend();
			} else {
				((Jellyfish)animal).setSuspend();
			}
			
		}
	}
	
	public void wakeFish() {
		for (Swimmable animal : sealife) {
			if (animal instanceof Fish) {
				((Fish)animal).setResume();
			} else {
				((Jellyfish)animal).setResume();
			}
			
		}
	}

}
