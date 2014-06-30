package com.gdb.datastore;


import java.io.IOException;
import java.io.RandomAccessFile;


public class NodeTypeIndex {
        String[] nodeTypes;
        RandomAccessFile file;
        
        public NodeTypeIndex(String[] nt, String dbName) throws IOException{
                nodeTypes = nt;
                file = new RandomAccessFile(Constants.PATH_TO_GRAPH_DATA+dbName,"rw");
                for(int i = 0; i < nodeTypes.length; i++){
                        file.writeBytes(nodeTypes[i]);
                        for(int j = 0; j < Constants.TYPE_NAME_LENGTH-nodeTypes[i].length(); j++)
                                file.writeBytes(" ");
                }
        }
        
        public String[] getNodeTypes(){
                return nodeTypes;
        }
        
        public int getIndex(String element){
                for(int i = 0; i < nodeTypes.length; i++){
                        if(nodeTypes[i].equals(element))
                                return i;
                }
                return -1;
        }
        
        public void writeToFile() throws IOException{
                for(int i = 0; i < nodeTypes.length; i++){
                        file.writeBytes(nodeTypes[i]);
                        for(int j = 0; j < 32-nodeTypes[i].length(); j++)
                                file.writeBytes(" ");
                }
        }
        
        public void readFromFile() throws IOException{
                for(int i = 0; i < Constants.NUMBER_OF_NODE_TYPES; i++){
                        byte[] type = new byte[Constants.TYPE_NAME_LENGTH];
                        file.read(type);
                        String name = new String(type);
                        nodeTypes[i] = name.trim();
                }
        }
        
}