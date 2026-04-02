package com.worms;

import java.util.List;

public class Enemy extends Sprite {

	private List<WormEdge> path;

	public Enemy(GamePanel gameView, int cols, int rows, int tn, String tc, String name, int x, int y) {
		super(gameView, cols, rows, tn, tc, name, x, y);
	}

	public void updatePath() {
		if (this.getPath() == null || this.getPath().isEmpty()) {
			System.out.println("this-i:" + this.getY() / GameConfig.BLOCK_SIZE + " this-j: " + this.getX() / GameConfig.BLOCK_SIZE);
			this.setPath(GraphFacilities
					.findShortestPathLength(GraphFacilities.vertexMat[this.getY() / GameConfig.BLOCK_SIZE][this.getX() / GameConfig.BLOCK_SIZE]));
		} else {
			GraphFacilities.goForward(this);
		}
	}

	public void setLocation(int x, int y) {
		this._currentFrame = (this._currentFrame + 1) % (this._bmpColumns - 1);
		setX(x);
		setY(y);
		_recW2.x = x;
		_recW2.y = y;
	}

	public List<WormEdge> getPath() {
		return path;
	}

	public void setPath(List<WormEdge> path) {
		this.path = path;
	}
}
