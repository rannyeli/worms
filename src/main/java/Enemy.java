package main.java;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.font.GraphicAttribute;
import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

import org.jgrapht.alg.DijkstraShortestPath;
import org.omg.DynamicAny._DynEnumStub;

/**
 * ENEMY CLASS
 * 
 * 
 */
public class Enemy extends Sprite {

	// enemy settings
	// private int level;
	// private int life;
	// what type of enemy this is
	// private TYPEOFENEMY type;
	// timer of enemy , to see when he will pop out
	// private int counter=0;
	// public static Random rand=new Random();
	// enemy's path calculated by dijkstra
	private DijkstraShortestPath path;

	public Enemy(GamePanel gameView, int cols, int rows, int tn, String tc, String name, int x, int y)// ctor
	{
		super(gameView, cols, rows, tn, tc, name, x, y);
		// this.level=level;
		// this.life=level * 100;
		// this.counter=rand.nextInt()%15+5;
		// this.type=TYPEOFENEMY.ONWAIT;

	}
	// public void drawEnemy(Graphics g)
	// {
	// if(type==TYPEOFENEMY.ONGO)
	// {
	// super.draw(g);
	// }
	// }
	// public boolean checkCounter()//a function that will be called every loop in
	// the main thread
	// {
	// boolean finished=false;
	// if(counter<=0)
	// {
	// finished=true;
	// if(this.getPath()==null)
	// {
	// this.type=TYPEOFENEMY.ONGO;
	// this.setPath(GraphFacilities.findShortestPathLength(GraphFacilities.vertexMat[yLocationInMat][xLocationInMat]));//dijkstra
	// }
	// }
	// else
	// {
	// counter--;
	// }
	// return finished;
	// }

	public void updatePath() {
		if (this.getPath() == null || this.getPath().getPathEdgeList() == null
				|| this.getPath().getPathEdgeList().size() == 0) {
			System.out.println("this-i:" + this.getY() / 42 + " this-j: " + this.getX() / 42);
			this.setPath(GraphFacilities
					.findShortestPathLength(GraphFacilities.vertexMat[this.getY() / 42][this.getX() / 42]));// dijkstra
			// this.setPath(GraphFacilities.findShortestPathLength(GraphFacilities.vertexMat[this.getY()/6][this.getX()/6]));
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

	// public boolean beShot(int dmg)//called when the enemy is shot
	// {
	// this.life=life-dmg;
	// if(life<0)
	// return true;
	// return false;
	// }
	// public int getLife() {
	// return life;
	// }
	// public void setLife(int life) {
	// this.life = life;
	// }
	// public int getLevel() {
	// return level;
	// }
	// public void setLevel(int level) {
	// this.level = level;
	// }
	// public int getCounter() {
	// return counter;
	// }
	// public void setCounter(int counter) {
	// this.counter = counter;
	// }
	public DijkstraShortestPath getPath() {
		return path;
	}

	public void setPath(DijkstraShortestPath path) {
		this.path = path;
	}
	// public TYPEOFENEMY getType() {
	// return type;
	// }
	// public void setType(TYPEOFENEMY type) {
	// this.type = type;
	// }
}
