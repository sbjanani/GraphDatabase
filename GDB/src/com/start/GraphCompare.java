package com.start;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.compare.Queries;
import com.gdb.query.Graph;
import com.gdb.util.Constants;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;






public class GraphCompare {

	com.compare.NeoTester neoTester = new com.compare.NeoTester();
	com.gdb.query.GdbTester gdbTester = new com.gdb.query.GdbTester();
	
	public static void main(String args[]) throws IOException, SQLException{
		
		GraphCompare compare = new GraphCompare();
		
		for(int i=0; i<1; i++){
					
			Graph gdbGraph = new Graph("/home/data/Gplus/gdb_data/");
			Neo4jGraph neoGraph = new Neo4jGraph("/home/data/Gplus/Neo4j_data/");		
			//SparkseeGraph sparekseeGraph1 = new SparkseeGraph("/home/data/Gplus/Dex_data/sparksee.gdb");
		   //compare.khop(gdbGraph,neoGraph);
		  compare.path(gdbGraph, neoGraph);
			//compare.structure();
			
			/**/
			
			//neoGraph.shutdown();
		}
	}
	
	public void structure() throws IOException{
		
		Graph gdbGraph1 = new Graph("/home/data/Gplus/gdb_data/");
		Neo4jGraph neoGraph1 = new Neo4jGraph("/home/data/Gplus/Neo4j_data/");
		//SparkseeGraph sparekseeGraph1 = new SparkseeGraph("/home/data/Gplus/Dex_data/sparksee.gdb");
		byte vertexLabel = (byte)new Random().nextInt(Constants.NUMBER_OF_NODE_TYPES);
		Map<Byte,Short> edgeCountMap = new HashMap<Byte,Short>();
		int numOfEdgeLabels = (byte)new Random().nextInt(Constants.NUMBER_OF_EDGE_TYPES);
		for(byte i=0; i<numOfEdgeLabels; i++){
			if(!edgeCountMap.containsKey(i))
				edgeCountMap.put(i, (short)new Random().nextInt(20));
		}
		
		System.out.println("label = "+vertexLabel+" edgeCountMap="+edgeCountMap.toString());
		gdbTester.structureQuery(gdbGraph1, vertexLabel, edgeCountMap);
		neoTester.structure(neoGraph1, vertexLabel, edgeCountMap);
		//neoTester.structure(sparekseeGraph1, vertexLabel, edgeCountMap);
		neoGraph1.shutdown();
	}
	
	public void khop(Graph gdbGraph, Neo4jGraph neoGraph){
		int id= new Random().nextInt(132669);
		int k=8;
		int numOfLabels = new Random().nextInt(Constants.NUMBER_OF_EDGE_TYPES);			
		Byte[] labels = new Byte[numOfLabels];
		Set<Byte> labelSet = new HashSet<Byte>();
		Random rand = new Random();
		for(int j=0; j<numOfLabels; j++){
			labelSet.add((Byte)(byte)rand.nextInt(Constants.NUMBER_OF_EDGE_TYPES));
			
		}
		labels = labelSet.toArray(new Byte[labelSet.size()]);
		System.out.println("k= "+k+" id= "+id+"\nlabels="+Arrays.toString(labels));
		gdbTester.kHop(gdbGraph, id,k,labels);
		neoTester.getKHop(neoGraph, id,k,labels);
	}
	
	public void path(Graph gdbGraph, Neo4jGraph neoGraph){
		Initialization init = new Initialization();
		init.pathQueryInitialization(1, 6);
		gdbTester.pathQuery(gdbGraph);
		neoTester.pathQuery(neoGraph);
	}
}
