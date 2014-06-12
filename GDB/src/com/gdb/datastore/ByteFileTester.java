package com.gdb.datastore;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ByteFileTester {
	
	public static void displayRecord(String path, int recordNo, int size) throws IOException{
		RandomAccessFile file = new RandomAccessFile(path,"r");
		file.seek(recordNo*size);
		for(int i = 0; i < size; i++){
			int value = file.readByte();
			int high = (byte) (value/16);
			int low =  (byte)(value%16); 
			System.out.print(high+""+low+" ");
		}
		file.close();
	}
	
	public static void main(String[] args) throws IOException{
		displayRecord("/Users/Naveen/git/GraphDatabase/GDB/graphIndex.idx",12,35);
	}
}
