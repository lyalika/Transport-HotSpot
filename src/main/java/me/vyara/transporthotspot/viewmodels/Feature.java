package me.vyara.transporthotspot.viewmodels;

import java.util.Map;

public class Feature {
	public Geometry geometry;
	public String type;
	public long id;
	public Map<String, Object> properties;
	
	public Feature(long id, String type, Geometry geometry, Map<String, Object> properties) {
		this.geometry = geometry;
		this.type = type;
		this.id = id;
		this.properties = properties;
	}
}
