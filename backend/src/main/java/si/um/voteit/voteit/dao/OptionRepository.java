package si.um.voteit.voteit.dao;

import org.springframework.data.repository.CrudRepository;
import si.um.voteit.voteit.vao.Option;

public interface OptionRepository extends CrudRepository<Option, Integer> {
}
