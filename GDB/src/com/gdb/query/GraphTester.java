package com.gdb.query;

import java.io.IOException;

public class GraphTester {
	public static void main(String[] args) throws IOException{
		Graph g = new Graph("./");
		Vertex v = g.getVertex(7);
		System.out.println("V id and label: "+v);
		
		System.out.println(v.getEdges(Direction.OUT));
		System.out.println(v.getEdges(Direction.IN));
		 //System.out.println(g.getGraphIndex());
		//System.out.println(g.getTypeIndex());
		//System.out.println(g.getVertex(65));
		//System.out.println(g.getVertices(0));
		//Query q = new Query();
		//q.khopNeighborhood(v, 2, "");
	}
}
