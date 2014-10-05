package com.gdb.query;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * This class contains methods for the graph as a whole.
 * This is a starting point for general graph operations.
 * @author 
 *
 */
public class Graph {

	/**
	 *  This arraylist holds the main graph index. 
	 *  Each entry in the graphIndex.idx file is mapped as one entry in the graphIndex arraylist.
	 *  So, each vertex will have one entry and the id of the vertex = position of the vertex in the arraylist
	 */
	ArrayList<Index> graphIndex;
	
	/**
	 * This map is a secondary index that groups the vertices by their type(label).
	 * There will will 8 entries, one for each vertex label and value will be an arraylist of vertices belonging to that type
	 */
	Map<Byte, ArrayList<Integer>> typeIndex;
	
	String dbPath;
	

	/**
	 * Constructor for the graph
	 * It loads the graphIndex and typeIndex into memory
	 * @param path = path where the the index files are located
	 * @throws IOException 
	 */
	public Graph(String path) throws IOException{
		this.graphIndex = initializeGraphIndex(path+"graph.idx");
		this.typeIndex = initializeTypeIndex(path+"graph.idx");
		dbPath = path;
	}
	
	
	/**
	 * @return the graphIndex
	 */
	public ArrayList<Index> getGraphIndex() {
		return graphIndex;
	}

	/**
	 * This method initializes the graph index
	 * @param path - path to graphIndex.idx 
	 * @throws IOException 
	 */
	public ArrayList<Index> initializeGraphIndex(String path) throws IOException {
		ArrayList<Index> result = new ArrayList<Index>();
		RandomAccessFile file = new RandomAccessFile(path,"rw");
		//Go through all of the nodes in the file
		//num nodes = file.length/35 because each record is 35 bytes
		for(int i = 0; i < (file.length()/35); i++){
			int index = 0;
			Index element = new Index();
			//read the whole record into a buffer
			byte[] record = new byte[35];
			file.read(record);
			element.setVertexId(i);
			byte vertexType = record[index++];
			element.setVertexType(vertexType);
			byte incomingEdgeBitMap = record[index++];
			byte outgoingEdgeBitMap = record[index++];
			short edgeBitMap = (short) (256*incomingEdgeBitMap + outgoingEdgeBitMap);
			element.setEdgeBitmap(edgeBitMap);
			Map<String,Short> edgeNums = new HashMap<String,Short>();
			//go through all of the node types and populate the edgeNums array
			for(int edgeType = 0; edgeType < Constants.NUMBER_OF_EDGE_TYPES; edgeType++){
				//extract the number of incoming edges of each type
				byte b1 = record[index++];
				byte b2 = record[index++];
				short numNodesIncoming = (short)(256*b1 + b2);
				edgeNums.put("i"+edgeType, numNodesIncoming);
				
				//same for outoing
				byte b11 = record[index++];
				byte b22 = record[index++];
				short numNodesOutgoing = (short)(256*b11 + b22);
				edgeNums.put("o"+edgeType, numNodesOutgoing);
			}
			element.setEdgeNums(edgeNums);
			result.add(element);
		}
		file.close();
		return result;
	}
	
	/**
	 * @return the graphIndex
	 */
	public Map<Byte, ArrayList<Integer>> getTypeIndex() {
		return typeIndex;
	}

	/**
	 * This method initializes the graph index
	 * @param path - path to graphIndex.idx 
	 */
	public Map<Byte, ArrayList<Integer>> initializeTypeIndex(String path) {
		Map<Byte,ArrayList<Integer>> result = new HashMap<Byte,ArrayList<Integer>>();
		
		for(int j = 0; j < graphIndex.size(); j++){
			if(!(result.containsKey(graphIndex.get(j).vertexType))){
				ArrayList<Integer> vertexList = new ArrayList<Integer>();
				result.put(graphIndex.get(j).vertexType, vertexList);
			}
			result.get(graphIndex.get(j)).add(j);
				
		}
		
		return result;
	}
	

	/**
	 * This method retrieves all vertices belonging to a given label
	 * This method should work in conjunction with the index to retrieve vertices
	 * @param labels - the label types that the vertex can belong to
	 * @return - returns an ArrayList containing the vertex objects
	 * @throws IOException 
	 */
	public ArrayList<Vertex> getVertices(int labels) throws IOException{
		ArrayList<Vertex> result = new ArrayList<Vertex>();
		ArrayList<Integer> nodes = typeIndex.get(labels);
		for(int i = 0; i < nodes.size(); i++)
			result.add(getVertex(nodes.get(i)));
		return result;
	}
	
