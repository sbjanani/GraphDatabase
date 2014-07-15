package com.gdb.datastore;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class EdgesTester {
	
	private static int bytesToInt(byte b3, byte b2, byte b1, byte b0){
		return b3 << 24 | (b2 & 0xff) << 16 | (b1 & 0xff) << 8 | (b0 & 0xff);
	}
	
	private static byte[] intToBytes(int num){
		byte[] result = new byte[4];
		
		return result;
	}
	
	private static void printOneRecord(RandomAccessFile eFile,int record) throws IOException{
		eFile.seek(record*Constants.EDGE_DAT_SIZE);
	    int fromNodeId = eFile.readInt();
	    int toNodeId = eFile.readInt();
	    System.out.println("From Node Id: "+fromNodeId);
	    System.out.println("To Node Id: "+toNodeId);
	}
	
	public static void main(String[] args) throws IOException{
		RandomAccessFile eFile = new RandomAccessFile("/Users/Naveen/DB7Nodes/edges.dat","rw");
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the number of the record you want to see or -1 to see the whole file: ");
		int record = s.nextInt();
		s.nextLine();
		if(record == -1){
			for(int i = 0; i < eFile.length()/Constants.EDGE_DAT_SIZE; i++){
				//print whole file
			}
		}
		else{
			printOneRecord(eFile,record);
		}
		s.close();
		eFile.close();
	}
}
