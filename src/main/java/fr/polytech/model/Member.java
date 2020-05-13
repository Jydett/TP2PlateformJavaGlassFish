package fr.polytech.model;

import fr.polytech.dao.Identifiable;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
public class Member implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String login;

    private String mail;

    private Boolean administrator;
}
