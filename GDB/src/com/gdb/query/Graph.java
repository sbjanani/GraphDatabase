package com.gdb.query;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import com.gdb.util.Constants;
import com.mysql.jdbc.Connection;


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
	LRUCache<Integer,Index> graphIndex;

	/**
	 * This map is a secondary index that groups the vertices by their type(label).
	 * There will will 8 entries, one for each vertex label and value will be an arraylist of vertices belonging to that type
	 */
	Map<Byte, ArrayList<Integer>> typeIndex;


	String dbPath;

	public MySQLConnector mysql;
	RandomAccessFile indexFile ;



	/**
	 * Constructor for the graph
	 * It loads the graphIndex and typeIndex into memory
	 * @param path = path where the the index files are located
	 * @throws IOException
	 */
	public Graph(String path) throws IOException{
		initializeGraphIndex(path+"graph.idx");
		indexFile = new RandomAccessFile(path+"graph.idx","r");
		dbPath = path;
		mysql = new MySQLConnector();
		mysql.getConnection();

	}


	/**
	 * @return the graphIndex
	 */
	public LRUCache<Integer,Index> getGraphIndex() {
		return graphIndex;
	}



	public void initializeGraphIndex(String path) throws IOException {



		int limit = 1750000;
		int i=0;
		graphIndex = new LRUCache<Integer, Index>(limit);
		typeIndex = new HashMap<Byte, ArrayList<Integer>>();
		RandomAccessFile file = new RandomAccessFile(path,"rw");
		//Go through all of the nodes in the file
		//num nodes = file.length/35 because each record is 35 bytes
		while (graphIndex.size() < limit &&  i < (file.length()/Constants.INDEX_RECORD_LENGTH)){


			//read the whole record into a buffer
			byte[] record = new byte[Constants.INDEX_RECORD_LENGTH];
			file.read(record);
			Index element = parseIndexRecord(i, record);
			int count = 0;
			for(Short num : element.getIncomingEdgeNums().values()){
				count += num;
			}
			if(count >=10)
				graphIndex.put(i,element);




			if(typeIndex.containsKey(element.getVertexType())){
				typeIndex.get(element.getVertexType()).add(element.getVertexId());
			}
			else{
				ArrayList<Integer> list = new ArrayList<Integer>();
				list.add(element.getVertexId());
				typeIndex.put(element.getVertexType(), list);
			}
			i++;


		}


		file.close();

	}

	/**
	 * @return the graphIndex
	 */
	public Map<Byte, ArrayList<Integer>> getTypeIndex() {
		return typeIndex;
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
		//System.out.println("id="+id);
		Vertex v = new Vertex(id,this,getIndex(id).getVertexType());
		v.setNumIncoming(getNumIncoming(id));
		v.setNumOutgoing(getNumOutGoing(id));
		return v;
	}



	public short getNumIncoming(int nodeId) throws IOException{
		short numIncoming = 0;
		Map<Byte,Short> neighborCount = getIndex(nodeId).getIncomingEdgeNums();
		for(Map.Entry<Byte, Short> neighborCountEntry : neighborCount.entrySet()){
			numIncoming += neighborCountEntry.getValue();
		}

		return numIncoming;
	}

	public short getNumOutGoing(int nodeId) throws IOException{
		short numOutgoing = 0;
		Map<Byte,Short> neighborCount = getIndex(nodeId).getOutgoingEdgeNums();
		for(Map.Entry<Byte, Short> neighborCountEntry : neighborCount.entrySet()){
			numOutgoing += neighborCountEntry.getValue();
		}
		return numOutgoing;
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


	/**
	 * @return the dbPath
	 */
	public String getDbPath() {
		return dbPath;
	}


	/**
	 * @param dbPath the dbPath to set
	 */
	public void setDbPath(String dbPath) {
		this.dbPath = dbPath;
	}


	/**
	 * @param graphIndex the graphIndex to set
	 */
	public void setGraphIndex(LRUCache<Integer,Index> graphIndex) {
		this.graphIndex = graphIndex;
	}


	/**
	 * @param typeIndex the typeIndex to set
	 */
	public void setTypeIndex(Map<Byte, ArrayList<Integer>> typeIndex) {
		this.typeIndex = typeIndex;
	}

	/**
	 * This method returns the index object given a vertex id
	 * @param vertexId
	 * @return
	 * @throws IOException
	 */
	public Index getIndex(int vertexId) throws IOException{
		Index index = null;

		// if the searched index is present in cache, return it
		if(graphIndex.containsKey(vertexId)){
			index = graphIndex.get(vertexId);
			GdbTester.cachehit++;
		}


		// else, access it from the index file and add to the cache
		else{
			indexFile.seek(vertexId*Constants.INDEX_RECORD_LENGTH);
			byte[] inputArray=new byte[Constants.INDEX_RECORD_LENGTH];
			indexFile.read(inputArray);
			index = parseIndexRecord(vertexId,inputArray);

			//if an unwanted vertex exists in the file, replace it with the new vertex
			graphIndex.put(vertexId, index);
			GdbTester.cachemiss++;
		}

		return index;
	}

	public Index getIndex(int vertexId, Queue<Integer> unwantedList) throws IOException{
		Index index = null;

		// if the searched index is present in cache, return it
		if(graphIndex.containsKey(vertexId)){
			index = graphIndex.get(vertexId);
			GdbTester.cachehit++;
		}


		// else, access it from the index file and add to the cache
		else{
			indexFile.seek(vertexId*Constants.INDEX_RECORD_LENGTH);
			byte[] inputArray=new byte[Constants.INDEX_RECORD_LENGTH];
			indexFile.read(inputArray);
			index = parseIndexRecord(vertexId,inputArray);

			//if an unwanted vertex exists in the file, replace it with the new vertex
			if(!unwantedList.isEmpty()){
				while(!graphIndex.containsKey(unwantedList.peek())){
					unwantedList.poll();
				}
				graphIndex.remove(unwantedList.poll());
			}

			// if the unwanted list is empty, replace the graph index with the new one
			graphIndex.put(vertexId, index);
			GdbTester.cachemiss++;
		}

		return index;
	}

	public Index parseIndexRecord(int vertexId, byte[] record) {
		int index=0;
		Index element = new Index();
		element.setVertexId(vertexId);
		byte deleted = record[index++];
		element.setDeleted(deleted);
		byte vertexType = record[index++];
		element.setVertexType(vertexType);

		Map<Byte,Short> incomingEdgeNums = new HashMap<Byte,Short>();
		Map<Byte,Short> outgoingEdgeNums = new HashMap<Byte,Short>();


		//go through all of the node types and populate the edgeNums array
		for(Byte edgeType = Constants.NUMBER_OF_EDGE_TYPES-1; edgeType >=0; edgeType--){
			//extract the number of outgoing edges of each type
			byte b11 = record[index++];
			byte b22 = record[index++];
			short numNodesOutgoing = (short)((b11 << 8) | (b22 & 0x00FF));
			if(numNodesOutgoing!=0)
				outgoingEdgeNums.put(edgeType, numNodesOutgoing);


			//extract the number of incoming edges of each type
			byte b1 = record[index++];
			byte b2 = record[index++];
			short numNodesIncoming = (short)(((b1 <<8 )& 0xFFFF) | (b2 & 0x00FF));
			if(numNodesIncoming!=0)
				incomingEdgeNums.put(edgeType, numNodesIncoming);


		}

		element.setIncomingEdgeNums(incomingEdgeNums);
		element.setOutgoingEdgeNums(outgoingEdgeNums);
		element.setPropBitmap(record[index++]);


		return element;
	}

}
