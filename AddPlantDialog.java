import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class AddPlantDialog extends JDialog {

	private Random random = new Random();

	private final JPanel contentPanel = new JPanel();
	private JComboBox plantType_cb;
	private JTextField plantSize_txtField;

	/**
	 * Create the dialog.
	 */
	public AddPlantDialog() {
		setTitle("Add plant");
		setResizable(false);
		setBounds(100, 100, 365, 239);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		plantType_cb = new JComboBox();
		plantType_cb.setFont(new Font("Tahoma", Font.PLAIN, 18));
		plantType_cb.setModel(new DefaultComboBoxModel(new String[] { "Laminaria", "Zostera" }));
		plantType_cb.setBounds(151, 29, 125, 21);
		contentPanel.add(plantType_cb);

		plantSize_txtField = new JTextField();
		plantSize_txtField.setToolTipText("20-320 pixels");
		plantSize_txtField.setBounds(117, 77, 96, 19);
		contentPanel.add(plantSize_txtField);
		plantSize_txtField.setColumns(10);

		JLabel lbl2 = new JLabel("Size:                     pixels");
		lbl2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl2.setBounds(77, 78, 200, 18);
		contentPanel.add(lbl2);

		JLabel lbl1 = new JLabel("Plant type:");
		lbl1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl1.setBounds(51, 27, 110, 21);
		contentPanel.add(lbl1);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							int size = Integer.parseInt(plantSize_txtField.getText());
							int x_pos = random.nextInt(AquaFrame.panel.getWidth());
							int y_pos = AquaFrame.panel.getHeight() - random.nextInt(30, 100);

							if (size < 100 || size > 400)
								throw new IllegalStateException();
							
							AbstractSeaFactory factory = new PlantFactory(size, x_pos, y_pos);
							if (plantType_cb.getSelectedIndex() == 0) {
								SeaCreature obj = factory.produceSeaCreature("Laminaria");
								AquaPanel.plants.add((Immobile)obj);
							} else{
								SeaCreature obj = factory.produceSeaCreature("Zostera");
								AquaPanel.plants.add((Immobile)obj);
							}
							AquaFrame.initializeTable();
							AquaFrame.panel.repaint();
							AquaFrame.btnAddPlant.setEnabled(true);
							dispose();
						} catch (NumberFormatException ex) {
							JOptionPane.showMessageDialog(null, "Field must contain numbers!");
						} catch (IllegalStateException ex) {
							JOptionPane.showMessageDialog(null, "Size must be between 20 to 320!");
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
						AquaFrame.btnAddAnimal.setEnabled(true);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
