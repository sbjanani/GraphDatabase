package com.gdb.query;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;





public class GraphTester {
	String[] nodelabels = {"golfclub", "friend", "family", "colleague", "bookclub", "pta", "neighborhood", "church"};
	String[] edgelabels = {"like", "comment", "add", "share", "invite", "post", "poke", "chat"};
	public static void main(String[] args) throws IOException{
		
		
		Graph g = new Graph("/home/data/Gplus/gdb_data/");
		long totaltime=0;
		Query q = new Query(g);		
		int k = 1;
		ArrayList<Byte> labelList = new ArrayList<Byte>();
		labelList.add((byte)5);
		labelList.add((byte)0);
		// uncomment this block if you want to test with random nodes
	/*	File idFile = new File("/home/data/Gplus/idFile.dat");
		FileWriter writer = new FileWriter(idFile);
		String strIds = "";*/
		
		// uncomment this block for k-hop testing of 100 nodes
		/*System.out.println("k="+k);
		System.out.println("labels :"+labelList);
		System.out.println("direction :OUT");*/
		
		File file = new File("/home/data/Gplus/idFile.dat");		
		
		Scanner scanner = new Scanner(file);	
		while(scanner.hasNext()){
			int i = scanner.nextInt();
		
			Vertex v = g.getVertex(i);		
			//System.out.println("id="+i);
			
			
			
			long startTime = System.currentTimeMillis();
			//v.getEdges(Direction.OUT);
			//q.khop(v, k, Direction.OUT, labelList);
			q.khop(v,k,Direction.OUT);
			totaltime +=System.currentTimeMillis()-startTime;
		}
		System.out.println("Totaltime = "+totaltime);
		System.out.println("**********************************");
		scanner.close();
		
		/*writer.write(strIds);
		writer.close();*/
		
		
		
		/*Vertex v = g.getVertex(44);
		//System.out.println("V id and label: "+v);
		ArrayList<Byte> labelList = new ArrayList<Byte>();
		labelList.add((byte)5);
		labelList.add((byte)0);
		q.khop(v, k, Direction.OUT, labelList);
		//System.out.println(v.getEdges(Direction.OUT,labelList).size());
		//System.out.println(v.getEdges(Direction.OUT, labelList));
		//System.out.println(v.getEdges(Direction.OUT));
		// System.out.println(g.getGraphIndex());
		//System.out.println(g.getTypeIndex());
		//System.out.println(g.getVertex(65));
		//System.out.println(g.getVertices(0));*/
		
		
		
		
	}
}
