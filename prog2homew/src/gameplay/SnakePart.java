package gameplay;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import elements.Coordinate;
import elements.DrawGUI;
import enumtype.CoordinateType;

public class SnakePart extends Marker {

	private BufferedImage snakehead;

	public SnakePart(Coordinate crdinate) {

		super(crdinate);
		loadimage();

	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

		g.setColor(new Color(33, 157, 0));

		if (getCoordinates().getCtype() == CoordinateType.PLAYER) {
			g.fillRect(getCoordinates().getX() * Coordinate.SIZE, getCoordinates().getY() * Coordinate.SIZE,
					Coordinate.SIZE, Coordinate.SIZE);
		} else {

			drawmarker(g, snakehead);
		}

	}

	@Override
	public void loadimage() {
		// TODO Auto-generated method stub
		try {
			snakehead = getmode() ? ImageIO.read(getClass().getResourceAsStream("/assets/white_snakehead.png"))
					: ImageIO.read(getClass().getResourceAsStream("/assets/black_snakehead.png"));
		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean collideProblem() {
		// TODO Auto-generated method stub
		return true;
	}

	public BufferedImage getImage() {
		return snakehead;
	}

}