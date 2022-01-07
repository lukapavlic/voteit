package si.um.voteit.voteit.vao;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data @NoArgsConstructor @ToString
public class Option {

    public Option(int orderValue, String value) {
        this.orderValue = orderValue;
        this.value = value;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int orderValue;

    private String value="Value";

}
