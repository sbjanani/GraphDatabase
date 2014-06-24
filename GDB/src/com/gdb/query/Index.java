package com.gdb.query;

public class Index {

	int vertexId;
	int vertexType;
	long edgeBitmap;
	int[] edgeNums;
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
	public int getVertexType() {
		return vertexType;
	}
	/**
	 * @param vertexType the vertexType to set
	 */
	public void setVertexType(int vertexType) {
		this.vertexType = vertexType;
	}
	/**
	 * @return the edgeBitmap
	 */
	public long getEdgeBitmap() {
		return edgeBitmap;
	}
	/**
	 * @param edgeBitmap the edgeBitmap to set
	 */
	public void setEdgeBitmap(long edgeBitmap) {
		this.edgeBitmap = edgeBitmap;
	}
	/**
	 * @return the edgeNums
	 */
	public int[] getEdgeNums() {
		return edgeNums;
	}
	/**
	 * @param edgeNums the edgeNums to set
	 */
	public void setEdgeNums(int[] edgeNums) {
		this.edgeNums = edgeNums;
	}
	
	
}
