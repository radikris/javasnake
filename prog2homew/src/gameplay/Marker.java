package gameplay;

import java.awt.Graphics;

import elements.Coordinate;

public abstract class Marker {
	Coordinate coordinates;

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

	public abstract void move();
	public abstract void draw(Graphics g);
	
}
