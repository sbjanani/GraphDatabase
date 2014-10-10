package com.gdb.query;

import java.io.IOException;




public class GraphTester {
	public static void main(String[] args) throws IOException{
		
		/*RandomAccessFile rag = new RandomAccessFile("/home/data/Gplus/nodefile0.dat","r");
		System.out.println(rag.readInt());
		rag.close();*/
		Graph g = new Graph("/home/data/Gplus/gdb_data/");
		Vertex v = g.getVertex(355);
		System.out.println("V id and label: "+v);
		//System.out.println(v.getEdges(Direction.OUT).size());
		//System.out.println(v.getEdges(Direction.IN).size());
		 //System.out.println(g.getGraphIndex());
		//System.out.println(g.getTypeIndex());
		//System.out.println(g.getVertex(65));
		//System.out.println(g.getVertices(0));*/
		Query q = new Query(g);		
		q.khopNeighborhood(v, 3, "");//use vertex 355
	}
}
