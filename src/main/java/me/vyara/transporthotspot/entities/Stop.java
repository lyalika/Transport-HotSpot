package me.vyara.transporthotspot.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.*;

import me.vyara.transporthotspot.viewmodels.Feature;
import me.vyara.transporthotspot.viewmodels.Geometry;

@Entity
public class Stop implements Serializable {

	@Id
	private long id;
	private double x;
	private double y;
	private long number;
	private String name;
	private static final long serialVersionUID = 4537523009397282673L;


	protected Stop() {
	}
	
	public Stop(long id, double x, double y, long number, String name) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.number = number;
		this.name = name;
	}

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "StopsToLines", joinColumns = @JoinColumn(name = "stopId"), inverseJoinColumns = @JoinColumn(name = "lineId"))
	private Set<Line> lines = new HashSet<>();

	@Override
	public String toString() {
		return String.format("Stop number: %d with name %s", number, name);
	}
	
	public double[] getCoordinatesAsArray() {
		return new double[] {x, y};
	}
	
	public Feature toFeature() {
		Map<String, Object> options = new HashMap<String, Object>(); 
		options.put("lines", new int[] {1, 2, 3});
		
		return new Feature(id, "Point", new Geometry("Point", getCoordinatesAsArray()), options);
	}
}
