package create;

import java.io.IOException;



public class GraphStart {

	public static void main(String args[]) throws IOException{
		
		DataLoader gi = new DataLoader("/home/data/Gplus", "/home/data/Gplus/Neo4j_data");
        gi.readNodes();
        gi.readEdges();         
        gi.writeGraph();
	}
}
