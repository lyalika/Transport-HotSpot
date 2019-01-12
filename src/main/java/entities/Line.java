package entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Line implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9043867412429889255L;
	@Id
	private long lineId;
	private long transport_type_id;
	private String transport_prefix;
	private String line_name;
	
	protected Line() {}
	
	public Line(long lineId, long transportType, String transportPrefix, String lineName) {
		this.lineId = lineId;
		this.transport_type_id = transportType;
		this.transport_prefix = transportPrefix;
		this.line_name = lineName;
	}
	
	@Override
	public String toString() {
		return String.format("Line %s of type %s" , line_name, transport_prefix);
	}
}
