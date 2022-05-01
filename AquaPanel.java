import java.awt.*;
import javax.swing.*;
import java.util.HashSet;
import java.util.concurrent.CyclicBarrier;

@SuppressWarnings("serial")
public class AquaPanel extends JPanel {

	static HashSet<Swimmable> sealife = new HashSet<>();
	CyclicBarrier barrier;
	boolean hasWorm = false;

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
		if(hasWorm == true) {
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

	public void setWorm() {
		if (hasWorm == true)
			hasWorm = false;
		else
			hasWorm = true;
	}

	public void drawWorm(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.PINK);
		g2.setStroke(new BasicStroke(3));
		g2.drawArc(this.getWidth() / 2, this.getHeight() / 2 - 5, 10, 10, 30, 210);
		g2.drawArc(this.getWidth() / 2, this.getHeight() / 2 + 5, 10, 10, 180, 270);
		g2.setStroke(new BasicStroke(1));
	}

	public void wormEatenBy(Swimmable obj) {
		obj.eatInc();
		AquaFrame.totalEatCounter++;
	}
	
	public void createBarrier() {
		if(sealife.size()>0) {
			barrier = new CyclicBarrier(sealife.size());
			for(Swimmable animal:sealife) {
				animal.setBarrier(barrier);
			}
		}
		
	}

}
