package me.vyara.transporthotspot.entities;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface StopRepository extends CrudRepository<Stop, Long> {
	// List<Line> getLines(long id);
	Stop findByNumber(long number);

	// @Query("select s from Stop s where s.number like %?1 or s.name like %?1")
	@Query(value = "SELECT * FROM stop WHERE CAST(number AS TEXT) ILIKE %?1% OR name ILIKE %?1% ORDER BY number LIMIT 5", nativeQuery = true)
	Iterable<Stop> fuzzyFind(String q);
}
