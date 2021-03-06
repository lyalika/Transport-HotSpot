package me.vyara.transporthotspot.controllers.api;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.web.bind.annotation.*;
import me.vyara.transporthotspot.entities.*;
import me.vyara.transporthotspot.exceptions.StopNotFoundException;
import me.vyara.transporthotspot.viewmodels.Feature;
import me.vyara.transporthotspot.viewmodels.FeatureCollection;

@RestController
public class StopsController {
	private StopRepository stopRepository;

	StopsController(StopRepository stopRepository) {
		this.stopRepository = stopRepository;
	}

	@GetMapping(value = "/api/stops", produces = "application/json")
	FeatureCollection index(@RequestParam(value = "q", required=false) final String q) {
		Set<Stop> list = new TreeSet<>();
		
		if(q == null) {
			stopRepository.findAll().forEach(list::add);
		} else {
			stopRepository.fuzzyFind(q).forEach(list::add);
		}

		return FeatureCollection
				.epsg4326FeatureCollection(list.stream().map(stop -> stop.toFeature()).toArray(Feature[]::new));
	}

	@GetMapping(value = "/api/stops/{id}", produces = "application/json")
	Feature show(@PathVariable("id") long id) {
		return stopRepository.findById(id).orElseThrow(() -> new StopNotFoundException(id)).toFeature(true);
		// return new Stop(1, 10.0, 0.10, 123, "Foo").toFeature();
	}
}
