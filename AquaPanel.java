import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import java.util.HashSet;
import java.util.concurrent.CyclicBarrier;

@SuppressWarnings("serial")
public class AquaPanel extends JPanel implements PropertyChangeListener{

	static HashSet<Swimmable> sealife = new HashSet<>();
	static HashSet<Immobile> plants = new HashSet<>();

	CyclicBarrier barrier;
	public static Singleton wormInstance = null;

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
				((Fish) animal).drawCreature(g);
				new Thread(animal).start();
			} else {
				((Jellyfish) animal).drawCreature(g);
				new Thread(animal).start();
			}
		}

		for (Immobile plant : plants) {
			if (plant instanceof Laminaria) {
				((Laminaria) plant).drawCreature(g);
			} else {
				((Zostera) plant).drawCreature(g);
			}
		}

		if (wormInstance != null) {
			drawWorm(g);
		}
	}

	public void sleepFish() {
		for (Swimmable animal : sealife) {
			if (animal instanceof Fish) {
				((Fish) animal).setSuspend();
			} else {
				((Jellyfish) animal).setSuspend();
			}

		}
	}

	public void wakeFish() {
		for (Swimmable animal : sealife) {
			if (animal instanceof Fish) {
				((Fish) animal).setResume();
			} else {
				((Jellyfish) animal).setResume();
			}

		}
	}

	public void drawWorm(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.PINK);
		g2.setStroke(new BasicStroke(3));
		g2.drawArc(this.getWidth() / 2, this.getHeight() / 2 - 5, 10, 10, 30, 210);
		g2.drawArc(this.getWidth() / 2, this.getHeight() / 2 + 5, 10, 10, 180, 270);
		g2.setStroke(new BasicStroke(1));
	}

	public void drawWorm() {
		if (AquaPanel.sealife.size() > 0) {
			wormInstance = Singleton.getInstance();
			repaint();
		}
	}

	public void wormEatenBy(Swimmable obj) {
		obj.eatInc();
		AquaFrame.totalEatCounter++;
	}

	public void createBarrier() {

		barrier = new CyclicBarrier(sealife.size());
		for (Swimmable animal : sealife) {
			animal.setBarrier(barrier);
		}

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		sleepFish();
		JFrame frame = new JFrame(); //Send to JOptionPane constructor to
		frame.setAlwaysOnTop(true);  //make sure error dialogs are always on top
		int input = JOptionPane.showOptionDialog(frame, "Fish hungry!\nFeed now?", "Attention!", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
		if(input == JOptionPane.OK_OPTION) {
			wakeFish();
			drawWorm();
			if(AquaPanel.sealife.size()>1) createBarrier();
		}
		else {
			wakeFish();
		}
	}

}
