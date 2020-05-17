package fr.polytech.dao.member.impl;

import fr.polytech.dao.HibernateDao;
import fr.polytech.dao.member.MemberDao;
import fr.polytech.model.Member;

import javax.ejb.Stateless;
import javax.inject.Named;
import java.util.Optional;

@Named
@Stateless
public class HibernateMemberDao extends HibernateDao<Long, Member> implements MemberDao {

    public HibernateMemberDao() {
        super(Member.class);
    }

    @Override
    public Optional<Member> authentificate(String login, String password) {
        return super.getOne(SESSION.createQuery("from Member m where m.login = :login and m.password = :pass", Member.class)
                .setParameter("login", login)
                .setParameter("pass", password));
    }

    @Override
    public Optional<Member> findUserByLogin(String login) {
        return super.getOne(SESSION.createQuery("from Member m where m.login = :login", Member.class)
                .setParameter("login", login));
    }

    @Override
    public Optional<Member> findUserByMail(String mail) {
        return super.getOne(SESSION.createQuery("from Member m where m.mail = :mail", Member.class)
                .setParameter("mail", mail));
    }
}
