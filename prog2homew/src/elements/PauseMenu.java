package elements;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import enumtype.Direction;
import enumtype.STATE;
import gameplay.Table;
import gameplay.Table.MyKeyAdapter;

public class PauseMenu {

	public Table mytable;
	private JFrame f;
	static int I=0;

	public PauseMenu(Table table) {
		mytable = table;
		// f = new JFrame();
	}

	public void showPause() {
		System.out.println(I++);
		
		if (f == null) {
			f = new JFrame();

		} else {
			
		}
		f.setUndecorated(true); // Remove title bar
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.pack();
		f.setVisible(true);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		f.setLocation(screenSize.width / 2 - (150), screenSize.height / 2 - (150));
		final JLabel label = new JLabel();

		JButton b = new JButton("Select");
		f.getRootPane().setDefaultButton(b);
		f.getRootPane().setBackground(Color.BLACK);
		b.setBounds(100, 250, 100, 40);
		final DefaultListModel<String> l1 = new DefaultListModel<>();
		l1.addElement("Resume Game");
		l1.addElement("Restart");
		l1.addElement("Quit");
		Icon imgIcon = new ImageIcon(this.getClass().getResource("/assets/animate_snake.gif"));
		JLabel gifplace = new JLabel(imgIcon);

		gifplace.setBounds(50, 200, 200, 50);

		f.add(gifplace);
		final JList<String> list1 = new JList<>(l1);
		list1.setBounds(50, 50, 200, 150);
		list1.setFixedCellHeight(50);

		DefaultListCellRenderer renderer = (DefaultListCellRenderer) list1.getCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);

		f.setBackground(Color.BLACK);
		f.add(list1);
		f.add(b);
		f.add(label);
		f.setSize(300, 300);

		f.setVisible(true);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				f.setVisible(false); // you can't see me!
				f.dispose();
				f=null;
				switch (list1.getSelectedIndex()) {
				case 0: // resume
					mytable.start();
					break;
				case 1: // restart
					mytable.restart();
					break;
				case 2: // quit
					System.exit(0);
					break;
				}
			}
		});

	}

	public static void centreWindow(Window frame) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);
	}
}