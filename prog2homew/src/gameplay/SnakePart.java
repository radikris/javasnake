package gameplay;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import elements.Coordinate;
import enumtype.CoordinateType;

public class SnakePart extends Marker {

	private int width, height;
	private BufferedImage snakehead;

	public SnakePart(Coordinate crdinate, int SIZE) {

		super(crdinate);

		width = SIZE;

		height = SIZE;

	}

	public void tick() {

	}

	public void draw(Graphics g) {

		g.setColor(new Color(33, 157, 0));

		if (getCoordinates().getCtype() == CoordinateType.PLAYER) {
			g.fillRect(getCoordinates().getX() * width, getCoordinates().getY() * height, width, height);
		} else {
			try {
				snakehead = ImageIO.read(getClass().getResourceAsStream("/assets/white_snakehead.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.drawImage(snakehead, getCoordinates().getX() * width + 2, getCoordinates().getY() * height, width - 2,
					height - 2, null);
		}

	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

}