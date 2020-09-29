package gameplay;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import elements.Coordinate;

public class Apple extends Marker {
	public int width, height;
	// private Coordinate coordinate;
	private BufferedImage apple;

	public Apple(Coordinate coordinate, int tileSize) {
		super(coordinate);
		width = tileSize;
		height = tileSize;
		try {
			apple = ImageIO.read(getClass().getResourceAsStream("/assets/white_apple.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void tick() {

	}

	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.drawImage(apple, getCoordinates().getX() * width + 2, getCoordinates().getY() * height, width - 2, height - 2,
				null);

	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

}