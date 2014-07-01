package com.gdb.query;

/**
 * This class contains methods to operate the edges
 * @author 
 *
 */
/**
 * @author sysadmin
 *
 */
public class Edge {

	String label;
	int headNodeLabel;
	int tailNodeLabel;
	int offset;
	
	
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label- the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	
	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	

	/**
	 * @return the headNodeType
	 */
	public int getHeadNodeType() {
		return headNodeLabel;
	}

	/**
	 * @param headNodeType the headNodeType to set
	 */
	public void setHeadNodeType(int headNodeType) {
		this.headNodeLabel = headNodeType;
	}

	/**
	 * @return the tailNodeType
	 */
	public int getTailNodeType() {
		return tailNodeLabel;
	}

	/**
	 * @param tailNodeType the tailNodeType to set
	 */
	public void setTailNodeType(int tailNodeType) {
		this.tailNodeLabel = tailNodeType;
	}

	/**
	 * This method returns the tail/out or head/in vertex. 
	 * Throws IllegalArgumentException if BOTH is provided in Direction
	 * @param direction - tail/out or head/in vertex. 
	 * @return - returns the tail/out or head/in vertex. 
	 */
	public Vertex getVertex(Direction direction) throws IllegalArgumentException{
		if(direction.equals(Direction.BOTH))
			throw new IllegalArgumentException("Direction cannot be BOTH");
		Vertex v = new Vertex();
		if(direction.equals(Direction.OUT))
			v.setId(tailNodeLabel);
		else
			v.setId(headNodeLabel);
		return v;
	}
	
	/**
	 * This method returns the tail/out or head/in vertex satisfying the given label. 
	 * Throws IllegalArgumentException if BOTH is provided in Direction
	 *@param direction - tail/out or head/in vertex. 
	 * @param label - the type of vertex to return
	 * @return - returns the tail/out or head/in vertex of the type Label. 
	 * @throws IllegalArgumentException
	 */
	public Vertex getVertex(Direction direction, String label) throws IllegalArgumentException{
		
		return null;
	}
}
