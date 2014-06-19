package com.gdb.datastore;

import java.util.ArrayList;

public class AdjacencyRecord {
	ArrayList<NeighborNodeRecord> inComing;
	ArrayList<NeighborNodeRecord> outGoing;
	byte inComingEdgeBitMap;
	byte outGoingEdgeBitMap;
	
	
	
	public AdjacencyRecord() {
		inComing = new ArrayList<NeighborNodeRecord>();
		outGoing = new ArrayList<NeighborNodeRecord>();
		inComingEdgeBitMap = 0;
		outGoingEdgeBitMap = 0;
	}
	
	public short getIncomingCount(byte edgeType){
		short result = 0;
		for(int i = 0; i < inComing.size(); i++){
			if(inComing.get(i).edgeType == edgeType)
				result++;
		}
		return result;
	}
	
	public short getOutGoingCount(byte edgeType){
		short result = 0;
		for(int i = 0; i < outGoing.size(); i++){
			if(outGoing.get(i).edgeType == edgeType)
				result++;
		}
		return result;
	}
	
	public void addIncoming(int value, byte edgeType){
		NeighborNodeRecord nr = new NeighborNodeRecord(value,edgeType);
		inComing.add(nr);
	}
	
	public void addOutGoing(int value,byte edgeType){
		NeighborNodeRecord nr = new NeighborNodeRecord(value,edgeType);
		outGoing.add(nr);
	}
	
	public void setInComingBit(int index){
		inComingEdgeBitMap = (byte) (inComingEdgeBitMap | (byte)(Math.pow(2,index)));
	}
	
	public void setOutGoingBit(int index){
		outGoingEdgeBitMap = (byte) (outGoingEdgeBitMap | (byte)(Math.pow(2,index)));
	}

	public ArrayList<NeighborNodeRecord> getInComing() {
		return inComing;
	}
	
	public void setInComing(ArrayList<NeighborNodeRecord> inComing) {
		this.inComing = inComing;
	}
	
	public ArrayList<NeighborNodeRecord> getOutGoing() {
		return outGoing;
	}
	
	public void setOutGoing(ArrayList<NeighborNodeRecord> outGoing) {
		this.outGoing = outGoing;
	}
	
	public byte getInComingEdgeBitMap() {
		return inComingEdgeBitMap;
	}
	
	public void setInComingEdgeBitMap(byte edgeBitMap) {
		this.inComingEdgeBitMap = edgeBitMap;
	}
	
	public byte getOutGoingEdgeBitMap() {
		return outGoingEdgeBitMap;
	}
	
	public void setOutGoingEdgeBitMap(byte edgeBitMap) {
		this.outGoingEdgeBitMap = edgeBitMap;
	}
	
	public String toString(){
		return "incoming = "+inComing + "\n" + "outoing= "+outGoing + "\n\n";
	}
	
	
}
