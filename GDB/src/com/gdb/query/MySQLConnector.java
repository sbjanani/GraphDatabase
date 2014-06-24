package com.gdb.query;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;

public class MySQLConnector {

	Connection connection;
	/**
	 * This method returns a SQL connection
	 * @return connection- returns a Connection object
	 */
	public Connection getConnection(){
		
		return null;
	}
	
	/**
	 * This method returns a list of objects with the given attributes
	 * @param attributeMap - attribute map containing key-value attributes
	 * @param type - Edge / Vertex
	 * @return - returns arrayList of ids matching the attribtues
	 */
	public ArrayList<Integer> getElement(Map<String, Object> attributeMap, String type){
		
		return null;
	}
	
	/**
	 * This method returns the attributes for the given object
	 * @param id - object id 
	 * @param type - Edge/ Vertex
	 * @return - map containing attributes as key-value pairs
	 */
	public Map<String, Object> getAttributes(int id, String type){
		
		return null;
	}
	
	/**
	 * This method sets the attribute in the mysql database
	 * @param id - object id
	 * @param type - edge / vertex
	 * @param attributeMap - map containing attributes as key-value pairs
	 */
	public void setAttribute(int id, String type, Map<String, Object> attributeMap){
		
	
	}
	
	/**
	 * This method removes given attributes for an element
	 * @param id - object id
	 * @param type - edge / vertex
	 * @param attributes - list of attributes to remove
	 */
	public void removeAttribute(int id, String type, String... attributes){
		
	}
	
	/**
	 * This method closes the sql connection
	 */
	public void endConnection(){
		
	}
}
