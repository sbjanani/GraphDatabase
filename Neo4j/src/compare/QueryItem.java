package compare;

import java.util.Map;

/**
 * This class contains each parsed entry from the query
 * @author sysadmin
 *
 */
public class QueryItem {

	/**
	 * The vertex label specified in the query
	 */
	int vertexLabel;
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
	int edgeLabel;
	/**
	 * Attribute list for the edge
	 */
	Map<String, Object> edgeAttributeMap;
	
	/**
	 * @return the type
	 */
	public int getVertexLabel() {
		return vertexLabel;
	}
	/**
	 * @param type the type to set
	 */
	public void setVertexLabel(int vertexLabel) {
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
	public int getEdgeLabel() {
		return edgeLabel;
	}
	/**
	 * @param edgeLabel the edgeLabel to set
	 */
	public void setEdgeLabel(int edgeLabel) {
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
