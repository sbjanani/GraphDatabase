package create;

import java.io.IOException;

import com.tinkerpop.blueprints.TransactionalGraph;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;
import com.tinkerpop.blueprints.util.wrappers.batch.BatchGraph;
import com.tinkerpop.blueprints.util.wrappers.batch.VertexIDType;


public class GraphStart {

    public static void main(String args[]) throws IOException{


        //DataLoader gi = new DataLoader("/home/data/Gplus", "/home/data/Gplus/Neo4j_data");
        DataLoader neo4j = new DataLoader("/home/data/Gplus/biggraphs");

        //TransactionalGraph graph = new Neo4jGraph(destinationPath);
        //TransactionalGraph graph = new SparkseeGraph("/home/data/Gplus/Dex_data/sparksee.gdb");
        TransactionalGraph graph = new Neo4jGraph("/home/data/Gplus/biggraphs/Neo4j_data");
        BatchGraph<TransactionalGraph> bgraph = new BatchGraph<TransactionalGraph>(graph, VertexIDType.NUMBER, 10000);
        bgraph.setLoadingFromScratch(true);

        // gi.readNodes();
        // gi.readEdges();
        //  gi.readProperties();
        double startTime = System.currentTimeMillis();
        try {
            neo4j.readAndWriteGraph(bgraph);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Total graph write time = " + (double) (System.currentTimeMillis() - startTime));
    }
}