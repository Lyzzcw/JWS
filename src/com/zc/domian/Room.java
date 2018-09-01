package com.zc.domian;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Room {
	
	private Map<String, Object> t = new ConcurrentHashMap<String, Object>();

	public Object getSth(String key) {
		return t.get(key);
	}

	public void setSth(String key, Object value) {
		t.put(key, value);
	}
	public void removeKey(String key){
		t.remove(key);
	}
	
	public Map<String, Object> getT() {
		return t;
	}

	public void setT(Map<String, Object> t) {
		this.t = t;
	}
	public void addT(Map<String, Object> t){
		if (this.t!=null) {
			this.t.putAll(t);
		}
	}

}
