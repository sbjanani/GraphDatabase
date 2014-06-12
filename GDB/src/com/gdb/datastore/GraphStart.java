package com.gdb.datastore;

import java.io.FileNotFoundException;
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
                GraphIndex gi = new GraphIndex("nodes.dat");
                ArrayList<AdjacencyRecord> a = gi.readGraph("rel.dat");
                /*for(int i = 0; i < a.size(); i++){
                        System.out.print("i= "+i);
                        for(int j = 0; j < a.get(i).length; j++){
                                System.out.print(" "+a.get(i)[j]+ " ");
                        }
                        System.out.println();
                }*/
                GraphIndex g = new GraphIndex("nodes.dat");
                g.writeGraph("/Users/Naveen/git/GraphDatabase/GDB/graphIndex.idx",a);
        }
}