import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.util.Random;

@SuppressWarnings("serial")
public class DupeAnimalDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	JScrollPane scrollPane = new JScrollPane();

	String[] tableHeaders = new String[] { "Animal", "Color", "Size", "Hor. Speed", "Ver. Speed", "Hunger frequency" };
	static DefaultTableModel tableModel;
	JTable table = new JTable() {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	private static Random random = new Random();

	/**
	 * Create the dialog.
	 */
	public DupeAnimalDialog() {
		JFrame frame = new JFrame();
		frame.setAlwaysOnTop(true);
		setTitle("Duplicate Animal");
		setBounds(100, 100, 731, 329);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			scrollPane.setBounds(0, 29, 717, 232);
			contentPanel.add(scrollPane);
			tableModel = new DefaultTableModel(tableHeaders, 0);
			table.setModel(tableModel);
			scrollPane.setViewportView(table); // add table to scrollpane
			{
				JLabel lblSelectAnimal = new JLabel("Select animal:");
				lblSelectAnimal.setFont(new Font("Tahoma", Font.BOLD, 17));
				lblSelectAnimal.setBounds(0, 0, 149, 21);
				contentPanel.add(lblSelectAnimal);
			}
			initializeTable();
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Duplicate without editing");
				okButton.setFont(new Font("Tahoma", Font.BOLD, 12));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						try {
							String animalType = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
							AbstractSeaFactory factory = animalInfoAt(table, table.getSelectedRow());
							SeaCreature obj = factory.produceSeaCreature(animalType);
							AquaPanel.sealife.add((Swimmable) obj);
							AquaFrame.updateJTable();
							initializeTable();
							AquaFrame.enableAllButtons();
							dispose();
							JOptionPane.showMessageDialog(frame, "Duplicated successfuly!");
						} catch (IndexOutOfBoundsException ex) {
							JOptionPane.showMessageDialog(frame, "No animal selected!");
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(frame, ex.getMessage());
						}
					}
				});
				{
					JButton editButton = new JButton("Edit and Duplicate");
					editButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							dispose();
							String animalType = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
							AbstractSeaFactory factory = animalInfoAt(table, table.getSelectedRow());
							SeaCreature obj = factory.produceSeaCreature(animalType);
							EditDupeAnimalDialog editDupeAnimalDialog = new EditDupeAnimalDialog((Swimmable)obj);
							editDupeAnimalDialog.setVisible(true);
							editDupeAnimalDialog.setAlwaysOnTop(true);
						}
					});
					editButton.setFont(new Font("Tahoma", Font.BOLD, 12));
					editButton.setActionCommand("OK");
					buttonPane.add(editButton);
				}
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setFont(new Font("Tahoma", Font.BOLD, 12));
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

	public static AnimalFactory animalInfoAt(JTable table, int index) {
		String animalType = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
		Color c = colorByName(table.getModel().getValueAt(table.getSelectedRow(), 1).toString());
		int size = Integer.parseInt(table.getModel().getValueAt(index, 2).toString());
		int horSpd = Integer.parseInt(table.getModel().getValueAt(index, 3).toString());
		int verSpd = Integer.parseInt(table.getModel().getValueAt(index, 4).toString());
		int x_pos = random.nextInt(AquaFrame.panel.getWidth());
		int y_pos = random.nextInt(AquaFrame.panel.getHeight());

		if(animalType.equals("Fish")) {
			int hungerFreq = Integer.parseInt(table.getModel().getValueAt(index, 5).toString());
			return new AnimalFactory(size, x_pos, y_pos, horSpd, verSpd, c, hungerFreq); // Fish animal factory
		}
		else
			return new AnimalFactory(size, x_pos, y_pos, horSpd, verSpd, c); //Jellyfish animal factory
	}

	public static void initializeTable() {
		tableModel.setRowCount(0);
		for (Swimmable animal : AquaPanel.sealife) {
			Object[] objs = { animal.getAnimalName(), animal.getColorName(), animal.getSize(), animal.getHorSpeed(),
					animal.getVerSpeed(), (animal.getAnimalName().equals("Fish") ? animal.getHungerFreq() : "") };
			tableModel.addRow(objs);
		}
	}

	public static Color colorByName(String name) {
		Color c;

		switch (name) {
		case "Red":
			c = new Color(255, 0, 0);
			break;
		case "Blue":
			c = new Color(0, 0, 255);
			break;
		case "Green":
			c = new Color(0, 255, 0);
			break;
		case "Cyan":
			c = new Color(0, 255, 255);
			break;
		case "Orange":
			c = new Color(255, 165, 0);
			break;
		case "Yellow":
			c = new Color(255, 255, 0);
			break;
		case "Magenta":
			c = new Color(255, 0, 255);
			break;
		case "Pink":
			c = new Color(255, 105, 180);
			break;
		case "Black":
			c = new Color(254, 254, 254);
			break;
		default:
			String[] rgb = name.split(",");
			int r = Integer.parseInt(rgb[0]);
			int g = Integer.parseInt(rgb[1]);
			int b = Integer.parseInt(rgb[2]);
			c = new Color(r, g, b);
			break;
		}
		return c;
	}

}
