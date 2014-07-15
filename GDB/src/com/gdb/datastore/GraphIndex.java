package com.gdb.datastore;
/**
 * This file populates the "graph.idx" file
 * @author janani
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GraphIndex {
        byte[] nodeTypes;


        //Constructor to populate the nodeTypes array
        //for each node it stores its type for easier access later
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
                s.close();
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
                //the node number of the largest node is stored at the top of the file
                int largestNodeNumber = s.nextInt();
                s.nextLine();
                //fill adjArray with empty AdjacencyRecords
                for(int i = 0; i <= largestNodeNumber; i++){
                    AdjacencyRecord ar = new AdjacencyRecord();
                    adjArray.add(i,ar);
                }
                //go through all of the edges
                while(s.hasNext()){
                        int fromNode = s.nextInt();
                        int toNode = s.nextInt();
                        byte edgeType = s.nextByte();
                        //go to the next line if not at the end of file
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

                s.close();
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
            //counter for the overflow file, to list the extra edges for each node
            int overFlowCounter = 0;
            int[] globalVars = new int[2];
            globalVars[0] = newEdgeNumber;
            for(int i = 0; i < edgeArray.size(); i++){
            	//index for each record in the overflow file 
                int overFlowBufferIndex = 0;
                globalVars[1] = overFlowBufferIndex;
                //size of nodes.dat is 84 bytes per record
                byte[] nBuffer = new byte[4 + 5*Constants.MAX_EDGES_NODES_DAT];
                //size of edges.dat is 9 bytes per record
                byte[] eBuffer = new byte[Constants.EDGE_DAT_SIZE];
                //method call fills in the incoming/outgoing arraylist of nodes,edgeType, and edgeNumber
                ArrayList<NeighborNodeRecord> incoming = edgeArray.get(i).getInComing();
                ArrayList<NeighborNodeRecord> outgoing = edgeArray.get(i).getOutGoing();
                //the nodes.dat file can store information about max 16 edges
                //until it goes to overFlow.dat where its stores the rest.
                int size = incoming.size() + outgoing.size() - Constants.MAX_EDGES_NODES_DAT;
                //buffer for the overFlow.dat file, size is 5*num of edges
                byte[] oBuffer = null;
                if(size > 0)
                    oBuffer = new byte[5*size];
                
                //process incoming and outgoing edges 
                processIncoming(i,edgeArray,incoming,eBuffer,nBuffer,oBuffer,eFile,globalVars);
                processOutgoing(i,edgeArray,outgoing,incoming,eBuffer,nBuffer,oBuffer,eFile,globalVars);
                
                //add the overFlowCounter to the end of the buffer
                nBuffer[5*Constants.MAX_EDGES_NODES_DAT] = (byte)(overFlowCounter>>>24);
                nBuffer[5*Constants.MAX_EDGES_NODES_DAT+1] = (byte)(overFlowCounter>>>16);
                nBuffer[5*Constants.MAX_EDGES_NODES_DAT+2] = (byte)(overFlowCounter>>>8);
                nBuffer[5*Constants.MAX_EDGES_NODES_DAT+3] = (byte)(overFlowCounter);
                
                //increase the overFlowCounter if elements have been added to the overFlow file
                if(size > 0)
                    overFlowCounter += 5*size;
                //write the nodes.dat file
                nFile.write(nBuffer);
                //write the overFlowFile if the overFlow was used
                if(oBuffer != null)
                    oFile.write(oBuffer);
            }
            nFile.close();
            eFile.close();
            oFile.close();
        }

        private void processIncoming(int node, ArrayList<AdjacencyRecord> edgeArray,
                ArrayList<NeighborNodeRecord> incoming,
                byte[] eBuffer, byte[] nBuffer,byte[] oBuffer, RandomAccessFile eFile,
                int[] globalVars) throws IOException{
            int newEdgeNumber = globalVars[0];
            int overFlowBufferIndex = globalVars[1];

            for(int in = 0; in < incoming.size(); in++){
                int eNumber = 0;
                if(node < incoming.get(in).neighborNode){
                    eNumber = newEdgeNumber;
                    incoming.get(in).edgeNumber = eNumber;
                    setEdgeNumberOutGoing(edgeArray,node,incoming.get(in).neighborNode,eNumber);
                    //int offSet = incoming.get(in).neighborNode -node;
                   /* eBuffer[0] = nodeTypes[node];
                    eBuffer[1] = nodeTypes[incoming.get(in).neighborNode];
                    eBuffer[2] = (byte)(offSet>>>24);
                    eBuffer[3] = (byte)(offSet>>>16);
                    eBuffer[4] = (byte)(offSet>>>8);
                    eBuffer[5] = (byte)(offSet);*/
                    //write the edge type 
                    eBuffer[0] = edgeArray.get(node).inComing.get(in).edgeType;
                    //Write tail(from) node first
                    eBuffer[1] = (byte)(incoming.get(in).neighborNode>>>24);
                    eBuffer[2] = (byte)(incoming.get(in).neighborNode>>>16);
                    eBuffer[3] = (byte)(incoming.get(in).neighborNode>>>8);
                    eBuffer[4] = (byte)(incoming.get(in).neighborNode);
                    //Write head(To) node second
                    eBuffer[5] = (byte)(node>>>24);
                    eBuffer[6] = (byte)(node>>>16);
                    eBuffer[7] = (byte)(node>>>8);
                    eBuffer[8] = (byte)(node);
                    eFile.write(eBuffer);
                    newEdgeNumber++;
                }
                else{
                    eNumber = getOutGoingEdgeNumber(node,incoming.get(in).neighborNode,edgeArray);
                }
                if(in < Constants.MAX_EDGES_NODES_DAT){
                    nBuffer[5*in] = 0;
                    nBuffer[5*in+1] = (byte)(eNumber>>>24);
                    nBuffer[5*in+2] = (byte)(eNumber>>>16);
                    nBuffer[5*in+3] = (byte)(eNumber>>>8);
                    nBuffer[5*in+4] = (byte)eNumber;
                }
                else{
                    oBuffer[overFlowBufferIndex++] = 0;
                    oBuffer[overFlowBufferIndex++] = (byte)(eNumber>>>24);
                    oBuffer[overFlowBufferIndex++] = (byte)(eNumber>>>16);
                    oBuffer[overFlowBufferIndex++] = (byte)(eNumber>>>8);
                    oBuffer[overFlowBufferIndex++] = (byte)eNumber;
                }
            }
            globalVars[0] = newEdgeNumber;
            globalVars[1] = overFlowBufferIndex;
        }

        private void processOutgoing(int node, ArrayList<AdjacencyRecord> edgeArray,
                ArrayList<NeighborNodeRecord> outgoing,
                ArrayList<NeighborNodeRecord> incoming,byte[] eBuffer, byte[] nBuffer,byte[] oBuffer, RandomAccessFile eFile,
                int[] globalVars) throws IOException{
            int newEdgeNumber = globalVars[0];
            int overFlowBufferIndex = globalVars[1];
            for(int out = 0; out < outgoing.size(); out++){
                int eNumber = 0;
                if(node < outgoing.get(out).neighborNode){
                    eNumber = newEdgeNumber;
                    outgoing.get(out).edgeNumber = eNumber;
                    setEdgeNumberIncoming(edgeArray,node,outgoing.get(out).neighborNode,eNumber);
                    /*int offSet =  node - outgoing.get(out).neighborNode;
                    eBuffer[0] = nodeTypes[outgoing.get(out).neighborNode];
                    eBuffer[1] = nodeTypes[node];
                    eBuffer[2] = (byte)(offSet>>>24);
                    eBuffer[3] = (byte)(offSet>>>16);
                    eBuffer[4] = (byte)(offSet>>>8);
                    eBuffer[5] = (byte)(offSet);*/
                    eBuffer[0] = edgeArray.get(node).outGoing.get(out).edgeType;
                    //Write tail(from) node first
                    eBuffer[1] = (byte)(node>>>24);
                    eBuffer[2] = (byte)(node>>>16);
                    eBuffer[3] = (byte)(node>>>8);
                    eBuffer[4] = (byte)(node);
                    //Write head(To) node second
                    eBuffer[5] = (byte)(outgoing.get(out).neighborNode>>>24);
                    eBuffer[6] = (byte)(outgoing.get(out).neighborNode>>>16);
                    eBuffer[7] = (byte)(outgoing.get(out).neighborNode>>>8);
                    eBuffer[8] = (byte)(outgoing.get(out).neighborNode);
                    eFile.write(eBuffer);
                    newEdgeNumber++;
                }
                else
                    eNumber = getIncomingEdgeNumber(node,outgoing.get(out).neighborNode,edgeArray);
                if(out + incoming.size() < Constants.MAX_EDGES_NODES_DAT){
                    //System.out.println("OUT = "+out+" INCOMINg.SIZE = "+incoming.size());
                    nBuffer[5*(out+incoming.size())] = 1;
                    nBuffer[5*(out+incoming.size())+1] = (byte)(eNumber>>>24);
                    nBuffer[5*(out+incoming.size())+2] = (byte)(eNumber>>>16);
                    nBuffer[5*(out+incoming.size())+3] = (byte)(eNumber>>>8);
                    nBuffer[5*(out+incoming.size())+4] = (byte)eNumber;
                }
                else{
                    oBuffer[overFlowBufferIndex++] = 1;
                    oBuffer[overFlowBufferIndex++] = (byte)(eNumber>>>24);
                    oBuffer[overFlowBufferIndex++] = (byte)(eNumber>>>16);
                    oBuffer[overFlowBufferIndex++] = (byte)(eNumber>>>8);
                    oBuffer[overFlowBufferIndex++] = (byte)eNumber;
                }
            }
            globalVars[0] = newEdgeNumber;
            globalVars[1] = overFlowBufferIndex;
        }

        private void setEdgeNumberIncoming(
                ArrayList<AdjacencyRecord> edgeArray, int node1, int neighborNode,
                int eNumber) {
            for(int j = 0; j < edgeArray.get(neighborNode).inComing.size(); j++){
                if(edgeArray.get(neighborNode).inComing.get(j).neighborNode == node1)
                    edgeArray.get(neighborNode).inComing.get(j).edgeNumber = eNumber;
            }
        }

        private void setEdgeNumberOutGoing(
                ArrayList<AdjacencyRecord> edgeArray,int node1, int neighborNode,
                int eNumber) {
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