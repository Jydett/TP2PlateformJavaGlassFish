package fr.polytech.dao.member;

import fr.polytech.model.Member;

import java.util.Optional;

public interface MemberDao {//TODO liste des membres
    Optional<Member> authentificate(String login, String password);
    void save(Member member);
}
