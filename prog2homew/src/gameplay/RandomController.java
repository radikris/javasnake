package gameplay;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import elements.Coordinate;
import enumtype.CoordinateType;

public class RandomController {

	private List<Marker> apple_and_walls = new ArrayList<Marker>();

	public void resetWallandApple() {

		apple_and_walls.clear();

		apple_and_walls = new ArrayList<Marker>();
	}

	public void addRandom(Marker wallorapple) throws Exception {

		apple_and_walls.add(wallorapple);
	}

	public Marker getAppleorWall(int idx) {
		return apple_and_walls.get(idx);
	}

	public void removeAppleorWall(int idx) {
		apple_and_walls.remove(idx);
	}

	public List<Marker> getAppleandWalls() {
		return apple_and_walls;
	}

	public void removeByCoordinate(int x, int y) {
		for (int i = 0; i < apple_and_walls.size(); i++) {
			if (apple_and_walls.get(i).getCoordinates().getX() == x
					&& apple_and_walls.get(i).getCoordinates().getY() == y) {
				apple_and_walls.remove(i);
			}
		}
	}

}
