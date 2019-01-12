package me.vyara.transporthotspot.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Line implements Serializable{
	private static final long serialVersionUID = 9043867412429889255L;
	@Id
	public long id;
	public long transportTypeId;
	public String transportPrefix;
	public String name;
	
	@ManyToMany(mappedBy = "lines")
	private Set<Stop> posts = new HashSet<>();
	
	protected Line() {}
	
	public Line(long id, long transportType, String transportPrefix, String name) {
		this.id = id;
		this.transportTypeId = transportType;
		this.transportPrefix = transportPrefix;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return String.format("Line %s of type %s" , name, transportPrefix);
	}
}