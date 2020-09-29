package gameplay;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import elements.Coordinate;

public class Wall extends Marker {
	public int width, height;
	// private Coordinate coordinate;
	private BufferedImage wall;

	public Wall(Coordinate coordinate, int tileSize) {
		super(coordinate);
		width = tileSize;
		height = tileSize;
		try {
			wall = ImageIO.read(getClass().getResourceAsStream("/assets/wall.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void tick() {

	}

	public void draw(Graphics g) {
		g.drawImage(wall, getCoordinates().getX() * width, getCoordinates().getY() * height, width, height, null);

	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

}
