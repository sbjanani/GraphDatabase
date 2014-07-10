package com.gdb.query;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * This class contains methods to operate the edges
 * @author
 *
 */

public class Edge extends Element {

	
	/**
	 * label of the head node
	 */
	int headNodeLabel;
	/**
	 * label of the tail node
	 */
	int tailNodeLabel;
	/**
	 * offset from the tail node to the head node
	 */
	int offset;
	/**
	 * id of the node from which this edge object was created.
	 */
	int tailNodeId;
	
	int headNodeId;
	
	
	Graph g;//graph in which the edge belongs to
	
	String dbPath;
	
	//go to the correct edge and initialize all of the fields
	public Edge(int nodeId,int edgeNumber,Graph graph, String path) throws IOException{
		dbPath = path;
		g = graph;
		this.tailNodeId = nodeId;
		id = edgeNumber;
		RandomAccessFile eFile = new RandomAccessFile(path+"edges.dat","rw");
		//seek to the point in the edges file where the edgeId information is stored
		eFile.seek(id*6);
		headNodeLabel = eFile.readByte();
		tailNodeLabel = eFile.readByte();
		offset = eFile.readInt();
		headNodeId = offset + tailNodeId;
		eFile.close();
	}
	
	/**
	 * @return the nodeId
	 */
	public int getNodeId() {
		return tailNodeId;
	}

	/**
	 * @param nodeId
	 *            the nodeId to set
	 */
	public void setNodeId(int nodeId) {
		this.tailNodeId = nodeId;
	}

	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * @return the headNodeType
	 */
	public int getHeadNodeLabel() {
		return headNodeLabel;
	}

	/**
	 * @param headNodeType
	 *            the headNodeType to set
	 */
	public void setHeadNodeLabel(int headNodeType) {
		this.headNodeLabel = headNodeType;
	}

	/**
	 * @return the tailNodeType
	 */
	public int getTailNodeLabel() {
		return tailNodeLabel;
	}

	/**
	 * @param tailNodeType
	 *            the tailNodeType to set
	 */
	public void setTailNodeLabel(int tailNodeType) {
		this.tailNodeLabel = tailNodeType;
	}

	/**
	 * This method returns the tail/out or head/in vertex. Throws
	 * IllegalArgumentException if BOTH is provided in Direction
	 * 
	 * @param direction
	 *            - tail/out or head/in vertex.
	 * @return - returns the tail/out or head/in vertex.
	 * @throws IOException 
	 */

	public Vertex getVertex(Direction direction) throws IllegalArgumentException, IOException{
		if(direction.equals(Direction.BOTH))
			throw new IllegalArgumentException("Direction cannot be BOTH");
		Vertex v = null;
		if(direction.equals(Direction.OUT))
			v = g.getVertex(tailNodeId);
		else
			v = g.getVertex(headNodeId);
		return v;
	}

	/**
	 * This method returns the tail/out or head/in vertex satisfying the given
	 * label. Throws IllegalArgumentException if BOTH is provided in Direction
	 *
	 * @param direction
	 *            - tail/out or head/in vertex.
	 * @param labelc
	 *            - the type of vertex to return
	 * @return - returns the tail/out or head/in vertex of the type Label.
	 * @throws IllegalArgumentException
	 * @throws IOException 
	 */
	public Vertex getVertex(Direction direction, int label)
			throws IllegalArgumentException, IOException {
		if(direction.equals(Direction.BOTH))
			throw new IllegalArgumentException("Direction cannot be BOTH");
		Vertex v = null;	
		if(direction.equals(Direction.OUT) && tailNodeLabel == label)
			v = g.getVertex(tailNodeId);
		else if(direction.equals(Direction.IN) && headNodeLabel == label)
			v = g.getVertex(headNodeId);
		else
			return null;
		return v;
	}
}
