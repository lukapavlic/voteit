package si.um.voteit.voteit.vao;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Data @NoArgsConstructor @ToString
public class Vote {

    public Vote(String token, int owningSurveyId) {
        this.token = token;
        this.owningSurveyId = owningSurveyId;
    }

    @Id
    private String token="000000";

    private Integer value;

    private Integer optionIdSelected;

    private int owningSurveyId;

    private LocalDateTime timeVoted;

}