	/**
	 * This method retrieves all vertices belonging to a given label and containing the given attributes
	 * This  method should work in conjunction with the index and MySqlConnectior to retrieve vertices
	 * @param attributeMap - a map containing the attributes as key-value pairs
	 * @param labels - the label types that the vertex can belong to
	 * @return - returns an ArrayList containing the vertex objects
	 */
	public ArrayList<Vertex> getVertices(Map<String, Object> attributeMap, int labels){
		
		return null;
	}
	
	
	
	/**
	 * This method returns all the vertices with a given attribute key-value
	 * @param attributeMap - a map containing the attributes as key-value pairs
	 * @return - returns an ArrayList containing the vertex objects
	 */
	public ArrayList<Vertex> getVertices(Map<String, Object> attributeMap){
		
		return null;
	}
	
	/**
	 * This is more of a debug method. Returns all the vertices of a graph
	 * @return - returns an ArrayList containing the vertex objects
	 * @throws IOException 
	 */
	public ArrayList<Vertex> getVertices() throws IOException{
		ArrayList<Vertex> result = new ArrayList<Vertex>();
		for(Byte i : typeIndex.keySet()){
			ArrayList<Integer> nodes = typeIndex.get(i);
			for(int j = 0; j < nodes.size(); j++)
				result.add(getVertex(nodes.get(j)));
		}
		return result;
	}
	
	/**
	 * This method returns the vertex with the given id
	 * @param id - id of the vertex
	 * @return - returns the vertex object
	 * @throws IOException 
	 */
	public Vertex getVertex(int id) throws IOException{
		Vertex v = new Vertex();
		v.setId(id);
		v.setGraph(this);
		int l = getLabel(id);
		v.setLabel(l);
		ArrayList<ArrayList<ArrayList<Integer>>> edgeList = new ArrayList<ArrayList<ArrayList<Integer>>>();
		for(int i = 0; i < Constants.NUMBER_OF_EDGE_TYPES; i++){
			ArrayList<ArrayList<Integer>> inOut = new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> in = new ArrayList<Integer>();
			ArrayList<Integer> out = new ArrayList<Integer>();
			inOut.add(in);
			inOut.add(out);
			edgeList.add(inOut);
		}
		RandomAccessFile nFile = new RandomAccessFile(dbPath+"nodes.dat","r");
		RandomAccessFile eFile = new RandomAccessFile(dbPath+"edges.dat","r");
		RandomAccessFile oFile = new RandomAccessFile(dbPath+"overFlow.dat","r");
//System.out.println("ID ABOVE: "+id);
	    //arraylists to hold the edge numbers for the node id passed in the method
		ArrayList<Integer> incoming = getIncomingNeighbors(nFile,oFile,id);
		ArrayList<Integer> outgoing = getOutgoingNeighbors(nFile,oFile,id);
//System.out.println("INCOMING: "+incoming);
//System.out.println("OUTOING: "+outgoing);
//for(int x = 0; x < graphIndex.get(id).edgeNums.length; x++){
	//System.out.print(graphIndex.get(id).edgeNums[x]+" ");
//}
//System.out.println();
		//incoming edge nums
		for(int i = 0; i < incoming.size(); i++){
			int eNum = incoming.get(i);
			eFile.seek(Constants.EDGE_DAT_SIZE*eNum);
			int edgeType = eFile.readByte();
			int fromNodeId = eFile.readInt();
			edgeList.get(edgeType).get(0).add(fromNodeId);
		}
		//outgoing edge nums
		for(int i = 0; i < outgoing.size(); i++){
			int eNum = outgoing.get(i);
			eFile.seek(Constants.EDGE_DAT_SIZE*eNum);
			int edgeType = eFile.readByte();
			eFile.readInt();
			int toNodeId = eFile.readInt();
			edgeList.get(edgeType).get(1).add(toNodeId);
		}
		nFile.close();
		eFile.close();
		oFile.close();
		v.setEdges(edgeList);
		return v;
	}
	
	 
	
	private int getLabel(int id) {
		for(int i = 0; i < typeIndex.size(); i++){
			if(typeIndex.get(i).contains(id))
				return i;
		}
		return -1;
	}


	private int getIncomingNeighborNode(RandomAccessFile eFile, ArrayList<Integer> incoming,
			 int id, int eNum) throws IOException {
		eFile.seek(Constants.EDGE_DAT_SIZE*incoming.get(eNum));
		int fromNodeId = eFile.readInt();
		return fromNodeId;
	}
	
	private int getOutgoingNeighborNode(RandomAccessFile eFile,
			ArrayList<Integer> outgoing, int id, int eNum) throws IOException {
		eFile.seek(Constants.EDGE_DAT_SIZE*outgoing.get(eNum));
		eFile.readInt();
		int toNodeId = eFile.readInt();
		return toNodeId;
	}
	
