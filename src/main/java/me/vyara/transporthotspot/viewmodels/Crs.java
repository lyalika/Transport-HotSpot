package me.vyara.transporthotspot.viewmodels;

import java.util.Map;

public class Crs {
	public String type;
	public Map<String, String> properties;
	
	public Crs(String type, Map<String, String> properties) {
		this.type = type;
		this.properties = properties;
	}
}
