package com.gdb.query;

import java.util.List;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import com.gdb.util.Constants;

/**
 * This class holds the methods related to a vertex.
 * 
 * @author sysadmin
 *
 */
public class Vertex extends Element {

	short numIncoming;
	short numOutgoing;
	Map<Byte, Short> incomingEdgeCount;
	Map<Byte, Short> outgoingEdgeCount;

	public Vertex(int id, Graph graph, int label) {

		this.id = id;
		this.graph = graph;
		this.label = label;
		this.incomingEdgeCount = this.graph.getGraphIndex().get(this.id)
				.getIncomingEdgeNums();
		this.outgoingEdgeCount = this.graph.getGraphIndex().get(this.id)
				.getOutgoingEdgeNums();

	}

	/**
	 * This method returns the edges connected to a vertex of a given direction
	 * and label
	 * 
	 * @param direction
	 *            - IN/OUT/BOTH edge
	 * @param labels
	 *            - label for the edge
	 * @return - returns an ArrayList containing the Edge objects
	 * @throws IOException
	 */
	public ArrayList<Edge> getEdges(Direction direction, ArrayList<Byte>labelList)
			throws IOException {
		
		Collections.sort(labelList);
		
		ArrayList<Edge> edgeList = new ArrayList<Edge>();
		ArrayList<Byte> labels = new ArrayList<Byte>();
		
		
		
		
			
			int edgeTypeOffset;
			
			ArrayList<Integer> offsetList = new ArrayList<Integer>();			
			ArrayList<Map<Byte, Short>> neighborCountList = new ArrayList<Map<Byte,Short>>(); 

			if(direction.equals(Direction.IN)){
				
				for(int i=0; i<labelList.size();i++){
					if(this.getIncomingEdgeCount().containsKey(labelList.get(i))){
						labels.add(labelList.get(i));
					}
				}
				
				if(labels.size()==0)
					return null;
		
				neighborCountList.add(this.getIncomingEdgeCount());
						
				edgeTypeOffset=this.getNumOutgoing();
				
				for(Map.Entry<Byte, Short> incomingcount : this.getIncomingEdgeCount().entrySet()){
					if(labelList.contains(incomingcount.getKey())){
						offsetList.add(edgeTypeOffset);
						edgeTypeOffset += incomingcount.getValue();
						offsetList.add(edgeTypeOffset);
					}
					else
						edgeTypeOffset += incomingcount.getValue();
					
					
				}
				
				edgeList = getNeighborEdges(offsetList, neighborCountList,labels, direction);
			}
			else if(direction.equals(Direction.OUT)){
				
				for(int i=0; i<labelList.size();i++){
					if(this.getOutgoingEdgeCount().containsKey(labelList.get(i))){
						labels.add(labelList.get(i));
					}
				}
				
				
				if(labels.size()==0)
					return null;
				neighborCountList.add(this.getOutgoingEdgeCount());				
				
				edgeTypeOffset=0;
				
				for(Map.Entry<Byte, Short> outgoingcount : this.getOutgoingEdgeCount().entrySet()){
					if(labelList.contains(outgoingcount.getKey())){
						offsetList.add(edgeTypeOffset);
						edgeTypeOffset += outgoingcount.getValue();
						offsetList.add(edgeTypeOffset);
					}
					else
						edgeTypeOffset += outgoingcount.getValue();
					
					
				}
				
				edgeList = getNeighborEdges(offsetList, neighborCountList,labels, direction);
			}
			else{
				
					for(int i=0; i<labelList.size();i++){
						if(this.getIncomingEdgeCount().containsKey(labelList.get(i))){
							labels.add(labelList.get(i));
						}
					}
					for(int i=0; i<labelList.size();i++){
						if(this.getOutgoingEdgeCount().containsKey(labelList.get(i))){
							labels.add(labelList.get(i));
						}
					}
					
					
					if(labels.size()==0)
						return null;
					
					neighborCountList.add(this.getOutgoingEdgeCount());
					neighborCountList.add(this.getIncomingEdgeCount());
					
					edgeTypeOffset=0;
					
					for(Map.Entry<Byte, Short> outgoingcount : this.getOutgoingEdgeCount().entrySet()){
						if(labelList.contains(outgoingcount.getKey())){
							offsetList.add(edgeTypeOffset);
							edgeTypeOffset += outgoingcount.getValue();
							offsetList.add(edgeTypeOffset);
						}
						else
							edgeTypeOffset += outgoingcount.getValue();
						
						
					}
				
				
					edgeTypeOffset=this.getNumOutgoing();
					for(Map.Entry<Byte, Short> incomingcount : this.getIncomingEdgeCount().entrySet()){
						if(labelList.contains(incomingcount.getKey())){
							offsetList.add(edgeTypeOffset);
							edgeTypeOffset += incomingcount.getValue();
							offsetList.add(edgeTypeOffset);
						}
						else
							edgeTypeOffset += incomingcount.getValue();
						
						
					}
							
					edgeList = getNeighborEdges(offsetList, neighborCountList,labels, direction);
			}
			
							
			
		//System.out.println("Vertex : "+this.getId()+" Neighborhood :"+edgeList);
			
		
		
		return edgeList;
	}

