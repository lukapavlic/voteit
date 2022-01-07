package si.um.voteit.voteit.vao;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data @NoArgsConstructor  @ToString
public class Survey {

    public static final String ATTRIBUTE_ENABLED="enabled";
    public static final String ATTRIBUTE_RESULTS_PUBLICLY_AVAILABLE="publicResults";
    public static final String ATTRIBUTE_VOTE_CAN_BE_ALTERED="alterVote";

    public Survey(String creator) {
        this.creator = creator;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String creator="unknown";

    private SurveyType type=SurveyType.SELECT_ONE;

    private LocalDateTime timeCreated=LocalDateTime.now();

    private LocalDateTime timeLastModified=LocalDateTime.now();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Option> options=new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, Boolean> attributes=new HashMap<>();

}
