package elements;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
//GridBagLayoutSparse

public class GridBagLayoutSparse extends JPanel {

	public GridBagLayoutSparse() {
		int index = 0;
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		for (int row = 0; row < Coordinate.SIZE; row++) {
			for (int col = 0; col < Coordinate.SIZE; col++) {
				Color color = index % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE;
				gbc.gridx = col;
				gbc.gridy = row;
				add(new Cell(color), gbc);
				index++;
			}
			index++;
		}
	}

}

class Cell extends JPanel {

	public Cell(Color background) {

		setBackground(background);
		setOpaque(true);
		// setSize(27, 27);

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(Coordinate.SIZE, Coordinate.SIZE);
	}

}