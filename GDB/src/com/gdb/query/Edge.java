package com.gdb.query;

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
	int nodeId;

	

	/**
	 * @return the nodeId
	 */
	public int getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId
	 *            the nodeId to set
	 */
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
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
	 */

	public Vertex getVertex(Direction direction) throws IllegalArgumentException{
		/*if(direction.equals(Direction.BOTH))
			throw new IllegalArgumentException("Direction cannot be BOTH");
		Vertex v = new Vertex();
		if(direction.equals(Direction.OUT))
			v.setId(tailNodeLabel);
		else
			v.setId(headNodeLabel);*/
		return null;

	

	}

	/**
	 * This method returns the tail/out or head/in vertex satisfying the given
	 * label. Throws IllegalArgumentException if BOTH is provided in Direction
	 *
	 * @param direction
	 *            - tail/out or head/in vertex.
	 * @param label
	 *            - the type of vertex to return
	 * @return - returns the tail/out or head/in vertex of the type Label.
	 * @throws IllegalArgumentException
	 */
	public Vertex getVertex(Direction direction, int label)
			throws IllegalArgumentException {

		return null;
	}
}
