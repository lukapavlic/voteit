package si.um.voteit.voteit.vao;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "Optn")
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

    @Column(name = "val")
    private String value="Value";

}
