
package com.gdb.query;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class is the base class for both the Vertex and Edge classes.
 * It implements standard functions common for both the Vertex and the Edge
 * @author 
 *
 */
/**
 * @author sysadmin
 *
 */
public class Element {

	/**
	 * This method returns the absolute id of the element
	 * @return returns the id of the element (both Vertex and Edge)
	 */
	public int getId(){
		
		
		return 0;
	}
	
	/**
	 * This method returns the value of the property
	 * @param key - the property key
	 * @return - returns the value of the property
	 */
	public Object getAttribute(String key){
		
		return null;
	}
	
	
	/**
	 * This method returns the set of attribute keys applicable for the element
	 * @return - returns an ArrayList containing the attribute keys
	 */
	public ArrayList<String> getAttributeKeys(){
		
		return null;
	}
	
	/**
	 * This method returns a map containing all the attributes of the element
	 * @return - returns a Map with the attribute key-value pairs as entries
	 */
	public Map<String, Object> getAttributes(){
		
		return null;
	}
	
	
	/**
	 * This method removes a specific attribute
	 * @param key - the attribute to remove
	 */
	public void removeAttribute(String key){
		
		
	}
	
	
	/**
	 * This method removes all attributes for an element
	 */
	public void removeAttributes(){
		
		
	}
	
	/**
	 * This method adds an attribute to an element
	 * @param key - name of the attribute
	 * @param value - value of the attribute
	 */
	public void setAttribute(String key, Object value){
		
	}
}