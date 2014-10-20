
package compare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Scanner;
import java.util.HashSet;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.KeyIndexableGraph;
import com.tinkerpop.blueprints.TransactionalGraph;
import com.tinkerpop.blueprints.Vertex;



/**
 * This class handles all the queries
 * 
 * @author janani
 *
 */
public class Queries {
	

	static long totalExecTime=0;

	long execTime = 0;
	static int k = 0;

	String[] nodeLabels = { "golfclub", "friend", "family", "colleague",
			"bookclub", "pta", "neighborhood", "church" };
	String[] edgeLabels = { "like", "comment", "add", "share", "invite",
			"post", "poke", "chat" };
	
	private static final int MegaBytes = 10241024;
	
    long freeMemory = Runtime.getRuntime().freeMemory()/MegaBytes;
   long totalMemory = Runtime.getRuntime().totalMemory()/MegaBytes;
   long maxMemory = Runtime.getRuntime().maxMemory()/MegaBytes;

	
	/**
	 * This method returns all the properties of a given vertex
	 * @param graph - the graph to read from
	 * @param id - the id of the vertex
	 */
	public void getVertexWithProp(Graph graph, Vertex vertex){
		long startTime = System.nanoTime();
		
		Set<String> keys = vertex.getPropertyKeys();
	//	System.out.println("id : "+verte);
		for(String key : keys){
			System.out.println(key+" : "+vertex.getProperty(key));
		}
		
		long stopTime  = System.nanoTime();
		execTime = stopTime - startTime;
		System.out.println(Long.toString(execTime));
	}
	
	/**
	 * This method updates a vertex with the given attributes
	 * @param graphhe graph to read from
	 * @param id - the id of the vertex
	 * @param attributes - a map of attributes and their values
	 */
	public void updateVertex(Graph graph, Vertex vertex, Map<String,Object> attributes){
		
	
		for(Map.Entry<String, Object> entry : attributes.entrySet()){
			vertex.setProperty(entry.getKey(), entry.getValue());
		}
		
		
	}

	/**
	 * This method returns the edges of a given vertex
	 * @param graph - the graph to read from
	 * @param id - the id of the vertex
	 */
	public void getEdges(Graph graph, Vertex vertex) {
		System.out.println("Im here");
		Iterable<Edge> edges = vertex.getEdges(Direction.OUT);
		for (Edge edge : edges){
			System.out.println(edge.getVertex(Direction.IN).toString());
			if(!(null == edge.getVertex(Direction.IN).getProperty("ident")))
				System.out.println(edge.getVertex(Direction.IN).getProperty("ident").toString());
		}
			
	}

	/**
	 * This method reads the entire graph with all the properties and edges
	 * 
	 * @param graph
	 *            - the type of graph to read from. Pass the appropriate graph
	 *            database object to change databases
	 */
	public void readFullGraph(Graph graph) {

		
		long startTime = System.nanoTime();

		Iterable<Vertex> vertexList = graph.getVertices();
		for (Vertex vertex : vertexList) {
			
			System.out.println("id:" + vertex.getId());
			Set<String> properties = vertex.getPropertyKeys();
			for (String property : properties)
				System.out.println(property + ":"
						+ vertex.getProperty(property));
			Iterable<Edge> outEdges = vertex.getEdges(Direction.OUT);
			Iterable<Edge> inEdges = vertex.getEdges(Direction.IN);
			System.out.println("##Out Edge##");
			for (Edge edge : outEdges)
				System.out.println(edge.getVertex(Direction.IN));
			System.out.println("##In Edge##");
			for (Edge edge : inEdges)
				System.out.println(edge.getVertex(Direction.OUT).toString());
			System.out.println("*****************************************************");
		}
		long stopTime  = System.nanoTime();
		execTime = stopTime - startTime;
		
		/*Runtime.getRuntime().gc();
		
		freeMemory = Runtime.getRuntime().freeMemory() / MegaBytes;
        totalMemory = Runtime.getRuntime().totalMemory() / MegaBytes;
        maxMemory = Runtime.getRuntime().maxMemory() / MegaBytes;

        System.out.println("Used Memory in JVM: " + (maxMemory - freeMemory));
        System.out.println("freeMemory in JVM: " + freeMemory);
        System.out.println("totalMemory in JVM shows current size of java heap : "
                                   + totalMemory);
        System.out.println("maxMemory in JVM: " + maxMemory);*/




	}

