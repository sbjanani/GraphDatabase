package com.gdb.query;

import java.io.Serializable;
import java.util.Map;

/**
 * This class contains each parsed entry from the query
 * @author sysadmin
 *
 */
public class QueryItem implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The vertex label specified in the query
	 */
	byte vertexLabel;
	/**
	 * Attribute list for the vertex
	 */
	Map<String, Object> vertexAttributeMap;
	/**
	 * Direction of the edge
	 */
	Direction direction;
	/**
	 * Edge label specified in the query
	 */
	byte edgeLabel;
	/**
	 * Attribute list for the edge
	 */
	Map<String, Object> edgeAttributeMap;

	/**
	 * @return the type
	 */
	public byte getVertexLabel() {
		return vertexLabel;
	}
	/**
	 * @param vertexLabel the type to set
	 */
	public void setVertexLabel(byte vertexLabel) {
		this.vertexLabel = vertexLabel;
	}

	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}
	/**
	 * @param direction the direction to set
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	/**
	 * @return the edgeLabel
	 */
	public byte getEdgeLabel() {
		return edgeLabel;
	}
	/**
	 * @param edgeLabel the edgeLabel to set
	 */
	public void setEdgeLabel(byte edgeLabel) {
		this.edgeLabel = edgeLabel;
	}
	/**
	 * @return the attributeMap
	 */
	public Map<String, Object> getVertexAttributeMap() {
		return vertexAttributeMap;
	}
	/**
	 * @param vertexAttributeMap the edgeLabel to set
	 */
	public void setAttributeMap(Map<String, Object> vertexAttributeMap) {
		this.vertexAttributeMap = vertexAttributeMap;
	}
	/**
	 * @return the edgeAttributeMap
	 */
	public Map<String, Object> getEdgeAttributeMap() {
		return edgeAttributeMap;
	}
	/**
	 * @param edgeAttributeMap the edgeAttributeMap to set
	 */
	public void setEdgeAttributeMap(Map<String, Object> edgeAttributeMap) {
		this.edgeAttributeMap = edgeAttributeMap;
	}

	public String toString(){
		return "vertex label:"+this.getVertexLabel()+"\n vertex properties:"+this.getVertexAttributeMap()+"\n edge label:"+this.getEdgeLabel()+"\n edge properties:"+this.getEdgeAttributeMap();
	}

}
