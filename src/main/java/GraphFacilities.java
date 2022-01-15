package main.java;

import java.awt.Point;
import java.util.Vector;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.SimpleGraph;

/**
 * GRAPH FACILITIES
 * 
 */
public class GraphFacilities {
	public static final int W = 55, H = 16;// the height and width of the
	// public static final int W = 385, H = 112; // enemies matrix
	public static WormVertex[][] vertexMat = new WormVertex[H][W];
	public static UndirectedGraph<WormVertex, WormEdge> g = new SimpleGraph<WormVertex, WormEdge>(WormEdge.class);
	public static int _x = 700, _y = 50;

	public static void CreateGraph() {
		int i;
		// Fill the matrix with vertexes
		for (i = 0; i < (H) * W; i++) {
			// if (GamePanel._map.get_map()[i / W][i % W] == 1 ||
			// GamePanel._map.get_map()[i / W][i % W] == 2 ||
			// GamePanel._map.get_map()[i / W][i % W] == 5) {
			vertexMat[i / W][i % W] = new WormVertex(i % W, i / W);
			g.addVertex(vertexMat[i / W][i % W]);
			// }
		}
		/*
		 * //top left g.addEdge(vertexMat[0][0],vertexMat[0][1] , new
		 * TowerDefenceEdge(vertexMat[0][0],vertexMat[0][1]));
		 * g.addEdge(vertexMat[0][0],vertexMat[1][0] , new
		 * TowerDefenceEdge(vertexMat[0][0],vertexMat[1][0]));
		 * g.addEdge(vertexMat[0][1] ,vertexMat[0][0], new
		 * TowerDefenceEdge(vertexMat[0][1] ,vertexMat[0][0]));
		 * g.addEdge(vertexMat[1][0] ,vertexMat[0][0], new
		 * TowerDefenceEdge(vertexMat[1][0] ,vertexMat[0][0])); //top right
		 * g.addEdge(vertexMat[0][W-1],vertexMat[0][W-2] , new
		 * TowerDefenceEdge(vertexMat[0][W-1],vertexMat[0][W-2]));
		 * g.addEdge(vertexMat[0][W-1],vertexMat[1][W-1] , new
		 * TowerDefenceEdge(vertexMat[0][W-1],vertexMat[1][W-1]));
		 * g.addEdge(vertexMat[0][W-2] ,vertexMat[0][W-1], new
		 * TowerDefenceEdge(vertexMat[0][W-2] ,vertexMat[0][W-1]));
		 * g.addEdge(vertexMat[1][W-1] ,vertexMat[0][W-1], new
		 * TowerDefenceEdge(vertexMat[1][W-1] ,vertexMat[0][W-1])); //bottom
		 * left g.addEdge(vertexMat[H-1][0],vertexMat[H-2][0] , new
		 * TowerDefenceEdge(vertexMat[H-1][0],vertexMat[H-2][0]));
		 * g.addEdge(vertexMat[H-1][0],vertexMat[H-1][1] , new
		 * TowerDefenceEdge(vertexMat[H-1][0],vertexMat[H-1][1]));
		 * g.addEdge(vertexMat[H-2][0],vertexMat[H-1][0] , new
		 * TowerDefenceEdge(vertexMat[H-2][0],vertexMat[H-1][0]));
		 * g.addEdge(vertexMat[H-1][1],vertexMat[H-1][0] , new
		 * TowerDefenceEdge(vertexMat[H-1][1],vertexMat[H-1][0])); //bottom
		 * right g.addEdge(vertexMat[H-1][W-1],vertexMat[H-2][W-1] , new
		 * TowerDefenceEdge(vertexMat[H-1][W-1],vertexMat[H-2][W-1]));
		 * g.addEdge(vertexMat[H-1][W-1],vertexMat[H-1][W-2] , new
		 * TowerDefenceEdge(vertexMat[H-1][W-1],vertexMat[H-1][W-2]));
		 * g.addEdge(vertexMat[H-2][W-1],vertexMat[H-1][W-1] , new
		 * TowerDefenceEdge(vertexMat[H-2][W-1],vertexMat[H-1][W-1]));
		 * g.addEdge(vertexMat[H-1][W-2],vertexMat[H-1][W-1] , new
		 * TowerDefenceEdge(vertexMat[H-1][W-2],vertexMat[H-1][W-1])); //left &&
		 * right col for(i=1;i<H-1;i++) {
		 * g.addEdge(vertexMat[i][0],vertexMat[i+1][0] , new
		 * TowerDefenceEdge(vertexMat[i][0],vertexMat[i+1][0]));
		 * g.addEdge(vertexMat[i+1][0],vertexMat[i][0] , new
		 * TowerDefenceEdge(vertexMat[i+1][0],vertexMat[i][0]));
		 * 
		 * g.addEdge(vertexMat[i][0],vertexMat[i][1] , new
		 * TowerDefenceEdge(vertexMat[i][0],vertexMat[i][1]));
		 * g.addEdge(vertexMat[i][1],vertexMat[i][0] , new
		 * TowerDefenceEdge(vertexMat[i][1],vertexMat[i][0]));
		 * 
		 * g.addEdge(vertexMat[i][W-1],vertexMat[i+1][W-1] , new
		 * TowerDefenceEdge(vertexMat[i][W-1],vertexMat[i+1][W-1]));
		 * g.addEdge(vertexMat[i+1][W-1],vertexMat[i][W-1] , new
		 * TowerDefenceEdge(vertexMat[i+1][W-1],vertexMat[i][W-1]));
		 * 
		 * g.addEdge(vertexMat[i][W-1],vertexMat[i][W-2] , new
		 * TowerDefenceEdge(vertexMat[i][W-1],vertexMat[i][W-2]));
		 * g.addEdge(vertexMat[i][W-2],vertexMat[i][W-1] , new
		 * TowerDefenceEdge(vertexMat[i][W-2],vertexMat[i][W-1])); }
		 * /*for(i=1;i<H-1;i++) { g.addEdge(vertexMat[i][W-1],vertexMat[i][W-2]
		 * , new TowerDefenceEdge(vertexMat[i][W-1],vertexMat[i][W-2])); }
		 * //bottom && up row for(i=1;i<W-1;i++) {
		 * g.addEdge(vertexMat[0][i],vertexMat[0][i+1] , new
		 * TowerDefenceEdge(vertexMat[0][i],vertexMat[0][i+1] ));
		 * g.addEdge(vertexMat[0][i+1],vertexMat[0][i] , new
		 * TowerDefenceEdge(vertexMat[0][i+1],vertexMat[0][i] ));
		 * 
		 * g.addEdge(vertexMat[H-1][i],vertexMat[H-1][i+1] , new
		 * TowerDefenceEdge(vertexMat[H-1][i],vertexMat[H-1][i+1]));
		 * g.addEdge(vertexMat[H-1][i+1],vertexMat[H-1][i] , new
		 * TowerDefenceEdge(vertexMat[H-1][i+1],vertexMat[H-1][i])); }
		 */
		// all others
		/*
		 * for(i=0;i<(H-2)*(W-2);i++) { //right
		 * g.addEdge(vertexMat[1+(i/(W-2))][1+(i%(W-2))],vertexMat[1+(i/(W-2))][
		 * 2+(i%(W-2))] , new
		 * TowerDefenceEdge(vertexMat[1+(i/(W-2))][1+(i%(W-2))],vertexMat[1+(i/(
		 * W-2))][2+(i%(W-2))]));
		 * g.addEdge(vertexMat[1+(i/(W-2))][2+(i%(W-2))],vertexMat[1+(i/(W-2))][
		 * 1+(i%(W-2))] , new
		 * TowerDefenceEdge(vertexMat[1+(i/(W-2))][2+(i%(W-2))],vertexMat[1+(i/(
		 * W-2))][1+(i%(W-2))])); //left
		 * g.addEdge(vertexMat[1+(i/(W-2))][1+(i%(W-2))],vertexMat[1+(i/(W-2))][
		 * (i%(W-2))] , new
		 * TowerDefenceEdge(vertexMat[1+(i/(W-2))][1+(i%(W-2))],vertexMat[1+(i/(
		 * W-2))][(i%(W-2))]));
		 * g.addEdge(vertexMat[1+(i/(W-2))][(i%(W-2))],vertexMat[1+(i/(W-2))][1+
		 * (i%(W-2))] , new
		 * TowerDefenceEdge(vertexMat[1+(i/(W-2))][(i%(W-2))],vertexMat[1+(i/(W-
		 * 2))][1+(i%(W-2))])); //bottom
		 * g.addEdge(vertexMat[1+(i/(W-2))][1+(i%(W-2))],vertexMat[2+(i/(W-2))][
		 * 1+(i%(W-2))] , new
		 * TowerDefenceEdge(vertexMat[1+(i/(W-2))][1+(i%(W-2))],vertexMat[2+(i/(
		 * W-2))][1+(i%(W-2))])); g.addEdge(vertexMat[2+(i/(W-2))][1+(i%(W-2))],
		 * vertexMat[1+(i/(W-2))][1+(i%(W-2))], new
		 * TowerDefenceEdge(vertexMat[2+(i/(W-2))][1+(i%(W-2))],
		 * vertexMat[1+(i/(W-2))][1+(i%(W-2))])); //top
		 * g.addEdge(vertexMat[1+(i/(W-2))][1+(i%(W-2))],vertexMat[(i/(W-2))][1+
		 * (i%(W-2))] , new
		 * TowerDefenceEdge(vertexMat[1+(i/(W-2))][1+(i%(W-2))],vertexMat[(i/(W-
		 * 2))][1+(i%(W-2))])); g.addEdge(vertexMat[(i/(W-2))][1+(i%(W-2))]
		 * ,vertexMat[1+(i/(W-2))][1+(i%(W-2))], new
		 * TowerDefenceEdge(vertexMat[(i/(W-2))][1+(i%(W-2))]
		 * ,vertexMat[1+(i/(W-2))][1+(i%(W-2))])); }
		 */

		for (i = 0; i < (H) * (W); i++) {
			// right
			// if (i % W < (W - 1)) {
			if (i % W < W - 1 && i / W > 0) {
				if (vertexMat[(i / (W))][(i % (W))] != null && vertexMat[(i / (W))][(i % (W)) + 1] != null)
					g.addEdge(vertexMat[(i / (W))][(i % (W))], vertexMat[(i / (W))][(i % (W)) + 1],
							new WormEdge(vertexMat[(i / (W))][(i % (W))], vertexMat[(i / (W))][(i % (W)) + 1]));

				if (vertexMat[(i / (W))][(i % (W))] != null && vertexMat[(i / W) - 1][(i % W)] != null) {
					g.addEdge(vertexMat[(i / (W))][(i % (W))], vertexMat[(i / W) - 1][(i % W)],
							new WormEdge(vertexMat[(i / (W))][(i % (W))], vertexMat[(i / W) - 1][(i % W)]));
				}
			}

			// g.addEdge(vertexMat[(i / (W))][(i % (W)) + 1], vertexMat[(i /
			// (W))][(i % (W))],
			// new WormEdge(vertexMat[(i / (W))][(i % (W)) + 1],
			// vertexMat[(i / (W))][(i % (W))]));
			// System.out.println(new Point((i/W), ((i%W)))+" "+new
			// Point(((i/W)), (((i%W)+1))));
		}
		// left
		// if (i % W > 0) {
		// g.addEdge(vertexMat[(i / (W))][(i % (W)) - 1], vertexMat[(i /
		// (W))][(i % (W))],
		// new WormEdge(vertexMat[(i / (W))][(i % (W)) - 1], vertexMat[(i /
		// (W))][(i % (W))]));
		// g.addEdge(vertexMat[(i / (W))][(i % (W))], vertexMat[(i /
		// (W))][(i % (W)) - 1],
		// new WormEdge(vertexMat[(i / (W))][(i % (W))], vertexMat[(i /
		// (W))][(i % (W)) - 1]));
		// // System.out.println(new Point((i/W), ((i%W)))+" "+new
		// // Point(((i/W)), (((i%W)-1))));
		// }
		// // main
		// if ((i / W) < (H - 1) && (i % W) < (H - 1)) {
		// if (vertexMat[(i / (W))][(i % (W))] != null && vertexMat[(i / (W)) + 1][(i %
		// (W)) + 1] != null)
		// g.addEdge(vertexMat[(i / (W))][(i % (W))], vertexMat[(i / (W)) + 1][(i % (W))
		// + 1],
		// new WormEdge(vertexMat[(i / (W))][(i % (W))], vertexMat[(i / (W)) + 1][(i %
		// (W)) + 1]));
		// }
		//
		// // secondery
		// if ((i / W) < (H - 1) && (i % W) > 0 && (i % W) < H) {
		// if (vertexMat[(i / (W))][(i % (W))+(H-1)] != null && vertexMat[(i / (W)) +
		// 1][(i % (W)) - 1] != null)
		// g.addEdge(vertexMat[(i / (W))][(i % (W))], vertexMat[(i / (W)) + 1][(i % (W))
		// - 1],
		// new WormEdge(vertexMat[(i / (W))][(i % (W))], vertexMat[(i / (W)) + 1][(i %
		// (W)) - 1]));
		// }

		// g.addEdge(vertexMat[(i / (W)) + 1][(i % (W))], vertexMat[(i /
		// (W))][(i % (W))],
		// new WormEdge(vertexMat[(i / (W)) + 1][(i % (W))], vertexMat[(i /
		// (W))][(i % (W))]));
		// // System.out.println(new Point((i/W), ((i%W)))+" "+new
		// // Point(((i/W)+1), (((i%W)))));
		// }
		// // top
		// if (i / W > 0) {
		// g.addEdge(vertexMat[(i / (W)) - 1][(i % (W))], vertexMat[(i /
		// (W))][(i % (W))],
		// new WormEdge(vertexMat[(i / (W)) - 1][(i % (W))], vertexMat[(i /
		// (W))][(i % (W))]));
		// g.addEdge(vertexMat[(i / (W))][(i % (W))], vertexMat[(i / (W)) -
		// 1][(i % (W))],
		// new WormEdge(vertexMat[(i / (W))][(i % (W))], vertexMat[(i / (W))
		// - 1][(i % (W))]));
		// System.out.println(new Point((i/W), ((i%W)))+" "+new
		// Point(((i/W)-1), (((i%W)))));
		// }
	}

