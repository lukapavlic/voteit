package si.um.voteit.voteit.dao;

import org.springframework.data.repository.CrudRepository;
import si.um.voteit.voteit.vao.Survey;

public interface SurveyRepository extends CrudRepository<Survey, Integer> {
}
