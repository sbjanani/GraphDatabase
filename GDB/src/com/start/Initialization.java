package com.start;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.gdb.query.Direction;
import com.gdb.query.QueryItem;
import com.gdb.util.Constants;

public class Initialization {

	public void pathQueryInitialization(int times, int pathLength) {
		BufferedReader bReader;
		ObjectOutputStream outStream;
		ArrayList<String> properties = new ArrayList<String>();
		ArrayList<QueryItem> query = new ArrayList<QueryItem>();
		ArrayList<ArrayList<QueryItem>> timesQuery = new ArrayList<ArrayList<QueryItem>>();
		try {
			bReader = new BufferedReader(new FileReader(Constants.PROP_FILE_PATH));
			outStream = new ObjectOutputStream(new FileOutputStream(Constants.QUERTY_INPUT_PATH));
			
			 
			String line;
			while((line = bReader.readLine())!=null){
				properties.add(line);
			}
			bReader.close();
			System.out.println("***************************************");
			System.out.println("Path Length ="+pathLength);
			
			for(int k=0; k<times; k++){
			for(int i=0; i<pathLength; i++){
				
				QueryItem qi = new QueryItem();
				byte vertexLabel = (byte)new Random().nextInt(Constants.NUMBER_OF_NODE_TYPES);
				//System.out.println("Vertex Label ="+vertexLabel);
				byte edgeLabel = (byte) new Random().nextInt(Constants.NUMBER_OF_EDGE_TYPES);
				//System.out.println("Edge Label ="+edgeLabel);
				Map<String,Object> propMap = new HashMap<String,Object>();
				int numOfProps = new Random().nextInt(Constants.MAX_VERTEX_PROPS);
				//System.out.println("Vertex Properties");
				/*for(int j=0; j<numOfProps; j++){
					int prop = new Random().nextInt(properties.size());
					if(!(properties.get(prop).split(":")[0].equalsIgnoreCase("last_name")) && !(propMap.containsKey(properties.get(prop).split(":")[0]))){
						propMap.put(properties.get(prop).split(":")[0], properties.get(prop).split(":")[1]);
						//System.out.println(properties.get(prop));
					}
				}*/
				qi.setVertexLabel(vertexLabel);
				qi.setAttributeMap(propMap);
				qi.setEdgeLabel(edgeLabel);
				qi.setDirection(Direction.OUT);
				
				query.add(qi);
				//System.out.println("-------------------");
			}
			timesQuery.add(query);
			}
		
		outStream.writeObject(timesQuery);
		outStream.flush();
		outStream.close();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
