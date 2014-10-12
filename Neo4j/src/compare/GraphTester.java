package compare;

import java.io.IOException;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;




public class GraphTester {
	public static void main(String[] args) throws IOException{
		
		/*RandomAccessFile rag = new RandomAccessFile("/home/data/Gplus/nodefile0.dat","r");
		System.out.println(rag.readInt());
		rag.close();*/
		Neo4jGraph g = new Neo4jGraph("/home/data/Gplus/Neo4j_data/");
		Vertex v = g.getVertex(45);
		System.out.println("V id and label: "+v+" "+v.getProperty("label"));
	/*	Iterable<Edge> outEdges = v.getEdges(Direction.OUT);
		Iterable<Edge> inEdges = v.getEdges(Direction.IN);
		System.out.println("Out edges");
		for(Edge edge : outEdges)
			System.out.println(edge.getVertex(Direction.IN));
		System.out.println("In edges");
		for(Edge edge : inEdges)
			System.out.println(edge.getVertex(Direction.OUT));*/
		
		 //System.out.println(g.getGraphIndex());
		//System.out.println(g.getTypeIndex());
		//System.out.println(g.getVertex(65));
		//System.out.println(g.getVertices(0));*/
		Queries q = new Queries();		
		long startTime = System.currentTimeMillis();
		q.khopNeighborhood(v, g, 4);//use vertex 355
		System.out.println("Total time ="+ (System.currentTimeMillis()-startTime));
	}
}
