package si.um.voteit.voteit.dao;

import org.springframework.data.repository.CrudRepository;
import si.um.voteit.voteit.vao.Vote;

public interface VoteRepository extends CrudRepository<Vote, String> {
}
