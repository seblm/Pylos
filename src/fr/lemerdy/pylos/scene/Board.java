/*
 * Created on 16 janv. 2005
 */
package fr.lemerdy.pylos.scene;

import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleFanArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

/**
 * @author banou
 */
public class Board extends Shape3D {
	
	public Board() {
		
		// Color of board.
		Color3f boardColor = new Color3f(.45f, .31f, .13f);
		
		// Define geometry.
		int[] stripVertexCounts = new int[2];
		stripVertexCounts[0] = 8;
		stripVertexCounts[1] = 8;
		TriangleFanArray board = new TriangleFanArray(16 ,TriangleFanArray.COORDINATES | TriangleFanArray.COLOR_3, stripVertexCounts);
		
		// Declares points. 
		Point3f topSouthEast = new Point3f( 0.5f,  0.5f, -0.46f);
		Point3f topSouthWest = new Point3f(-0.5f,  0.5f, -0.46f);
		Point3f topNorthWest = new Point3f(-0.5f, -0.5f, -0.46f);
		Point3f topNorthEast = new Point3f( 0.5f, -0.5f, -0.46f);
		
		Point3f bottomSouthEast = new Point3f( 0.6f,  0.6f, -0.5f);
		Point3f bottomSouthWest = new Point3f(-0.6f,  0.6f, -0.5f);
		Point3f bottomNorthWest = new Point3f(-0.6f, -0.6f, -0.5f);
		Point3f bottomNorthEast = new Point3f( 0.6f, -0.6f, -0.5f);
		
		// Link each point.
		board.setCoordinate(0 ,    topSouthEast);
		board.setCoordinate(1 ,    topSouthWest);
		board.setCoordinate(2 , bottomSouthWest);
		board.setCoordinate(3 , bottomSouthEast);
		board.setCoordinate(4 , bottomNorthEast);
		board.setCoordinate(5 ,    topNorthEast);
		board.setCoordinate(6 ,    topNorthWest);
		board.setCoordinate(7 ,    topSouthWest);
		board.setCoordinate(8 , bottomNorthWest);
		board.setCoordinate(9 ,    topSouthWest);
		board.setCoordinate(10, bottomSouthWest);
		board.setCoordinate(11, bottomSouthEast);
		board.setCoordinate(12, bottomNorthEast);
		board.setCoordinate(13,    topNorthEast);
		board.setCoordinate(14,    topNorthWest);
		board.setCoordinate(15,    topSouthWest);
		
		// Set color for all of vertices.
		for (int i = 0; i < 16; i++) {
			board.setColor(i, boardColor);
		}
		
		// Link the geometry to the current instance.
		this.addGeometry(board);
		
	}

}