	public int getNumIncoming(int nodeId){
		int numIncoming = 0;
		for(int i = 0; i < graphIndex.get(nodeId).edgeNums.length; i+=2)
			numIncoming += graphIndex.get(nodeId).edgeNums[i];
		return numIncoming;
	}
	
	public int getNumOutGoing(int nodeId){
		int numOutgoing = 0;
		for(int j = 1; j < graphIndex.get(nodeId).edgeNums.length; j+=2)
			numOutgoing += graphIndex.get(nodeId).edgeNums[j];
		return numOutgoing;
	}


	private ArrayList<Integer> getOutgoingNeighbors(RandomAccessFile nFile,
			 RandomAccessFile oFile, int id) throws IOException {
		ArrayList<Integer> result = new ArrayList<Integer>();
		int numIncoming = 0;
		int numOutgoing = 0;
		nFile.seek(id*(Constants.MAX_EDGES_NODES_DAT*5+4));
		for(int i = 0; i < graphIndex.get(id).edgeNums.length; i+=2)
			numIncoming += graphIndex.get(id).edgeNums[i];
		for(int j = 1; j < graphIndex.get(id).edgeNums.length; j+=2)
			numOutgoing += graphIndex.get(id).edgeNums[j];
		if(numIncoming < 16){//add the outgoing edgeNumbers that fit in the regular space
			nFile.seek(numIncoming*5+id*(Constants.MAX_EDGES_NODES_DAT*5+4));
			for(int j = 0; j < Math.min(16-numIncoming,numOutgoing); j++){
				nFile.readByte();
				int edgeNumber = nFile.readInt();
				result.add(edgeNumber);
			}
		}
		if(numIncoming + numOutgoing > 16){//add from overflow area
			nFile.seek(Constants.MAX_EDGES_NODES_DAT*5+id*(Constants.MAX_EDGES_NODES_DAT*5+4));
			int overFlowPointer = nFile.readInt();
			int offset = (numIncoming < 16) ? overFlowPointer : overFlowPointer+(numIncoming-16)*5;
			oFile.seek(offset);
			int numToRead = (numIncoming < 16) ? numOutgoing - 16 + numIncoming : numOutgoing;
			for(int i = 0; i < numToRead; i++){
				oFile.readByte();
				int edgeNumber = oFile.readInt();
				result.add(edgeNumber);
			}
		}
		return result;
	}


	private ArrayList<Integer> getIncomingNeighbors(RandomAccessFile nFile,
			RandomAccessFile oFile, int id) throws IOException {
		ArrayList<Integer> result = new ArrayList<Integer>();
		int numEdges = 0;
//System.out.println("ID: "+id);
		for(int i = 0; i < graphIndex.get(id).edgeNums.length; i+=2)
			numEdges += graphIndex.get(id).edgeNums[i];
		nFile.seek(id*(Constants.MAX_EDGES_NODES_DAT*5+4));
		for(int j = 0; j < Math.min(16,numEdges); j++){
			nFile.readByte();
			int edgeNumber = nFile.readInt();
			result.add(edgeNumber);
		}
		if(numEdges > 16){
			int overFlowPointer = nFile.readInt();
			oFile.seek(overFlowPointer);
			for(int i = 0; i < numEdges-16; i++){
				oFile.readByte();
				int edgeNumber = oFile.readInt();
				result.add(edgeNumber);
			}
		}
		return result;
	}


	/**
	 * This method adds a vertex to the graph
	 * @param label - label for the vertex
	 * @param attributeMap - optional attributes for the vertex. Can be null.
	 */
	public void addVertex(int label, Map<String, Object> attributeMap){
		
		
	}
	
	/**
	 * This method adds a vertex to the graph
	 * @param label - label for the edge
	 * @param head - id of the head vertex
	 * @param tail - id of the tail vertex
	 * @param attributeMap - optional attributes for the edge. Can be null.
	 */
	public void addEdge(int label, int head, int tail, Map<String, Object> attributeMap){
		
	}
	
	/**
	 * This method deletes a vertex. It also deletes any edge connected to the vertex
	 * @param id - id of the vertex to delete
	 */
	public void deleteVertex(int id){
		
	}
	
	/**
	 * This method deletes the edge given its head and tail vertices
	 * @param head - id of the head vertex
	 * @param tail - id of the tail vertex
	 */
	public void deleteEdge(int head, int tail){
		
	}
	
	/**
	 * This method deletes the edge given its id is known
	 * @param id - id of the edge to delete
	 */
	public void deleteEdge(int id){
		
	}
}