	/**
	 * This method reads the entire graph without the properties and edges. It
	 * reads just the vertex ids.
	 * 
	 * @param graph
	 *            - the type of graph to read from. Pass the appropriate graph
	 *            database object to change databases
	 */

	public void readGraphWithoutProps(Graph graph) {
		

		System.out.println("Read Graph Without Properties and edges");
		long startTime = System.nanoTime();
		Iterable<Vertex> vertexList = graph.getVertices();
		for (Vertex vertex : vertexList) {
			System.out.println("id :"+vertex);
			System.out.println("ident :"+vertex.getProperty("ident"));
		}
		long stopTime  = System.nanoTime();
		execTime = stopTime - startTime;
	}

	/**
	 * This method reads the entire graph with the properties but without edges.
	 * It reads just the vertex ids.
	 * 
	 * @param graph
	 *            - the type of graph to read from. Pass the appropriate graph
	 *            database object to change databases
	 */
	public void readGraphWithPropsWithoutEdges(Graph graph) {

		System.out.println("Read Graph With Properties Without Edges");
		long startTime = System.nanoTime();
		Iterable<Vertex> vertexList = graph.getVertices(); 
		for (Vertex vertex : vertexList) {
			//System.out.println(vertex.getId());
			Set<String> properties = vertex.getPropertyKeys();
			for (String property : properties);
				//System.out.println(property + ":"
						//+ vertex.getProperty(property));
			//System.out.println("****************");
		}
		long stopTime  = System.nanoTime();
		execTime = stopTime - startTime;
	}

	/**
	 * This method reads the entire graph without the properties but with edges.
	 * It reads just the vertex ids.
	 * 
	 * @param graph
	 *            - the type of graph to read from. Pass the appropriate graph
	 *            database object to change databases
	 */
	public void readGraphWithoutPropsWithEdges(Graph graph) {

		System.out.println("Read Graph Without Properties With Edges");
		long startTime = System.nanoTime();
		Iterable<Vertex> vertexList = graph.getVertices();
		for (Vertex vertex : vertexList) {
			//System.out.println(vertex.getId());
			Iterable<Edge> outEdges = vertex.getEdges(Direction.OUT);
			Iterable<Edge> inEdges = vertex.getEdges(Direction.IN);
		//	System.out.println("##Out Edge##");
			for (Edge edge : outEdges);
				//System.out.println(edge.getVertex(Direction.IN));
			//System.out.println("##In Edge##");
			for (Edge edge : inEdges);
				//System.out.println(edge.getVertex(Direction.OUT));
		//	System.out
					//.println("*****************************************************");
		}

		long stopTime  = System.nanoTime();
		execTime = stopTime - startTime;
	}

	public void getVertexWithLabel(Graph graph, String label) {

		Iterable<Vertex> vertexList = graph.getVertices("label1", label);
		for (Vertex vertex : vertexList) {
			System.out.println(vertex.getId().toString());
			Set<String> propertyKeys = vertex.getPropertyKeys();
			for (String key : propertyKeys) {
				System.out.println(key + " : " + vertex.getProperty(key));
			}

		}
	}

	/**
	 * This method retrieves all the edges of a graph
	 * @param graph - the graph of question
	 */
	public void getallEdges(Graph graph) {
		Iterable<Edge> edgeList = graph.getEdges();
		for (Edge edge : edgeList) {
			System.out.println(edge.getId().toString());
			Set<String> properties = edge.getPropertyKeys();
			for (String property : properties)
				System.out.println(property + ":" + edge.getProperty(property));
			System.out.println("****************");
		}
	}
	
