package com.gdb.datastore;

public class NeighborNodeRecord  {
	public int neighborNode;
	public byte edgeType;
	public int edgeNumber;
	
	public NeighborNodeRecord(int nodeNumber, byte edgeType, int edgeNumber) {
		//neighborNode = n;
		this.neighborNode = nodeNumber;
		this.edgeType = edgeType;
		this.edgeNumber = edgeNumber;
	}
	

	/**
	 * @return the neighborNode
	 */
	public int getNeighborNode() {
		return neighborNode;
	}

	/**
	 * @param neighborNode the neighborNode to set
	 */
	public void setNeighborNode(int neighborNode) {
		this.neighborNode = neighborNode;
	}

	/**
	 * @return the edgeNumber
	 */
	public int getEdgeNumber() {
		return edgeNumber;
	}

	/**
	 * @param edgeNumber the edgeNumber to set
	 */
	public void setEdgeNumber(int edgeNumber) {
		this.edgeNumber = edgeNumber;
	}


	/**
	 * @return the edgeType
	 */
	public byte getEdgeType() {
		return edgeType;
	}


	/**
	 * @param edgeType the edgeType to set
	 */
	public void setEdgeType(byte edgeType) {
		this.edgeType = edgeType;
	}

	
	
	
}
