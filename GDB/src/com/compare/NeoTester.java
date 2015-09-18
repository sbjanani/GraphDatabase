package com.compare;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.gdb.query.QueryItem;
import com.gdb.util.Constants;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.GraphFactory;
import com.tinkerpop.blueprints.Parameter;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;
import com.tinkerpop.blueprints.Direction;




public class NeoTester { 
	
	static long totaltime=0;
	String[] nodelabels = {"golfclub", "friend", "family", "colleague", "bookclub", "pta", "neighborhood", "church"};
	String[] edgelabels = {"like", "comment", "add", "share", "invite", "post", "poke", "chat"};	
	
	public  void start() throws IOException{
		Queries q = new Queries();	
		//Graph g = GraphFactory.open("graph.properties");
		Neo4jGraph g = new Neo4jGraph("/home/data/Gplus/Neo4j_data/");		
		NeoTester gTester = new NeoTester();
		//gTester.getProperties(18786);
		//gTester.getNeighbors(100567);
		
		gTester.pathQuery(g);
		g.shutdown();
		
		}
		

		/*
		*/
		
	public void getNeighbors(int id, Graph g, Queries q){
		Iterable<Vertex> vertices = g.getVertices("ident", id);
		for(Vertex v : vertices){			
			Iterable<Edge> outEdges = v.getEdges(Direction.OUT);
			for(Edge edge : outEdges)				
				System.out.println(edge.getVertex(Direction.IN).getProperty("ident")+" ");
			
		}
	}
	
	public Vertex getVertex(int ident, Graph g, Queries q){
		Iterable<Vertex> vertices = g.getVertices("ident", ident);
		for(Vertex v : vertices){
			//String[] labels = {"like","post"};
			//q.khopNeighborhood(v, g, k, labels);
			//Iterable<Edge> outEdges = v.getEdges(Direction.OUT);
			//for(Edge edge : outEdges)
				//numoutedges++;
			 //System.out.println("Number of out edges = "+numoutedges);
				//System.out.println(edge.getVertex(Direction.IN).getProperty("ident")+" "+edge.getLabel());
			//q.getVertexWithProp(g, v);
			return v;
		}
		return null;
	}
		
	public void getKHop(Graph g, int id, int k, Byte ... labelList){
		Queries q = new Queries();
		System.out.println("Neo4j");
		String [] labels = new String[labelList.length];
		for(int i=0; i<labelList.length; i++)
			labels[i]=edgelabels[labelList[i]];
			
		
			Iterable<Vertex> vertices = g.getVertices("ident", id);
			for(Vertex v : vertices){			
			long startTime = System.currentTimeMillis();
			q.khopNeighborhood(v, g, k, labels);
			totaltime +=System.currentTimeMillis()-startTime;
			}
		
		
		System.out.println("Totaltime = "+totaltime);
		System.out.println("**********************************");
		
	}
		
	public void structure(Graph g, byte vertexLabel, Map<Byte,Short> edgeCountMap){
		Queries q= new Queries();
		System.out.println("Neo4j");
		Map<String,Short> edgeMap = new HashMap<String,Short>();
		for(Map.Entry<Byte, Short> entry : edgeCountMap.entrySet())
			edgeMap.put(edgelabels[entry.getKey()], entry.getValue());
		q.structure(g, vertexLabel, edgeMap);
		
		System.out.println("Total time "+Queries.execTime);
		System.out.println("*****************************************************************************************");
	}
	
	
	public void getProperties(int id, Graph g, Queries q){
		//int numoutedges=0;
		Iterable<Vertex> vertices = g.getVertices("ident", id);
		for(Vertex v : vertices){
			//String[] labels = {"like","post"};
			//q.khopNeighborhood(v, g, k, labels);
			//Iterable<Edge> outEdges = v.getEdges(Direction.OUT);
			//for(Edge edge : outEdges)
				//numoutedges++;
			 //System.out.println("Number of out edges = "+numoutedges);
				//System.out.println(edge.getVertex(Direction.IN).getProperty("ident")+" "+edge.getLabel());
			q.getVertexWithProp(g, v);
		}
		
	}
	
	public void pathQuery(Graph g ){
		Queries q = new Queries();
		
		ArrayList<ArrayList<QueryItem>> queries;
		try {
			ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(Constants.QUERTY_INPUT_PATH));
			queries = (ArrayList<ArrayList<QueryItem>>)inStream.readObject();
			int i=0;
			for(ArrayList<QueryItem> query : queries)	{	
				System.out.println("Running query "+ ++i);
				q.pathQuery(query, g);
				//q.getPathQuery(v, new ArrayList<Vertex> () , query, 0);
			}
			inStream.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		System.out.println("Neo4J");
		System.out.println("Total time "+Queries.execTime);
		System.out.println("Total entry "+Queries.entryTimes);
		System.out.println("Prop check time "+Queries.propCheckTime);
		System.out.println("*****************************************************************************************");
		
		
	}
}
