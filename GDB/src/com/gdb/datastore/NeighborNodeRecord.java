package com.gdb.datastore;

public class NeighborNodeRecord  {
	public int neighborNode;
	//public byte edgeType;
	public int edgeNumber;
	
	public NeighborNodeRecord(int nodeNumber, int edgeNumber) {
		//neighborNode = n;
		neighborNode = nodeNumber;
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

	
	
	
}
