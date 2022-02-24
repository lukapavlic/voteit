package si.um.voteit.voteit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import si.um.voteit.voteit.vao.Survey;
import si.um.voteit.voteit.vao.SurveyType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data @ToString @NoArgsConstructor
public class SurveyWithResultsDto {

    public SurveyWithResultsDto(Survey s) {
        setId(s.getId());
        setName(s.getName());
        setOwner(s.getOwner());
        setType(s.getType());
        setCreatedTimestamp(s.getTimeCreated());
        setModifiedTimestamp(s.getTimeLastModified());
        setEnabled(s.getEnabled());
        setVotesCanBeAltered(s.getVoteCanBeAltered());
        setResultsPubliclyAvailable(s.getResultsPubliclyAvailable());
        //votesSoFar
        //firstVote
        //lastVote

        s.getOptions().forEach(o -> options.add(new OptionWithResultsDto(o)));

    }

    private int id;
    private String name;
    private String owner="unknown";
    private SurveyType type;
    private LocalDateTime createdTimestamp;
    private LocalDateTime modifiedTimestamp;
    private Boolean enabled;
    private Boolean resultsPubliclyAvailable;
    private Boolean votesCanBeAltered;

    private LocalDateTime firstVote;
    private LocalDateTime lastVote;

    private int votesSoFar;

    private List<OptionWithResultsDto> options=new ArrayList<>();

}
