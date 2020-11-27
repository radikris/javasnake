package gameplay;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import elements.Coordinate;

public class Wall extends Marker {

	// private Coordinate coordinate;
	private BufferedImage wall;

	public Wall(Coordinate coordinate, int tileSize) {
		super(coordinate);

		loadimage();
	}

	public void draw(Graphics g) {

		drawmarker(g, wall);

	}

	@Override
	public void loadimage() {
		// TODO Auto-generated method stub
		try {
			wall = ImageIO.read(getClass().getResourceAsStream("/assets/wall.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean collideProblem() {
		// TODO Auto-generated method stub
		return true;
	}

}
