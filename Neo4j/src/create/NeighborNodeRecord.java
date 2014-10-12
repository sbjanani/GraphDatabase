package create;

public class NeighborNodeRecord  {
	public int neighborNode;
	public byte edgeType;

	
	public NeighborNodeRecord(int nodeNumber, byte edgeType) {
		//neighborNode = n;
		this.neighborNode = nodeNumber;
		this.edgeType = edgeType;
		
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
