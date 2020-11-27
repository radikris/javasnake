package gameplay;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import elements.Coordinate;
import enumtype.STATE;

public class BackGround extends Canvas {

	private STATE State;
	private int width, height;
	private BufferedImage menubackground = null;
	//private BufferedImage gamebackground = null;
	private Table table;

	private Image gif;
	
	BackGround(STATE cstate, int colnum, int rownum, Table table) throws Exception {
		this.State = cstate;
		this.width = colnum;
		this.height = rownum;
		this.table = table;
		try {
			URL url = this.getClass().getResource("/assets/animate_snake.gif");

			menubackground = ImageIO.read(getClass().getResourceAsStream("/assets/background.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void render(Graphics g) throws Exception {

		if (table.State == STATE.MENU) {

			g.drawImage(menubackground, 0, 0, width, height, null);
			if (gif != null) {
				System.out.println("nemnullagif");
				g.drawImage(gif, 0, 0, 300, 300, this);
			}

			Font title = new Font("Brush Script MT", Font.BOLD, 80);
			g.setFont(title);
			g.setColor(table.colormode);
			g.drawString("Snakeyy", 30, 100);

			Graphics2D g2d = (Graphics2D) g;
			Font medium = new Font("Century", Font.BOLD, 30);
			g.setFont(medium);

			drawCenteredString("Press 'PLAY' to start game", width, height - 120, g, true);
			drawCenteredString("Scoreboard", width, height + 200, g, true);

			Font small = new Font("Century", Font.ITALIC, 20);
			g.setFont(small);

			table.readwrite.readFile();
			int size_or_five = table.readwrite.getNamepoint().size() > 5 ? 5 : table.readwrite.getNamepoint().size();
			for (int i = 0; i < size_or_five; i++) {
				String nameandpoint = reverseWords(table.readwrite.getNamepoint().get(i));
				drawCenteredString(i + 1 + ". " + nameandpoint + " points", width, height + 205 + 70 * (i + 1), g,
						false);
			}

			table.setLayout(new GridBagLayout());
			JButton playbutton = new JButton("PLAY");
			playbutton.setPreferredSize(new Dimension(100, 50));

			table.add(playbutton);
			playbutton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if (table.State == State.MENU) {
						
						table.requestFocus();
						setState(State.GAME);
						JLabel label = new JLabel("What is your name?");
						label.setFont(new Font("Brush Script MT", Font.BOLD, 25));

						while (true) {
							table.username = JOptionPane.showInputDialog(null, label, "Username",
									JOptionPane.QUESTION_MESSAGE);
							if (table.username == null || table.username.length() < 5) {
								JOptionPane.showMessageDialog(null, "Invalid Input\nPlease enter at least 5 characters", "Cannot Cancel",
										JOptionPane.ERROR_MESSAGE);
								continue;
							} else {
								break;
							}
						}

						if (table.username != null && table.username.length() > 4) {
							table.username = table.username.replaceAll("\\s+", "");
							if (table.firstgame) {
								System.out.println("star");
								table.start();
							} else {
								System.out.println("restar");
								table.restart();
							}

						}
					}
				}
			});
			table.add(new JLabel());

		} else {

			g.setColor(table.colormode);
			g.fillRect(0, 0, width, height);
			g.setColor(Color.DARK_GRAY);

			for (int i = 0; i < height; i += Coordinate.SIZE) {
				g.drawLine(i, 0, i, height);
			}

			for (int i = 0; i < width; i += Coordinate.SIZE) {
				g.drawLine(0, i, width, i);
			}

		}

	}

	public void setState(STATE state) {
		State = state;
	}

	public STATE getState() {
		return State;
	}

	public void drawCenteredString(String s, int w, int h, Graphics g, boolean drawRect) {
		FontMetrics fm = g.getFontMetrics();
		int x = (w - fm.stringWidth(s)) / 2;
		int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
		g.drawString(s, x, y);
		if (drawRect)
			g.drawRoundRect(x - 10, y - 30, fm.stringWidth(s) + 15, fm.getAscent() + 10, 13, 13);
	}

	static String reverseWords(String str) {

		Pattern pattern = Pattern.compile("\\s");

		String[] temp = pattern.split(str);
		String result = "";

		for (int i = 0; i < temp.length; i++) {
			if (i == temp.length - 1)
				result = temp[i] + result;
			else
				result = " " + temp[i] + result;
		}
		return result;
	}

}
