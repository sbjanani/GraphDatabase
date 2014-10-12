package compare;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.tinkerpop.blueprints.TransactionalGraph;
import com.tinkerpop.blueprints.Vertex;

import com.tinkerpop.blueprints.util.wrappers.batch.BatchGraph;
import com.tinkerpop.blueprints.util.wrappers.batch.VertexIDType;
/**
 * This class has the code for the neo4j implementations of the graph
 * @author janani
 *
 */
public class GraphDB {
	
	
	String path ;
	TransactionalGraph graph;
	
	String[] nodelabels = {"golfclub", "friend", "family", "colleague", "bookclub", "pta", "neighborhood", "church"};
	String[] edgelabels = {"like", "comment", "add", "share", "invite", "post", "poke", "chat"};
	
	
	public GraphDB(TransactionalGraph graph, String path){
	
		this.path = path;	
		this.graph = graph;
	}

	/**
	 * @return the neo4jGraph
	 */
	public TransactionalGraph getGraph() {
		return graph;
	}



	/**
	 * This method creates the graphs from the edge list, feature names and features array
	 * @param edgeList - an arrayList containing the edges as a string array
	 * @param featNames - a map containing the array array location as the id and the featurename:value as the value
	 * @param feats - a map containing the node id as the id and the list of features as an array with 0 and 1 to indicate presence of the feature
	 */
	public void createGraph(ArrayList<String[]> edgeList, Map<Integer,String> featNames, Map<String,ArrayList<Integer>> feats ){
		
		
		
		
		
		
				
		Map<String, Vertex> vertexList = new HashMap<String,Vertex>();
		
		// iterate through the feats map and create a vertex object for each node
		for(Map.Entry<String, ArrayList<Integer>> entry : feats.entrySet()){
			
			// create the vertex object and add it to the graph. make the key of the map to be the id of the node
			Vertex vertex = graph.addVertex(entry.getKey());
			
			ArrayList<Integer> features = entry.getValue();
			
			// iterate through the arrayList of attributes and add them as properties to the vertex
			for(int index=0; index<features.size(); index++){
				
				// if the entry in the feats arrayList is 1, it means the node as the property mentioned in the featNames map under the corresponding entry
				if(features.get(index)==1){
					
					// get the feature and value for the specific index
					String feature = featNames.get(index);
					
					// the feature and value are specified in the format feature:value in the data file. So parse it using the ":" separator
					String[] featValues = feature.split(":");
					
					if(featValues.length==1)
						vertex.setProperty(featValues[0], " ");
					else
						// the first entry in the split array becomes the feature and the second entry becomes the value. Add it to the vertex
						vertex.setProperty(featValues[0], featValues[1]);
				}
				
			}
			
			// add the vertex to the vertexList Map
			vertexList.put(entry.getKey(), vertex);
			
		}
		
		// iterate through the edges and add an edge between vertices
		for(String[] edges : edgeList){
			
			Vertex outVertex = vertexList.get(edges[0]);
			Vertex inVertex = vertexList.get(edges[1]);
			
			// add the edge to the graph
			graph.addEdge(null, outVertex, inVertex, "knows");
			
		}
		
		graph.shutdown();
	}
	
	public void createGraph(String sourceFile){
		
	
		BatchGraph<TransactionalGraph> bgraph = new BatchGraph<TransactionalGraph>(graph, VertexIDType.NUMBER, 1000);
		//bgraph.setVertexIdKey("ident");
		FileReader iStream;
		BufferedReader bReader;
		try {
			
			iStream = new FileReader(sourceFile);
			bReader = new BufferedReader(iStream);		 

			
			for(String line; (line=bReader.readLine())!=null;){
				
				
				String[] entireVertex = line.split(";");
				
				String[] part1 = entireVertex[0].split(" ");
				// create vertex
				Vertex vertex = bgraph.getVertex(Long.parseLong(part1[0]));
				if(vertex==null)
					vertex = bgraph.addVertex(Long.parseLong(part1[0]));
				
				// add properties				
				vertex.setProperty("ident", Long.parseLong(part1[0]));
				vertex.setProperty("label1", nodelabels[Integer.parseInt(part1[1])]);
				
				if(entireVertex.length>=2 && !entireVertex[1].equals("")){
					
					String[] part2 = entireVertex[1].split(" ");
					for(String property : part2){
						String[] propValue = property.split(":");
						if(propValue.length==2)
						vertex.setProperty(propValue[0], propValue[1]);				
					}
					
					if(entireVertex.length>=3 && !entireVertex[2].equals("")){
						// add edges
						String[] edges = entireVertex[2].split(":");
						for(String edge : edges){
							String[] edgePart = edge.split(" ");
							if(edgePart.length==2){
							Vertex toVertex = null;
							try{
								toVertex = bgraph.getVertex(Long.parseLong(edgePart[0]));
								if(toVertex==null)
									toVertex = bgraph.addVertex(Long.parseLong(edgePart[0]));
							}
							catch(NumberFormatException nex){
								System.out.println("skipping "+edge);
								continue;
							}
							
							bgraph.addEdge(null, vertex, toVertex, edgelabels[Integer.parseInt(edgePart[1])]);
							
							
							
							
						}
					}
}
					
				}
				
				
				System.out.println("Writing vertex "+ part1[0]);
				System.out.println("ident "+vertex.getProperty("ident"));
			}
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		graph.shutdown();
		
		
	}
	
}
