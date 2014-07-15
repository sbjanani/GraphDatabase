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

	/**
	 * id of the node from which this edge object was created.
	 */
	int tailNodeId;
	
	int headNodeId;
	
	
	
	//go to the correct edge and initialize all of the fields,nodeId is tail node
	public Edge(int nodeId,int edgeNumber,Graph g) throws IOException{
		graph = g;
		this.tailNodeId = nodeId;
		id = edgeNumber;
		RandomAccessFile eFile = new RandomAccessFile(g.dbPath+"edges.dat","r");
		//seek to the point in the edges file where the edgeId information is stored
		eFile.seek(id*Constants.EDGE_DAT_SIZE);
		label = eFile.readByte();
		eFile.readInt();
		headNodeId = eFile.readInt();
		tailNodeLabel = g.graphIndex.get(tailNodeId).getVertexType();
		headNodeLabel = g.graphIndex.get(headNodeId).getVertexType();
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
			v = graph.getVertex(tailNodeId);
		else
			v = graph.getVertex(headNodeId);
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
			v = graph.getVertex(tailNodeId);
		else if(direction.equals(Direction.IN) && headNodeLabel == label)
			v = graph.getVertex(headNodeId);
		else
			return null;
		return v;
	}
	
	public String toString(){
		return "Edge Id: "+id;
	}
}
