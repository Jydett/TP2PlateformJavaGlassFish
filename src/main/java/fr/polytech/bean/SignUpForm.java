package fr.polytech.bean;

import fr.polytech.dao.member.MemberDao;
import fr.polytech.model.Member;
import lombok.Getter;
import lombok.Setter;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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

    @NotBlank(message = "Le login ne peut pas être vide")
    @Getter
    @Setter
    private String login;

    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    @Getter
    @Setter
    private String password;

    @NotBlank(message = "Le nom de compte ne peut pas être vide")
    @Getter
    @Setter
    private String username;

    @Email(message = "Ce mail n'est pas valide")
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
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cet login est déjà utilisé"));
            error = true;
        }
        optionalMember = memberDao.findUserByMail(login);
        if (optionalMember.isPresent()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cet email est déjà utilisé"));
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
        return "home.xhtml";
    }

}
