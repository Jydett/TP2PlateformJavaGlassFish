package fr.polytech.bean;

import fr.polytech.dao.member.MemberDao;
import fr.polytech.model.Member;
import lombok.Getter;
import lombok.Setter;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ValidationException;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Optional;

@Named
@ViewScoped
public class SignUpForm implements Serializable {

    @NotBlank
    @Getter
    @Setter
    private String login;

    @NotBlank
    @Getter
    @Setter
    private String password;

    @NotBlank
    @Getter
    @Setter
    private String username;

    @Email
    @Getter
    @Setter
    private String mail;

    @Inject
    private MemberDao memberDao;

    @Inject
    private ConnectedUser connectedUser;

    public String createUser() {
        Optional<Member> optionalMember = memberDao.authentificate(login, password);
        if (optionalMember.isPresent()) {
            throw new ValidationException("Cet utilisaeur existe déjà");
        }
        Member member = new Member();
        member.setLogin(login);
        member.setPassword(password);
        member.setAdministrator(false);
        member.setMail(mail);
        member.setUsername(username);
        memberDao.save(member);
        connectedUser.setConnectedUser(member);

        return "home.xhtml";
    }

}
