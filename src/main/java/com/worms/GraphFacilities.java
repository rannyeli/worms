package com.worms;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleGraph;

public class GraphFacilities {
	public static final int W = GameConfig.NUM_COLS, H = GameConfig.NUM_ROWS;
	public static WormVertex[][] vertexMat = new WormVertex[H][W];
	public static Graph<WormVertex, WormEdge> g = new SimpleGraph<>(WormEdge.class);
	public static int _x = 700, _y = 50;

	public static void CreateGraph() {
		int i;
		for (i = 0; i < H * W; i++) {
			vertexMat[i / W][i % W] = new WormVertex(i % W, i / W);
			g.addVertex(vertexMat[i / W][i % W]);
		}

		for (i = 0; i < H * W; i++) {
			if (i % W < W - 1 && i / W > 0) {
				if (vertexMat[i / W][i % W] != null && vertexMat[i / W][(i % W) + 1] != null)
					g.addEdge(vertexMat[i / W][i % W], vertexMat[i / W][(i % W) + 1],
							new WormEdge(vertexMat[i / W][i % W], vertexMat[i / W][(i % W) + 1]));

				if (vertexMat[i / W][i % W] != null && vertexMat[(i / W) - 1][i % W] != null) {
					g.addEdge(vertexMat[i / W][i % W], vertexMat[(i / W) - 1][i % W],
							new WormEdge(vertexMat[i / W][i % W], vertexMat[(i / W) - 1][i % W]));
				}
			}
		}
	}

	public static List<WormEdge> findShortestPathLength(WormVertex ver) {
		System.out.println("i: " + _y / GameConfig.BLOCK_SIZE + " j: " + _x / GameConfig.BLOCK_SIZE);
		GraphPath<WormVertex, WormEdge> path =
				DijkstraShortestPath.findPathBetween(g, ver, vertexMat[_y / GameConfig.BLOCK_SIZE][_x / GameConfig.BLOCK_SIZE]);
		List<WormEdge> edgeList = new ArrayList<>(path.getEdgeList());
		System.out.println("path: " + edgeList.size());
		return edgeList;
	}

	public static void goForward(Enemy enemy) {
		WormEdge edge = enemy.getPath().remove(0);
		enemy.setLocation(
				(int) edge.getDestVertx().getLocation().getX() * GameConfig.BLOCK_SIZE,
				(int) edge.getDestVertx().getLocation().getY() * GameConfig.BLOCK_SIZE);
	}

	public static void removeEdgesAllDirections(int xOffset, int yOffset) {
		for (int i = 0; i < 4; i++) {
			if (xOffset + i % 2 < W - 1) {
				g.removeEdge(vertexMat[yOffset + (i / 2)][xOffset + (i % 2)],
						vertexMat[yOffset + (i / 2)][xOffset + (i % 2) + 1]);
				g.removeEdge(vertexMat[yOffset + (i / 2)][xOffset + (i % 2) + 1],
						vertexMat[yOffset + (i / 2)][xOffset + (i % 2)]);
			}
			if (xOffset + i % 2 > 0) {
				g.removeEdge(vertexMat[yOffset + (i / 2)][xOffset + (i % 2)],
						vertexMat[yOffset + (i / 2)][xOffset + (i % 2) - 1]);
				g.removeEdge(vertexMat[yOffset + (i / 2)][xOffset + (i % 2) - 1],
						vertexMat[yOffset + (i / 2)][xOffset + (i % 2)]);
			}
			if (yOffset + i / 2 > 0) {
				g.removeEdge(vertexMat[yOffset + (i / 2)][xOffset + (i % 2)],
						vertexMat[yOffset + (i / 2) - 1][xOffset + (i % 2)]);
				g.removeEdge(vertexMat[yOffset + (i / 2) - 1][xOffset + (i % 2)],
						vertexMat[yOffset + (i / 2)][xOffset + (i % 2)]);
			}
			if (yOffset + i / 2 < H - 1) {
				g.removeEdge(vertexMat[yOffset + (i / 2)][xOffset + (i % 2)],
						vertexMat[yOffset + (i / 2) + 1][xOffset + (i % 2)]);
				g.removeEdge(vertexMat[yOffset + (i / 2) + 1][xOffset + (i % 2)],
						vertexMat[yOffset + (i / 2)][xOffset + (i % 2)]);
			}
		}
	}
}
