package elements;

import java.awt.Graphics;

import enumtype.CoordinateType;

public class Coordinate {
	
	public final static int SIZE = 26;
	
	int x, y;
	CoordinateType ctype;
	
	public Coordinate(int xc, int yc, CoordinateType ctypec) {
		// TODO Auto-generated constructor stub
		this.x=xc;
		this.y=yc;
		this.ctype=ctypec;
	}
	
	public Coordinate(int xc, int yc) {
		// TODO Auto-generated constructor stub
		this(xc, yc, CoordinateType.EMPTY);
	}
	
	public CoordinateType getCtype() {
		return ctype;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setCtype(CoordinateType ctype) {
		this.ctype = ctype;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
}
