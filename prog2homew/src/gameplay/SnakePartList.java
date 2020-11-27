package gameplay;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import elements.Coordinate;
import enumtype.CoordinateType;

public class SnakePartList {
	private List<SnakePart> snake = new ArrayList<SnakePart>();
	public int size;

	public SnakePartList() {
		size = 1;
	}

	public List<SnakePart> getSnake() {
		return snake;
	}

	public SnakePart getSnakePart(int idx) {
		return snake.get(idx);
	}

	public void ResetSnake() {
		size = 1;
		snake.clear();
		snake = new ArrayList<SnakePart>();
	}

	public void addSnakePart(SnakePart sp) {
		snake.add(sp);
	}

	public void removeSnakePart(int idx) {
		snake.remove(idx);
	}
	

	public void paintSnake(Graphics g) {
		for (int i = 0; i < snake.size(); i++) {
			if (i == snake.size() - 1) {
				snake.get(i).getCoordinates().setCtype(CoordinateType.PLAYERHEAD);
			} else {
				snake.get(i).getCoordinates().setCtype(CoordinateType.PLAYER);
			}
			snake.get(i).draw(g);
		}
	}

	public void moveSnake(int xCoor, int yCoor) {
		SnakePart b = new SnakePart(new Coordinate(xCoor, yCoor, CoordinateType.PLAYER));

		snake.add(b);
		// Hozzaadunk egy uj kigyoreszt arra, amerra mozog a kigyo, es toroljuk az
		// utolso kigyot ha nem evett almát most (ha bónuszalmát evétt, akkor annyit nem
		// törlünk ki amennyit ért az alma)

		if (snake.size() > size) {
			snake.remove(0);
		}
	}

	public void addSize(int appleWorth) {
		size += appleWorth;
	}

	public Integer getSnakeSize() {
		Integer s = snake.size();
		return s;
	}

}
