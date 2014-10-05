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
	Map<String,Short> edgeNums;
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
	 * @return the edgeNums
	 */
	public Map<String,Short> getEdgeNums() {
		return edgeNums;
	}
	/**
	 * @param edgeNums the edgeNums to set
	 */
	public void setEdgeNums(Map<String, Short> edgeNums) {
		this.edgeNums = edgeNums;
	}
	
	public String toString(){
		String s = "Vertex ID: "+vertexId+ " Vertex Type: "+vertexType+" BitMap: "+edgeBitmap+"\n";
		for(Map.Entry<String, Short> countEntry : edgeNums.entrySet()){
			s += countEntry.getKey()+":"+countEntry.getValue();
		}
		
		return s+"\n";
	}
	
	
}
