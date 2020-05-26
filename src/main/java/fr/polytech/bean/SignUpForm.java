package fr.polytech.bean;

import fr.polytech.dao.member.MemberDao;
import fr.polytech.constants.Constants;
import fr.polytech.model.Member;
import lombok.Getter;
import lombok.Setter;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Optional;

@Named
@ViewScoped
public class SignUpForm implements Serializable {

    @NotBlank(message = Constants.Errors.LOGIN_IS_EMPTY)
    @Getter
    @Setter
    private String login;

    @NotBlank(message = Constants.Errors.PASSWORD_IS_EMPTY)
    @Getter
    @Setter
    private String password;

    @NotBlank(message = Constants.Errors.USERNAME_IS_EMPTY)
    @Getter
    @Setter
    private String username;

    @Email(message = Constants.Errors.MAIL_NOT_VALID)
    @Getter
    @Setter
    private String mail;

    @Inject
    private MemberDao memberDao;

    @Inject
    private ConnectedUser connectedUser;

    public String createUser() {
        Optional<Member> optionalMember = memberDao.findUserByLogin(login);
        boolean error = false;
        if (optionalMember.isPresent()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(Constants.Errors.LOGIN_ALREADY_USED));
            error = true;
        }
        optionalMember = memberDao.findUserByMail(login);
        if (optionalMember.isPresent()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(Constants.Errors.EMAIL_ALREADY_USED));
            error = true;
        }
        if (error) {
            return null;
        }
        Member member = new Member();
        member.setLogin(login);
        member.setPassword(password);
        member.setAdministrator(false);
        member.setMail(mail);
        member.setUsername(username);
        memberDao.save(member);
        connectedUser.setConnectedUser(member);
        return Constants.Routing.HOME_PAGE;
    }
}