package main.java;

/**
 * DIJKSTRA'S GRAPH EDGES
 * 
 */
public class WormEdge {
	private WormVertex srcVertx;// source vertex
	private WormVertex destVertx;// destination vertex
	private int weight;// weight of the edge

	public WormEdge(WormVertex srcVertx, WormVertex destVertex)// ctor
	{
		this.srcVertx = srcVertx;
		this.destVertx = destVertex;
		weight = 1;
	}

	// getters and setters
	public WormVertex getSrcVertx() {
		return srcVertx;
	}

	public void setSrcVertx(WormVertex srcVertx) {
		this.srcVertx = srcVertx;
	}

	public WormVertex getDestVertx() {
		return destVertx;
	}

	public void setDestVertx(WormVertex destVertx) {
		this.destVertx = destVertx;
	}
}
