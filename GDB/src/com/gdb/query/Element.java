
package com.gdb.query;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class is the base class for both the Vertex and Edge classes.
 * It implements standard functions common for both the Vertex and the Edge
 * @author
 *
 */

public class Element {

	/**
	 * id of the element
	 */
	int id;
	/**
	 * label of the element
	 */
	byte label;

	Graph graph;

	/**
	 * This method returns the absolute id of the element
	 * @return returns the id of the element (both Vertex and Edge)
	 */
	public int getId(){
		return id;
	}

	public void setId(int i){
		id = i;
	}

	public byte getLabel(){
		return label;
	}

	public void setLabel(byte l){
		label = l;
	}


}
