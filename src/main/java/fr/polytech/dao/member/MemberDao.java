package fr.polytech.dao.member;

import fr.polytech.model.Member;

import java.util.Optional;

public interface MemberDao {
    Optional<Member> authentificate(String login, String password);
}
