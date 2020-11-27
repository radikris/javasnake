package gameplay;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import elements.Coordinate;
import elements.DrawGUI;

public class Apple extends Marker {

	private BufferedImage apple;
	boolean isBonusApple;
	int appleWorth;

	public Apple(Coordinate coordinate) {
		super(coordinate);
		isBonusApple = isBonus();
		appleWorth = isBonusApple ? 5 : 1;

		loadimage();
	}

	public void draw(Graphics g) {

		drawmarker(g, apple);

	}

	public boolean isBonus() {
		Random r = new Random();
		int low = 1;
		int high = 100;
		int result = r.nextInt(high - low) + low;

		return (result % 7) == 0; // why 7? just so
	}

	@Override
	public void loadimage() {
		// TODO Auto-generated method stub
		try {
			apple = getmode()
					? (isBonusApple ? ImageIO.read(getClass().getResourceAsStream("/assets/goldenapple_white.png"))
							: ImageIO.read(getClass().getResourceAsStream("/assets/white_apple.png")))
					: (isBonusApple ? ImageIO.read(getClass().getResourceAsStream("/assets/goldenapple_black.png"))
							: ImageIO.read(getClass().getResourceAsStream("/assets/black_apple.jpg")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean collideProblem() {
		// TODO Auto-generated method stub
		return false;
	}

}