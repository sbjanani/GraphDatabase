package com.gdb.query;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class contains all the querying methods
 * @author 
 *
 */
public class Query {

	
	/**
	 * This method returns the set of paths satisfying the given query
	 * The paths are returned in the form Vertex-Edge-Vertex-Edge
	 * @param queryPath
	 * @return
	 */
	public ArrayList<ArrayList<Element>> pathQuery(String queryPath){
		
		return null;
	}
	
	/**
	 * This method returns the attributes of the given element
	 * @param element - vertex/edge object with id
	 * @return
	 */
	public Map<String, Object> getAttributes(Element element){
		
		return null;
	}
	
	/**
	 * This method returns the k-hop neighborhood of a given vertex, k being the number of levels to traverse
	 * The traversal happens in a depth first fashion.
	 * The user can also specify optional vertex types.
	 * @param vertex - the starting vertex
	 * @param k - the number of hops
	 * @param edgeLabels - optional list of vertex labels
	 * @return
	 */
	public ArrayList<ArrayList<Element>> khopNeighborhood(Vertex vertex, int k, String vertexLabels){
		
		return null;
		
	}
	
}
