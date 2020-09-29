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
import elements.GridBagLayoutSparse;
import elements.PauseMenu;
import elements.ReadWrite;
import enumtype.CoordinateType;
import enumtype.Direction;
import enumtype.STATE;

public class Table extends JPanel implements ActionListener, Runnable {

	private static final long serialVersionUID = 8806551928421848891L; // avoid compile warning
	int rownum, colnum;
	Direction direction = null;
	private Thread thread;
	private boolean running = false;

	public int snakelength = 0;
	boolean gamestarted = false;
	public ArrayList<Coordinate> snakeCoordinate = new ArrayList<Coordinate>();
	private Apple apple;
	public List<SnakePart> snake = new ArrayList<SnakePart>();
	private List<Apple> apples = new ArrayList<Apple>();
	private List<Wall> walls = new ArrayList<Wall>();
	int size = 1;
	public static String username;
	public ReadWrite readwrite = new ReadWrite("players.txt");
	static boolean firstgame = true;
	MyKeyAdapter keyadapter = new MyKeyAdapter(this);

	int xCoor, yCoor;

	public static STATE State = STATE.MENU;

	BackGround background;

	public Table() {
		initTable();
	}

	private int ticks = 0;

	private void initTable() {
		initMarkers();
		snakeCoordinate = getSnake(snakelength);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println("paintcomet");
		paint(g);
	}

	public void paint(Graphics g) {

		g.clearRect(0, 0, colnum, rownum);
		if (State == STATE.GAME) {

			try {
				background.render(g);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (int i = 0; i < snake.size(); i++) {
				if (i == snake.size() - 1) {
					snake.get(i).getCoordinates().setCtype(CoordinateType.PLAYERHEAD);
				} else {
					snake.get(i).getCoordinates().setCtype(CoordinateType.PLAYER);
				}
				snake.get(i).draw(g);
			}

			for (int i = 0; i < apples.size(); i++) {
				apples.get(i).draw(g);
			}

			for (int i = 0; i < walls.size(); i++) {
				walls.get(i).draw(g);
			}
			Font title = new Font("Monospaced Bold", Font.BOLD, 20);
			g.setFont(title);
			g.setColor(Color.BLACK);
			g.drawString("Score: " + getSnakeSize().toString(), 5, 20);

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
		// requestFocus();
		State = STATE.GAME;
		background.setState(State);
		running = true;

		snake.clear();
		size = 1;
		apples.clear();
		walls.clear();
		snake = new ArrayList<SnakePart>();
		apples = new ArrayList<Apple>();
		walls = new ArrayList<Wall>();
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
		if (snake.size() == 0) {
			Coordinate cs = randomandSetandDelete(CoordinateType.PLAYERHEAD, table);
			xCoor = cs.getX();
			yCoor = cs.getY();
			SnakePart b = new SnakePart(cs, Coordinate.SIZE);
			snake.add(b);
		}

		thread.start();
	}

	void stop() throws IOException {
		running = false;
		Integer s = snake.size();
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
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			repaint();
		}
	}

	public Table(int rownum, int colnum) {
		this.rownum = rownum;
		this.colnum = colnum;
		initTable();
	}

	Coordinate table[][] = new Coordinate[Coordinate.SIZE][Coordinate.SIZE];

	public Coordinate randomandSetandDelete(CoordinateType ctype, Coordinate table[][]) {
		ArrayList<Coordinate> emptycoordinate = new ArrayList<Coordinate>();

		for (int j = 0; j < Coordinate.SIZE; j++) {

			for (int i = 0; i < Coordinate.SIZE; i++) {
				if (table[i][j].getCtype() == CoordinateType.EMPTY) {
					emptycoordinate.add(table[i][j]);
				}
			}

		}
		int randomMark = ThreadLocalRandom.current().nextInt(0, emptycoordinate.size() - 1);

		table[emptycoordinate.get(randomMark).getX()][emptycoordinate.get(randomMark).getY()].setCtype(ctype);

		emptycoordinate.remove(randomMark);
		return new Coordinate(emptycoordinate.get(randomMark).getX(), emptycoordinate.get(randomMark).getY(), ctype);
	}

	private void move() throws IOException {
		if (State == STATE.GAME) {

			ticks++;
			int substraction = snake.size() > 18 ? 100000 * 18 : 100000 * snake.size();
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

				SnakePart b = new SnakePart(new Coordinate(xCoor, yCoor, CoordinateType.PLAYER), Coordinate.SIZE);
				snake.add(b);

				if (snake.size() > size)
					snake.remove(0);
				ticks = 0;

				for (int i = 0; i < apples.size(); i++) {
					if (xCoor == apples.get(i).getCoordinates().getX()
							&& yCoor == apples.get(i).getCoordinates().getY()) {
						size++;
						apples.remove(i);
						Coordinate whereapple = randomandSetandDelete(CoordinateType.COIN, table);
						apple = new Apple(whereapple, Coordinate.SIZE);
						apples.add(apple);
						i++;
					}
				}

				for (int i = 0; i < snake.size(); i++) {
					table[snake.get(i).coordinates.getX()][snake.get(i).coordinates.getY()]
							.setCtype(CoordinateType.PLAYER);
				}
				if (apples.size() > 0)
					table[apples.get(0).coordinates.getX()][apples.get(0).coordinates.getY()]
							.setCtype(CoordinateType.COIN);

				detectCollosion(snake);
				detectCollosion(walls);
			}

		}
	}

