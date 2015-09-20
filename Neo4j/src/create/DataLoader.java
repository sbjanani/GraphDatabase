/**
 * This file populates the "graph.idx" file
 * @author janani
 *
 */
package create;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.tinkerpop.blueprints.Edge;
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

	Map<Integer,Map<String,Object>> propertiesMap = new HashMap<Integer,Map<String,Object>>();

	String[] nodelabels = {"golfclub", "friend", "family", "colleague", "bookclub", "pta", "neighborhood", "church"};
	String[] edgelabels = {"like", "comment", "add", "share", "invite", "post", "poke", "chat"};
	/**
	 * This is the constructor for the GraphIndex. It sets the input and output
	 * directories.
	 *
	 * @param sourcePath
	 * @throws FileNotFoundException
	 */
	public DataLoader(String sourcePath)
			throws FileNotFoundException {

		this.sourcePath = sourcePath;

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
	 *            - location where the input edge file is stored
	 * @return
	 * @throws FileNotFoundException
	 */
	public void readEdges() throws FileNotFoundException {

		adjArray = new ArrayList<AdjacencyRecord>();
		for (int i = 0; i < nodeTypes.size(); i++) {
			adjArray.add(new AdjacencyRecord());
		}


		int count=0;
		Scanner s = new Scanner(new File(sourcePath + "/rel2.dat"));
		while (s.hasNext()) {

			String[] line = s.nextLine().split(" ");

			int fromNode = Integer.parseInt(line[0])-1;
			int toNode = Integer.parseInt(line[1])-1;
			byte edgeType = Byte.parseByte(line[2]);
			Map<String,Object> pMap = new HashMap<String,Object>();
			for(int i=3; i<line.length; i +=2){
				pMap.put(line[i], line[i+1]);
			}


			adjArray.get(fromNode).addOutGoing(toNode, edgeType,pMap);
			adjArray.get(toNode).addIncoming(fromNode, edgeType, pMap);

			if (s.hasNext())
				s.nextLine();

			System.out.println("Edge count "+ ++count);
		}

		s.close();

	}

	public void readProperties() throws FileNotFoundException{

		Scanner scanner = new Scanner(new File(sourcePath+"/gplus1.dat"));

		while(scanner.hasNext()){
			String line = scanner.nextLine();
			String[] lineArray = line.split(";");
			int nodeid = Integer.parseInt(lineArray[0].split(" ")[0])-1;
			if(lineArray.length==2){
				String[] propArray = lineArray[1].split(" ");
				Map<String,Object> propMap = new HashMap<String,Object>();
				if(propArray.length>0)
					for(String prop : propArray){
						String[] property = prop.split(":");
						if(property.length==2)
							propMap.put(property[0], property[1]);
					}
				propertiesMap.put(nodeid, propMap);

			}

		}
		scanner.close();
	}

	/**
	 * This method writes the node index information into the file graph.idx
	 * @param bgraph
	 *            - An instance of the Neo4J graph
	 * @throws IOException
	 */


	public void writeGraph(BatchGraph<TransactionalGraph> bgraph) throws IOException {


		BufferedReader reader = new BufferedReader(new FileReader(sourcePath + "/rel.dat"));
		BufferedReader vReader = new BufferedReader(new FileReader(sourcePath+"/gplus3.dat"));
		String[] props = new String[]{"gender","last_name","place","job_title","university","institution"};



		String line;
		int count=0;
		while((line=vReader.readLine())!=null){

			String[] lineArray = line.split(" ");
			long nodeId = Long.parseLong(lineArray[0]);
			Vertex vertex = bgraph.addVertex(new Long(nodeId));
			vertex.setProperty("ident", nodeId);
			vertex.setProperty("lable",Integer.parseInt(lineArray[1]));
			if(lineArray.length>2){
				for(int i=2; i<8; i++){
					if(lineArray[i]!="//N")
						vertex.setProperty(props[i-2], lineArray[i]);
				}
			}

			if(count%1000==0)
				System.out.println("Writing vertex "+count);
			count++;

		}
		vReader.close();




		count=0;
		while((line=reader.readLine())!=null)
		{
			String[] entry = line.split(" ");
			int fromNode = Integer.parseInt(entry[0]);
			int toNode = Integer.parseInt(entry[1]);
			byte edgeType = Byte.parseByte(entry[2]);

			Vertex fromVertex = bgraph.getVertex(new Long(fromNode));
			Vertex toVertex = bgraph.getVertex(new Long(toNode));

			try{
				Edge edge = bgraph.addEdge(null, fromVertex, toVertex, edgelabels[edgeType]);
				if(entry.length>3){
					for(int i=3; i<entry.length; i +=2){
						edge.setProperty(entry[i], entry[i+1]);
					}
				}

				if(count%1000==0)
					System.out.println("Writing edge count ="+count);

				count++;
			}
			catch(IllegalArgumentException ex){
				System.out.println(fromVertex.getId()+ " "+toVertex.getId());
			}


			//System.out.println("Writing edge count ="+ ++count);
		}

		bgraph.shutdown();
		reader.close();
	}

	public void displayAdjRecord() {

		System.out.println(adjArray);
		for (int i = 0; i < adjArray.size(); i++) {
			System.out.print("i= " + i);
			System.out.println(adjArray.get(i));
		}
	}

    /**
     *
     * @param bgraph - The Transactional Graph created
     * @throws Exception
     */
    public void readAndWriteGraph(BatchGraph<TransactionalGraph> bgraph) throws Exception{
        BufferedReader reader = new BufferedReader(new FileReader(sourcePath + "/rel_outgoing.dat"));

        String[] props = new String[]{"gender","last_name","place","job_title","university","institution"};



        String line;
        int count=0;
        while((line=reader.readLine())!=null){

            String[] lineArray = line.split(" ");
            long nodeId = Long.parseLong(lineArray[0]);
            Vertex vertex;
            if(bgraph.getVertex(nodeId)==null)
                 vertex = bgraph.addVertex(nodeId);
            else
                 vertex = bgraph.getVertex(nodeId);
            vertex.setProperty("ident", nodeId);
            vertex.setProperty("lable",Integer.parseInt(lineArray[1]));
            Vertex neighborVertex;
            if(lineArray.length>=4){
                long neighborId = Long.parseLong(lineArray[2]);
                if(bgraph.getVertex(neighborId)==null)
                    neighborVertex = bgraph.addVertex(neighborId);
                else
                    neighborVertex = bgraph.getVertex(neighborId);
                bgraph.addEdge(null,vertex,neighborVertex,edgelabels[Integer.parseInt(lineArray[3])]);

                // Add properties if present
                if(lineArray.length>4){
                    for(int i=2; i<8; i++){
                        if(lineArray[i]!="//N")
                            vertex.setProperty(props[i-2], lineArray[i]);
                    }
                }

            }



            if(count%100000==0)
                System.out.println("Writing Edge "+count);
            count++;

        }
        reader.close();

    }

}