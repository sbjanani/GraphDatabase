package com.gdb.query;

import java.util.Map;

/**
 * This class holds the index object for a vertex
 * @author sysadmin
 *
 */
public class Index {

	/**
	 * The vertex id 
	 */
	int vertexId;
	/**
	 * The type of the vertex. 
	 */
	byte vertexType;
	/**
	 * The edge bitmap which specifies if there is an incoming/outgoing edge of each type
	 * 2 bits per edge type(1 for incoming, 1 for outgoing) for a total of 8 types = 16 bits = short
	 */
	short edgeBitmap;
	/**
	 * array holding the number of edges incident with the vertex of a given type
	 * The array length will be 8, one for each type 
	 */
	Map<Byte,Short> incomingEdgeNums;
	Map<Byte,Short> outgoingEdgeNums;
	/**
	 * @return the vertexId
	 */
	public int getVertexId() {
		return vertexId;
	}
	/**
	 * @param vertexId the vertexId to set
	 */
	public void setVertexId(int vertexId) {
		this.vertexId = vertexId;
	}
	/**
	 * @return the vertexType
	 */
	public byte getVertexType() {
		return vertexType;
	}
	/**
	 * @param vertexType the vertexType to set
	 */
	public void setVertexType(byte vertexType) {
		this.vertexType = vertexType;
	}
	/**
	 * @return the edgeBitmap
	 */
	public short getEdgeBitmap() {
		return edgeBitmap;
	}
	/**
	 * @param edgeBitmap the edgeBitmap to set
	 */
	public void setEdgeBitmap(short edgeBitmap) {
		this.edgeBitmap = edgeBitmap;
	}

	
	/**
	 * @return the incomingEdgeNums
	 */
	public Map<Byte, Short> getIncomingEdgeNums() {
		return incomingEdgeNums;
	}
	/**
	 * @param incomingEdgeNums the incomingEdgeNums to set
	 */
	public void setIncomingEdgeNums(Map<Byte, Short> incomingEdgeNums) {
		this.incomingEdgeNums = incomingEdgeNums;
	}
	/**
	 * @return the outgingEdgeNums
	 */
	public Map<Byte, Short> getOutgoingEdgeNums() {
		return outgoingEdgeNums;
	}
	/**
	 * @param outgingEdgeNums the outgingEdgeNums to set
	 */
	public void setOutgoingEdgeNums(Map<Byte, Short> outgoingEdgeNums) {
		this.outgoingEdgeNums = outgoingEdgeNums;
	}
	
	public String toString(){
		String s = "Vertex ID: "+vertexId+ " Vertex Type: "+vertexType+" BitMap: "+edgeBitmap+"\n";
		s += "\n incoming \n";
		for(Map.Entry<Byte, Short> countEntry : incomingEdgeNums.entrySet()){
			s += countEntry.getKey()+":"+countEntry.getValue()+";";
		}
		s += "\n outgoing \n";
		for(Map.Entry<Byte, Short> countEntry : outgoingEdgeNums.entrySet()){
			s += countEntry.getKey()+":"+countEntry.getValue()+";";
		}
		
		return s+"\n";
	}
	
	
}
