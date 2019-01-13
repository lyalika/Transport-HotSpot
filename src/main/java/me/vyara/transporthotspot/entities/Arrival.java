package me.vyara.transporthotspot.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Arrival implements Serializable {
	private static final long serialVersionUID = -3638759253843158250L;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	@JoinColumn(name = "stop_id")
	private Stop stop;

	@ManyToOne
	@JoinColumn(name = "line_id")
	private Line line;

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timeOfArrival;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	public LocalDateTime getTimeOfArrival() {
		return timeOfArrival;
	}

	public void setTimeOfArrival(LocalDateTime timeOfArrival) {
		this.timeOfArrival = timeOfArrival;
	}

	protected Arrival() {
	}

	public Arrival(Stop stop, Line line, LocalDateTime timeOfArrival) {
		this.stop = stop;
		this.line = line;
		this.timeOfArrival = timeOfArrival;
	}
}
