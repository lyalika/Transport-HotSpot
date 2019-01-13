package me.vyara.transporthotspot.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
public class Arrival implements Serializable {
	private static final long serialVersionUID = -3638759253843158250L;
	
    @Id
    @GeneratedValue
    private long id;
    
    @ManyToOne
    @JoinColumn(name="stop_id")
    private Stop stop;
    
    @ManyToOne
    @JoinColumn(name="line_id")
    private Line line;
    
    public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	private LocalDateTime timeOfArrival;
    
    protected Arrival() {}
    
    public Arrival(Stop stop, Line line, LocalDateTime timeOfArrival) {
    	this.stop = stop;
    	this.line = line;
    	this.timeOfArrival = LocalDateTime.now();
    }
}
