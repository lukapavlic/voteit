package si.um.voteit.voteit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import si.um.voteit.voteit.vao.Option;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data @ToString @NoArgsConstructor
public class OptionWithResultsDto {

    public OptionWithResultsDto(Option o) {
        setId(o.getId());
        setValue(o.getValue());
        setOrderValue(o.getOrderValue());
        //voteScore
    }

    private int id;

    private int orderValue;

    private int voteScore;

    private String value="Value";

}
