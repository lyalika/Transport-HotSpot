package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Stop implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4537523009397282673L;
	@Id
	long stopId;
	double coordinates0;
	double coordinates1;
	long stop_number;
	String stop_name;
	//List<Line> line_list;
	
	protected Stop() {}
	
	public Stop(long stopId, double coordinates0, double coordinates1,long stopNumber, String stopName) {
		this.stopId = stopId;
		this.coordinates0 = coordinates0;
		this.coordinates1 = coordinates1;
		this.stop_number = stopNumber;
		this.stop_name = stopName;
	}
	
	@Override
	public String toString() {
		return String.format("Stop number: %d with name %s", stop_number, stop_name);
	}
}
