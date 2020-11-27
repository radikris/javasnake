package elements;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import enumtype.Direction;
import enumtype.STATE;
import gameplay.Table;

public class MyKeyAdapter extends KeyAdapter {

	Table mytable;

	public MyKeyAdapter(Table table) {
		this.mytable = table;
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (mytable.State == STATE.GAME) {
			// snakeCoordinate = getSnake(snakelength);

			switch (e.getKeyCode()) {

			case KeyEvent.VK_LEFT:

				if (mytable.direction != Direction.RIGHT) {
					mytable.direction = Direction.LEFT;
				}
				break;
			case KeyEvent.VK_RIGHT:

				if (mytable.direction != Direction.LEFT) {
					mytable.direction = Direction.RIGHT;
				}
				break;
			case KeyEvent.VK_UP:

				if (mytable.direction != Direction.DOWN) {
					mytable.direction = Direction.UP;
				}
				break;
			case KeyEvent.VK_DOWN:

				if (mytable.direction != Direction.UP) {
					mytable.direction = Direction.DOWN;
				}
				break;
			case KeyEvent.VK_ESCAPE:

				if (mytable.getGameRunning()) {
					PauseMenu pause = new PauseMenu(mytable);

					pause.showPause();
					try {
						mytable.stop();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				break;

			}

		}

	}
}
