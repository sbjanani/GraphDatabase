package com.gdb.datastore;

public class NeighborNodeRecord {
	public int neighborNode;
	public byte edgeType;
	
	public NeighborNodeRecord(int n, byte b) {
		neighborNode = n;
		edgeType = b;
	}
	
	public int getNeighborNode() {
		return neighborNode;
	}
	
	public void setNeighborNode(int neighborNode) {
		this.neighborNode = neighborNode;
	}
	
	public byte getEdgeType() {
		return edgeType;
	}
	
	public void setEdgeType(byte edgeType) {
		this.edgeType = edgeType;
	}
	
	
}
