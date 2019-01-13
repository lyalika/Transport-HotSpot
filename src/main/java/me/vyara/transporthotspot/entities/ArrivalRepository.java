package me.vyara.transporthotspot.entities;


import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ArrivalRepository extends CrudRepository<Arrival, Long>{
	@Transactional
	void deleteByStop(Stop stop);
}
