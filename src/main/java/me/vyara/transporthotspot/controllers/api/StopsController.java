package me.vyara.transporthotspot.controllers.api;

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
	FeatureCollection index() {
		//return stopRepository.findAll();
		//Feature stop = new Stop(1, 2596182.03, 5266071.32, 123, "Foo").toFeature();
		Feature stop = new Stop(1,  23.3219, 42.6977, 123, "Foo").toFeature();
		return FeatureCollection.epsg4326FeatureCollection(new Feature[] {stop});
	}
	
	@GetMapping(value = "/api/stops/{id}", produces = "application/json")
	Feature show(@PathVariable("id") long id) {
		// return stopRepository.findById(id).orElseThrow(() -> new
		// StopNotFoundException(id));
		return new Stop(1, 10.0, 0.10, 123, "Foo").toFeature();
	}
}
