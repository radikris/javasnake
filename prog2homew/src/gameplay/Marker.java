package gameplay;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import elements.Collision;
import elements.Coordinate;
import elements.DrawGUI;
import enumtype.CoordinateType;

public abstract class Marker implements DrawGUI, Collision {
	Coordinate coordinates;
	static boolean lightmode=true;

	public Marker() {
		// TODO Auto-generated constructor stub
	}

	public Marker(Coordinate coordinate) {
		// TODO Auto-generated constructor stub
		this.coordinates = coordinate;
	}

	public Coordinate getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinate coordinates) {
		this.coordinates = coordinates;
	}


	public void drawmarker(Graphics g, BufferedImage image) {
	
		g.drawImage(image, getCoordinates().getX() * Coordinate.SIZE, getCoordinates().getY() * Coordinate.SIZE,
				Coordinate.SIZE, Coordinate.SIZE, null);
	}
	
	public static void setmode(boolean mode) {
		lightmode=mode;
	}
	
	public static boolean getmode() {
		return lightmode;
	}
	
	public abstract void loadimage();
	
	public static Coordinate randomandSetandDelete(CoordinateType ctype, Coordinate table[][]) {
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

}
