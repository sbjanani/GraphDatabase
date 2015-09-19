package com.gdb.query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
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
	 * @throws SQLException
	 */
	public void getConnection() {
		try {
			// The newInstance() call is a work around for some
			// broken Java implementations

			Class.forName("com.mysql.jdbc.Driver").newInstance();

		} catch (Exception ex) {
			System.out.println("Could not find MySQL driver");
			ex.printStackTrace();
		}

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/gdb", "gdb", "gdb");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("connection created");

	}

	/**
	 * This method returns a list of objects with the given attributes
	 * @param attributeMap - attribute map containing key-value attributes
	 * @return - returns arrayList of ids matching the attribtues
	 */
	public ArrayList<Integer> getElement(Map<String, Object> attributeMap, String elementType){

		ArrayList<Integer> vertexIds = new ArrayList<Integer>();

		String query="", start="";
		if(elementType.equalsIgnoreCase("vertex")){
			start="(SELECT vid FROM "+elementType+" WHERE ";

			for(Map.Entry<String, Object> attribute : attributeMap.entrySet()){

				query = query+start + "pkey=\'"+attribute.getKey().toUpperCase()+"\' and pvalue=\'"+attribute.getValue().toString().toUpperCase()+ ""
						+ "') INTERSECT ";
			}
		}
		query = query+";";
		query = query.replace("INTERSECT ;", ";");
		System.out.println("Query = "+query);
		//System.out.println(constraints);

		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(query);
			while(result.next()){
				vertexIds.add(result.getInt(1));
				System.out.println(result.getInt(1));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error executing statement");
			e.printStackTrace();
		}


		return null;
	}

	/**
	 * This method returns the attributes for the given object
	 * @param id - object id 
	 * @return - map containing attributes as key-value pairs
	 */
	public Map<String, Object> getAttributes(String elementType, int id, String... keyList){

		String[] keys={"gender","last_name","place","job_title","university","institution"};

		Map<String,Object> attributeMap = new HashMap<String,Object>();
		String query,end="";
		if(keyList.length>0)
			keys = keyList;


		query="SELECT "+keys[0];
		if(elementType.equalsIgnoreCase("vertex")){
			end=" FROM "+elementType+" WHERE id="+id+";";

			for(int i=1; i<keys.length; i++){

				query = query+","+keys[i];
			}
		}
		query = query+end;

		System.out.println("Query = "+query);
		//System.out.println(constraints);

		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(query);

			while(result.next()){
				for(int i=0; i<keys.length; i++)
					attributeMap.put(keys[i], result.getObject(keys[i]));

			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error executing statement");
			e.printStackTrace();
		}

		return attributeMap;
	}

	public ArrayList<Integer> getNextNeighbors(String ids, Map<String,Object> attributeMap){

		ArrayList<Integer> neighborList = new ArrayList<Integer>();
		int i=0;
		String query="SELECT id FROM VERTEX WHERE id in ("+ids+")" ;

		for(Map.Entry<String, Object> attributes : attributeMap.entrySet()){

			query = query+" and "+attributes.getKey()+" = '"+attributes.getValue()+"'";
		}

		query = query+";";
		//System.out.println("Query = "+query);
		//System.out.println(constraints);

		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(query);

			while(result.next()){
				neighborList.add(result.getInt(1));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error executing statement");
			e.printStackTrace();
		}


		return neighborList;
	}

	/**
	 * This method returns the attributes for the given object
	 * @param id - object id 
	 * @return - map containing attributes as key-value pairs
	 */
	public int checkVertexAttributes(String elementType, int id, Map<String,Object> attributeMap){

		int i=0;
		String query="SELECT 1 FROM "+elementType+" WHERE id='"+id+"'" ;

		for(Map.Entry<String, Object> attributes : attributeMap.entrySet()){

			query = query+" and "+attributes.getKey()+" = '"+attributes.getValue()+"'";
		}

		query = query+";";
		//System.out.println("Query = "+query);
		//System.out.println(constraints);

		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(query);

			while(result.next()){
				i = result.getInt(1);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error executing statement");
			e.printStackTrace();
		}


		return i;
	}

	public int checkEdgeAttributes(int vertexid, Direction direction, Map<String,Object> edgeAttributes){
		int result=0;
		String query;
		if(direction.equals(Direction.IN))
			query= "SELECT 1 from edge where toid ="+vertexid;
		else
			query = "SELECT 1 from edge where fromid ="+vertexid;

		for(Map.Entry<String, Object> attributes : edgeAttributes.entrySet()){

			query = query+" and "+attributes.getKey()+" = "+attributes.getValue();
		}

		query = query+";";
		//System.out.println("Query = "+query);
		//System.out.println(constraints);

		try {
			Statement stmt = connection.createStatement();
			ResultSet resultset = stmt.executeQuery(query);

			while(resultset.next()){
				result = resultset.getInt(1);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error executing statement");
			e.printStackTrace();
		}


		return result;
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
