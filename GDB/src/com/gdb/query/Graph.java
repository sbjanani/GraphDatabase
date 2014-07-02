package com.gdb.query;

import java.util.ArrayList;
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
	Map<Integer, ArrayList<Integer>> typeIndex;
	

	/**
	 * Constructor for the graph
	 * It loads the graphIndex and typeIndex into memory
	 * @param path = path where the the index files are located
	 */
	public Graph(String path){
		
		this.graphIndex = initializeGraphIndex(path);
		this.typeIndex = initializeTypeIndex(path);
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
	 */
	public ArrayList<Index> initializeGraphIndex(String path) {
		


		return null;
	}
	
	/**
	 * @return the graphIndex
	 */
	public Map<Integer, ArrayList<Integer>> getTypeIndex() {
		return typeIndex;
	}

	/**
	 * This method initializes the graph index
	 * @param path - path to graphIndex.idx 
	 */
	public Map<Integer, ArrayList<Integer>> initializeTypeIndex(String path) {
		


		return null;
	}
	

	/**
	 * This method retrieves all vertices belonging to a given label
	 * This method should work in conjunction with the index to retrieve vertices
	 * @param labels - the label types that the vertex can belong to
	 * @return - returns an ArrayList containing the vertex objects
	 */
	public ArrayList<Vertex> getVertices(int... labels){
		
		return null;
	}
	
	/**
	 * This method retrieves all vertices belonging to a given label and containing the given attributes
	 * This  method should work in conjunction with the index and MySqlConnectior to retrieve vertices
	 * @param attributeMap - a map containing the attributes as key-value pairs
	 * @param labels - the label types that the vertex can belong to
	 * @return - returns an ArrayList containing the vertex objects
	 */
	public ArrayList<Vertex> getVertices(Map<String, Object> attributeMap, int... labels){
		
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
	 */
	public ArrayList<Vertex> getVertices(){
		
		return null;
	}
	
	/**
	 * This method returns the vertex with the given id
	 * @param id - id of the vertex
	 * @return - returns the vertex object
	 */
	public Vertex getVertex(int id){
		
		return null;
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
