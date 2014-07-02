package com.gdb.query;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;

/**
 * This class handles all connections to the MySQL database
 * Any query involving attributes has to be made through this class
 * @author sysadmin
 *
 */
public class MySQLConnector {

	/**
	 * Connection object to connect to MySQL
	 */
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
	  * @return - returns arrayList of ids matching the attribtues
	 */
	public ArrayList<Integer> getElement(Map<String, Object> attributeMap){
		
		return null;
	}
	
	/**
	 * This method returns the attributes for the given object
	 * @param id - object id 
	 * @return - map containing attributes as key-value pairs
	 */
	public Map<String, Object> getAttributes(int id){
		
		return null;
	}
	
	/**
	 * This method sets the attribute in the mysql database
	 * @param id - object id
	 * @param attributeMap - map containing attributes as key-value pairs
	 */
	public void setAttribute(int id, Map<String, Object> attributeMap){
		
	
	}
	
	/**
	 * This method removes given attributes for an element
	 * @param id - object id
	 * @param attributes - list of attributes to remove
	 */
	public void removeAttribute(int id, String... attributes){
		
	}
	
	/**
	 * This method closes the sql connection
	 */
	public void endConnection(){
		
	}
}
