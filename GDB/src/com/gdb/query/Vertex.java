package com.gdb.query;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class holds the methods related to a vertex.
 * @author sysadmin
 *
 */
public class Vertex extends Element{

	/** This structure can hold the edges incident with the given vertex
	 * This is a three dimensional arraylist structure. The first dimension specifies the edge type.
	 * The second dimension is an arraylist containing the incoming edges at index 0 and outgoing edges at index 1
	 * The third dimension is an arraylist containing the actual vertex ids.
	 * A sample edges array would be of the form:
	 * [<(1,2),(3,4)>, ----> This is the entry for edge type 0
	 *    |     -----------> This is the outgoing edge list for edge type 0
	 *    -----------------> This is the incoming edge list for edge type 0
	 * <(5,6),(7,8)>,
	 * <(9,10),(11,12)>]
	 * Here the entire arraylist is the one marked under [ and ]
	 * Each entry in the arraylist marked under < and > and represents the edges of a specific type incident with the vertex
	 * Each entry marked under ( and ) represents the actual edge numbers  
	 * 
	 */
	public ArrayList<ArrayList<ArrayList<Integer>>> edgeList;
	
	
	

	/**
	 * @return the edges
	 */
	public ArrayList<ArrayList<ArrayList<Integer>>> getEdgeList() {
		return edgeList;
	}


	/**
	 * @param edges the edges to set
	 */
	public void setEdges(ArrayList<ArrayList<ArrayList<Integer>>> edgeList) {
		this.edgeList = edgeList;
	}


	/**
	 * This method returns the edges connected to a vertex of a given direction and label
	 * @param direction - IN/OUT/BOTH edge
	 * @param labels - label for the edge
	 * @return - returns an ArrayList containing the Edge objects
	 */
	public ArrayList<Edge> getEdges(Direction direction, String... labels){
		
		return null;
	}
	
	
	/**
	 * This method returns all the edges of a given direction connected to a vertex
	 * @param direction - IN/OUT/BOTH
	 * @return - returns an ArrayList containing the Edge objects
	 */
	public ArrayList<Edge> getEdges(Direction direction){
		
		return null;
	}
	
	/**
	 * This method returns all the edges of a given direction, label and attributes connected to a vertex
	 * @param direction- IN/OUT/BOTH
	 * @param attributeMap - Map containing attribute key-value pairs
	 * @param labels- label for the edge
	 * @return- returns an ArrayList containing the Edge objects
	 */
	public ArrayList<Edge> getEdges(Direction direction, Map<String, Object> attributeMap, String... labels){
		
		return null;
	}
	
	public String toString(){
		return "Vertex id: "+id+" Vertex label: "+label+"\n"; 
	}
}
