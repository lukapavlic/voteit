package si.um.voteit.voteit.vao;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data @NoArgsConstructor  @ToString
public class Survey {

    public enum SurveyType {
        SELECT_ONE, RANK_ALL
    }

    public Survey(String owner, String name) {
        this.owner = owner;
        this.name=name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name="unknown";

    private String owner="unknown";

    private SurveyType type=SurveyType.SELECT_ONE;

    private LocalDateTime timeCreated=LocalDateTime.now();

    private LocalDateTime timeLastModified=LocalDateTime.now();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Option> options=new ArrayList<>();

    private Boolean enabled=false;

    private Boolean resultsPubliclyAvailable=false;

    private Boolean voteCanBeAltered=false;

    public void setModifiedNow(){
        setTimeLastModified(LocalDateTime.now());
    }

    public void removeOptionWithId(int optId) {
        Option toDelete=null;
        for (Option opt : options)
            if (opt.getId()==optId) {
                toDelete=opt;
            }
        if (toDelete!=null)options.remove(toDelete);
    }

}
