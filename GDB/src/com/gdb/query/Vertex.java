package com.gdb.query;

import java.util.ArrayList;
import java.util.Map;

public class Vertex {

	int id;
	String label;
	
	
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}


	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
}
