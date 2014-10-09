package com.gdb.query;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
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
	public ArrayList<Edge> getEdges(Direction direction, String labels)
			throws IOException {

		return null;
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

		// set the file pointer to the beginning of the node record
		int nodeOffset = id * Constants.MAX_EDGES_NODES_DAT * 4;

		// if the direction is both, get both incoming as well as outgoing edges
		if (direction.equals(Direction.BOTH)) {
			result = getNeighborEdges(nodeOffset, Direction.IN);

			result.addAll(getNeighborEdges(nodeOffset, Direction.OUT));
		}
		// else get only the incoming/outgoing edges
		else
			result = getNeighborEdges(nodeOffset, direction);

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

	public ArrayList<Edge> getNeighborEdges(int nodeOffset, Direction direction) {

		ArrayList<Edge> neighborList = new ArrayList<Edge>();
		Map<Byte, Short> neighborCount;
		short offset;
		int count = 0;
		
		short totalNeighbors;

		if (direction.equals(Direction.IN)) {
			neighborCount = this.getIncomingEdgeCount();
			offset = this.getNumOutgoing();
			
			count = count + offset;
			totalNeighbors = this.getNumIncoming();
		} else
			neighborCount = this.getOutgoingEdgeCount();
		offset = 0;
		totalNeighbors = this.getNumOutgoing();

		try {
			

			RandomAccessFile dataFile;

			ArrayList<byte[]> edgeList = new ArrayList<byte[]>();
			for (int i = offset / Constants.MAX_EDGES_NODES_DAT; i < totalNeighbors
					/ Constants.MAX_EDGES_NODES_DAT; i++) {

				byte[] edges = new byte[Constants.MAX_EDGES_NODES_DAT * 4];
				dataFile = new RandomAccessFile(graph.dbPath + "nodefile" + i
						+ ".dat", "r");
				dataFile.seek(nodeOffset);
				dataFile.readFully(edges);
				dataFile.close();
				edgeList.add(edges);

			}
			int edgeCount = 0;
			int index=(offset % Constants.MAX_EDGES_NODES_DAT)*4;
			for (byte edgeType = 0; edgeType < Constants.NUMBER_OF_EDGE_TYPES; edgeType++) {
				if (neighborCount.containsKey(edgeType)) {
					for (short i = 0; i < neighborCount.get(edgeType); i++) {
											
						int arrayCounter = edgeCount/ Constants.MAX_EDGES_NODES_DAT;
						int neighbor = (edgeList.get(arrayCounter)[index++] << 24 & 0xFF)
								+ (edgeList.get(arrayCounter)[index++] << 16 & 0xFF)
								+ (edgeList.get(arrayCounter)[index++] << 8 & 0xFF)
								+ edgeList.get(arrayCounter)[index++];

						Edge edge = new Edge(edgeType,this.graph.getVertex(neighbor),direction);
						neighborList.add(edge);
						
						edgeCount++;
						if(edgeCount%Constants.MAX_EDGES_NODES_DAT==0)
							  index = 0;
					}
				}
			}

			
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
