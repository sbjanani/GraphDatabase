package com.gdb.query;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K,V> extends LinkedHashMap<K,V> {
	private final int capacity;

	  public LRUCache(int capacity)
	  {
	    super(capacity + 1, 1.1f, true);
	    this.capacity = capacity;
	  }

	  protected boolean removeEldestEntry(Map.Entry<K,V> eldest)
	  {
	    return size() > capacity;
	  }
}
