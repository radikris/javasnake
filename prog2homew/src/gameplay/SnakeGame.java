package gameplay;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import jaco.mp3.player.MP3Player;

@SuppressWarnings("deprecation")
public class SnakeGame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 177836389200929559L; // avoid compile warning
	boolean pause = false; // the button to switch from pause to not pause
	private JButton pauseandResume = new JButton("Pause");
	public Thread musicThread = new Thread();

	static MP3Player mp3player = new MP3Player(
			new File("C:\\Java projects\\prog2homew\\src\\assets/background_music.mp3"));

	public SnakeGame() {
		initUI();
	}

	private void initUI() {
		mp3player.setRepeat(true);
		mp3player.setShuffle(true);
		mp3player.play();

		add(new Table(676, 676));
		pauseandResume.setForeground(Color.RED);
		pauseandResume.addActionListener(this);

		setResizable(false);

		pack();

		setTitle("Snakeyy");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			JFrame ex = new SnakeGame();
			ex.setVisible(true);
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		pause = !pause;
		// update button text accordingly
		System.out.println("This will be printed");
		if (pause)
			pauseandResume.setText("Resume");
		else
			pauseandResume.setText("Pause");

	}
}
