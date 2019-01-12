package me.vyara.transporthotspot.viewmodels;

import java.util.Collections;

public class FeatureCollection {
	public String type;
	public Crs crs;
	public Feature[] features;

	public FeatureCollection(Crs crs, Feature[] features) {
		this.type = "FeatureCollection";
		this.crs = crs;
		this.features = features;
	}

	public static FeatureCollection epsg3857FeatureCollection(Feature[] features) {
		return new FeatureCollection(new Crs("name", Collections.singletonMap("name", "EPSG:3857")), features);
	}
	
	public static FeatureCollection epsg4326FeatureCollection(Feature[] features) {
		return new FeatureCollection(new Crs("name", Collections.singletonMap("name", "EPSG:4326")), features);
	}
}
