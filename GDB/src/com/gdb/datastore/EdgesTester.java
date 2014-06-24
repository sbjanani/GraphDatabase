package com.gdb.datastore;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class EdgesTester {
	
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
	
	private static void printOneRecord(RandomAccessFile eFile,int record) throws IOException{
		eFile.seek(record*6);
	    int toNodeType = eFile.readByte();
	    int fromNodeType = eFile.readByte();
	    int offSet = eFile.readInt();
	    System.out.println("From Node Type: "+fromNodeType);
	    System.out.println("To Node Type: "+toNodeType);
	    System.out.println("Offset: "+offSet);
	}
	
	public static void main(String[] args) throws IOException{
		RandomAccessFile eFile = new RandomAccessFile("/Users/Naveen/DB1/edges.dat","rw");
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the number of the record you want to see or -1 to see the whole file: ");
		int record = s.nextInt();
		s.nextLine();
		if(record == -1){
			for(int i = 0; i < eFile.length()/6; i++){
				//print whole file
			}
		}
		else{
			printOneRecord(eFile,record);
		}
		eFile.close();
	}
}
