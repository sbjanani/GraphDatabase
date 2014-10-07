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
        	
                GraphIndex gi = new GraphIndex("/home/data/Gplus", "/home/data/Gplus");
                gi.readNodes();
                gi.readEdges();
                //gi.displayAdjRecord();                
                gi.writeGraph();
                
             /*   RandomAccessFile raf = new RandomAccessFile("nodefile.dat","r");
                raf.seek(65);
                System.out.println(raf.readInt());
                raf.close();*/
                
        }
}