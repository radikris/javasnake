package gameplay;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import elements.Coordinate;
import elements.MyKeyAdapter;
//import elements.GridBagLayoutSparse;
import elements.PauseMenu;
import elements.ReadWrite;
import enumtype.CoordinateType;
import enumtype.Direction;
import enumtype.STATE;

public class Table extends JPanel implements ActionListener, Runnable {

	private static final long serialVersionUID = 8806551928421848891L; // avoid compile warning
	int rownum, colnum;
	public Direction direction = null;

	private Thread thread;
	private boolean running = false;

	static boolean firstgame = true;

	private Apple apple;

	public SnakePartList snakelist = new SnakePartList();
	public RandomController wallandapplelist = new RandomController();
	int xCoor, yCoor;

	public static String username;
	public ReadWrite readwrite = new ReadWrite("example.json");
	MyKeyAdapter keyadapter = new MyKeyAdapter(this);

	public static STATE State = STATE.MENU;
	public static Color colormode = Color.white;
	BackGround background;

	public Table() {
		initTable();
	}

	public Table(int rownum, int colnum) {
		this.rownum = rownum;
		this.colnum = colnum;
		initTable();
	}

	public int ticks = 0;

	private void initTable() {
		initMarkers();

		for (KeyListener l : getKeyListeners()) {
			if (l instanceof KeyAdapter) {
				removeKeyListener(l);
			}
		}
		addKeyListener(keyadapter);
		setBackground(Color.black);
		setFocusable(true);

		setPreferredSize(new Dimension(colnum, rownum));
		try {
			background = new BackGround(State, colnum, rownum, this);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void paint(Graphics g) {

		g.setColor(Color.BLACK);
		g.clearRect(0, 0, colnum, rownum);
		if (State == STATE.GAME) {
			try {
				background.render(g);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			snakelist.paintSnake(g);

			for (int i = 0; i < wallandapplelist.getAppleandWalls().size(); i++) {
				wallandapplelist.getAppleorWall(i).draw(g);
			}

			Font title = new Font("Monospaced Bold", Font.BOLD, 20);
			g.setFont(title);
			g.setColor(Color.BLACK);
			g.drawString("Score: " + snakelist.getSnakeSize().toString(), 5, 20);

		} else {
			try {
				background.render(g);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void restart() {
		State = STATE.GAME;
		background.setState(State);
		running = true;

		snakelist.ResetSnake();
		wallandapplelist.resetWallandApple();

		direction = null;
		table = new Coordinate[Coordinate.SIZE][Coordinate.SIZE];
		initTable();

		start();
	}

	public void start() {
		requestFocus();
		firstgame = false;
		State = STATE.GAME;
		background.setState(State);

		running = true;
		thread = new Thread(this, "Game Loop");
		if (snakelist.getSnake().size() == 0) {

			Coordinate whereapple = Marker.randomandSetandDelete(CoordinateType.COIN, table);
			apple = new Apple(whereapple);

			try {
				wallandapplelist.addRandom(apple);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Coordinate cs = Marker.randomandSetandDelete(CoordinateType.PLAYERHEAD, table);
			xCoor = cs.getX();
			yCoor = cs.getY();
			SnakePart b = new SnakePart(cs);
			// snake.add(b);
			snakelist.addSnakePart(b);
		}

		thread.start();
	}

	public void stop() throws IOException {
		running = false;
		Integer s = snakelist.getSnake().size();
		if (thread != null) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (running) {
			if (direction != null)
				try {
					move();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			repaint();
		}
	}

	public Coordinate table[][] = new Coordinate[Coordinate.SIZE][Coordinate.SIZE];

	public void move() throws Exception {

		if (State == STATE.GAME) {

			ticks++;
			int substraction = snakelist.getSnake().size() > 18 ? 100000 * 18 : 100000 * snakelist.getSnake().size();
			if (ticks > 2500000 - substraction) {
				switch (direction) {
				case LEFT:
					xCoor--;
					break;
				case RIGHT:
					xCoor++;
					break;
				case UP:
					yCoor--;
					break;
				case DOWN:
					yCoor++;
					break;
				}

				snakelist.moveSnake(xCoor, yCoor);

				ticks = 0;

				if (apple.getCoordinates().getX() == xCoor && apple.getCoordinates().getY() == yCoor) {
					// size++; // noveljuk a kigyo "hosszat", kovetkezo lepesben nem toroljuk ki az
					// utolsot

					snakelist.addSize(apple.appleWorth);

					wallandapplelist.removeByCoordinate(apple.getCoordinates().getX(), apple.getCoordinates().getY());

					Coordinate whereapple = Marker.randomandSetandDelete(CoordinateType.COIN, table);
					apple = new Apple(whereapple);

					wallandapplelist.addRandom(apple);

				}

				for (int i = 0; i < snakelist.getSnake().size(); i++) {
					table[snakelist.getSnakePart(i).coordinates.getX()][snakelist.getSnakePart(i).coordinates.getY()]
							.setCtype(CoordinateType.PLAYER);

				}

				detectCollosion(snakelist.getSnake());
				detectCollosion(wallandapplelist.getAppleandWalls());
			}

		}
	}

	public void detectCollosion(List<? extends Marker> list) throws Exception {

		for (int i = 0; i < list.size(); i++) {
			if (xCoor == list.get(i).getCoordinates().getX() && yCoor == list.get(i).getCoordinates().getY()) {
				if (i != list.size() - 1 && list.get(i).collideProblem()) {

					System.out.println("ez vege testverem");
					JLabel label = new JLabel("Game Over # " + snakelist.getSnake().size() + " points");
					label.setFont(new Font("Brush Script MT", Font.BOLD, 25));
					JOptionPane.showMessageDialog(null, label, "GAMEOVER", JOptionPane.CANCEL_OPTION);

					State = STATE.MENU;
					try {
						background = new BackGround(State, colnum, rownum, this);
						background.setState(STATE.MENU);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					repaint();
					Integer s = snakelist.getSnake().size();
					readwrite.writeFile(username, s.toString());
					stop();

				}
			}
		}
	}

	public void initMarkers() {

		for (int j = 0; j < Coordinate.SIZE; j++) {

			for (int i = 0; i < Coordinate.SIZE; i++) {
				if (i == 0 || j == 0 || i == Coordinate.SIZE - 1 || j == Coordinate.SIZE - 1) {
					table[i][j] = new Coordinate(i, j, CoordinateType.WALL);
					Wall wall = new Wall(new Coordinate(i, j, CoordinateType.WALL), Coordinate.SIZE);

					try {
						wallandapplelist.addRandom(wall);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else
					table[i][j] = new Coordinate(i, j, CoordinateType.EMPTY);

			}

		}

		for (int howmuch = 0; howmuch < 15; howmuch++) {
			Coordinate wallc = Marker.randomandSetandDelete(CoordinateType.WALL, table);
			Wall wall = new Wall(wallc, Coordinate.SIZE);

			try {
				wallandapplelist.addRandom(wall);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			table[wallc.getX()][wallc.getY()].setCtype(CoordinateType.WALL);

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("actionperformed");
		repaint();
	}

	public void changeCurrentMode() {
		if (apple != null)
			apple.loadimage();
		if (snakelist.getSnake().size() > 0)
			snakelist.getSnakePart(snakelist.getSnakeSize() - 1).loadimage();
	}

	public void setColorMode(Color colorm) {
		this.colormode = colorm;
		boolean lightmode = colorm == Color.black ? false : true;
		Marker.setmode(lightmode);
		changeCurrentMode(); // igy az adott blokk alma és kígyófeje is megváltozik egybõl, különben nem
								// töltené újra a képet
								// mert ha játék közben váltjuk, akkor az elõtte betöltött kép látszódna (hiába
								// változik a colormode)
		repaint();
	}

	public boolean getGameRunning() {
		return running;
	}

	public void setXcoor(int x) {
		xCoor = x;
	}

	public void setYcoor(int y) {
		yCoor = y;
	}

	public Thread getThread() {
		return thread;
	}

	public Apple getApple() {
		return apple;
	}

	public void setApple(Apple a) {
		apple = a;
	}

	public void setSnakelist(SnakePartList s) {
		snakelist = s;
	}

}
