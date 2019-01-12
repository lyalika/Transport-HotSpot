package entities;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface StopRepository extends CrudRepository<Stop, Long>{
	//List<Line> getLines(long id);
}
