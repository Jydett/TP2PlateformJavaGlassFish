package fr.polytech.model;

import fr.polytech.dao.Identifiable;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Entity
@Getter
public class Message implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    private Member author;

    @Basic
    private LocalDateTime postedTime;

    private Integer reputation;

    private static final DateTimeFormatter DATE_UNIQUE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_HOUR_FORMAT = DateTimeFormatter.ofPattern("hh:mm");

    public String getFormattedPostedTime() {
        return postedTime.format(DATE_UNIQUE_FORMAT);
    }

    public String getFormattedPostedDate() {
        return postedTime.format(DATE_HOUR_FORMAT);
    }
}