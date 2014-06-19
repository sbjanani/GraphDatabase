package com.gdb.datastore;

public class NeighborNodeRecord implements Comparable {
	public int neighborNode;
	public byte edgeType;
	public int edgeNumber;
	
	public NeighborNodeRecord(int n, byte b) {
		neighborNode = n;
		edgeType = b;
		edgeNumber = -1;
	}
	
	public int getNeighborNode() {
		return neighborNode;
	}
	
	public void setNeighborNode(int neighborNode) {
		this.neighborNode = neighborNode;
	}
	//change mades
	public byte getEdgeType() {
		return edgeType;
	}
	
	public void setEdgeType(byte edgeType) {
		this.edgeType = edgeType;
	}

	public int compareTo(Object arg0) {
		NeighborNodeRecord n = (NeighborNodeRecord)(arg0);
		return this.neighborNode - n.neighborNode;
	}
	
	public String toString(){
		return "neighborNode = "+neighborNode + "edgeType= "+edgeType + "edgeNumber = "+edgeNumber;
	}
	
	
}
