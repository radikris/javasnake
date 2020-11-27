package prog2homew;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import elements.Coordinate;
import elements.MyMenuBar;
import enumtype.CoordinateType;
import enumtype.STATE;
import gameplay.Marker;
import gameplay.RandomController;
import gameplay.SnakePart;
import gameplay.SnakePartList;
import gameplay.Table;

public class TableTest {

	SnakePartList snakelist;
	RandomController appleandwall;
	Table table;

	@Before
	public void setUp() {
		snakelist = new SnakePartList();
		for (int i = 0; i < 5; i++) {
			Coordinate c = new Coordinate(10, 10 + i, CoordinateType.PLAYER);
			SnakePart sp = new SnakePart(c);
			snakelist.addSnakePart(sp);
		}
		table = new Table(676 + 23, 676);

	}

	@Test
	public void collisionSnakeTest() throws Exception {
		table.setXcoor(snakelist.getSnakePart(snakelist.getSnake().size() - 1).getCoordinates().getX());
		table.setYcoor(snakelist.getSnakePart(snakelist.getSnake().size() - 1).getCoordinates().getY());
		table.detectCollosion(snakelist.getSnake());
		Assert.assertEquals(STATE.MENU, table.State);
	}

	@Test
	public void tableInitTest() {
		table.initMarkers();

		for (int i = 0; i < Coordinate.SIZE; i++) {
			Assert.assertEquals(CoordinateType.WALL, table.table[0][i].getCtype());
		}

	}

	@Test
	public void checkGameEnd() throws Exception {

		table.setXcoor(table.wallandapplelist.getAppleorWall(0).getCoordinates().getX());
		table.setYcoor(table.wallandapplelist.getAppleorWall(0).getCoordinates().getY());

		table.detectCollosion(table.wallandapplelist.getAppleandWalls());

		Assert.assertEquals(false, table.getGameRunning());

	}

	@Test
	public void checkColorModeTest() {

		table.setColorMode(Color.black);

		Assert.assertEquals(false, Marker.getmode());

	}

}
