import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class AddAnimalDialog extends JDialog {
	private Random random = new Random();

	private final JPanel contentPanel = new JPanel();
	private JComboBox animalType_cb;
	private JTextField animalVerSpd_txtField;
	private JTextField animalHorSpd_txtField;
	private JTextField animalSize_txtField;
	private JComboBox animalColor_cb;
	private JLabel hunglbl;
	private JTextField textField;

	/**
	 * Create the dialog.
	 */
	public AddAnimalDialog() {
		
		JFrame frame = new JFrame(); //Send to JOptionPane constructor to
		frame.setAlwaysOnTop(true);  //make sure error dialogs are always on top
		
		setTitle("Add animal");
		setResizable(false);
		setBounds(100, 100, 373, 272);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		animalType_cb = new JComboBox();
		animalType_cb.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(animalType_cb.getSelectedIndex() == 1) {
					hunglbl.setVisible(false);
					textField.setVisible(false);
					textField.setText("-1");
				}
				else {
					hunglbl.setVisible(true);
					textField.setVisible(true);
					textField.setText("");
				}
			}
		});
		animalType_cb.setFont(new Font("Tahoma", Font.PLAIN, 18));
		animalType_cb.setModel(new DefaultComboBoxModel(new String[] { "Fish", "Jellyfish" }));
		animalType_cb.setBounds(110, 12, 125, 21);
		contentPanel.add(animalType_cb);

		animalVerSpd_txtField = new JTextField();
		animalVerSpd_txtField.setToolTipText("1-10 px/ms");
		animalVerSpd_txtField.setBounds(122, 111, 46, 19);
		contentPanel.add(animalVerSpd_txtField);
		animalVerSpd_txtField.setColumns(10);

		JLabel lbl4 = new JLabel("Vertical speed:           pixels per millisecond");
		lbl4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl4.setBounds(10, 108, 330, 21);
		contentPanel.add(lbl4);

		animalHorSpd_txtField = new JTextField();
		animalHorSpd_txtField.setToolTipText("1-10 px/ms");
		animalHorSpd_txtField.setBounds(135, 79, 52, 19);
		contentPanel.add(animalHorSpd_txtField);
		animalHorSpd_txtField.setColumns(10);

		JLabel lbl3 = new JLabel("Horizotal speed:             pixels per millisecond");
		lbl3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl3.setBounds(10, 80, 340, 21);
		contentPanel.add(lbl3);

		animalSize_txtField = new JTextField();
		animalSize_txtField.setToolTipText("20-320 pixels");
		animalSize_txtField.setBounds(61, 50, 96, 19);
		contentPanel.add(animalSize_txtField);
		animalSize_txtField.setColumns(10);

		JLabel lbl2 = new JLabel("Size:                      pixels");
		lbl2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl2.setBounds(20, 48, 200, 18);
		contentPanel.add(lbl2);

		animalColor_cb = new JComboBox();
		animalColor_cb.setFont(new Font("Tahoma", Font.PLAIN, 16));
		animalColor_cb.setModel(new DefaultComboBoxModel(
				new String[] { "Red", "Blue", "Green", "Cyan", "Orange", "Yellow", "Magenta", "Pink", "Black" }));
		animalColor_cb.setBounds(51, 141, 96, 21);
		contentPanel.add(animalColor_cb);

		JLabel lbl1 = new JLabel("Animal type:");
		lbl1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl1.setBounds(10, 10, 110, 21);
		contentPanel.add(lbl1);

		JLabel lbl5 = new JLabel("Color:");
		lbl5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl5.setBounds(10, 140, 52, 21);
		contentPanel.add(lbl5);
		
		hunglbl = new JLabel("Hunger frequency:            moves");
		hunglbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		hunglbl.setBounds(10, 172, 330, 21);
		contentPanel.add(hunglbl);
		
		textField = new JTextField();
		textField.setToolTipText("8 - 24 moves");
		textField.setColumns(10);
		textField.setBounds(150, 175, 46, 19);
		contentPanel.add(textField);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							int size = Integer.parseInt(animalSize_txtField.getText());
							int verSpd = Integer.parseInt(animalVerSpd_txtField.getText());
							int horSpd = Integer.parseInt(animalHorSpd_txtField.getText());
							int x_pos = random.nextInt(AquaFrame.panel.getWidth());
							int y_pos = random.nextInt(AquaFrame.panel.getHeight());
							int hungerFreq = Integer.parseInt(textField.getText());	
							
							if (size < 20 || size > 320)
								throw new IllegalStateException();
							if (verSpd < 1 || verSpd > 10 || horSpd < 1 || horSpd > 10)
								throw new IllegalArgumentException();
							if((hungerFreq < 8 || hungerFreq > 24 ) && animalType_cb.getSelectedIndex() == 0)
								throw new ArithmeticException();

							Color c = colorByIndex(animalColor_cb.getSelectedIndex());
							
							AbstractSeaFactory factory = new AnimalFactory(size, x_pos, y_pos, horSpd, verSpd, c, hungerFreq);
							if (animalType_cb.getSelectedIndex() == 0) {
								SeaCreature obj = factory.produceSeaCreature("Fish");
								AquaPanel.sealife.add((Swimmable)obj);
								((Fish)obj).addPropertyChangeListener(AquaFrame.panel);
							} else if(animalType_cb.getSelectedIndex() == 1){
								SeaCreature obj = factory.produceSeaCreature("Jellyfish");
								AquaPanel.sealife.add((Swimmable)obj);
							}
							AquaFrame.updateJTable();
							AquaFrame.panel.repaint();
							AquaFrame.enableAllButtons();
							dispose();
						} catch (NumberFormatException ex) {
							JOptionPane.showMessageDialog(frame, "Fields must contain numbers!");
						} catch (IllegalStateException ex) {
							JOptionPane.showMessageDialog(frame, "Size must be between 20 to 320!");
						} catch (IllegalArgumentException ex) {
							JOptionPane.showMessageDialog(frame, "Speed must be between 1 to 10!");
						}catch (ArithmeticException ex) {
							JOptionPane.showMessageDialog(frame, "Frequency must be between 8 to 24!");
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						AquaFrame.enableAllButtons();
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public Color colorByIndex(int i) {
		Color c;
		switch (i) {
		case 0:
			c = new Color(255, 0, 0); // Red
			break;
		case 1:
			c = new Color(0, 0, 255); // Blue
			break;
		case 2:
			c = new Color(0, 255, 0); // Green
			break;
		case 3:
			c = new Color(0, 255, 255); // Cyan
			break;
		case 4:
			c = new Color(255, 165, 0); // Orange
			break;
		case 5:
			c = new Color(255, 255, 0); // Yellow
			break;
		case 6:
			c = new Color(255, 0, 255); // Magenta
			break;
		case 7:
			c = new Color(255, 105, 180); // Pink
			break;
		case 8:
			c = new Color(254, 254, 254); // Black
			break;
		default:
			c = new Color(255, 255, 255); // Black
			break;
		}
		return c;
	}
}
