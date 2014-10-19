package com.gdb.query;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

/**
 * This class contains all the querying methods
 * @author 
 *
 */
public class Query {

	String path;
	Graph graph;
	
	public Query(Graph graph){
		this.graph=graph;
	}
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
	 * @param vertexLabels - optional list of vertex labels
	 * @return
	 * @throws IOException 
	 */
	public void khopNeighborhood(Vertex vertex,int k,String vertexLabel) throws IOException{
		
		long starttime = System.currentTimeMillis();
		
		// arraylist containing khop neighborhood. start with level = 0
		 getKHop(vertex, new ArrayList<Vertex>(), k,0);
		
		System.out.println("Total time = "+(System.currentTimeMillis()-starttime));
		
		/*for(ArrayList<Vertex> neighbor : neighborhood){
			for(Vertex v : neighbor){
				System.out.print(v.getId()+" ");
			}
			System.out.println();
		}*/
		
		
	}

	/**
	 * This is a recursive program that finds the khop neighborhood of a given vertex
	 * @param vertex - the vertex of origin
	 * @param depth - value of k
	 * @param level - starting level is 0
	 * @return
	 * @throws IOException 
	 */
	public ArrayList<ArrayList<Vertex>> getKHop(Vertex vertex, ArrayList<Vertex> visitedArray, int depth, int level) throws IOException{
		
		if(vertex.getNumOutgoing()==0)
			return null;
		
		if(visitedArray.contains(vertex))
			return null;
		
		
		// if you reach the depth desired, return the end vertex
		if(level==depth){
			ArrayList<ArrayList<Vertex>> returnList = new ArrayList<ArrayList<Vertex>>();
			ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
			vertexList.add(vertex);
			returnList.add(vertexList);
			return returnList;
		}
		
		// if level < depth
		else{
			
			// arraylist of arraylist containing neighborlist
			ArrayList<ArrayList<Vertex>> neighborList = new ArrayList<ArrayList<Vertex>>();		
			
			// get the edges
			Iterable<Edge> outEdges = vertex.getEdges(Direction.OUT);	
			
			ArrayList<Vertex> tempVisitedArray = new ArrayList<Vertex>();
			tempVisitedArray.addAll(visitedArray);
			tempVisitedArray.add(vertex);
			
			// iterate through all the edges
			for(Edge edge : outEdges){	
				
				if(graph.getGraphIndex().get(edge.getId()).getOutgoingEdgeNums()==null)
					continue;
				
				int templevel = level;
				
				// get the next level neighbor
				Vertex neighborVertex = edge.getVertex();		
				
				
				// get the neighborhood of the current vertex
				ArrayList<ArrayList<Vertex>> returnList = getKHop(neighborVertex, tempVisitedArray, depth, ++templevel);
				
				if(!(null==returnList)){
					// add this list to the master list
					for(ArrayList<Vertex> vList : returnList){
						ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
						vertexList.add(vertex);
						vertexList.addAll(vList);
						neighborList.add(vertexList);
					}
				}
						
				
			}
			
			// neighborhood list
			return neighborList;
		}
		
		
	}
	
	 protected void khop(Vertex vertex, int k) throws IOException{
         
         

         ArrayList<Vertex> curr = new ArrayList<Vertex>();
         ArrayList<Vertex> next = new ArrayList<Vertex>();
         HashSet<Object> result = new HashSet<Object>();

         curr.add(vertex);

         for(int real_hops = 0; real_hops < k; real_hops++) {

                 for (Vertex u : curr) {

                        

                         Iterable<Edge> itr = u.getEdges(Direction.BOTH);
                         for (Edge r : itr) {
                                 Vertex v = r.getVertex();

                                

                                 if (result.add(v.getId())) {
                                         next.add(v);
                                 }
                         }
                         
                 }

                 if(next.size() == 0)
                         break;

                 ArrayList<Vertex> tmp = curr;
                 curr = next;
                 tmp.clear();
                 next = tmp;
         }
         
        //System.out.println("Total neighborhood = "+result.size());

              
 }


	
}
