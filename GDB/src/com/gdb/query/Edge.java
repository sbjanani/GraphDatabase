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
	int id;
	
	
	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

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
	public int getHeadNodeLabel() {
		return headNodeLabel;
	}

	/**
	 * @param headNodeType the headNodeType to set
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
	 * @param tailNodeType the tailNodeType to set
	 */
	public void setTailNodeLabel(int tailNodeType) {
		this.tailNodeLabel = tailNodeType;
	}

	/**
	 * This method returns the tail/out or head/in vertex. 
	 * Throws IllegalArgumentException if BOTH is provided in Direction
	 * @param direction - tail/out or head/in vertex. 
	 * @return - returns the tail/out or head/in vertex. 
	 */
	public Vertex getVertex(Direction direction) throws IllegalArgumentException{
		
		return null;
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
