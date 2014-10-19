package com.gdb.query;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;





public class GraphTester {
	String[] nodelabels = {"golfclub", "friend", "family", "colleague", "bookclub", "pta", "neighborhood", "church"};
	String[] edgelabels = {"like", "comment", "add", "share", "invite", "post", "poke", "chat"};
	public static void main(String[] args) throws IOException{
		
		
		Graph g = new Graph("/home/data/Gplus/gdb_data/");
		long totaltime=0;
		Query q = new Query(g);		
		int k = 4;
		
	/*	File idFile = new File("/home/data/Gplus/idFile.dat");
		FileWriter writer = new FileWriter(idFile);
		String strIds = "";*/
		
		System.out.println("k="+k);
		File file = new File("/home/data/Gplus/idFile.dat");		
		
		Scanner scanner = new Scanner(file);	
		while(scanner.hasNext()){
			int i = scanner.nextInt();
		
		Vertex v = g.getVertex(i);	
		
		//System.out.println("id="+i);
		long startTime = System.currentTimeMillis();
		q.khop(v,k);//use vertex 355
		totaltime +=System.currentTimeMillis()-startTime;
		}
		System.out.println("Totaltime = "+totaltime);
		System.out.println("**********************************");
		
		/*writer.write(strIds);
		writer.close();*/
		scanner.close();
		
		/*Vertex v = g.getVertex(87953);
		System.out.println("V id and label: "+v);
		System.out.println(v.getEdges(Direction.BOTH));
		/*System.out.println(v.getEdges(Direction.IN).size());*/
		// System.out.println(g.getGraphIndex());
		//System.out.println(g.getTypeIndex());
		//System.out.println(g.getVertex(65));
		//System.out.println(g.getVertices(0));*/
		
	}
}
