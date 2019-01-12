package me.vyara.transporthotspot.entities;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface LineRepository extends CrudRepository<Line, Integer>{
	List<Line> findByName(String lineName);
}
