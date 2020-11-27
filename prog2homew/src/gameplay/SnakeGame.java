package gameplay;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.InputStream;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import elements.MyMenuBar;
import jaco.mp3.player.MP3Player;

@SuppressWarnings("deprecation")
public class SnakeGame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 177836389200929559L; // avoid compile warning
	private JButton pauseandResume = new JButton("Pause");

	static MP3Player mp3player = new MP3Player(new File("src/assets/background_music.mp3"));

	public SnakeGame() {
		initUI();
	}

	public static Table mytable;

	private void initUI() {
		mp3player.setRepeat(true);
		mp3player.setShuffle(true);
		mp3player.play();
		mytable = new Table(676 + 23, 676);
		add(mytable);

		setResizable(false);

		pack();

		setTitle("Snakeyy");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			JFrame ex = new SnakeGame();

			MyMenuBar menuBarom = new MyMenuBar(mp3player, mytable);

			ex.setJMenuBar(menuBarom);

			ex.setVisible(true);
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
