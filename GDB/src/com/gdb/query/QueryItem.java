package com.gdb.query;

import java.util.Map;

public class QueryItem {

	String vertexLabel;
	Map<String, Object> vertexAttributeMap;
	Direction direction;
	String edgeLabel;
	Map<String, Object> edgeAttributeMap;
	
	/**
	 * @return the type
	 */
	public String getType() {
		return vertexLabel;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String vertexLabel) {
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
	public String getEdgeLabel() {
		return edgeLabel;
	}
	/**
	 * @param edgeLabel the edgeLabel to set
	 */
	public void setEdgeLabel(String edgeLabel) {
		this.edgeLabel = edgeLabel;
	}
	/**
	 * @return the attributeMap
	 */
	public Map<String, Object> getVertexAttributeMap() {
		return vertexAttributeMap;
	}
	/**
	 * @param attributeMap the attributeMap to set
	 */
	public void setAttributeMap(Map<String, Object> vertexAattributeMap) {
		this.vertexAttributeMap = vertexAattributeMap;
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
	
	
}
