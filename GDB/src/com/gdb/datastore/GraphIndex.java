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
                for(int i = 0; i < adjArray.size(); i++){
                	Collections.sort(adjArray.get(i).inComing);
                	Collections.sort(adjArray.get(i).outGoing);
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
        	writeGraphIndex(path,edgeArray);
        	writeNodesAndEdges(path,edgeArray);
        }
        
        public void writeGraphIndex(String path, ArrayList<AdjacencyRecord> edgeArray) throws IOException{
        	 RandomAccessFile file = new RandomAccessFile(path+"/graph.idx","rw");
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
        
        public void writeNodesAndEdges(String path, ArrayList<AdjacencyRecord> edgeArray) throws IOException{
       	 	RandomAccessFile nFile = new RandomAccessFile(path+"/nodes.dat","rw");
       	 	RandomAccessFile eFile = new RandomAccessFile(path+"/edges.dat","rw");
       	 	RandomAccessFile oFile = new RandomAccessFile(path+"/overFlow.dat","rw");
       	 	int newEdgeNumber = 0;
       	 	int overFlowCounter = 0;
            for(int i = 0; i < edgeArray.size(); i++){
            	System.out.println("I= "+i);
            	byte[] nBuffer = new byte[4 + 5*Constants.MAX_EDGES_NODES_DAT];
            	byte[] eBuffer = new byte[Constants.EDGE_DAT_SIZE];
            	ArrayList<NeighborNodeRecord> incoming = edgeArray.get(i).getInComing();
            	ArrayList<NeighborNodeRecord> outgoing = edgeArray.get(i).getOutGoing();
            	int size = incoming.size() + outgoing.size() - Constants.MAX_EDGES_NODES_DAT;
            	byte[] oBuffer = null;
            	if(size > 0)
            		oBuffer = new byte[size];
            	int neighborCount = 0;
            	int eNumber = 0;
            	for(int in = 0; in < incoming.size(); in++){
            		System.out.println("In= "+in);
            		neighborCount++;
            		if(i < incoming.get(in).neighborNode){
            			eNumber = newEdgeNumber;
            			incoming.get(in).edgeNumber = eNumber;
            			setEdgeNumberOutGoing(edgeArray,i,incoming.get(in).neighborNode,eNumber);
            		}
            		else
            			eNumber = getOutGoingEdgeNumber(i,incoming.get(in).neighborNode,edgeArray);
            		if(neighborCount <= Constants.MAX_EDGES_NODES_DAT){
            			nBuffer[5*in] = 0;
            			nBuffer[5*in+1] = (byte)(eNumber>>>24);
            			nBuffer[5*in+2] = (byte)(eNumber>>>16);
            			nBuffer[5*in+3] = (byte)(eNumber>>>8);
            			nBuffer[5*in+4] = (byte)eNumber;
            		}
            		else{
            			oBuffer[overFlowCounter++] = 0;
            			oBuffer[overFlowCounter++] = (byte)(eNumber>>>24);
            			oBuffer[overFlowCounter++] = (byte)(eNumber>>>16);
            			oBuffer[overFlowCounter++] = (byte)(eNumber>>>8);
            			oBuffer[overFlowCounter++] = (byte)eNumber;
            		}
            		int offSet = in -i;
            		eBuffer[0] = nodeTypes[i];
            		eBuffer[1] = nodeTypes[in];
            		eBuffer[2] = (byte)(offSet>>>24);
            		eBuffer[3] = (byte)(offSet>>>16);
            		eBuffer[4] = (byte)(offSet>>>8);
            		eBuffer[5] = (byte)(offSet);
            		eFile.write(eBuffer);
            		newEdgeNumber++;
            	}
            	for(int out = 0; out < outgoing.size(); out++){
            		System.out.println("out= "+out);
            		neighborCount++;
            		if(i < outgoing.get(out).neighborNode){
            			eNumber = newEdgeNumber;
            			outgoing.get(out).edgeNumber = eNumber;
            			setEdgeNumberIncoming(edgeArray,i,outgoing.get(out).neighborNode,eNumber);
            		}
            		else
            			eNumber = getIncomingEdgeNumber(i,outgoing.get(out).neighborNode,edgeArray);
            		if(neighborCount <= Constants.MAX_EDGES_NODES_DAT){
            			nBuffer[5*out] = 0;
            			nBuffer[5*out+1] = (byte)(eNumber>>>24);
            			nBuffer[5*out+2] = (byte)(eNumber>>>16);
            			nBuffer[5*out+3] = (byte)(eNumber>>>8);
            			nBuffer[5*out+4] = (byte)eNumber;
            		}
            		else{
            			oBuffer[overFlowCounter++] = 0;
            			oBuffer[overFlowCounter++] = (byte)(eNumber>>>24);
            			oBuffer[overFlowCounter++] = (byte)(eNumber>>>16);
            			oBuffer[overFlowCounter++] = (byte)(eNumber>>>8);
            			oBuffer[overFlowCounter++] = (byte)eNumber;
            		}
            		/*int offSet =  i - out;
            		eBuffer[0] = nodeTypes[out];
            		eBuffer[1] = nodeTypes[i];
            		eBuffer[2] = (byte)(offSet>>>24);
            		eBuffer[3] = (byte)(offSet>>>16);
            		eBuffer[4] = (byte)(offSet>>>8);
            		eBuffer[5] = (byte)(offSet);*/
            		//eFile.write(eBuffer);
            		newEdgeNumber++;
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
				ArrayList<AdjacencyRecord> edgeArray, int neighborNode,
				int eNumber, int node1) {
			for(int j = 0; j < edgeArray.get(neighborNode).inComing.size(); j++){
				if(edgeArray.get(neighborNode).inComing.get(j).neighborNode == node1)
					edgeArray.get(neighborNode).inComing.get(j).edgeNumber = eNumber;
			}
		}
		
		private void setEdgeNumberOutGoing(
				ArrayList<AdjacencyRecord> edgeArray, int neighborNode,
				int eNumber, int node1) {
			for(int j = 0; j < edgeArray.get(neighborNode).outGoing.size(); j++){
				if(edgeArray.get(neighborNode).outGoing.get(j).neighborNode == node1)
					edgeArray.get(neighborNode).outGoing.get(j).edgeNumber = eNumber;
			}
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
        
}