package movie.quotes.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "actor")
public class Actor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotNull
    private String name;

    @JsonIgnore
//    @JsonBackReference("actor-quote")
    @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL)
    private List<Quote> quotes = new ArrayList<>();

    public void addQuote(Quote quote) {
        this.quotes.add(quote);
    }
}
