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
                GraphIndex gi = new GraphIndex("/home/data/GDBData_small", "/home/data/gendataset");
                gi.readNodes();
                gi.readEdges();
                //gi.displayAdjRecord();                
                gi.writeGraph();
        }
}