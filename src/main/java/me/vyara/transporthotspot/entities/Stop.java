package me.vyara.transporthotspot.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.persistence.*;

import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;

import me.vyara.transporthotspot.viewmodels.Feature;
import me.vyara.transporthotspot.viewmodels.Geometry;

@Entity
@Table(indexes = { @Index(columnList = "name"), @Index(columnList = "number", unique = true) })
public class Stop implements Serializable, Comparable<Stop> {

	@Id
	private long id;
	private double x;
	private double y;
	private long number;
	private String name;

	@Column(name = "location", nullable = false, columnDefinition = "geometry(Point,4326)")
	private com.vividsolutions.jts.geom.Point location;

	private static final long serialVersionUID = 4537523009397282673L;

	protected Stop() {
	}

	public Stop(long id, double x, double y, long number, String name) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.number = number;
		this.name = name;
		GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 3857);
		this.location = transform(factory.createPoint(new Coordinate(x, y)));
	}
	
	// @JoinTable(name = "StopsToLines", joinColumns = @JoinColumn(name = "stopId"),
	// inverseJoinColumns = @JoinColumn(name = "lineId"))
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	public Set<Line> lines = new TreeSet<>();

	public void setLines(TreeSet<Line> lines) {
		this.lines = lines;
	}
	@OneToMany(mappedBy = "stop")
	private Set<Arrival> arrivals = new HashSet<>();

	@Override
	public String toString() {
		return String.format("Stop number: %d with name %s", number, name);
	}

	public double[] getCoordinatesAsArray() {
		return new double[] { location.getCoordinate().x, location.getCoordinate().y };
	}
	
	public Feature toFeature() {
		return toFeature(false);
	}

	public Feature toFeature(boolean withArrivals) {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("lines", new TreeSet<Line>(this.lines));
		if (withArrivals) {
			Map<String, TreeSet<String>> simplifiedArrivals = new HashMap<>();
			this.arrivals.forEach(new Consumer<Arrival>() {
				@Override
				public void accept(Arrival arrival) {
					if (!simplifiedArrivals.containsKey(arrival.getLine().humanName())) {
						simplifiedArrivals.put(arrival.getLine().humanName(), new TreeSet<>());
					}
					simplifiedArrivals.get(arrival.getLine().humanName()).add(arrival.getTimeOfArrival().toString());
				}
			});
			properties.put("arrivals", simplifiedArrivals);
		}
		properties.put("name", name);
		properties.put("number", number);

		return new Feature(id, "Point", new Geometry("Point", getCoordinatesAsArray()), properties);
	}

	private static Point transform(Point point) {
		CoordinateReferenceSystem sourceCRS;
		CoordinateReferenceSystem targetCRS;
		MathTransform transform;
		try {
			sourceCRS = CRS.decode("EPSG:3857");
			targetCRS = CRS.decode("EPSG:4326");
			transform = CRS.findMathTransform(sourceCRS, targetCRS, false);
			GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);
			Coordinate newCoordinate = JTS.transform(point, transform).getCoordinate();
			return factory.createPoint(new Coordinate(newCoordinate.y, newCoordinate.x));
		} catch (FactoryException e) {
			return null;
		} catch (MismatchedDimensionException e) {
			return null;
		} catch (TransformException e) {
			return null;
		}

	}

	@Override
	public int compareTo(Stop other) {
		return Long.compare(number, other.number);
	}
}
