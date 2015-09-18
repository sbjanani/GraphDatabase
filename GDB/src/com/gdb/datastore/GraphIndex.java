/**
 * This file populates the "graph.idx" file
 * @author janani
 *
 */
package com.gdb.datastore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.gdb.util.Constants;

public class GraphIndex {
	ArrayList<Byte> nodeTypes;
	ArrayList<AdjacencyRecord> adjArray;
	String sourcePath;
	String destinationPath;
	ArrayList<Byte> nodeProps;
	List<RandomAccessFile> filePool = new ArrayList<RandomAccessFile>();

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
	 * @throws IOException
	 */
	public void readNodes() throws IOException {

		/*Scanner s = new Scanner(new File(sourcePath + "/nodes.dat"));
		System.out.println(sourcePath + "/nodes.dat");
		nodeTypes = new ArrayList<Byte>();
		while (s.hasNext()) {
			nodeTypes.add(s.nextInt() - 1, s.nextByte());
			if (s.hasNext())
				s.nextLine();
		}
		s.close();*/
		BufferedReader bReader = new BufferedReader(new FileReader(sourcePath+"/gplus3.dat"));
		//RandomAccessFile raf = new RandomAccessFile(destinationPath+"/properties.dat","rw");
		nodeTypes = new ArrayList<Byte>();
		nodeProps = new ArrayList<Byte>();
		String line;
		int count=0;
		while(( line = bReader.readLine())!=null){
			count++;
			String[] vertex = line.split(" ");
			nodeTypes.add(Integer.parseInt(vertex[0]), Byte.parseByte(vertex[1]));
			byte prop = (byte)0;

			for(int i=2; i<vertex.length; i++){
				if(!(vertex[i].equalsIgnoreCase("\\N")))
					prop = (byte) (prop | (byte)(Math.pow(2, (i-2))));
			}
			nodeProps.add(Integer.parseInt(vertex[0]), prop);
			if(count%1000==0)
				System.out.println("Reading node"+count);
		}
		bReader.close();
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

		// int[] incoming = new int[nodeTypes.size()];
		// int[] outgoing = new int[nodeTypes.size()];

		Scanner s = new Scanner(new File(sourcePath + "/rel.dat"));
		int count=0;
		while (s.hasNext()) {
			count++;
			int fromNode = s.nextInt() ;
			int toNode = s.nextInt() ;
			byte edgeType = s.nextByte();

			// outgoing[fromNode]++;
			// incoming[toNode]++;
			// if(outgoing[fromNode]<=16 && incoming[toNode]<=16){

			adjArray.get(fromNode).addOutGoing(toNode, edgeType);
			adjArray.get(toNode).addIncoming(fromNode, edgeType);
			// }

			// System.out.println("out for "+fromNode+" is "+adjArray.get(fromNode).getOutGoing().size());
			// System.out.println("inf for "+toNode+" is "+adjArray.get(toNode).getInComing().size());

			if (s.hasNext())
				s.nextLine();

			if(count%1000==0)
				System.out.println("Reading edge "+count);
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
		writeGraphIndexProp();
		writeNodesAndEdges();
	}

	public void writeGraphIndexProp() throws IOException {

		//new File(destinationPath + "/gdb_data/graph.idx").delete();
		RandomAccessFile file = new RandomAccessFile(destinationPath
				+ "/graph.idx", "rw");

		for (int i = 0; i < adjArray.size(); i++) {
			byte[] buffer = new byte[3 + 4 * Constants.NUMBER_OF_EDGE_TYPES];
			buffer[0] = 0;
			buffer[1] = nodeTypes.get(i);
			int index = 2;
			for (byte k = Constants.NUMBER_OF_EDGE_TYPES - 1; k >= 0; k--) {
				short s = adjArray.get(i).getOutGoingCount()[k];
				buffer[index++] = (byte) (s >>> 8);
				buffer[index++] = (byte) s;
				s = adjArray.get(i).getInComingCount()[k];
				buffer[index++] = (byte) (s >>> 8);
				buffer[index++] = (byte) s;

			}
			buffer[index++] = nodeProps.get(i);
			file.write(buffer);

			if(i%1000==0)
				System.out.println("Writing index "+i);

			//System.out.println("index : " + i + " " + Arrays.toString(buffer));
		}
		file.close();
	}
	public void writeGraphIndex() throws IOException {

		//new File(destinationPath + "/gdb_data/graph.idx").delete();
		RandomAccessFile file = new RandomAccessFile(destinationPath
				+ "/graph.idx", "rw");
		for (int i = 0; i < adjArray.size(); i++) {
			byte[] buffer = new byte[2 + 4 * Constants.NUMBER_OF_EDGE_TYPES];
			buffer[0] = 0;
			buffer[1] = nodeTypes.get(i);
			int index = 2;
			for (byte k = Constants.NUMBER_OF_EDGE_TYPES - 1; k >= 0; k--) {
				short s = adjArray.get(i).getOutGoingCount()[k];
				buffer[index++] = (byte) (s >>> 8);
				buffer[index++] = (byte) s;
				s = adjArray.get(i).getInComingCount()[k];
				buffer[index++] = (byte) (s >>> 8);
				buffer[index++] = (byte) s;

			}
			file.write(buffer);

			System.out.println("index : " + i + " " + Arrays.toString(buffer));
		}
		file.close();
	}

	public void writeNodesAndEdges() throws IOException {

		//new File(destinationPath	+ "/gdb_data/nodefile0.dat").delete();
		RandomAccessFile nFile = null;

		for (int node = 0; node < adjArray.size(); node++) {

			nFile = new RandomAccessFile(destinationPath
					+ "/nodefile0.dat", "rw");
			nFile.seek(node*Constants.MAX_EDGES_NODES_DAT*4);

			// System.out.println("I= "+node);
			byte[] nBuffer = new byte[4 * Constants.MAX_EDGES_NODES_DAT];


			Map<Byte, ArrayList<NeighborNodeRecord>> incoming = adjArray.get(
					node).getInComing();
			Map<Byte, ArrayList<NeighborNodeRecord>> outgoing = adjArray.get(
					node).getOutGoing();

			int startBuffer = 0;
			int count=0;
			int fileCount = 0;

			// iterate through the outgoing list
			for (Map.Entry<Byte, ArrayList<NeighborNodeRecord>> outEntry : outgoing
					.entrySet()) {

				// get the arraylist of outgoing neighbors per edge type
				ArrayList<NeighborNodeRecord> outlist = outEntry.getValue();

				// iterate through each outgoing neighbor
				if (!(null == outlist))
					for (NeighborNodeRecord outNeighbor : outlist) {

						if(count%Constants.MAX_EDGES_NODES_DAT==0 && count>=Constants.MAX_EDGES_NODES_DAT){
							System.out.println("node : " + node + " "
									+ Arrays.toString(nBuffer)+" nbuffer size = "+nBuffer.length);
							nFile.write(nBuffer);
							nFile.close();


							fileCount++;

							nFile = new RandomAccessFile(destinationPath
									+ "/nodefile"+fileCount+".dat", "rw");
							startBuffer=0;
							nFile.seek(node*Constants.MAX_EDGES_NODES_DAT*4);
							nBuffer = new byte[4 * Constants.MAX_EDGES_NODES_DAT];
						}

						nBuffer[4 * startBuffer] = (byte) (outNeighbor
								.getNeighborNode() >>> 24);
						nBuffer[4 * startBuffer + 1] = (byte) (outNeighbor
								.getNeighborNode() >>> 16);
						nBuffer[4 * startBuffer + 2] = (byte) (outNeighbor
								.getNeighborNode() >>> 8);
						nBuffer[4 * startBuffer + 3] = (byte) outNeighbor
								.getNeighborNode();
						startBuffer++;
						count++;


					}

			}

			// iterate through the incoming list
			for (Map.Entry<Byte, ArrayList<NeighborNodeRecord>> inEntry : incoming
					.entrySet()) {

				// get the arraylist of incoming neighbors per edge type
				ArrayList<NeighborNodeRecord> inlist = inEntry.getValue();

				// iterate through each incoming neighbor
				if (!(null == inlist))
					for (NeighborNodeRecord inNeighbor : inlist) {

						if(count%Constants.MAX_EDGES_NODES_DAT==0 && count>=Constants.MAX_EDGES_NODES_DAT){
							System.out.println("node : " + node + " "
									+ Arrays.toString(nBuffer)+" nbuffer size = "+nBuffer.length);
							nFile.write(nBuffer);
							nFile.close();

							fileCount++;

							nFile = new RandomAccessFile(destinationPath
									+ "/nodefile"+fileCount+".dat", "rw");
							startBuffer=0;
							nFile.seek(node*Constants.MAX_EDGES_NODES_DAT*4);
							nBuffer = new byte[4 * Constants.MAX_EDGES_NODES_DAT];
						}
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
						count++;



					}

			}



			nFile.write(nBuffer);
			nFile.close();

			//System.out.println("node : " + node + " "
			//+ Arrays.toString(nBuffer)+" nbuffer size = "+nBuffer.length);

			if(node%1000==0)
				System.out.println("Writing edges for node "+node);

		}



	}



	public void displayAdjRecord() {

		System.out.println(adjArray);
		for (int i = 0; i < adjArray.size(); i++) {
			System.out.print("i= " + i);
			System.out.println(adjArray.get(i));
		}
	}

	public void readAndWriteFile() throws NumberFormatException, IOException{

		// input files
		BufferedReader incomingFile = new BufferedReader(new FileReader(sourcePath+"/rel_incoming.dat"));
		BufferedReader outgoingFile = new BufferedReader(new FileReader(sourcePath+"/rel_outgoing.dat"));

		//output files
		RandomAccessFile indexFile = new RandomAccessFile(destinationPath+ "/graph.idx", "rw");
		//RandomAccessFile relFile = new RandomAccessFile(destinationPath+ "/relfile0.dat", "rw");

		int counter=0;

		//int numOfVertices = Integer.parseInt(incomingFile.readLine());
		int numOfVertices = 4847572;
		//int numOfVertices = 10;
		//outgoingFile.readLine();

		String incomingLine = incomingFile.readLine();
		String outgoingLine = outgoingFile.readLine();

		String[] inParts = incomingLine.split(" ");
		String[] outParts = outgoingLine.split(" ");

		int prevIncoming = Integer.parseInt(inParts[1]);
		int prevOutgoing = Integer.parseInt(outParts[0]);

		Map<Byte,List<NeighborNodeRecord>> incomingMap = new HashMap<Byte,List<NeighborNodeRecord>>();
		Map<Byte,List<NeighborNodeRecord>> outgoingMap = new HashMap<Byte,List<NeighborNodeRecord>>();

		while(counter < numOfVertices){
			byte vertexType = Byte.parseByte(outParts[1]);

			if(outParts.length==4){
				// if it is the same outgoing vertex
				while(prevOutgoing==Integer.parseInt(outParts[0]) && outgoingLine!=null){
					if(outgoingMap.containsKey(Byte.parseByte(outParts[3]))){
						NeighborNodeRecord neighbor = new NeighborNodeRecord(Integer.parseInt(outParts[2]),Byte.parseByte(outParts[3]));
						outgoingMap.get(Byte.parseByte(outParts[3])).add(neighbor);
					}
					else{
						List<NeighborNodeRecord> list = new ArrayList<NeighborNodeRecord>();
						NeighborNodeRecord neighbor = new NeighborNodeRecord(Integer.parseInt(outParts[2]),Byte.parseByte(outParts[3]));
						list.add(neighbor);
						outgoingMap.put(Byte.parseByte(outParts[3]), list);
					}

					if((outgoingLine=outgoingFile.readLine())!=null)
						outParts = outgoingLine.split(" ");


				}
			}
			else{
				if((outgoingLine=outgoingFile.readLine())!=null)
					outParts = outgoingLine.split(" ");
			}



			if(inParts.length==3){
				// The vertex does not have any incoming edges, write the empty incoming map
				if(prevOutgoing==Integer.parseInt(inParts[1])){
					while(prevIncoming==Integer.parseInt(inParts[1])&& incomingLine!=null){
						if(incomingMap.containsKey(Byte.parseByte(inParts[2]))){
							NeighborNodeRecord neighbor = new NeighborNodeRecord(Integer.parseInt(inParts[0]),Byte.parseByte(inParts[2]));
							incomingMap.get(Byte.parseByte(inParts[2])).add(neighbor);
						}
						else{
							List<NeighborNodeRecord> list = new ArrayList<NeighborNodeRecord>();
							NeighborNodeRecord neighbor = new NeighborNodeRecord(Integer.parseInt(inParts[0]),Byte.parseByte(inParts[2]));
							list.add(neighbor);
							incomingMap.put(Byte.parseByte(inParts[2]), list);
						}

						if((incomingLine = incomingFile.readLine())!=null)
							inParts = incomingLine.split(" ");



					}


				}
			}
			else{
				if((incomingLine = incomingFile.readLine())!=null)
					inParts = incomingLine.split(" ");
			}


			byte[] idx = returnGraphIndexRecord(vertexType, outgoingMap, incomingMap);
			//System.out.println("index node "+counter+" : "+Arrays.toString(idx));
			indexFile.write(idx);

			writeEdges(prevOutgoing,incomingMap, outgoingMap);
			counter++;
			prevIncoming = Integer.parseInt(inParts[1]);
			prevOutgoing = Integer.parseInt(outParts[0]);
			incomingMap = new HashMap<Byte,List<NeighborNodeRecord>>();
			outgoingMap = new HashMap<Byte,List<NeighborNodeRecord>>();

			if(counter%1000==0)
				System.out.println("Writing node  "+counter);

		}

		incomingFile.close();
		outgoingFile.close();
		indexFile.close();
	}

	public byte[] returnGraphIndexRecord(int vertexType, Map<Byte,List<NeighborNodeRecord>> outgoingMap, Map<Byte,List<NeighborNodeRecord>> incomingMap){
		byte[] buffer = new byte[3 + 4 * Constants.NUMBER_OF_EDGE_TYPES];

		// deleted bit
		buffer[0] = 0;

		// vertex type
		buffer[1] = (byte)vertexType;
		int index = 2;
		for (byte k = Constants.NUMBER_OF_EDGE_TYPES - 1; k >= 0; k--) {
			short s;

			if(outgoingMap.containsKey(k))
				s = (short)outgoingMap.get(k).size();
			else
				s=0;

			// outgoing count
			buffer[index++] = (byte) (s >>> 8);
			buffer[index++] = (byte) s;

			if(incomingMap.containsKey(k))
				s = (short)incomingMap.get(k).size();
			else
				s=0;

			// incoming count
			buffer[index++] = (byte) (s >>> 8);
			buffer[index++] = (byte) s;


		}

		// properties
		buffer[index] = 0;
		//System.out.println("indenode :"+Arrays.toString(buffer));
		return buffer;
	}

	public void writeEdges(int node, Map<Byte,List<NeighborNodeRecord>> incoming, Map<Byte,List<NeighborNodeRecord>> outgoing) throws IOException {
		RandomAccessFile nFile = null;
		if(filePool.isEmpty()){
			nFile = new RandomAccessFile(destinationPath+ "/relfile0.dat", "rw");
			filePool.add(nFile);
		}
		nFile = filePool.get(0);
		nFile.seek(node*Constants.MAX_EDGES_NODES_DAT*4);


		// System.out.println("I= "+node);
		byte[] nBuffer = new byte[4 * Constants.MAX_EDGES_NODES_DAT];

		int startBuffer = 0;
		int count=0;
		int fileCount = 0;

		// iterate through the outgoing list
		for (Map.Entry<Byte, List<NeighborNodeRecord>> outEntry : outgoing
				.entrySet()) {

			// get the arraylist of outgoing neighbors per edge type
			List<NeighborNodeRecord> outlist = outEntry.getValue();

			// iterate through each outgoing neighbor
			if (!(null == outlist))
				for (NeighborNodeRecord outNeighbor : outlist) {

					if(count%Constants.MAX_EDGES_NODES_DAT==0 && count>=Constants.MAX_EDGES_NODES_DAT){
							/*System.out.println("node : " + node + " "
							+ Arrays.toString(nBuffer)+" nbuffer size = "+nBuffer.length);*/
						nFile.write(nBuffer);
						//nFile.close();


						fileCount++;

						if(filePool.size()<=fileCount){
							nFile = new RandomAccessFile(destinationPath
									+ "/relfile"+fileCount+".dat", "rw");
							filePool.add(nFile);
						}
						else
							nFile = filePool.get(fileCount);

						startBuffer=0;
						nFile.seek(node*Constants.MAX_EDGES_NODES_DAT*4);
						nBuffer = new byte[4 * Constants.MAX_EDGES_NODES_DAT];
					}

					nBuffer[4 * startBuffer] = (byte) (outNeighbor
							.getNeighborNode() >>> 24);
					nBuffer[4 * startBuffer + 1] = (byte) (outNeighbor
							.getNeighborNode() >>> 16);
					nBuffer[4 * startBuffer + 2] = (byte) (outNeighbor
							.getNeighborNode() >>> 8);
					nBuffer[4 * startBuffer + 3] = (byte) outNeighbor
							.getNeighborNode();
					startBuffer++;
					count++;


				}

		}

		// iterate through the incoming list
		for (Map.Entry<Byte, List<NeighborNodeRecord>> inEntry : incoming
				.entrySet()) {

			// get the arraylist of incoming neighbors per edge type
			List<NeighborNodeRecord> inlist = inEntry.getValue();

			// iterate through each incoming neighbor
			if (!(null == inlist))
				for (NeighborNodeRecord inNeighbor : inlist) {

					if(count%Constants.MAX_EDGES_NODES_DAT==0 && count>=Constants.MAX_EDGES_NODES_DAT){
							/*System.out.println("node : " + node + " "
									+ Arrays.toString(nBuffer));*/
						nFile.write(nBuffer);
						//nFile.close();

						fileCount++;

						if(filePool.size()<=fileCount){
							nFile = new RandomAccessFile(destinationPath
									+ "/relfile"+fileCount+".dat", "rw");
							filePool.add(nFile);
						}
						else
							nFile = filePool.get(fileCount);
						startBuffer=0;
						nFile.seek(node*Constants.MAX_EDGES_NODES_DAT*4);
						nBuffer = new byte[4 * Constants.MAX_EDGES_NODES_DAT];
					}
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
					count++;



				}

		}



		nFile.write(nBuffer);
		//nFile.close();

			/*System.out.println("node : " + node + " "
					+ Arrays.toString(nBuffer));
			*/


	}


}