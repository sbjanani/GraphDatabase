package com.gdb.datastore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.gdb.util.Constants;

public class AdjacencyRecord {
	Map<Byte,ArrayList<NeighborNodeRecord>> inComing;
	Map<Byte,ArrayList<NeighborNodeRecord>> outGoing;
	short[] inComingCount;
	short[] outGoingCount;
	byte inComingEdgeBitMap;
	byte outGoingEdgeBitMap;
	
	
	
	public AdjacencyRecord() {
		inComing = new HashMap<Byte,ArrayList<NeighborNodeRecord>>();
		outGoing = new HashMap<Byte,ArrayList<NeighborNodeRecord>>();
		inComingCount = new short[Constants.NUMBER_OF_EDGE_TYPES];
		outGoingCount = new short[Constants.NUMBER_OF_EDGE_TYPES];
		inComingEdgeBitMap = 0;
		outGoingEdgeBitMap = 0;
	}
	
	public short getIncomingCount(byte edgeType){

		
		return (short) inComing.get(edgeType).size();
			
	}
	
	public short getOutGoingCount(byte edgeType){
		return (short) outGoing.get(edgeType).size();
	}
	
	public void addIncoming(int value, byte edgeType, int edgeNumber){
		
		NeighborNodeRecord nr = new NeighborNodeRecord(value,edgeType,edgeNumber);
		
		if(inComing.containsKey(edgeType)){
			inComing.get(edgeType).add(nr);
			
		}
		else{
			ArrayList<NeighborNodeRecord> neighborList = new ArrayList<NeighborNodeRecord>();
			neighborList.add(nr);	
			inComing.put(edgeType, neighborList);
		}
		
		
		inComingCount[edgeType]++;
	}
	
	public void addOutGoing(int value,byte edgeType, int edgeNumber){
		NeighborNodeRecord nr = new NeighborNodeRecord(value,edgeType,edgeNumber);
		
		if(outGoing.containsKey(edgeType)){
			outGoing.get(edgeType).add(nr);
		}
		else{
			ArrayList<NeighborNodeRecord> neighborList = new ArrayList<NeighborNodeRecord>();
			neighborList.add(nr);	
			outGoing.put(edgeType, neighborList);
		}
		
		
		outGoingCount[edgeType]++;
	}
	
	public void setInComingBit(int index){
		inComingEdgeBitMap = (byte) (inComingEdgeBitMap | (byte)(Math.pow(2,index)));
	}
	
	public void setOutGoingBit(int index){
		outGoingEdgeBitMap = (byte) (outGoingEdgeBitMap | (byte)(Math.pow(2,index)));
	}

	public Map<Byte, ArrayList<NeighborNodeRecord>> getInComing() {
		return inComing;
	}
	
	public void setInComing(Map<Byte, ArrayList<NeighborNodeRecord>> inComing) {
		this.inComing = inComing;
	}
	
	public Map<Byte, ArrayList<NeighborNodeRecord>> getOutGoing() {
		return outGoing;
	}
	
	public void setOutGoing(Map<Byte, ArrayList<NeighborNodeRecord>> outGoing) {
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
	
	/**
	 * @return the inComingCount
	 */
	public short[] getInComingCount() {
		return inComingCount;
	}

	/**
	 * @param inComingCount the inComingCount to set
	 */
	public void setInComingCount(short[] inComingCount) {
		this.inComingCount = inComingCount;
	}

	/**
	 * @return the outGoingCount
	 */
	public short[] getOutGoingCount() {
		return outGoingCount;
	}

	/**
	 * @param outGoingCount the outGoingCount to set
	 */
	public void setOutGoingCount(short[] outGoingCount) {
		this.outGoingCount = outGoingCount;
	}
	
	
}