	public void detectCollosion(List<? extends Marker> list) throws IOException {

		for (int i = 0; i < list.size(); i++) {
			if (xCoor == list.get(i).getCoordinates().getX() && yCoor == list.get(i).getCoordinates().getY()) {
				if (i != list.size() - 1) {
					System.out.println("ez vege testverem");
					JLabel label = new JLabel("Game Over # " + snake.size() + " points");
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
					Integer s = snake.size();
					readwrite.writeFile(username, s.toString());
					stop();

				}
			}
		}
	}

	private void initMarkers() {

		for (int j = 0; j < Coordinate.SIZE; j++) {

			for (int i = 0; i < Coordinate.SIZE; i++) {
				if (i == 0 || j == 0 || i == Coordinate.SIZE - 1 || j == Coordinate.SIZE - 1) {
					table[i][j] = new Coordinate(i, j, CoordinateType.WALL);
					Wall wall = new Wall(new Coordinate(i, j, CoordinateType.WALL), Coordinate.SIZE);
					walls.add(wall);
				} else
					table[i][j] = new Coordinate(i, j, CoordinateType.EMPTY);

			}

		}

		for (int howmuch = 0; howmuch < 15; howmuch++) {
			Coordinate wallc = randomandSetandDelete(CoordinateType.WALL, table);
			Wall wall = new Wall(wallc, Coordinate.SIZE);
			walls.add(wall);
			table[wallc.getX()][wallc.getY()].setCtype(CoordinateType.WALL);

		}

		Coordinate whereapple = randomandSetandDelete(CoordinateType.COIN, table);
		apple = new Apple(whereapple, Coordinate.SIZE);
		apples = new ArrayList<Apple>();
		apples.add(apple);
		System.out.println(apples.get(0).coordinates.getX() + " : " + apples.get(0).coordinates.getY());

	}

	public ArrayList<Coordinate> getSnake(int slength) {
		slength = 0;
		int ph = 0;
		ArrayList<Coordinate> snakecoordinates = new ArrayList<Coordinate>();
		for (int j = 0; j < Coordinate.SIZE; j++) {

			for (int i = 0; i < Coordinate.SIZE; i++) {
				if (table[i][j].getCtype() == CoordinateType.PLAYER) {
					slength++;

					snakecoordinates.add(table[i][j]);
				} else if (table[i][j].getCtype() == CoordinateType.PLAYERHEAD) {
					slength++;
					// System.out.print(++ph);
					snakecoordinates.add(0, table[i][j]);
				}
			}

		}

		return snakecoordinates;
	}

	public class MyKeyAdapter extends KeyAdapter {

		Table mytable;

		MyKeyAdapter(Table table) {
			this.mytable = table;
		}

		@Override
		public void keyPressed(KeyEvent e) {

			if (State == STATE.GAME) {
				snakeCoordinate = getSnake(snakelength);

				switch (e.getKeyCode()) {

				case KeyEvent.VK_LEFT:

					if (direction != Direction.RIGHT) {
						direction = Direction.LEFT;
					}
					break;
				case KeyEvent.VK_RIGHT:

					if (direction != Direction.LEFT) {
						direction = Direction.RIGHT;
					}
					break;
				case KeyEvent.VK_UP:

					if (direction != Direction.DOWN) {
						direction = Direction.UP;
					}
					break;
				case KeyEvent.VK_DOWN:

					if (direction != Direction.UP) {
						direction = Direction.DOWN;
					}
					break;
				case KeyEvent.VK_ESCAPE:

					PauseMenu pause = new PauseMenu(mytable);
					pause.showPause();
					try {
						stop();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;

				}

			}

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("actionperformed");
		// move();
		repaint();
	}

	public Integer getSnakeSize() {
		return snake.size();
	}

}
