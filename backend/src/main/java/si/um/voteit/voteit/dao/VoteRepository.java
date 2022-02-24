package si.um.voteit.voteit.dao;

import org.springframework.data.repository.CrudRepository;
import si.um.voteit.voteit.vao.Vote;

import java.util.List;

public interface VoteRepository extends CrudRepository<Vote, String> {

    List<Vote> findAllByOwningSurveyId(int id);

    void deleteAllByOwningSurveyId(int id);

}
