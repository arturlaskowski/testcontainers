package pl.javasenior;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Horse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String breed;

    private boolean shod;

    public Horse(String name, String breed, boolean shod) {
        this.name = name;
        this.breed = breed;
        this.shod = shod;
    }
}