	// }

	public static DijkstraShortestPath findShortestPathLength(WormVertex ver) // calculating
																				// dijkstra
	{
		// System.out.println("x:"+_x+" y:"+_y);
		System.out.println("i: " + _y / 42 + " j: " + _x / 42);
		DijkstraShortestPath min;
		// System.out.println(g.containsVertex(ver));
		// System.out.println(g.containsVertex(vertexMat[(_y+1) / 42][_x /
		// 42]));
		// System.out.println(g.containsVertex(vertexMat[_y / 42][_x / 42]));
		min = new DijkstraShortestPath(g, ver, vertexMat[_y / 42][_x / 42]);
		// System.out.println("i: " + _y / 6 + " j: " + _x / 6);
		// min = new DijkstraShortestPath(g, ver, vertexMat[_y / 6][_x / 6]);
		System.out.println("path: " + min.getPathEdgeList().size());
		return min;

	}

	public static void goForward(Enemy enemy) {// giving an enemy the next
												// points to be at
		WormEdge edge;
		edge = WormEdge.class.cast(enemy.getPath().getPathEdgeList().remove(0));// first
		// edge
		// enemy._prevX = enemy.getX();
		// enemy._prevY = enemy.getY();
		enemy.setLocation(((int) edge.getDestVertx().getLocation().getX() * 42),
				(int) edge.getDestVertx().getLocation().getY() * 42);
		// enemy.setLocation(((int) edge.getDestVertx().getLocation().getX() * 6),
		// (int) edge.getDestVertx().getLocation().getY() * 6);
		// enemy.setLocation(((int) edge.getDestVertx().getLocation().getX() * 6),
		// (int) edge.getDestVertx().getLocation().getY() * 6);
	}

