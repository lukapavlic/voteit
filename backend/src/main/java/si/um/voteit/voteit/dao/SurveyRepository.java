package si.um.voteit.voteit.dao;

import org.springframework.data.repository.CrudRepository;
import si.um.voteit.voteit.vao.Survey;
import java.util.List;

public interface SurveyRepository extends CrudRepository<Survey, Integer> {

    List<Survey> findAllByOwner(String ownerId);

}
