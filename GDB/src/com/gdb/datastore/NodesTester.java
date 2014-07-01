package com.gdb.datastore;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class NodesTester {
	
	private static int bytesToInt(byte b3, byte b2, byte b1, byte b0){
		//return (int)(Math.pow(2,24)*b3 + Math.pow(2,16)*b2 + Math.pow(2,8)*b1 + b0);
		int result = b3;
		result = result<<8;
		result += b2;
		result = result<<8;
		result += b1;
		result = result<<8;
		result += b0;
		return result;
	}
	
	private static byte[] intToBytes(int num){
		byte[] result = new byte[4];
		
		return result;
	}
	
	private static void printOneRecord(RandomAccessFile nFile,RandomAccessFile iFile,RandomAccessFile oFile,int record) throws IOException{
		iFile.seek(record*35);
		byte[] iBuffer = new byte[35];
		iFile.read(iBuffer);
		int numEdges = 0;
		System.out.println("NODE NUMBER: "+record);
		System.out.println("------------graph.idx-------------");
		for(int i = 3; i < iBuffer.length; i+=2)
			numEdges += iBuffer[i]*256 + iBuffer[i+1];
		System.out.println("Node type: "+iBuffer[0]);
		System.out.println("Incoming edge bitmap "+iBuffer[1]);
		System.out.println("Outgoing edge bitmap "+iBuffer[2]);
		System.out.println("NUM EDGES: "+numEdges);
		System.out.println("incoming/outgoing counts");
		for(int j = 0; j < 8; j++){
			int nei = iBuffer[j*4+3]*256 + iBuffer[j*4+4];
			int neo = iBuffer[j*4+5]*256 + iBuffer[j*4+6]; 
			System.out.print("("+nei+","+neo+") ");
		}
		System.out.println();
		System.out.println("-----------nodes.dat--------------");
		nFile.seek(record*84);
		byte[] buffer = new byte[84];
	    nFile.read(buffer);
	    System.out.println("NUM EDGES: "+numEdges);
	    
		for(int i = 0; i < Math.min(16,numEdges); i++){
			if(buffer[5*i] == 0)
				System.out.print("InComing ");
			else
				System.out.print("OutGoing ");
			System.out.println(bytesToInt(buffer[5*i+1],buffer[5*i+2],buffer[5*i+3],buffer[5*i+4]));	
		}
		
		if(numEdges > 16){
			byte [] oBuffer = new byte[5*(numEdges - 16)];
			int pointer = bytesToInt(buffer[83],buffer[82],buffer[81],buffer[80]);
		    System.out.println("POINTER: "+pointer);
		    oFile.seek(pointer);
		    oFile.read(oBuffer);
			for(int i = 0; i < numEdges-16; i++){
				if(oBuffer[5*i] == 0)
					System.out.print("InComing ");
				else
					System.out.print("OutGoing ");
				System.out.println(bytesToInt(oBuffer[5*i+1],oBuffer[5*i+2],oBuffer[5*i+3],oBuffer[5*i+4]));	
			}
		}
	}
	
	public static void main(String[] args) throws IOException{
		RandomAccessFile nFile = new RandomAccessFile("/Users/Naveen/DB1/nodes.dat","rw");
		RandomAccessFile iFile = new RandomAccessFile("/Users/Naveen/DB1/graph.idx","rw");
		RandomAccessFile oFile = new RandomAccessFile("/Users/Naveen/DB1/overFlow.dat","rw");
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the number of the record you want to see or -1 to see the whole file: ");
		int record = s.nextInt();
		s.nextLine();
		if(record == -1){
			for(int i = 0; i < nFile.length()/84; i++){
				System.out.println("******************************");
				printOneRecord(nFile,iFile,oFile,i);
				System.out.println("******************************");
			}
		}
		else{
			printOneRecord(nFile,iFile,oFile,record);
		}
		s.close();
		iFile.close();
		nFile.close();
	}
}
