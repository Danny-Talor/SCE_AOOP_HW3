import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class AquaFrame extends JFrame {
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

	JButton btnAddAnimal;

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
		//AquaFrame  
		//
		setResizable(false);
		setTitle("HW2");
		setBounds(100, 100, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//
		panel.setOpaque(false);
		panel.setLocation(0, 0);
		panel.setSize(786, 541);
		panel.setRequestFocusEnabled(false);
		panel.setLayout(null);
		
		horizontalBox.setAlignmentY(Component.CENTER_ALIGNMENT);
		horizontalBox.setBorder(null);
		horizontalBox.setForeground(SystemColor.menuText);
		horizontalBox.setBackground(SystemColor.info);

		horizontalBox.setBounds(0, 497, 786, 44);
		panel.add(horizontalBox);

		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalBox.add(horizontalGlue);

		btnAddAnimal = new JButton("Add Animal");
		btnAddAnimal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddAnimalDialog addAnimalDialog = new AddAnimalDialog();
				addAnimalDialog.setVisible(true);
				addAnimalDialog.setAlwaysOnTop(true);
				addAnimalDialog.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent windowEvent) {

					}
				});
			}
		});
		btnAddAnimal.setFont(new Font("Arial", Font.BOLD, 17));
		horizontalBox.add(btnAddAnimal);

		JButton btnSleep = new JButton("Sleep");
		btnSleep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.sleepFish();
			}
		});
		btnSleep.setFont(new Font("Arial", Font.BOLD, 17));
		horizontalBox.add(btnSleep);

		JButton btnWakeUp = new JButton("Wake up");
		btnWakeUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.wakeFish();
			}
		});
		btnWakeUp.setFont(new Font("Arial", Font.BOLD, 17));
		horizontalBox.add(btnWakeUp);

		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AquaPanel.sealife.clear();
				tableModel.setRowCount(0);
			}
		});
		btnReset.setFont(new Font("Arial", Font.BOLD, 17));
		horizontalBox.add(btnReset);

		JButton btnFood = new JButton("Food");
		btnFood.setFont(new Font("Arial", Font.BOLD, 17));
		horizontalBox.add(btnFood);

		JButton btnInfo = new JButton("Info");
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (scrollPane.isVisible()) {
					scrollPane.setVisible(false);
				} else {
					scrollPane.setVisible(true);
				}

			}
		});

		btnInfo.setFont(new Font("Arial", Font.BOLD, 17));
		horizontalBox.add(btnInfo);

		Component horizontalGlue_1 = Box.createHorizontalGlue();
		horizontalBox.add(horizontalGlue_1);
		
		scrollPane.setEnabled(false);
		scrollPane.setLocation(0, 0);
		scrollPane.setSize(786, 500);
		scrollPane.setAutoscrolls(true);
		scrollPane.setFocusable(false);
		scrollPane.setVisible(false);
		
		tableModel = new DefaultTableModel(tableHeaders, 0);
		table.setModel(tableModel);

		scrollPane.setViewportView(table);

		getContentPane().add(layeredPane, BorderLayout.CENTER);

		
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
		imagePanel.setBounds(0, 0, 786, 541);
		layeredPane.add(imagePanel);
		layeredPane.setLayer(imagePanel, 1);
		imagePanel.setVisible(false);
		
		JPanel colorPanel = new JPanel();
		colorPanel.setBounds(0, 0, 786, 541);
		layeredPane.add(colorPanel);
		layeredPane.setLayer(colorPanel, 0);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnMain = new JMenu("File");
		menuBar.add(mnMain);

		JMenuItem file_Exit = new JMenuItem("Exit");
		file_Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnMain.add(file_Exit);

		JMenu mnBackground = new JMenu("Background");
		menuBar.add(mnBackground);

		JMenuItem bg_Image = new JMenuItem("Image");
		bg_Image.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imagePanel.setVisible(true);
				colorPanel.setVisible(false);
			}
		});
		mnBackground.add(bg_Image);

		JMenuItem bg_None = new JMenuItem("None");
		bg_None.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorPanel.setVisible(true);
				imagePanel.setVisible(false);
				colorPanel.setBackground(null);
			}
		});

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

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem help_About = new JMenuItem("About");
		help_About.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Home Work 2\nGUI @ Threads");

			}
		});
		mnHelp.add(help_About);
	}
}
