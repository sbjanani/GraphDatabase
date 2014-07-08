package com.gdb.query;

import java.io.IOException;

public class GraphTester {
	public static void main(String[] args) throws IOException{
		Graph g = new Graph("/Users/Naveen/DB400Nodes/");
		//System.out.println(g.getGraphIndex());
		//System.out.println(g.getTypeIndex());
		System.out.println(g.getVertex(65));
		//System.out.println(g.getVertices(4));
	}
}