	public static void removeEdgesAllDirections(int xOffset, int yOffset)// removing
																			// edges
																			// when
																			// putting
																			// a
																			// tower
	{
		for (int i = 0; i < 4; i++) {
			if (xOffset + i % 2 < W - 1) {
				g.removeEdge(vertexMat[yOffset + (i / 2)][xOffset + (i % 2)],
						vertexMat[yOffset + (i / 2)][xOffset + (i % 2) + 1]);
				g.removeEdge(vertexMat[yOffset + (i / 2)][xOffset + (i % 2) + 1],
						vertexMat[yOffset + (i / 2)][xOffset + (i % 2)]);
				// System.out.println(new Point((xOffset+(i%4)),
				// ((yOffset+(i/4))))+" "+new Point(((xOffset+(i%4)+1)),
				// ((yOffset+(i/4)))));
			}
			if (xOffset + i % 2 > 0) {
				g.removeEdge(vertexMat[yOffset + (i / 2)][xOffset + (i % 2)],
						vertexMat[yOffset + (i / 2)][xOffset + (i % 2) - 1]);
				g.removeEdge(vertexMat[yOffset + (i / 2)][xOffset + (i % 2) - 1],
						vertexMat[yOffset + (i / 2)][xOffset + (i % 2)]);
				// System.out.println(new Point((xOffset+(i%4)),
				// ((yOffset+(i/4))))+" "+new Point(((xOffset+(i%4)-1)),
				// ((yOffset+(i/4)))));
			}
			if (yOffset + i / 2 > 0) {
				g.removeEdge(vertexMat[yOffset + (i / 2)][xOffset + (i % 2)],
						vertexMat[yOffset + (i / 2) - 1][xOffset + (i % 2)]);
				g.removeEdge(vertexMat[yOffset + (i / 2) - 1][xOffset + (i % 2)],
						vertexMat[yOffset + (i / 2)][xOffset + (i % 2)]);
				// System.out.println(new Point((xOffset+(i%4)),
				// ((yOffset+(i/4))))+" "+new Point(((xOffset+(i%4))),
				// ((yOffset+(i/4)-1))));
			}
			if (yOffset + i / 2 < H - 1) {
				g.removeEdge(vertexMat[yOffset + (i / 2)][xOffset + (i % 2)],
						vertexMat[yOffset + (i / 2) + 1][xOffset + (i % 2)]);
				g.removeEdge(vertexMat[yOffset + (i / 2) + 1][xOffset + (i % 2)],
						vertexMat[yOffset + (i / 2)][xOffset + (i % 2)]);
				// System.out.println(new Point((xOffset+(i%4)),
				// ((yOffset+(i/4))))+" "+new Point(((xOffset+(i%4))),
				// ((yOffset+(i/4)+1))));
			}
		}
	}
}
