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
	 * 0 indicates active, 1 indicates deleted
	 */
	byte deleted;
	/**
	 * array holding the number of edges incident with the vertex of a given type
	 * The array length will be 8, one for each type 
	 */
	Map<Byte,Short> incomingEdgeNums;
	Map<Byte,Short> outgoingEdgeNums;

	byte propBitmap;
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
	 * @return the deleted
	 */
	public byte getDeleted() {
		return deleted;
	}
	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(byte deleted) {
		this.deleted = deleted;
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

	/**
	 * @return the propBitmap
	 */
	public byte getPropBitmap() {
		return propBitmap;
	}
	/**
	 * @param propBitmap the propBitmap to set
	 */
	public void setPropBitmap(byte propBitmap) {
		this.propBitmap = propBitmap;
	}
	public String toString(){
		String s = "Vertex ID: "+vertexId+ " Vertex Type: "+vertexType+"\n";
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
