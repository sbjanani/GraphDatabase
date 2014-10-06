package com.gdb.datastore;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

import com.gdb.util.Constants;

public class EdgesTester {
	
	private static int bytesToInt(byte b3, byte b2, byte b1, byte b0){
		return b3 << 24 | (b2 & 0xff) << 16 | (b1 & 0xff) << 8 | (b0 & 0xff);
	}
	
	private static byte[] intToBytes(int num){
		byte[] result = new byte[4];
		
		return result;
	}
	
	
	
	public static void main(String[] args) throws IOException{
		RandomAccessFile eFile = new RandomAccessFile("/Users/Naveen/DB7Nodes/edges.dat","rw");
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the number of the record you want to see or -1 to see the whole file: ");
		int record = s.nextInt();
		s.nextLine();
		
		s.close();
		eFile.close();
	}
}
