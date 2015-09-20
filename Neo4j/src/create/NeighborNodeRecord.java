package create;

import java.util.HashMap;
import java.util.Map;

public class NeighborNodeRecord  {
	int neighborNode;
	byte edgeType;
	Map<String,Object> pmap = new HashMap<String,Object>();


	public NeighborNodeRecord(int nodeNumber, byte edgeType,Map<String,Object> pmap) {
		//neighborNode = n;
		this.neighborNode = nodeNumber;
		this.edgeType = edgeType;
		this.pmap=pmap;


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


	/**
	 * @return the pmap
	 */
	public Map<String, Object> getPmap() {
		return pmap;
	}


	/**
	 * @param pmap the pmap to set
	 */
	public void setPmap(Map<String, Object> pmap) {
		this.pmap = pmap;
	}




}