package com.gdb.query;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * This class contains methods to operate the edges
 * @author
 *
 */

/**
 * @author sysadmin
 *
 */
public class Edge extends Element {

	Vertex vertex;
	Direction direction;
	
	
	
	//go to the correct edge and initialize all of the fields,nodeId is tail node
	public Edge(byte label, Vertex vertex, Direction direction) throws IOException{
		
		this.label= label;
		this.vertex = vertex;
		this.direction = direction;
	}
	
	/**
	 * @return the nodeId
	 */
	public Vertex getVertex() {
		return vertex;
	}

	/**
	 * @param nodeId
	 *            the nodeId to set
	 */
	public void setVertex(Vertex vertex) {
		this.vertex = vertex;
	}

	

	public String toString(){
		return "\n Direction: "+direction+" Vertex: "+vertex.getId()+"\n";
	}
}
