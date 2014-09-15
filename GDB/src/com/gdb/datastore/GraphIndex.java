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
import java.util.Collections;
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
        	
        	int edgeNumber = -1;
                
        		adjArray = new ArrayList<AdjacencyRecord>();
                Scanner s = new Scanner(new File(sourcePath+"/edges.dat"));
                int largestNodeNumber = s.nextInt();
                s.nextLine();
               /* for(int i = 0; i <= largestNodeNumber; i++){
                	AdjacencyRecord ar = new AdjacencyRecord();
                	adjArray.add(i,ar);
                }*/
                while(s.hasNext()){
                		edgeNumber++;
                        int fromNode = s.nextInt();
                        int toNode = s.nextInt();
                        byte edgeType = s.nextByte();
                        if(s.hasNext())
                                s.nextLine();
                        if(null==adjArray.get(fromNode))
                        	adjArray.add(fromNode,new AdjacencyRecord());
                        if(null==adjArray.get(toNode))
                        	adjArray.add(toNode,new AdjacencyRecord());
                        
                        	adjArray.get(fromNode).addOutGoing(toNode,edgeType, edgeNumber);
                            adjArray.get(fromNode).setOutGoingBit(edgeType);
                            adjArray.get(toNode).addIncoming(fromNode,edgeType, edgeNumber);
                            adjArray.get(toNode).setInComingBit(edgeType);
                        
                       
                }
               /* for(int i = 0; i < adjArray.size(); i++){
                	Collections.sort(adjArray.get(i).inComing);
                	Collections.sort(adjArray.get(i).outGoing);
                }*/
                
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
             }
             file.close();
        }
        
        public void writeNodesAndEdges() throws IOException{
       	 	RandomAccessFile nFile = new RandomAccessFile(destinationPath+"/nodes.dat","rw");
       	 	RandomAccessFile eFile = new RandomAccessFile(destinationPath+"/edges.dat","rw");
       	 	RandomAccessFile oFile = new RandomAccessFile(destinationPath+"/overFlow.dat","rw");
       	 	int newEdgeNumber = 0;
       	 	int overFlowCounter = 0;
            for(int i = 0; i < adjArray.size(); i++){
            	System.out.println("I= "+i);
            	byte[] nBuffer = new byte[4+4*Constants.MAX_EDGES_NODES_DAT];
            	byte[] eBuffer = new byte[Constants.EDGE_DAT_SIZE];
            	Map<Byte,ArrayList<NeighborNodeRecord>> incoming = adjArray.get(i).getInComing();
            	Map<Byte,ArrayList<NeighborNodeRecord>> outgoing = adjArray.get(i).getOutGoing();
            	int size = incoming.size() + outgoing.size() - Constants.MAX_EDGES_NODES_DAT;
            	byte[] oBuffer = new byte[Constants.MAX_EDGES_NODES_DAT];
            	int neighborCount = 0;
            	int eNumber = 0;
            	
            	for(Map.Entry<Byte, ArrayList<NeighborNodeRecord>> entryset : incoming.entrySet()){
            		
            			System.out.println("In= "+entryset.getKey());
                		//neighborCount++;
                		if(i < entryset.getKey()){
                			eNumber = newEdgeNumber;
                			ArrayList<NeighborNodeRecord> neighborList = entryset.getValue();
                			for(NeighborNodeRecord neighborNodeRecord : neighborList){
                				
                    			int offSet = entryset.getKey() -i;
                        		System.out.println("OFFSET = "+offSet);
                        		eBuffer[0] = nodeTypes.get(i);
                        		eBuffer[1] = nodeTypes.get(entryset.getKey());
                        		eBuffer[2] = (byte)(offSet>>>24);
                        		eBuffer[3] = (byte)(offSet>>>16);
                        		eBuffer[4] = (byte)(offSet>>>8);
                        		eBuffer[5] = (byte)(offSet);
                        		eFile.write(eBuffer);
                    			newEdgeNumber++;
                			}
                			
                		}
                		else{
                			eNumber = getOutGoingEdgeNumber(i,incoming.get(in).neighborNode,adjArray);
                			
                		}
                		if(neighborCount <= Constants.MAX_EDGES_NODES_DAT){
                			nBuffer[5*in] = 0;
                			nBuffer[5*in+1] = (byte)(eNumber>>>24);
                			nBuffer[5*in+2] = (byte)(eNumber>>>16);
                			nBuffer[5*in+3] = (byte)(eNumber>>>8);
                			nBuffer[5*in+4] = (byte)eNumber;
                			if(i == 3){
                				System.out.println("in = "+in);
                				System.out.println("INCOMING "+nBuffer[5*in+1]);
                				System.out.println("INCOMING "+nBuffer[5*in+2]);
                				System.out.println("INCOMING "+nBuffer[5*in+3]);
                				System.out.println("INCOMING "+nBuffer[5*in+4]);
                			}
                		}
                		/*else{
                			oBuffer[overFlowCounter++] = 0;
                			oBuffer[overFlowCounter++] = (byte)(eNumber>>>24);
                			oBuffer[overFlowCounter++] = (byte)(eNumber>>>16);
                			oBuffer[overFlowCounter++] = (byte)(eNumber>>>8);
                			oBuffer[overFlowCounter++] = (byte)eNumber;
                		}*/
                	
            	}
            	
            	for(int out = 0; out < outgoing.size(); out++){
            		System.out.println("out= "+out);
            		neighborCount++;
            		if(i < outgoing.get(out).neighborNode){
            			eNumber = newEdgeNumber;
            			outgoing.get(out).edgeNumber = eNumber;
            			setEdgeNumberIncoming(adjArray,i,outgoing.get(out).neighborNode,eNumber);
            			int offSet =  i - outgoing.get(out).neighborNode;
                		eBuffer[0] = nodeTypes.get(outgoing.get(out).neighborNode);
                		eBuffer[1] = nodeTypes.get(i);
                		eBuffer[2] = (byte)(offSet>>>24);
                		eBuffer[3] = (byte)(offSet>>>16);
                		eBuffer[4] = (byte)(offSet>>>8);
                		eBuffer[5] = (byte)(offSet);
                		eFile.write(eBuffer);
            			newEdgeNumber++;
            		}
            		else
            			eNumber = getIncomingEdgeNumber(i,outgoing.get(out).neighborNode,adjArray);
            		if(neighborCount <= Constants.MAX_EDGES_NODES_DAT){
            			nBuffer[5*(out+incoming.size())] = 1;
            			nBuffer[5*(out+incoming.size())+1] = (byte)(eNumber>>>24);
            			nBuffer[5*(out+incoming.size())+2] = (byte)(eNumber>>>16);
            			nBuffer[5*(out+incoming.size())+3] = (byte)(eNumber>>>8);
            			nBuffer[5*(out+incoming.size())+4] = (byte)eNumber;
            			if(i == 3){
            				System.out.println("OUT = "+out);
            				System.out.println("OUTGOING "+nBuffer[5*out+1]);
            				System.out.println("OUTGOING "+nBuffer[5*out+2]);
            				System.out.println("OUTGOING "+nBuffer[5*out+3]);
            				System.out.println("OUTGOING "+nBuffer[5*out+4]);
            			}
            		}
            		else{
            			oBuffer[overFlowCounter++] = 1;
            			oBuffer[overFlowCounter++] = (byte)(eNumber>>>24);
            			oBuffer[overFlowCounter++] = (byte)(eNumber>>>16);
            			oBuffer[overFlowCounter++] = (byte)(eNumber>>>8);
            			oBuffer[overFlowCounter++] = (byte)eNumber;
            		}
            	}
            	int correctOverFlowCounter = overFlowCounter - (incoming.size() +outgoing.size() -Constants.MAX_EDGES_NODES_DAT);
            	nBuffer[5*Constants.MAX_EDGES_NODES_DAT] = (byte)correctOverFlowCounter;
    			nBuffer[5*Constants.MAX_EDGES_NODES_DAT+1] = (byte)(correctOverFlowCounter>>>8);
    			nBuffer[5*Constants.MAX_EDGES_NODES_DAT+2] = (byte)(correctOverFlowCounter>>>16);
    			nBuffer[5*Constants.MAX_EDGES_NODES_DAT+3] = (byte)(correctOverFlowCounter>>>24);
    			
    			nFile.write(nBuffer);
    			if(oBuffer != null)
    				oFile.write(oBuffer);
            }
            nFile.close();
            eFile.close();
            oFile.close();
       }

		private void setEdgeNumberIncoming(
				ArrayList<AdjacencyRecord> edgeArray, int node1, int neighborNode,
				int eNumber) {
			edgeArray.get(neighborNode).inComing.get(node1).setEdgeNumber(eNumber);
		}
		
		private void setEdgeNumberOutGoing(
				ArrayList<AdjacencyRecord> edgeArray,int node1, int neighborNode,
				int eNumber) {
			edgeArray.get(neighborNode).outGoing.get(node1).setEdgeNumber(eNumber);
			
		}

		private int getIncomingEdgeNumber(int node1, int node2,
				ArrayList<AdjacencyRecord> edgeArray) {
			for(int i = 0; i < edgeArray.get(node2).inComing.size(); i++){
				if(edgeArray.get(node2).inComing.get(i).neighborNode == node1)
					return edgeArray.get(node2).inComing.get(i).edgeNumber; 
			}
			return -1;
		}
		
		private int getOutGoingEdgeNumber(int node1, int node2,
				ArrayList<AdjacencyRecord> edgeArray) {
			for(int i = 0; i < edgeArray.get(node2).outGoing.size(); i++){
				if(edgeArray.get(node2).outGoing.get(i).neighborNode == node1)
					return edgeArray.get(node2).outGoing.get(i).edgeNumber; 
			}
			return -1;
		}
		
		public void displayAdjRecord(){
			
			System.out.println(adjArray);
            for(int i = 0; i < adjArray.size(); i++){
                    System.out.print("i= "+i);
                    System.out.println(adjArray.get(i));
            }
		}
        
}