	/**
	 * This method returns all the edges of a given direction connected to a
	 * vertex
	 * 
	 * @param direction
	 *            - IN/OUT/BOTH
	 * @return - returns an ArrayList containing the Edge objects
	 * @throws IOException
	 */
	public ArrayList<Edge> getEdges(Direction direction) throws IOException {
		// dbPath = path;
		ArrayList<Edge> result;
		short offset;
		int totalNeighbors;
		ArrayList<Map<Byte, Short>> neighborCountList = new ArrayList<Map<Byte,Short>>(); 

		if(direction.equals(Direction.IN)){
			neighborCountList.add(this.getIncomingEdgeCount());
			offset = this.getNumOutgoing();			
			totalNeighbors = this.getNumIncoming();
		}
		else if(direction.equals(Direction.OUT)){
			neighborCountList.add(this.getOutgoingEdgeCount());
			offset = 0;
			totalNeighbors = this.getNumOutgoing();
		}
		else{
			neighborCountList.add(this.getOutgoingEdgeCount());
			neighborCountList.add(this.getIncomingEdgeCount());
			offset=0;
			totalNeighbors = this.getNumIncoming()+this.getNumOutgoing();
			
		}
			
		result = getNeighborEdges(neighborCountList, offset, totalNeighbors,direction);
		

		
		return result;
	}

	/**
	 * This method returns all the edges of a given direction, label and
	 * attributes connected to a vertex
	 * 
	 * @param direction
	 *            - IN/OUT/BOTH
	 * @param attributeMap
	 *            - Map containing attribute key-value pairs
	 * @param labels
	 *            - label for the edge
	 * @return- returns an ArrayList containing the Edge objects
	 */
	public ArrayList<Edge> getEdges(Direction direction,
			Map<String, Object> attributeMap, String... labels) {

		return null;
	}

