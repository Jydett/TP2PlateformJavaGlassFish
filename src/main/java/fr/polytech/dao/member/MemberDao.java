package fr.polytech.dao.member;

import fr.polytech.dao.Page;
import fr.polytech.model.Member;

import java.util.Optional;

public interface MemberDao {
    Optional<Member> authentificate(String login, String password);
    void save(Member member);
    Optional<Member> findUserByLogin(String login);
    Optional<Member> findUserByMail(String mail);
    Page<Member> getMemberPage(int pageNumber);
}
