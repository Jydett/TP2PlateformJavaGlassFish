package fr.polytech.model;

import fr.polytech.dao.Identifiable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Message implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    private Member author;

    @Basic
    private LocalDateTime postedTime;

    @Setter
    private Integer reputation;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<Reaction> reactions = new ArrayList<>();

    public Message(String message, Member author, LocalDateTime postedTime) {
        this.message = message;
        this.author = author;
        this.postedTime = postedTime;
        this.reputation = 0;
    }

    private static final DateTimeFormatter DATE_UNIQUE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_HOUR_FORMAT = DateTimeFormatter.ofPattern("hh:mm");

    public String getFormattedPostedTime() {
        return postedTime.format(DATE_HOUR_FORMAT);
    }

    public String getFormattedPostedDate() {
        return postedTime.format(DATE_UNIQUE_FORMAT);
    }

    public void addReaction(Reaction reaction) {
        reactions.add(reaction);
    }

    public void removeReaction(Reaction reaction) {
        reactions.remove(reaction);
    }
}