package com.gdb.query;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


/**
 * This class contains all the querying methods
 * @author
 *
 */
public class Query {

        String path;
        Graph graph;
        public static long execTime=0;
        public static int entryTimes=0;
        public static long propCheckTime=0;
        Map<String,Byte> propMap = new HashMap<String,Byte>();
        Queue<Integer> unwantedList = new LinkedList<Integer>();

        public Query(Graph graph){
                this.graph=graph;
                propMap.put("GENDER", (byte)0);
                propMap.put("LAST_NAME", (byte)1);
                propMap.put("PLACE", (byte)2);
                propMap.put("JOB_TITLE", (byte)3);
                propMap.put("UNIVERSITY", (byte)4);
                propMap.put("INSTITUTION", (byte)5);
        }
        /**
         * This method returns the set of paths satisfying the given query
         * The paths are returned in the form Vertex-Edge-Vertex-Edge
         * @param queryPath
         * @return
         */
        public ArrayList<ArrayList<Element>> pathQuery(String queryPath){

                return null;
        }

        /**
         * This method returns the attributes of the given element
         * @param element - vertex/edge object with id
         * @return
         */
        public Map<String, Object> getAttributes(Element element){

                return null;
        }



        protected void khop(Vertex vertex, int k, Direction direction, Byte ... labelList)throws IOException{


                for(byte label : labelList)
                        unwantedList.addAll(graph.typeIndex.get(label));

                ArrayList<Vertex> curr = new ArrayList<Vertex>();
                ArrayList<Vertex> next = new ArrayList<Vertex>();
                HashSet<Object> result = new HashSet<Object>();

                curr.add(vertex);

                for(int real_hops = 0; real_hops < k; real_hops++) {

                        for (Vertex u : curr) {



                                Iterable<Integer> itr = u.getEdges(direction,labelList);
                                if(!(null==itr))
                                        for (Integer r : itr) {
                                                Vertex v = graph.getVertex(r);



                                                if (result.add(v.getId())) {
                                                        next.add(v);
                                                }
                                        }

                        }

                        if(next.size() == 0)
                                break;

                        ArrayList<Vertex> tmp = curr;
                        curr = next;
                        tmp.clear();
                        next = tmp;
                }

                //System.out.println("Total neighborhood = "+result.size());


        }
        protected void khop(Vertex vertex, int k, Direction direction)throws IOException{



                ArrayList<Vertex> curr = new ArrayList<Vertex>();
                ArrayList<Vertex> next = new ArrayList<Vertex>();
                HashSet<Object> result = new HashSet<Object>();

                curr.add(vertex);

                for(int real_hops = 0; real_hops < k; real_hops++) {

                        for (Vertex u : curr) {



                                Iterable<Integer> itr = u.getEdges(direction);
                                for (Integer r : itr) {
                                        Vertex v = graph.getVertex(r);



                                        if (result.add(v.getId())) {
                                                next.add(v);
                                        }
                                }

                        }

                        if(next.size() == 0)
                                break;

                        ArrayList<Vertex> tmp = curr;
                        curr = next;
                        tmp.clear();
                        next = tmp;
                }

                //System.out.println("Total neighborhood = "+result.size());


        }

        public void pathQuery(ArrayList<QueryItem> query, Graph G) throws IOException {





                // get the first item in the query
                QueryItem startQuery = query.get(0);

                // get the vertices belonging to the first lable type in the query
                ArrayList<Integer> vertices = G.getTypeIndex().get(startQuery.getVertexLabel());
                String vertexList = "";
                for(Integer vertex : vertices)
                        vertexList = vertexList+vertex+",";
                vertexList += ";";
                vertexList = vertexList.replace(",;", "");
                if(!startQuery.getVertexAttributeMap().isEmpty())
                        vertices = graph.mysql.getNextNeighbors(vertexList, startQuery.getVertexAttributeMap());

                long startTime = System.currentTimeMillis();
                // iterate through all the vertices and send only those vertices that
                // match all the constraints
                if(!(vertices==null))
                        for (Integer id : vertices) {

                                Vertex vertex = G.getVertex(id);
                                if(!(graph.getIndex(vertex.getId()).getOutgoingEdgeNums().containsKey(startQuery.getEdgeLabel())))
                                        continue;
                                // get the corresponding attributes for the vertex
				/*Map<String, Object> attributes = startQuery.getVertexAttributeMap();

				// if the vertex does not have any attribtues, directly pass the
				// vertex
				boolean check=true;
				if (!(attributes.isEmpty())) {
					Set<String> keys = attributes.keySet();
					for(String key: keys){
						if((((graph.getGraphIndex().get(vertex.getId()).getPropBitmap()))&((byte)(Math.pow(2, propMap.get(key)))))==0){	
							check=false;
							break;
						}
					}
					if(check==false){
						continue;
					}
					long start = System.currentTimeMillis();
					int match = graph.mysql.checkVertexAttributes("vertex", vertex.id, attributes);
					propCheckTime += System.currentTimeMillis()-start;
					if(match==0)
						continue;
				}	*/
                                //System.out.println("here");


                                ArrayList<ArrayList<Vertex>> path = getPathQuery(vertex, new ArrayList<Vertex>(), query, 0);

                                // print the path results
				/*if (!(null == path)){
					for (ArrayList<Vertex> neighbor : path) {
						for (Vertex v : neighbor) {
							System.out.print(v.getId() + " ");
						}
						System.out.println();
					}
				}*/


                        }


                long stopTime = System.currentTimeMillis();
                execTime += stopTime - startTime;

        }

