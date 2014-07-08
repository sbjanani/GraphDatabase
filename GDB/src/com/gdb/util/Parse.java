package com.gdb.util;

import java.util.ArrayList;

import com.gdb.query.QueryItem;

/**
 * This class parses the input query
 * @author 
 *
 */
public class Parse {

	/**
	 * The arraylist containing the parsed query
	 */
	ArrayList<QueryItem> queryList;

	/**
	 * @return the queryList
	 */
	public ArrayList<QueryItem> getQueryList() {
		return queryList;
	}

	/**
	 * @param queryList the queryList to set
	 */
	public void setQueryList(ArrayList<QueryItem> queryList) {
		this.queryList = queryList;
	}
	
	
}
