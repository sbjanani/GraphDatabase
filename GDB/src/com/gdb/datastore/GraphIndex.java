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
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

import com.gdb.util.Constants;

public class GraphIndex {
	ArrayList<Byte> nodeTypes;
	ArrayList<AdjacencyRecord> adjArray;
	String sourcePath;
	String destinationPath;

	/**
	 * This is the constructor for the GraphIndex. It sets the input and output
	 * directories.
	 * 
	 * @param nodeFilePath
	 * @param edgeFilePath
	 * @throws FileNotFoundException
	 */
	public GraphIndex(String sourcePath, String destinationPath)
			throws FileNotFoundException {

		this.sourcePath = sourcePath;
		this.destinationPath = destinationPath;
	}

	/**
	 * This method reades the nodes.dat file and saves the result in the
	 * arraylist nodeTypes
	 * 
	 * @throws FileNotFoundException
	 */
	public void readNodes() throws FileNotFoundException {

		Scanner s = new Scanner(new File(sourcePath + "/nodes.dat"));
		System.out.println(sourcePath + "/nodes.dat");
		nodeTypes = new ArrayList<Byte>();
		while (s.hasNext()) {
		nodeTypes.add(s.nextInt()-1, s.nextByte());
			if (s.hasNext())
				s.nextLine();
		}
		s.close();
	}

	/**
	 * This method reads the input edge file and creates an ArrayList with the
	 * edge information
	 * 
	 * @param path
	 *            - location where the input edge file is stored
	 * @return
	 * @throws FileNotFoundException
	 */
	public void readEdges() throws FileNotFoundException {

		adjArray = new ArrayList<AdjacencyRecord>();
		for (int i = 0; i < nodeTypes.size(); i++) {
			adjArray.add(new AdjacencyRecord());
		}

		int[] incoming = new int[nodeTypes.size()];
		int[] outgoing = new int[nodeTypes.size()];

		Scanner s = new Scanner(new File(sourcePath + "/rel.dat"));
		while (s.hasNext()) {
			
			int fromNode = s.nextInt()-1;
			int toNode = s.nextInt()-1;
			byte edgeType = s.nextByte();
			

			outgoing[fromNode]++;
			incoming[toNode]++;
			if(outgoing[fromNode]<=16 && incoming[toNode]<=16){
			
				adjArray.get(fromNode).addOutGoing(toNode, edgeType);			
				adjArray.get(toNode).addIncoming(fromNode, edgeType);
			}

			//System.out.println("out for "+fromNode+" is "+adjArray.get(fromNode).getOutGoing().size());
			//System.out.println("inf for "+toNode+" is "+adjArray.get(toNode).getInComing().size());
		
			if (s.hasNext())
				s.nextLine();
		}

		s.close();

	}

	/**
	 * This method writes the node index information into the file graph.idx
	 * 
	 * @param path
	 *            - location where the output file "graph.idx" is stored
	 * @param edgeArray
	 *            - arraylist containing the edge information
	 * @throws IOException
	 */
	public void writeGraph() throws IOException {
		writeGraphIndex();
		writeNodesAndEdges();
	}

	public void writeGraphIndex() throws IOException {
		
		new File(destinationPath
				+ "/graph.idx").delete();
		RandomAccessFile file = new RandomAccessFile(destinationPath
				+ "/graph.idx", "rw");
		for (int i = 0; i < adjArray.size(); i++) {
			byte[] buffer = new byte[2 + 4 * Constants.NUMBER_OF_EDGE_TYPES];
			buffer[0] = 0;
			buffer[1] = nodeTypes.get(i);
			int index = 2;
			for (byte k = Constants.NUMBER_OF_EDGE_TYPES - 1; k >= 0; k--) {
				short s = adjArray.get(i).getInComingCount()[k];
				if(s>16)
					System.out.println("incoming greater than 16 here for node "+i);
				buffer[index++] = (byte) (s >>> 8);
				buffer[index++] = (byte) s;
				s = adjArray.get(i).getOutGoingCount()[k];
				if(s>16)
					System.out.println("outgoing greater than 16 here for node "+i);
				buffer[index++] = (byte) (s >>> 8);
				buffer[index++] = (byte) s;
			}
			file.write(buffer);

			System.out.println("index : " + i + " " + Arrays.toString(buffer));
		}
		file.close();
	}

	public void writeNodesAndEdges() throws IOException {
		
		new File(destinationPath
				+ "/nodefile.dat").delete();
		
		RandomAccessFile nFile = new RandomAccessFile(destinationPath
				+ "/nodefile.dat", "rw");

		for (int node = 0; node < adjArray.size(); node++) {
			// System.out.println("I= "+node);
			byte[] nBuffer = new byte[4 * Constants.MAX_EDGES_NODES_DAT];
			
			
			Map<Byte, ArrayList<NeighborNodeRecord>> incoming = adjArray.get(
					node).getInComing();
			Map<Byte, ArrayList<NeighborNodeRecord>> outgoing = adjArray.get(
					node).getOutGoing();

			int startBuffer = 0;

			// iterate through the incoming list
			for (Map.Entry<Byte, ArrayList<NeighborNodeRecord>> inEntry : incoming
					.entrySet()) {

				// get the arraylist of incoming neighbors per edge type
				ArrayList<NeighborNodeRecord> inlist = inEntry.getValue();

				// iterate through each incoming neighbor
				if (!(null == inlist))
					for (NeighborNodeRecord inNeighbor : inlist) {

						// store the 
						nBuffer[4 * startBuffer] = (byte) (inNeighbor
								.getNeighborNode() >>> 24);
						nBuffer[4 * startBuffer + 1] = (byte) (inNeighbor
								.getNeighborNode() >>> 16);
						nBuffer[4 * startBuffer + 2] = (byte) (inNeighbor
								.getNeighborNode() >>> 8);
						nBuffer[4 * startBuffer + 3] = (byte) inNeighbor
								.getNeighborNode();

						startBuffer++;
					}

			}

			// iterate through the outgoing list
			for (Map.Entry<Byte, ArrayList<NeighborNodeRecord>> outEntry : outgoing
					.entrySet()) {

				// get the arraylist of outgoing neighbors per edge type
				ArrayList<NeighborNodeRecord> outlist = outEntry.getValue();

				// iterate through each incoming neighbor
				if (!(null == outlist))
					for (NeighborNodeRecord outNeighbor : outlist) {

						nBuffer[4 * startBuffer] = (byte) (outNeighbor
								.getNeighborNode() >>> 24);
						nBuffer[4 * startBuffer + 1] = (byte) (outNeighbor
								.getNeighborNode() >>> 16);
						nBuffer[4 * startBuffer + 2] = (byte) (outNeighbor
								.getNeighborNode() >>> 8);
						nBuffer[4 * startBuffer + 3] = (byte) outNeighbor
								.getNeighborNode();
						startBuffer++;
					}

			}

			nFile.write(nBuffer);

			System.out.println("node : " + node + " "
					+ Arrays.toString(nBuffer)+" nbuffer size = "+nBuffer.length);

		}
		nFile.close();
		
	}

	public void displayAdjRecord() {

		System.out.println(adjArray);
		for (int i = 0; i < adjArray.size(); i++) {
			System.out.print("i= " + i);
			System.out.println(adjArray.get(i));
		}
	}
	
	
}