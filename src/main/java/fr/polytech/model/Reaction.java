package fr.polytech.model;

import fr.polytech.dao.Identifiable;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Reaction implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Message message;

    @ManyToOne
    private Member user;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean value;
}
