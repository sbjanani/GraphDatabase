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
	


	protected void khop(Vertex vertex, int k, Direction direction, ArrayList<Byte> labelList)throws IOException{
        
        

        ArrayList<Vertex> curr = new ArrayList<Vertex>();
        ArrayList<Vertex> next = new ArrayList<Vertex>();
        HashSet<Object> result = new HashSet<Object>();

        curr.add(vertex);

        for(int real_hops = 0; real_hops < k; real_hops++) {

                for (Vertex u : curr) {

                       

                        Iterable<Edge> itr = u.getEdges(direction,labelList);
                        if(!(null==itr))
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
	 protected void khop(Vertex vertex, int k, Direction direction)throws IOException{
         
         

         ArrayList<Vertex> curr = new ArrayList<Vertex>();
         ArrayList<Vertex> next = new ArrayList<Vertex>();
         HashSet<Object> result = new HashSet<Object>();

         curr.add(vertex);

         for(int real_hops = 0; real_hops < k; real_hops++) {

                 for (Vertex u : curr) {

                        

                         Iterable<Edge> itr = u.getEdges(direction);
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
