import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class AquaFrame extends JFrame {
	static int totalEatCounter = 0;
	JLayeredPane layeredPane = new JLayeredPane();
	static AquaPanel panel = new AquaPanel();
	Box horizontalBox = Box.createHorizontalBox();
	JScrollPane scrollPane = new JScrollPane();

	String[] tableHeaders = new String[] { "Animal", "Color", "Size", "Hor. Speed", "Ver. Speed", "Eat Counter" };
	static DefaultTableModel tableModel;
	JTable table = new JTable() {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};

	static JButton btnAddAnimal;
	static JButton btnAddPlant;
	static JButton btnFood;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AquaFrame frame = new AquaFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AquaFrame() {
		//
		// AquaFrame properties
		//
		setResizable(false);
		setTitle("HW3");
		setBounds(100, 100, 844, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//
		// AquaPanel properties
		//
		panel.setOpaque(false);
		panel.setLocation(0, 0);
		panel.setSize(830, 541);
		panel.setRequestFocusEnabled(false);
		panel.setLayout(null);

		// HorizontalBox Properties
		horizontalBox.setAlignmentY(Component.CENTER_ALIGNMENT);
		horizontalBox.setBorder(null);
		horizontalBox.setForeground(SystemColor.menuText);
		horizontalBox.setBackground(SystemColor.info);
		horizontalBox.setBounds(0, 512, 829, 29);
		panel.add(horizontalBox);

		//
		// HorizontalBox left glue
		//
		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalBox.add(horizontalGlue);

		//
		// AddAnimal button
		//
		btnAddAnimal = new JButton("Add Animal");
		btnAddAnimal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (AquaPanel.sealife.size() == 5) {
					JOptionPane.showMessageDialog(null, "Aquarium can not contain more than 5 fish or jellyfish!");
				} else {
					AddAnimalDialog addAnimalDialog = new AddAnimalDialog();
					addAnimalDialog.setVisible(true);
					addAnimalDialog.setAlwaysOnTop(true);
					btnAddAnimal.setEnabled(false);
				}
			}
		});
		btnAddAnimal.setFont(new Font("Arial", Font.BOLD, 17));
		horizontalBox.add(btnAddAnimal);

		//
		// AddPlant button
		//
		btnAddPlant = new JButton("Add Plant");
		btnAddPlant.setFont(new Font("Arial", Font.BOLD, 17));
		btnAddPlant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (AquaPanel.plants.size() == 5) {
					JOptionPane.showMessageDialog(null, "Aquarium can not contain more than 5 plants!");
				} else {
					AddPlantDialog addPlantDialog = new AddPlantDialog();
					addPlantDialog.setVisible(true);
					addPlantDialog.setAlwaysOnTop(true);
					btnAddPlant.setEnabled(false);
				}
			}
		});
		horizontalBox.add(btnAddPlant);

		//
		// Sleep button
		//
		JButton btnSleep = new JButton("Sleep");
		btnSleep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.sleepFish();
			}
		});
		btnSleep.setFont(new Font("Arial", Font.BOLD, 17));
		horizontalBox.add(btnSleep);

		//
		// Wake up button
		//
		JButton btnWakeUp = new JButton("Wake up");
		btnWakeUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.wakeFish();
			}
		});
		btnWakeUp.setFont(new Font("Arial", Font.BOLD, 17));
		horizontalBox.add(btnWakeUp);

		//
		// Reset button
		//
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AquaPanel.plants.clear();
				AquaPanel.sealife.clear();
				tableModel.setRowCount(0);
				panel.repaint();
			}
		});
		btnReset.setFont(new Font("Arial", Font.BOLD, 17));
		horizontalBox.add(btnReset);

		//
		// Food button
		//
		btnFood = new JButton("Food");
		btnFood.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.feedFish();
			}
		});
		btnFood.setFont(new Font("Arial", Font.BOLD, 17));
		horizontalBox.add(btnFood);

		//
		// Info button (table with creatures)
		//
		JButton btnInfo = new JButton("Info");
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (scrollPane.isVisible()) {
					scrollPane.setVisible(false);
					initializeTable();
				} else {
					scrollPane.setVisible(true);
				}

			}
		});
		btnInfo.setFont(new Font("Arial", Font.BOLD, 17));
		horizontalBox.add(btnInfo);

		//
		// Exit button
		//
		JButton btnNewButton = new JButton("Exit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 17));
		horizontalBox.add(btnNewButton);

		//
		// HorizontalBox right glue
		//
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		horizontalBox.add(horizontalGlue_1);

		//
		// Scrollpane properties (to hold info table)
		//
		scrollPane.setEnabled(false);
		scrollPane.setLocation(0, 0);
		scrollPane.setSize(830, 500);
		scrollPane.setAutoscrolls(true);
		scrollPane.setFocusable(false);
		scrollPane.setVisible(false);

		//
		// Table constructor
		//
		tableModel = new DefaultTableModel(tableHeaders, 0);
		table.setModel(tableModel);
		scrollPane.setViewportView(table); // add table to scrollpane

		getContentPane().add(layeredPane, BorderLayout.CENTER); // add a layered pane containing all panels

		// layered pane layers:
		// layer 0 - color panel
		// layer 1 - image panel
		// layer 2 - fish panel
		// layer 3 - scrollpane(table)
		layeredPane.add(panel);
		layeredPane.setLayer(panel, 2);
		layeredPane.add(scrollPane);
		layeredPane.setLayer(scrollPane, 3);

		JPanel imagePanel = new JPanel() {
			public void paintComponent(Graphics g) {
				ImageIcon ii = new ImageIcon("image.png");
				Image i = ii.getImage();
				g.drawImage(i, 0, 0, this.getSize().width, this.getSize().height, this);
			}
		};
		imagePanel.setBounds(0, 0, 830, 541);
		layeredPane.add(imagePanel);
		layeredPane.setLayer(imagePanel, 1);
		imagePanel.setVisible(false);

		JPanel colorPanel = new JPanel();
		colorPanel.setBounds(0, 0, 830, 541);
		layeredPane.add(colorPanel);
		layeredPane.setLayer(colorPanel, 0);

		//
		// Menu bar
		//
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		//
		// Menu bar file button
		//
		JMenu mnMain = new JMenu("File");
		menuBar.add(mnMain);

		//
		// Menu bar exit button
		//
		JMenuItem file_Exit = new JMenuItem("Exit");
		file_Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnMain.add(file_Exit);

		//
		// Menu bar background button
		//
		JMenu mnBackground = new JMenu("Background");
		menuBar.add(mnBackground);

		//
		// Menu bar > background > image button
		//
		JMenuItem bg_Image = new JMenuItem("Image");
		bg_Image.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imagePanel.setVisible(true);
				colorPanel.setVisible(false);
			}
		});
		mnBackground.add(bg_Image);

		//
		// Menu bar > background > none button
		//
		JMenuItem bg_None = new JMenuItem("None");
		bg_None.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorPanel.setVisible(true);
				imagePanel.setVisible(false);
				colorPanel.setBackground(null);
			}
		});

		//
		// Menu bar > background > blue button
		//
		JMenuItem bg_Blue = new JMenuItem("Blue");
		bg_Blue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorPanel.setVisible(true);
				imagePanel.setVisible(false);
				colorPanel.setBackground(SystemColor.textHighlight);
			}
		});
		mnBackground.add(bg_Blue);
		mnBackground.add(bg_None);

		//
		// Menu bar help button
		//
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		//
		// Menu bar about button
		//
		JMenuItem help_About = new JMenuItem("About");
		help_About.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Home Work 3\nGUI @ Threads");

			}
		});
		mnHelp.add(help_About);
	}

	/**
	 * Adds fish and jellyfish information to the table.
	 */
	public static void initializeTable() {
		AquaFrame.tableModel.setRowCount(0);
		for (Swimmable animal : AquaPanel.sealife) {
			Object[] objs = { animal.getAnimalName(), animal.getColor(), animal.getSize(), animal.getHorSpeed(),
					animal.getVerSpeed(), animal.getEatCount() };
			AquaFrame.tableModel.addRow(objs);
		}
		Object[] total = { "Total", "", "", "", "", totalEatCounter };
		AquaFrame.tableModel.addRow(total);
	}
}