	public Vertex getVertex(Graph graph, Map<String,Object> attributeMap){
		
		long startTime = System.nanoTime();
		System.out.println("start time = "+startTime);
		Iterable<Vertex> vertices = graph.getVertices();
		for(Vertex vertex : vertices){
			boolean match = true;
			for(Map.Entry<String,Object> attribute : attributeMap.entrySet()){
				if(vertex.getPropertyKeys().contains(attribute.getKey())){
					if(!(vertex.getProperty(attribute.getKey()).equals(attribute.getValue()))){
						match = false;
						break;						
					}
				}
				else{
					match = false;
					break;
				}
					
				
					
			}
			if(match==true)
			{
				long stopTime = System.nanoTime();
				System.out.println("stop time = "+stopTime);
				execTime = stopTime - startTime;
				return vertex;
			}
				
		}
		
		
		return null;
	}
	
public Vertex getVertex(Graph graph, String key, Object value){
	
	 long startTime = System.nanoTime();
	// System.out.println("start time = "+startTime);
		Iterable<Vertex> vertices = graph.getVertices(key,value);
		//System.out.println("got here");
		Vertex v = null;
		for(Vertex vertex : vertices){
			 v = vertex;
		}
			
		long stopTime = System.nanoTime();
		//System.out.println("stop time = "+stopTime);
		execTime = stopTime - startTime;
		return v;
		
	}
	

