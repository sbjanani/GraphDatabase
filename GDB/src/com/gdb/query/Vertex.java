package com.gdb.query;

import java.io.IOException;
import java.io.RandomAccessFile;
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
	 * @throws IOException 
	 */
	public ArrayList<Edge> getEdges(Direction direction, String labels) throws IOException{
		
		return null;
	}
	
	
	/**
	 * This method returns all the edges of a given direction connected to a vertex
	 * @param direction - IN/OUT/BOTH
	 * @return - returns an ArrayList containing the Edge objects
	 * @throws IOException 
	 */
	public ArrayList<Edge> getEdges(Direction direction) throws IOException{
		//dbPath = path;
		ArrayList<Edge> result = new ArrayList<Edge>();
		RandomAccessFile nFile = new RandomAccessFile(graph.dbPath+"nodefile.dat","rw");
		
		short[] edgenums = graph.getGraphIndex().get(id).getEdgeNums();
		nFile.seek(id*1024);
		int dir;
		if(direction.equals(Direction.IN))
			dir=1;
		else if(direction.equals(Direction.OUT))
			dir=0;
		else 
			dir = 2;
		if(direction.equals(Direction.BOTH)){
			for(int i=0; i<edgenums.length; i++){
				
				nFile.seek(numIncoming*5+id*(Constants.MAX_EDGES_NODES_DAT*5+4));
				for(int j = 0; j < Math.min(16-numIncoming,numOutgoing); j++){
					nFile.readByte();
					int edgeNumber = nFile.readInt();
					Edge e = new Edge(id,edgeNumber,graph);
					result.add(e);
				}
			}
			if(numIncoming + numOutgoing > 16){//add from overflow area
				nFile.seek(Constants.MAX_EDGES_NODES_DAT*5+id*(Constants.MAX_EDGES_NODES_DAT*5+4));
				int overFlowPointer = nFile.readInt();
				int offset = (numIncoming < 16) ? overFlowPointer : overFlowPointer+(numIncoming-16)*5;
				oFile.seek(offset);
				int numToRead = (numIncoming < 16) ? numOutgoing - 16 + numIncoming : numOutgoing;
				for(int i = 0; i < numToRead; i++){
					oFile.readByte();
					int edgeNumber = oFile.readInt();
					Edge e = new Edge(id,edgeNumber,graph);
					result.add(e);
				}
			}
		}//end of outoing if
		else{
			for(int j = 0; j < Math.min(16,numIncoming); j++){
				nFile.readByte();
				int edgeNumber = nFile.readInt();
				Edge e = new Edge(id,edgeNumber,graph);
				result.add(e);
			}
			if(numIncoming > 16){
				int overFlowPointer = nFile.readInt();
				oFile.seek(overFlowPointer);
				for(int i = 0; i < numIncoming-16; i++){
					oFile.readByte();
					int edgeNumber = oFile.readInt();
					Edge e = new Edge(id,edgeNumber,graph);
					result.add(e);
				}
			}
		}//end of incoming else
		nFile.close();
		oFile.close();
		return result;
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
		return "Vertex id: "+id+" Vertex label: "+label+"\n"+"EDGE LIST: "+edgeList; 
	}


	public void setGraph(Graph g) {
		graph  = g;	
	}
}
