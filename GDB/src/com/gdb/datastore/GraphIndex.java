/**
 * This file populates the "graph.idx" file
 * @author janani
 *
 */
package com.gdb.datastore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

public class GraphIndex {
        byte[] nodeTypes;
        
        public GraphIndex(String path) throws FileNotFoundException{
                Scanner s = new Scanner(new File(path));
                int numNodes = s.nextInt();
                s.nextLine();
                nodeTypes = new byte[numNodes];
                for(int i = 0; i < numNodes; i++){
                        s.nextInt();
                        byte nType = s.nextByte();
                        nodeTypes[i] = nType;
                        if(s.hasNext())
                                s.nextLine();
                }
        }
        
        /**
         * This method reads the input file and creates an ArrayList with the edge information
         * @param path - location where the input files are stored
         * @return
         * @throws FileNotFoundException 
         */
        public ArrayList<AdjacencyRecord> readGraph(String path) throws FileNotFoundException{
                ArrayList<AdjacencyRecord> adjArray = new ArrayList<AdjacencyRecord>();
                Scanner s = new Scanner(new File(path));
                int largestNodeNumber = s.nextInt();
                s.nextLine();
                for(int i = 0; i <= largestNodeNumber; i++){
                	AdjacencyRecord ar = new AdjacencyRecord();
                	adjArray.add(i,ar);
                }
                while(s.hasNext()){
                        int fromNode = s.nextInt();
                        int toNode = s.nextInt();
                        byte edgeType = s.nextByte();
                        if(s.hasNext())
                                s.nextLine();
                        adjArray.get(fromNode).addOutGoing(toNode,edgeType);
                        adjArray.get(fromNode).setOutGoingBit(edgeType);
                        adjArray.get(toNode).addIncoming(fromNode,edgeType);
                        adjArray.get(toNode).setInComingBit(edgeType);
                }
                return adjArray;
                
        }
        
        /**
         * This method writes the node index information into the file graph.idx 
         * @param path - location where the output file "graph.idx" is stored
         * @param edgeArray - arraylist containing the edge information
         * @throws IOException 
         */
        public void writeGraph(String path, ArrayList<AdjacencyRecord> edgeArray) throws IOException{
                RandomAccessFile file = new RandomAccessFile(path,"rw");
                for(int i = 0; i < edgeArray.size(); i++){
                	byte[] buffer = new byte[3 + 4*Constants.NUMBER_OF_EDGE_TYPES];
                	buffer[0] = nodeTypes[i];
                    buffer[1] = edgeArray.get(i).inComingEdgeBitMap;
                    buffer[2] = edgeArray.get(i).outGoingEdgeBitMap;
                    int index = 3;
                    for(byte k = Constants.NUMBER_OF_EDGE_TYPES - 1; k >= 0; k--){
                    	short s = edgeArray.get(i).getIncomingCount(k);
                    	buffer[index++] = (byte) (s>>>8);
                    	buffer[index++] = (byte) s;
                    	s = edgeArray.get(i).getOutGoingCount(k);
                    	buffer[index++] = (byte) (s>>>8);
                    	buffer[index++] = (byte) s; 
                    }
                    file.write(buffer);
                }
                file.close();
        }
}