	public ArrayList<Edge> getNeighborEdges(ArrayList<Map<Byte,Short>> neighborCountList, int offset, int totalNeighbors, Direction direction) {

		int nodeOffset = this.id*Constants.MAX_EDGES_NODES_DAT*4;
		ArrayList<Edge> neighborList = new ArrayList<Edge>();
		
	
		try {
			

			RandomAccessFile dataFile;

			ArrayList<byte[]> edgeList = new ArrayList<byte[]>();
			double maxFiles = (double)(offset+totalNeighbors)/(double)Constants.MAX_EDGES_NODES_DAT;
			
			for (int i = offset / Constants.MAX_EDGES_NODES_DAT; i < Math.ceil(maxFiles); i++) {

				byte[] edges = new byte[Constants.MAX_EDGES_NODES_DAT * 4];
				dataFile = new RandomAccessFile(graph.dbPath + "nodefile" + i
						+ ".dat", "r");
				dataFile.seek(nodeOffset);
				dataFile.readFully(edges);
				dataFile.close();
				edgeList.add(edges);

			}
			int edgeCount = offset % Constants.MAX_EDGES_NODES_DAT;
			int index=edgeCount*4;
			if(direction.equals(Direction.BOTH))
			 direction = Direction.OUT;
			
			for(Map<Byte,Short> neighborCount : neighborCountList){
				
			for (byte edgeType = 0; edgeType < Constants.NUMBER_OF_EDGE_TYPES; edgeType++) {
				if (neighborCount.containsKey(edgeType)) {
					for (short i = 0; i < neighborCount.get(edgeType); i++) {
											
						int arrayCounter = edgeCount/ Constants.MAX_EDGES_NODES_DAT;
						//System.out.println("edgeCount = "+edgeCount);
						//System.out.println("arraycounter = "+arrayCounter);
						
						
						
						int neighbor1 = (edgeList.get(arrayCounter)[index++] << 24)& 0xFFFFFFFF;
						int neighbor2 = (edgeList.get(arrayCounter)[index++] << 16)& 0x00FFFFFF;
						int neighbor3 = (edgeList.get(arrayCounter)[index++] << 8) & 0x0000FFFF;
						int neighbor4 = (edgeList.get(arrayCounter)[index++]& 0x000000FF );
						//System.out.println("offset = "+nodeOffset);
						//System.out.println(neighbor1 +" "+neighbor2+" "+Integer.toBinaryString(neighbor3)+" "+Integer.toBinaryString(neighbor4));
						int neighbor = neighbor1 | neighbor2 | neighbor3 | neighbor4;
								
						//System.out.println("neighbor="+neighbor);

						Edge edge = new Edge(edgeType,this.graph.getVertex(neighbor),direction);
						neighborList.add(edge);
						
						edgeCount++;
						if(edgeCount%Constants.MAX_EDGES_NODES_DAT==0)
							  index = 0;
					}
				}
			}
			direction = Direction.IN;
		}
			//System.out.println("Edge Count from map = "+edgeCount);
			
		}catch(EOFException e){
			System.out.println("Exception while reading neighbor of vertex "+this.getId());
			e.printStackTrace();
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return neighborList;
	}
	
	public ArrayList<Edge> getNeighborEdges( ArrayList<Integer> offsetList , ArrayList<Map<Byte,Short>> neighborCountList, ArrayList<Byte> labelList, Direction direction) {
		

		int nodeOffset = this.id*Constants.MAX_EDGES_NODES_DAT*4;
		ArrayList<Edge> neighborList = new ArrayList<Edge>();
		Direction dir = direction;
		
		
		try {
			

			RandomAccessFile dataFile;

			
			
			
			for(int offset=0; offset<offsetList.size(); offset +=2){
				
				ArrayList<byte[]> edgeList = new ArrayList<byte[]>();
				if(direction.equals(Direction.BOTH)){
					if(offset<labelList.size())
						dir=Direction.OUT;
					else
						dir = Direction.IN;
				}
				double maxFiles = (double)offsetList.get(offset+1)/(double)Constants.MAX_EDGES_NODES_DAT;
				for (int i = offsetList.get(offset) / Constants.MAX_EDGES_NODES_DAT; i < Math.ceil(maxFiles); i++) {

					byte[] edges = new byte[Constants.MAX_EDGES_NODES_DAT * 4];
					dataFile = new RandomAccessFile(graph.dbPath + "nodefile" + i
							+ ".dat", "r");
					dataFile.seek(nodeOffset);
					dataFile.readFully(edges);
					dataFile.close();					
					
					edgeList.add(edges);

				}
				
				byte edgeLabel = labelList.get((offset/labelList.size())%labelList.size());
				int edgeCount = offsetList.get(offset) % Constants.MAX_EDGES_NODES_DAT;
				int index=edgeCount*4;
				Map<Byte,Short> neighborCount = neighborCountList.get(offset/(2*labelList.size()));
				for(short j=0; j<neighborCount.get(edgeLabel);j++){
					int arrayCounter = edgeCount/ Constants.MAX_EDGES_NODES_DAT;
					int neighbor1 = (edgeList.get(arrayCounter)[index++] << 24)& 0xFFFFFFFF;
					int neighbor2 = (edgeList.get(arrayCounter)[index++] << 16)& 0x00FFFFFF;
					int neighbor3 = (edgeList.get(arrayCounter)[index++] << 8) & 0x0000FFFF;
					int neighbor4 = (edgeList.get(arrayCounter)[index++]& 0x000000FF );
					
					int neighbor = neighbor1 | neighbor2 | neighbor3 | neighbor4;
					
					Edge edge = new Edge(edgeLabel,this.graph.getVertex(neighbor),dir);
					neighborList.add(edge);
					
					edgeCount++;
					if(edgeCount%Constants.MAX_EDGES_NODES_DAT==0)
						  index = 0;
				}
				
			}		
			
			
			//System.out.println("Edge Count from map = "+edgeCount);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return neighborList;
	}

	/**
	 * @return the numIncoming
	 */
	public short getNumIncoming() {
		numIncoming = 0;
		Map<Byte, Short> neighborCount = this.getIncomingEdgeCount();
		for (Map.Entry<Byte, Short> neighborCountEntry : neighborCount
				.entrySet()) {
			numIncoming += neighborCountEntry.getValue();
		}
		return numIncoming;
	}

	/**
	 * @param numIncoming
	 *            the numIncoming to set
	 */
	public void setNumIncoming(short numIncoming) {
		this.numIncoming = numIncoming;
	}

	/**
	 * @return the numOutgoing
	 */
	public short getNumOutgoing() {
		numOutgoing = 0;
		Map<Byte, Short> neighborCount = this.getOutgoingEdgeCount();
		for (Map.Entry<Byte, Short> neighborCountEntry : neighborCount
				.entrySet()) {
			numOutgoing += neighborCountEntry.getValue();
		}
		return numOutgoing;
	}

	/**
	 * @param numOutgoing
	 *            the numOutgoing to set
	 */
	public void setNumOutgoing(short numOutgoing) {
		this.numOutgoing = numOutgoing;
	}

	/**
	 * @return the incomingEdgeCount
	 */
	public Map<Byte, Short> getIncomingEdgeCount() {
		return incomingEdgeCount;
	}

	/**
	 * @param incomingEdgeCount
	 *            the incomingEdgeCount to set
	 */
	public void setIncomingEdgeCount(Map<Byte, Short> incomingEdgeCount) {
		this.incomingEdgeCount = incomingEdgeCount;
	}

	/**
	 * @return the outgoingEdgeCount
	 */
	public Map<Byte, Short> getOutgoingEdgeCount() {
		return outgoingEdgeCount;
	}

	/**
	 * @param outgoingEdgeCount
	 *            the outgoingEdgeCount to set
	 */
	public void setOutgoingEdgeCount(Map<Byte, Short> outgoingEdgeCount) {
		this.outgoingEdgeCount = outgoingEdgeCount;
	}

	public String toString() {

		return "\n Id: " + this.getId() + " label: " + this.getLabel() + "\n";
	}

	public boolean equals(Object object) {

		return this.id == ((Vertex) object).id;
	}

}