	/**
	 * This method prints the khop neighborhood of a given vertex
	 * 
	 * @param graph
	 *            - the graph to be worked with
	 * @param depth
	 *            - value of k
	 * @param id
	 *            - id of the vertex
	 */
	public void khopNeighborhood(Vertex vertex, Graph graph, int k) {

		/*long startTime = System.currentTimeMillis();
		// arraylist containing khop neighborhood. start with level = 0
		ArrayList<ArrayList<Vertex>> neighborhood = getKHop(vertex,
				new ArrayList<Vertex>(), depth, 0);

		/*for (ArrayList<Vertex> neighbor : neighborhood) {
			for (Vertex v : neighbor) {
				System.out.print(v.getId() + " ");
			}
			System.out.println("\n******************");
		}*/

		/*long stopTime = System.currentTimeMillis();
		execTime = stopTime - startTime;*/
 	 ArrayList<Vertex> curr = new ArrayList<Vertex>();
         ArrayList<Vertex> next = new ArrayList<Vertex>();
         HashSet<Object> result = new HashSet<Object>();

         curr.add(vertex);

         for(int real_hops = 0; real_hops < k; real_hops++) {

                 for (Vertex u : curr) {

                        

                         Iterable<Edge> itr = u.getEdges(Direction.OUT);
                         for (Edge r : itr) {
                                 Vertex v = r.getVertex(Direction.IN);

                                

                                 if (result.add(v.getId())) {
                                         next.add(v);
                                 }
                         }
                         Iterable<Edge> in = u.getEdges(Direction.IN);
                         for (Edge r : in) {
                                 Vertex v = r.getVertex(Direction.OUT);

                                

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
	
	public void khopNeighborhood(Vertex vertex, Graph graph, int k, String[]labels) {

		/*long startTime = System.currentTimeMillis();
		// arraylist containing khop neighborhood. start with level = 0
		ArrayList<ArrayList<Vertex>> neighborhood = getKHop(vertex,
				new ArrayList<Vertex>(), depth, 0);

		/*for (ArrayList<Vertex> neighbor : neighborhood) {
			for (Vertex v : neighbor) {
				System.out.print(v.getId() + " ");
			}
			System.out.println("\n******************");
		}*/

		/*long stopTime = System.currentTimeMillis();
		execTime = stopTime - startTime;*/
 	 ArrayList<Vertex> curr = new ArrayList<Vertex>();
         ArrayList<Vertex> next = new ArrayList<Vertex>();
         HashSet<Object> result = new HashSet<Object>();

         curr.add(vertex);

         for(int real_hops = 0; real_hops < k; real_hops++) {

                 for (Vertex u : curr) {

                        

                         Iterable<Edge> itr = u.getEdges(Direction.OUT, labels);
                         for (Edge r : itr) {
                                 Vertex v = r.getVertex(Direction.IN);

                                

                                 if (result.add(v.getId())) {
                                         next.add(v);
                                 }
                         }
                        /* Iterable<Edge> in = u.getEdges(Direction.IN,labels);
                         for (Edge r : in) {
                                 Vertex v = r.getVertex(Direction.OUT);

                                

                                 if (result.add(v.getId())) {
                                         next.add(v);
                                 }
                         }*/
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
	
	public ArrayList<ArrayList<Vertex>> returnKhopNeighborhood(Vertex vertex, Graph graph, int depth){


				// arraylist containing khop neighborhood. start with level = 0
				ArrayList<ArrayList<Vertex>> neighborhood = getKHop(vertex,
						new ArrayList<Vertex>(), depth, 0);
				

				return neighborhood;

	}

	/**
	 * This is a recursive program that finds the khop neighborhood of a given
	 * vertex
	 * 
	 * @param vertex
	 *            - the vertex of origin
	 * @param depth
	 *            - value of k
	 * @param level
	 *            - starting level is 0
	 * @return
	 */
	public ArrayList<ArrayList<Vertex>> getKHop(Vertex vertex,
			ArrayList<Vertex> visitedArray, int depth, int level) {
		


		if (visitedArray.contains(vertex))
			return null;

		// if you reach the depth desired, return the end vertex
		if (level == depth) {
			ArrayList<ArrayList<Vertex>> returnList = new ArrayList<ArrayList<Vertex>>();
			ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
			vertexList.add(vertex);
			returnList.add(vertexList);
			return returnList;
		}

		// if level < depth
		else {
			
			System.out.println("Executing vertex "+vertex+" at depth "+level);

			// arraylist of arraylist containing neighborlist
			ArrayList<ArrayList<Vertex>> neighborList = new ArrayList<ArrayList<Vertex>>();

			// get the edges
			Iterable<Edge> outEdges = vertex.getEdges(Direction.OUT);

			ArrayList<Vertex> tempVisitedArray = new ArrayList<Vertex>();
			tempVisitedArray.addAll(visitedArray);
			tempVisitedArray.add(vertex);

			// iterate through all the edges
			for (Edge edge : outEdges) {

				int templevel = level;

				// get the next level neighbor
				Vertex neighborVertex = edge.getVertex(Direction.IN);

				// get the neighborhood of the current vertex
				ArrayList<ArrayList<Vertex>> returnList = getKHop(
						neighborVertex, tempVisitedArray, depth, ++templevel);

				if (!(null == returnList)) {
					// add this list to the master list
					for (ArrayList<Vertex> vList : returnList) {
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

	public void pathQuery(ArrayList<QueryItem> query, Graph G) {

		long startTime = System.nanoTime();
		
	
		
		// get the first item in the query
		QueryItem startQuery = query.get(0);

		// get the vertices belonging to the first label type in the query
		Iterable<Vertex> vertices = G.getVertices("label1",
				nodeLabels[startQuery.getVertexLabel()]);

		// iterate through all the vertices and send only those vertices that
		// match all the constraints
		for (Vertex vertex : vertices) {

			ArrayList<ArrayList<Vertex>> path;
			path = getPathQuery(vertex, new ArrayList<Vertex>(), query, 0);

/*			// print the path results
			if (!(null == path))
				for (ArrayList<Vertex> neighbor : path) {
					for (Vertex v : neighbor) {
						System.out.print(v.getId() + " ");
					}
					System.out.println();
				}
*/
		}
		long stopTime = System.nanoTime();
		execTime = stopTime - startTime;

	}

	/**
	 * This is a recursive program that computes the path for a given query
	 * 
	 * @param vertex
	 *            - the current vertex
	 * @param visitedArray
	 *            - array of vertices that are already in the path. This
	 *            prevents cycles
	 * @param query
	 *            - An arraylist of query items
	 * @param level
	 *            - the current level of the query
	 * @return
	 */
	public ArrayList<ArrayList<Vertex>> getPathQuery(Vertex vertex,
			ArrayList<Vertex> visitedArray, ArrayList<QueryItem> query,
			int level) {

		
		// if the vertex is already in the path, return null
		if (visitedArray.contains(vertex))
			return null;

		QueryItem qItem = query.get(level);
		boolean match = true;

		// check if the label satisfies the query
		if (vertex.getProperty("label1").equals(
				nodeLabels[qItem.getVertexLabel()])) {

			// get the corresponding attributes for the vertex
			Map<String, Object> attributes = qItem.getVertexAttributeMap();

			// if the vertex does not have any attribtues, directly pass the
			// vertex
			if (!(null == attributes)) {
				for (Map.Entry<String, Object> attribute : attributes
						.entrySet()) {
					String value;
					if (!(null == (value = vertex.getProperty(attribute
							.getKey())))) {
						if (!(value.equals(attribute.getValue()))) {
							match = false;
							break;
						}
					} else {
						match = false;
						break;
					}

				}
			}

		} else
			return null;

		// if you reach the depth desired, return the end vertex if it satisfies
		// all the constraints. Else return null
		if (match) {

			if (level == query.size() - 1) {
				ArrayList<ArrayList<Vertex>> returnList = new ArrayList<ArrayList<Vertex>>();
				ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
				vertexList.add(vertex);
				returnList.add(vertexList);
				return returnList;
			} else {

				// arraylist of arraylist containing neighborlist
				ArrayList<ArrayList<Vertex>> neighborList = new ArrayList<ArrayList<Vertex>>();

				// get the edges
				Iterable<Edge> outEdges = vertex.getEdges(Direction.OUT);

				ArrayList<Vertex> tempVisitedArray = new ArrayList<Vertex>();
				tempVisitedArray.addAll(visitedArray);
				tempVisitedArray.add(vertex);

				// iterate through all the edges
				for (Edge edge : outEdges) {

					// if the edge label does not match, go to the next edge
					// if(!edge.getLabel().equals(edgeLabels[qItem.getEdgeLabel()]))
					// continue;

					// if the edge labels match, check if the attributes match
					// else{
					Map<String, Object> edgeAttributes = qItem
							.getEdgeAttributeMap();
					boolean edgeMatch = true;
					if (!(null == edgeAttributes)) {

						for (Map.Entry<String, Object> attribute : edgeAttributes
								.entrySet()) {
							String value;
							if (!(null == (value = edge.getProperty(attribute
									.getKey()))))
								if (value.equals(attribute.getValue())) {
									edgeMatch = false;
									break;
								}
						}
					}
					// if all the attributes match, go to the next level
					if (edgeMatch) {
						int templevel = level;
						// get the next level neighbor
						Vertex neighborVertex = edge.getVertex(Direction.IN);
						// get the neighborhood of the current vertex
						ArrayList<ArrayList<Vertex>> returnList = getPathQuery(
								neighborVertex, tempVisitedArray, query,
								++templevel);

						if (!(null == returnList)) {
							// add this list to the master list
							for (ArrayList<Vertex> vList : returnList) {
								ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
								vertexList.add(vertex);
								vertexList.addAll(vList);
								neighborList.add(vertexList);
							}
						}

					}

					// if the edge does not match the constraints go to the next
					// edge
					else
						continue;

					// }

				}

				// neighborhood list
				return neighborList;
			}
		}

		// if the vertex does not match the constrains return null
		else
			return null;

	}

	/**
	 * This method inserts an edge between all vertices that are k hops away from the given node
	 * @param id - id of the origin node
	 * @param G - the graph of concern
	 * @param k - depth 
	 */
	public void addNeighborAtKHop(Vertex vertex, Graph G, int k){
		
		ArrayList<ArrayList<Vertex>>  neighborhood = returnKhopNeighborhood(vertex , G, k);
		for(ArrayList<Vertex> neighbors : neighborhood){
			 Random rand = new Random();
			    int randomNum = rand.nextInt(8) ;
			G.addEdge(null, neighbors.get(0), neighbors.get(k), edgeLabels[randomNum]);
		//	System.out.println("Added edge between " +neighbors.get(0)+" and "+neighbors.get(k));
		}
		
	}
	
	public void verticesWithKNeighbors(Graph g, int k, String...labels ){
		
		Iterable<Vertex> vertices = g.getVertices();
		for(Vertex vertex : vertices){
			Iterable<Vertex> neighbors = vertex.getVertices(Direction.BOTH, labels);
			int i=0;
			for(Vertex v : neighbors){
				i++;
			}
		//	if (i==k)
		//		System.out.println(vertex.getId());
		}
	}
	
	public void reachability(long startVertex, long endVertex, int maxDepth){
		
	}
	
}
