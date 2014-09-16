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
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class GraphIndex {
        ArrayList<Byte> nodeTypes;
        ArrayList<AdjacencyRecord> adjArray;
        String sourcePath;
        String destinationPath;
        
        /**
         * This is the constructor for the GraphIndex. It sets the input and output directories.
         * @param nodeFilePath
         * @param edgeFilePath
         * @throws FileNotFoundException
         */
        public GraphIndex(String sourcePath, String destinationPath) throws FileNotFoundException{
                
        	this.sourcePath = sourcePath;
        	this.destinationPath = destinationPath;
        }
        
        /**
         * This method reades the nodes.dat file and saves the result in the arraylist nodeTypes
         * @throws FileNotFoundException
         */
        public void readNodes() throws FileNotFoundException{
        	
        	Scanner s = new Scanner(new File(sourcePath+"/nodes.dat"));
            nodeTypes = new ArrayList<Byte>();
            while(s.hasNext()){                	
            	nodeTypes.add(s.nextInt(), s.nextByte());
            	if(s.hasNext())
                    s.nextLine();
            }
            s.close();
        }
        
        
        /**
         * This method reads the input edge file and creates an ArrayList with the edge information
         * @param path - location where the input edge file is stored
         * @return
         * @throws FileNotFoundException 
         */
        public void readEdges() throws FileNotFoundException{
        	
        	adjArray = new ArrayList<AdjacencyRecord>();
        	for(int i=0; i< nodeTypes.size(); i++){
        		adjArray.add(new AdjacencyRecord());
        	}
        	
        	int edgeNumber = 0;
                
        		
                Scanner s = new Scanner(new File(sourcePath+"/rel.dat"));
                while(s.hasNext()){
                		++edgeNumber;
                        int fromNode = s.nextInt();
                        int toNode = s.nextInt();
                        byte edgeType = s.nextByte();
                        if(s.hasNext())
                                s.nextLine();
                                                
                    	adjArray.get(fromNode).addOutGoing(toNode,edgeType, edgeNumber);
                        adjArray.get(fromNode).setOutGoingBit(edgeType);
                        adjArray.get(toNode).addIncoming(fromNode,edgeType, edgeNumber);
                        adjArray.get(toNode).setInComingBit(edgeType);
                        
                       
                }
                    
                s.close();
                                
        }
        
        /**
         * This method writes the node index information into the file graph.idx 
         * @param path - location where the output file "graph.idx" is stored
         * @param edgeArray - arraylist containing the edge information
         * @throws IOException 
         */
        public void writeGraph() throws IOException{
        	writeGraphIndex();
        	writeNodesAndEdges();
        }
        
        public void writeGraphIndex() throws IOException{
        	 RandomAccessFile file = new RandomAccessFile(destinationPath+"/graph.idx","rw");
             for(int i = 0; i < adjArray.size(); i++){
             	byte[] buffer = new byte[3 + 4*Constants.NUMBER_OF_EDGE_TYPES];
             	buffer[0] = nodeTypes.get(i);
                buffer[1] = adjArray.get(i).inComingEdgeBitMap;
                buffer[2] = adjArray.get(i).outGoingEdgeBitMap;
                int index = 3;
                for(byte k = Constants.NUMBER_OF_EDGE_TYPES - 1; k >= 0; k--){
                	short s = adjArray.get(i).getInComingCount()[k];
                 	buffer[index++] = (byte) (s>>>8);
                 	buffer[index++] = (byte) s;
                 	s = adjArray.get(i).getOutGoingCount()[k];
                 	buffer[index++] = (byte) (s>>>8);
                 	buffer[index++] = (byte) s; 
                 }
                 file.write(buffer);
                 
                 System.out.println("index : "+ i+" "+Arrays.toString(buffer));
             }
             file.close();
        }
        
        public void writeNodesAndEdges() throws IOException{
       	 	RandomAccessFile nFile = new RandomAccessFile(destinationPath+"/nodefile.dat","rw");
       	 	RandomAccessFile eFile = new RandomAccessFile(destinationPath+"/edgefile.dat","rw");
       	      for(int node = 0; node < adjArray.size(); node++){
            	//System.out.println("I= "+node);
            	byte[] nBuffer = new byte[5*Constants.MAX_EDGES_NODES_DAT];
            	byte[] eBuffer = new byte[Constants.EDGE_DAT_SIZE];
            	Map<Byte,ArrayList<NeighborNodeRecord>> incoming = adjArray.get(node).getInComing();
            	Map<Byte,ArrayList<NeighborNodeRecord>> outgoing = adjArray.get(node).getOutGoing();
            	
            	for(Byte edgeType = 0; edgeType < Constants.NUMBER_OF_EDGE_TYPES; edgeType++){
            		
            		ArrayList<NeighborNodeRecord> inlist = incoming.get(edgeType);
            		ArrayList<NeighborNodeRecord> outlist = outgoing.get(edgeType);
            		
            		if(!(null == inlist))
            		for(NeighborNodeRecord inNeighbor : inlist){
            			
            			eBuffer[0] = edgeType;
                        //Write tail(from) node first
                        eBuffer[1] = (byte)(inNeighbor.getNeighborNode()>>>24);
                        eBuffer[2] = (byte)(inNeighbor.getNeighborNode()>>>16);
                        eBuffer[3] = (byte)(inNeighbor.getNeighborNode()>>>8);
                        eBuffer[4] = (byte)(inNeighbor.getNeighborNode());
                        //Write head(To) node second
                        eBuffer[5] = (byte)(node>>>24);
                        eBuffer[6] = (byte)(node>>>16);
                        eBuffer[7] = (byte)(node>>>8);
                        eBuffer[8] = (byte)(node);
                        eFile.seek(inNeighbor.getEdgeNumber());
                        eFile.write(eBuffer);
                        
                      System.out.println("edge : " + inNeighbor.getEdgeNumber()+" "+Arrays.toString(eBuffer));
                       
                        
                        nBuffer[5*inlist.indexOf(inNeighbor)] = 0;
            			nBuffer[5*inlist.indexOf(inNeighbor)+1] = (byte)(inNeighbor.getEdgeNumber()>>>24);
            			nBuffer[5*inlist.indexOf(inNeighbor)+2] = (byte)(inNeighbor.getEdgeNumber()>>>16);
            			nBuffer[5*inlist.indexOf(inNeighbor)+3] = (byte)(inNeighbor.getEdgeNumber()>>>8);
            			nBuffer[5*inlist.indexOf(inNeighbor)+4] = (byte)inNeighbor.getEdgeNumber();
            			
            		}
            		
            		if(!(null == outlist))
            		for(NeighborNodeRecord outNeighbor : outlist){
         
                        nBuffer[5*outlist.indexOf(outNeighbor)] = 1;
            			nBuffer[5*outlist.indexOf(outNeighbor)+1] = (byte)(outNeighbor.getEdgeNumber()>>>24);
            			nBuffer[5*outlist.indexOf(outNeighbor)+2] = (byte)(outNeighbor.getEdgeNumber()>>>16);
            			nBuffer[5*outlist.indexOf(outNeighbor)+3] = (byte)(outNeighbor.getEdgeNumber()>>>8);
            			nBuffer[5*outlist.indexOf(outNeighbor)+4] = (byte)outNeighbor.getEdgeNumber();
            		}
            	}
            			   			
    			nFile.write(nBuffer);
    			
    			System.out.println("node : "+ node+" "+Arrays.toString(nBuffer));
    			
    			
            }
            nFile.close();
            eFile.close();
       }
		
		
		public void displayAdjRecord(){
			
			System.out.println(adjArray);
            for(int i = 0; i < adjArray.size(); i++){
                    System.out.print("i= "+i);
                    System.out.println(adjArray.get(i));
            }
		}
        
}