        /**
         * This is a recursive program that computes the path for a given query
         *
         * @param vertex
         *            - the current vertex
         * @param visitedArray
         *            - array of vertices that are already in the path. This
         *            prevents cycles
         * @param query
         *            - An arraylist of query items
         * @param level
         *            - the current level of the query
         * @return
         * @throws IOException
         */
        public ArrayList<ArrayList<Vertex>> getPathQuery(Vertex vertex,
                                                         ArrayList<Vertex> visitedArray, ArrayList<QueryItem> query,
                                                         int level) throws IOException {


                // if the vertex is already in the path, return null
                if (visitedArray.contains(vertex))
                        return null;

                QueryItem qItem = query.get(level);
                int match=1;






                // if you reach the depth desired, return the end vertex if it satisfies
                // all the constraints. Else return null
                if (match==1) {

                        if (level == query.size() - 1) {
                                ArrayList<ArrayList<Vertex>> returnList = new ArrayList<ArrayList<Vertex>>();
                                ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
                                vertexList.add(vertex);
                                returnList.add(vertexList);
                                return returnList;
                        } else {

                                // arraylist of arraylist containing neighborlist
                                ArrayList<ArrayList<Vertex>> neighborList = new ArrayList<ArrayList<Vertex>>();

                                String vertexString = "";
                                // get the edges
                                Iterable<Integer> outEdges = vertex.getEdges(Direction.OUT, qItem.getEdgeLabel());
                                if(!(outEdges==null))
                                        for(Integer edge : outEdges)
                                                vertexString += edge+",";
                                vertexString += ";";
                                vertexString = vertexString.replace(",;", "");


                                long start = System.currentTimeMillis();
                                if(!( query.get(level+1).getVertexAttributeMap().isEmpty()) && !(vertexString.equals(";")))
                                        outEdges = graph.mysql.getNextNeighbors(vertexString, query.get(level+1).getVertexAttributeMap());
                                propCheckTime += System.currentTimeMillis() - start;
				/*	if(!(null==qItem.edgeAttributeMap))
					outEdges = graph.mysql.getVertexFromEdgeAttribute(vertex.getId(), Direction.OUT, vertexString, qItem.edgeAttributeMap);*/

                                ArrayList<Vertex> tempVisitedArray = new ArrayList<Vertex>();
                                tempVisitedArray.addAll(visitedArray);
                                tempVisitedArray.add(vertex);

                                // iterate through all the edges
                                if(!(outEdges==null))
                                        for (Integer v : outEdges) {

                                                if(graph.getIndex(v).getVertexType()!=query.get(level+1).getVertexLabel())
                                                        continue;

                                                int result=1;
                                                if(!(qItem.edgeAttributeMap==null))
                                                        result = graph.mysql.checkEdgeAttributes(vertex.getId(), Direction.OUT, qItem.edgeAttributeMap);


                                                if(result==0)
                                                        continue;

                                                int templevel = level;
                                                ++entryTimes;
                                                // get the next level neighbor
                                                Vertex neighborVertex = graph.getVertex(v);

                                                // get the neighborhood of the current vertex
                                                ArrayList<ArrayList<Vertex>> returnList = getPathQuery(
                                                        neighborVertex, tempVisitedArray, query,
                                                        ++templevel);

                                                if (!(null == returnList)) {
                                                        // add this list to the master list
                                                        for (ArrayList<Vertex> vList : returnList) {
                                                                ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
                                                                vertexList.add(vertex);
                                                                vertexList.addAll(vList);
                                                                neighborList.add(vertexList);
                                                        }
                                                }



                                        }

                                // neighborhood list
                                return neighborList;
                        }
                }

                // if the vertex does not match the constrains return null
                else
                        return null;

        }

        public void getStructure(byte vertexLabel, Map<Byte,Short> edgeCountMap){

                ArrayList<Integer> result = new ArrayList<Integer>();
                ArrayList<Integer> vertices = graph.getTypeIndex().get(vertexLabel);

                for(int vertex : vertices){

                        Vertex v;
                        try {
                                v = graph.getVertex(vertex);
                                Boolean match = true;
                                for(Map.Entry<Byte, Short> edgeCount : edgeCountMap.entrySet()){
                                        long startTime = System.nanoTime();

                                        if((!v.getOutgoingEdgeCount().containsKey(edgeCount.getKey()))){
                                                match=false;
                                                break;
                                        }
                                        else if(!(v.getOutgoingEdgeCount().get(edgeCount.getKey()).equals(edgeCount.getValue()))){
                                                match = false;
                                                break;
                                        }

                                        execTime += System.nanoTime() - startTime;
                                }
					/*if(match==true)
						result.add(vertex);*/

                        } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }

                }


        }


}
