/**
 * This file populates the "graph.idx" file
 * @author janani
 *
 */
package create;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import com.tinkerpop.blueprints.TransactionalGraph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jVertex;
import com.tinkerpop.blueprints.util.wrappers.batch.BatchGraph;
import com.tinkerpop.blueprints.util.wrappers.batch.VertexIDType;

public class DataLoader {
	ArrayList<Byte> nodeTypes;
	ArrayList<AdjacencyRecord> adjArray;
	String sourcePath;
	String destinationPath;

	String[] nodelabels = {"golfclub", "friend", "family", "colleague", "bookclub", "pta", "neighborhood", "church"};
	String[] edgelabels = {"like", "comment", "add", "share", "invite", "post", "poke", "chat"};
	/**
	 * This is the constructor for the GraphIndex. It sets the input and output
	 * directories.
	 * 
	 * @param nodeFilePath
	 * @param edgeFilePath
	 * @throws FileNotFoundException
	 */
	public DataLoader(String sourcePath, String destinationPath)
			throws FileNotFoundException {

		this.sourcePath = sourcePath;
		this.destinationPath = destinationPath;
	}

	/**
	 * This method reades the nodes.dat file and saves the result in the
	 * arraylist nodeTypes
	 * 
	 * @throws FileNotFoundException
	 */
	public void readNodes() throws FileNotFoundException {

		Scanner s = new Scanner(new File(sourcePath + "/nodes.dat"));
		System.out.println(sourcePath + "/nodes.dat");
		nodeTypes = new ArrayList<Byte>();
		while (s.hasNext()) {
			nodeTypes.add(s.nextInt() - 1, s.nextByte());
			if (s.hasNext())
				s.nextLine();
		}
		s.close();
	}

	/**
	 * This method reads the input edge file and creates an ArrayList with the
	 * edge information
	 * 
	 * @param path
	 *            - location where the input edge file is stored
	 * @return
	 * @throws FileNotFoundException
	 */
	public void readEdges() throws FileNotFoundException {

		adjArray = new ArrayList<AdjacencyRecord>();
		for (int i = 0; i < nodeTypes.size(); i++) {
			adjArray.add(new AdjacencyRecord());
		}

		

		Scanner s = new Scanner(new File(sourcePath + "/rel.dat"));
		while (s.hasNext()) {

			int fromNode = s.nextInt() - 1;
			int toNode = s.nextInt() - 1;
			byte edgeType = s.nextByte();


			adjArray.get(fromNode).addOutGoing(toNode, edgeType);
			adjArray.get(toNode).addIncoming(fromNode, edgeType);
			
			if (s.hasNext())
				s.nextLine();
		}

		s.close();

	}

	/**
	 * This method writes the node index information into the file graph.idx
	 * 
	 * @param path
	 *            - location where the output file "graph.idx" is stored
	 * @param edgeArray
	 *            - arraylist containing the edge information
	 * @throws IOException
	 */
	

	public void writeGraph() throws IOException {
		

		
		TransactionalGraph graph = new Neo4jGraph(destinationPath);
		BatchGraph<TransactionalGraph> bgraph = new BatchGraph<TransactionalGraph>(graph, VertexIDType.NUMBER, 1000);
		bgraph.setLoadingFromScratch(true);	
		
		for (int node = 0; node < adjArray.size(); node++) {
			
			Vertex vertex  = bgraph.getVertex(new Long(node));
			
			if(null==vertex){
				vertex = bgraph.addVertex(new Long(node));
				
			}
			vertex.setProperty("label", nodelabels[nodeTypes.get(node)]);
			
			
			Map<Byte, ArrayList<NeighborNodeRecord>> outgoing = adjArray.get(
					node).getOutGoing();

		
			int count=0;
			// iterate through the outgoing list
			for (Map.Entry<Byte, ArrayList<NeighborNodeRecord>> outEntry : outgoing
					.entrySet()) {
				
				// get the arraylist of outgoing neighbors per edge type
				ArrayList<NeighborNodeRecord> outlist = outEntry.getValue();

				// iterate through each incoming neighbor
				if (!(null == outlist))
					for (NeighborNodeRecord outNeighbor : outlist) {
						
						Vertex outVertex = bgraph.getVertex(new Long(outNeighbor.getNeighborNode()));
						if(outVertex==null)
							outVertex = bgraph.addVertex(outNeighbor.getNeighborNode());
												
						bgraph.addEdge(null, vertex, outVertex, edgelabels[outNeighbor.getEdgeType()]);
						count++;
						
					}

			}

					
			System.out.println("Writing node "+node+" assigned id ="+vertex.getId());
			System.out.println("number of out neighbors = "+count);

		}
	
		bgraph.shutdown();
		
	}

	public void displayAdjRecord() {

		System.out.println(adjArray);
		for (int i = 0; i < adjArray.size(); i++) {
			System.out.print("i= " + i);
			System.out.println(adjArray.get(i));
		}
	}

}