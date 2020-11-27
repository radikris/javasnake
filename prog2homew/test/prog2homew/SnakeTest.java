package prog2homew;

import static org.junit.Assert.*;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import elements.Coordinate;
import enumtype.CoordinateType;
import enumtype.Direction;
import enumtype.STATE;
import gameplay.Apple;
import gameplay.Marker;
import gameplay.SnakePart;
import gameplay.SnakePartList;
import gameplay.Table;

@RunWith(Parameterized.class)
public class SnakeTest {

	Direction direction;
	SnakePartList snakelist;

	public SnakeTest(Direction d) {
		direction = d;

	}

	@Before
	public void setUp() {
		snakelist = new SnakePartList();
		snakelist.size=5;
		for (int i = 0; i < 5; i++) {
			Coordinate c = new Coordinate(10, 10 + i, CoordinateType.PLAYER);
			SnakePart sp = new SnakePart(c);
			snakelist.addSnakePart(sp);
		}
	}

	@Test
	public void moveSnakeTest() {

		SnakePart snakehead;
		snakehead = snakelist.getSnakePart(snakelist.getSnake().size() - 1);
		int xCoor = snakehead.getCoordinates().getX();
		int yCoor = snakehead.getCoordinates().getY();
		int firstx, firsty;
		firstx = xCoor;
		firsty = yCoor;

		switch (direction)

		{
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
		snakehead = snakelist.getSnakePart(snakelist.getSnake().size() - 1);

		switch (direction)

		{
		case LEFT:
			Assert.assertEquals(xCoor, firstx - 1, 0);
			break;
		case RIGHT:
			Assert.assertEquals(xCoor, firstx + 1, 0);
			break;
		case UP:
			Assert.assertEquals(yCoor, firsty - 1, 0);
			break;
		case DOWN:
			Assert.assertEquals(yCoor, firsty + 1, 0);
			break;
		}

	}

	@Test
	public void snakeEatApple() throws Exception {
		int withoutapple = snakelist.getSnakeSize();
		System.out.println(withoutapple);
		Table table = new Table(676 + 23, 676);
		table.State = STATE.GAME;
		table.ticks = 25000000;
		table.direction = Direction.RIGHT;

		Coordinate c = new Coordinate(10, 16, CoordinateType.COIN);
		Apple testapple = new Apple(c);
		table.setApple(testapple);
		table.setSnakelist(snakelist);

		table.setXcoor(testapple.getCoordinates().getX() - 1);
		table.setYcoor(testapple.getCoordinates().getY());

		table.move();

		Assert.assertTrue(withoutapple < table.snakelist.size);	//vagy eggyel nagyobb, vagy 5-tel, ha bonuszalmat kapott

	}
	
	@Test
	public void withoutPaintShouldBePLAYER() {
		Assert.assertNotEquals(snakelist.getSnakePart(snakelist.getSnake().size()-1).getCoordinates().getCtype(), CoordinateType.PLAYERHEAD);
		Assert.assertEquals(snakelist.getSnakePart(snakelist.getSnake().size()-1).getCoordinates().getCtype(), CoordinateType.PLAYER);
	}

	@Parameters
	public static List<Object[]> parameters() {
		List<Object[]> params = new ArrayList<Object[]>();
		params.add(new Object[] { Direction.LEFT });
		params.add(new Object[] { Direction.RIGHT });
		params.add(new Object[] { Direction.DOWN });
		params.add(new Object[] { Direction.UP });

		return params;
	}

}
