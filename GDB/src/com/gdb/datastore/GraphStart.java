package com.gdb.datastore;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author janani
 *
 */
public class GraphStart {

        /**
         * This is the starting point of the application
         * @param args
         * @throws IOException 
         */
        public static void main(String args[]) throws IOException{
                GraphIndex gi = new GraphIndex("nodes2.dat");
                ArrayList<AdjacencyRecord> a = gi.readGraph("rel.dat");
                //System.out.println(a);
                //for(int i = 0; i < a.size(); i++){
                /*for(int i = 0; i < 5; i++){
                        System.out.print("i= "+i);
                        System.out.println(a.get(i));
                }*/
                GraphIndex g = new GraphIndex("nodes2.dat");
                g.writeGraph(args[0],a);
        }
}
