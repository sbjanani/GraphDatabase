package compare;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;
import com.tinkerpop.blueprints.Direction;




public class GraphTester {
	
	String[] nodelabels = {"golfclub", "friend", "family", "colleague", "bookclub", "pta", "neighborhood", "church"};
	String[] edgelabels = {"like", "comment", "add", "share", "invite", "post", "poke", "chat"};
	public static void main(String[] args) throws IOException{
		
		/*RandomAccessFile rag = new RandomAccessFile("/home/data/Gplus/nodefile0.dat","r");
		System.out.println(rag.readInt());
		rag.close();*/
		Neo4jGraph g = new Neo4jGraph("/home/data/Gplus/Neo4j_data/");
		Queries q = new Queries();	
		long totaltime=0;
		int k = 1;
		String[] labels = {"like","post"};
		File file = new File("/home/data/Gplus/idFile.dat");
		
		
		Scanner scanner = new Scanner(file);		
		

		/*System.out.println("k="+k);
		System.out.println("labels :"+labels);
		System.out.println("direction :OUT");*/
		while(scanner.hasNext()){
			int i = scanner.nextInt();
			//System.out.println("id="+i);
			Iterable<Vertex> vertices = g.getVertices("ident", i);
			for(Vertex v : vertices){
				
			
			//System.out.println("V id and label: "+v+" "+v.getProperty("label"));
			/*Iterable<Edge> outEdges = v.getEdges(Direction.OUT);
			Iterable<Edge> inEdges = v.getEdges(Direction.IN);
			System.out.println("Out edges");
			int numoutedges=0;
			int numinedges=0;
			for(Edge edge : outEdges)
				numoutedges++;
				System.out.println("Number of out edges = "+numoutedges);
				//System.out.println(edge.getVertex(Direction.IN));
			//System.out.println("In edges");
			for(Edge edge : inEdges)
				numinedges++;
			System.out.println("Number of in edges = "+numinedges);
				//System.out.println(edge.getVertex(Direction.OUT));*/
			
			 //System.out.println(g.getGraphIndex());
			//System.out.println(g.getTypeIndex());
			//System.out.println(g.getVertex(65));
			//System.out.println(g.getVertices(0));*/
			
				
			
			
			
			long startTime = System.currentTimeMillis();
			q.khopNeighborhood(v, g, k);//use vertex 355
			 //v.getEdges(Direction.OUT);
			//q.khopNeighborhood(v, g, k, labels);
			totaltime +=System.currentTimeMillis()-startTime;
			}
		}
		
		System.out.println("Totaltime = "+totaltime);
		System.out.println("**********************************");
		scanner.close();
		
		/*
		int numoutedges=0;
		Iterable<Vertex> vertices = g.getVertices("ident", 44);
		for(Vertex v : vertices){
			String[] labels = {"like","post"};
			q.khopNeighborhood(v, g, k, labels);
			/*Iterable<Edge> outEdges = v.getEdges(Direction.OUT);
			for(Edge edge : outEdges)
				numoutedges++;
			 System.out.println("Number of out edges = "+numoutedges);*/
				//System.out.println(edge.getVertex(Direction.IN).getProperty("ident")+" "+edge.getLabel());
		//}
		
	}
}
