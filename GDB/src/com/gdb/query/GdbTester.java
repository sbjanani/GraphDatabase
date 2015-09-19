package com.gdb.query;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.start.Initialization;
import com.compare.Queries;
import com.gdb.util.Constants;





public class GdbTester {
	String[] nodelabels = {"golfclub", "friend", "family", "colleague", "bookclub", "pta", "neighborhood", "church"};
	String[] edgelabels = {"like", "comment", "add", "share", "invite", "post", "poke", "chat"};
	static long totaltime=0;
	static long cachehit = 0;
	static long cachemiss = 0;

	
	public static void main(String args[]) throws IOException, SQLException{
		
		Graph g = new Graph("/home/data/Gplus/biggraphs/gdb_data/");
		GdbTester gTester = new GdbTester();
		//Initialization init = new Initialization();
		//init.pathQueryInitialization(1, 6);
		System.out.println("Initial index size "+g.getGraphIndex().size());
		gTester.kHop(g, 1514564, 3, new Byte[]{3,2,6,5});
		//System.out.println(g.getVertex(17299).getEdges(Direction.OUT));
		//GdbTester gTester = new GdbTester();
		//gTester.pathQuery(g);
		//gTester.getProperties(g, 0);
		//gTester.getNeighbors(g,4000000);
		//System.out.println(g.getGraphIndex().get(0).getPropBitmap());
		System.out.println("Cache hit = "+cachehit+" Cache miss = "+cachemiss);
		System.out.println("Final index size "+g.getGraphIndex().size());
		
	}
	
	public void kHop(Graph g,int id, int k, Byte ... labelList){
		System.out.println("GDB");
		Query q = new Query(g);
		
	
		
			long startTime = System.currentTimeMillis();
			
			try {
				Vertex v = g.getVertex(id);		
				q.khop(v, k, Direction.OUT, labelList);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			totaltime +=System.currentTimeMillis()-startTime;
		
		
		System.out.println("Totaltime = "+totaltime);
		System.out.println("*********");
		
		
	}
	
	public void getNeighbors(Graph g, int id){
		Vertex v;
		try {
			v = g.getVertex(id);
			ArrayList<Integer> neighbors = v.getEdges(Direction.BOTH);
			System.out.println("Neighbors of "+id+" "+neighbors);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void getProperties(Graph g, int id){
		try {
			Vertex v = g.getVertex(id);
			Map<String,Object> properties = v.getAttributes();
			for(Map.Entry<String, Object> prop : properties.entrySet())
				System.out.println(prop.getKey()+" : "+prop.getValue());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void pathQuery(Graph g){
		
		Query q = new Query(g);	
		
		
		
		ArrayList<ArrayList<QueryItem>> queries;
		try {
			ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(Constants.QUERTY_INPUT_PATH));
			queries = (ArrayList<ArrayList<QueryItem>>)inStream.readObject();
			int i=0;
			for(ArrayList<QueryItem> query : queries){	
				System.out.println("Running query "+ ++i);
				q.pathQuery(query, g);
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
	
		System.out.println("GDB");
		System.out.println("Total time "+Query.execTime);
		System.out.println("Total entry "+Query.entryTimes);
		System.out.println("Prop check time "+Query.propCheckTime);
		System.out.println("**********************************");
	}
	
	public void structureQuery(Graph g, Byte vertexLabel, Map<Byte,Short> edgeCountMap){
		Query q = new Query(g);
		q.getStructure(vertexLabel, edgeCountMap);
		
		System.out.println("GDB");
		System.out.println("Total time "+Query.execTime);
	
	}
	
	
}
