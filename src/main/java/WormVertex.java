package main.java;

import java.awt.Point;

/**
 * DIJKSTRA'S GRAPH VERTEX
 * 
 */

public class WormVertex {
	private Point location;// loaction of the vertex in the game matrix

	public WormVertex(int x, int y) {
		this.location = new Point(x, y);
	}

	public Point getLocation() {
		return location;
	}
}
