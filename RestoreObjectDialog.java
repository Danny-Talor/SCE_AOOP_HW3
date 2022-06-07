import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class RestoreObjectDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	JScrollPane scrollPane = new JScrollPane();
	
	String[] tableHeaders = new String[] { "Animal", "Color", "Size", "Hor. Speed", "Ver. Speed", "Save Time" };
	static DefaultTableModel tableModel;
	JTable table = new JTable() {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	ArrayList<Swimmable> sealife = new ArrayList<>(AquaPanel.sealife);
	/**
	 * Create the dialog.
	 */
	public RestoreObjectDialog() {
		JFrame frame = new JFrame();
		frame.setAlwaysOnTop(true);
		setTitle("Restore animal state");
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

		JLabel lblNewLabel = new JLabel("Select animal state:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setBounds(0, 0, 174, 21);
		contentPanel.add(lblNewLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Restore state");
				okButton.setFont(new Font("Tahoma", Font.BOLD, 12));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Memento m = AquaFrame.caretaker.getMemento(table.getSelectedRow());
						Swimmable obj = sealife.get(table.getSelectedRow());
						obj.setState(m.getSize(), m.getX_front(), m.getY_front(), m.getHorSpeed(), m.getVerSpeed(), m.getColor());
						AquaPanel.sealife.add(obj);
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
		for (Memento state : AquaFrame.caretaker.statesList.values()) {
			Object[] objs = { state.getState().getAnimalName(), state.getColorRGB(), state.getSize(), state.getHorSpeed(),
					state.getVerSpeed(), state.getSaveTime() };
			tableModel.addRow(objs);
		}
	}
	
}
