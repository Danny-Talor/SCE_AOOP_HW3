import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class SaveObjectDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	JScrollPane scrollPane = new JScrollPane();
	
	String[] tableHeaders = new String[] { "Animal", "Color", "Size", "Hor. Speed", "Ver. Speed", "Date saved" };
	static DefaultTableModel tableModel;
	JTable table = new JTable() {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	ArrayList<Swimmable> sealife = new ArrayList<>(AquaPanel.sealife);

    //System.out.println(formatter.format(date));  
	/**
	 * Create the dialog.
	 */
	public SaveObjectDialog() {
		JFrame frame = new JFrame();
		frame.setAlwaysOnTop(true);
		setTitle("Decorate (recolor) animal");
		setBounds(100, 100, 731, 329);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			scrollPane.setBounds(0, 27, 717, 232);
			contentPanel.add(scrollPane);
			tableModel = new DefaultTableModel(tableHeaders, 0);
			table.setModel(tableModel);
			scrollPane.setViewportView(table); // add table to scrollpane
			updateJTable();
		}

		JLabel lblNewLabel = new JLabel("Select animal:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setBounds(0, 0, 149, 21);
		contentPanel.add(lblNewLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Change color");
				okButton.setFont(new Font("Tahoma", Font.BOLD, 12));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Originator originator = new Originator();
						Swimmable obj = sealife.get(table.getSelectedRow());
						originator.setState(obj); 
						Memento memento = originator.createMemento(); 
						CareTaker caretaker = new CareTaker();   		  	
					    caretaker.addMemento(memento);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Close");
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
	
	public static void updateJTable() {
		tableModel.setRowCount(0);
		for (Swimmable animal : AquaPanel.sealife) {
			Object[] objs = { animal.getAnimalName(), animal.getColorName(), animal.getSize(), animal.getHorSpeed(),
					animal.getVerSpeed(), saveDate };
			tableModel.addRow(objs);
		}
	}
	
}
