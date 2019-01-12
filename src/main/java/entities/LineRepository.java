package entities;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface LineRepository extends CrudRepository<Line, Integer>{
	//Line findById(int id);
}
