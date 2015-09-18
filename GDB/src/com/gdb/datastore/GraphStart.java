package com.gdb.datastore;

import java.io.IOException;
import java.io.RandomAccessFile;
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

        GraphIndex gi = new GraphIndex("/home/data/Gplus/biggraphs", "/home/data/Gplus/biggraphs/gdb_data");
                /*gi.readNodes();
                gi.readEdges();           
                gi.writeGraph();*/
        double startTime = System.currentTimeMillis();
        gi.readAndWriteFile();
        System.out.println("Load time = " + (System.currentTimeMillis() - startTime));
    }
}