package fr.polytech.bean;

import fr.polytech.dao.member.MemberDao;
import fr.polytech.model.Member;
import lombok.Getter;
import lombok.Setter;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ValidationException;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Optional;

@Named
@ViewScoped
public class LoginForm implements Serializable {

    @NotBlank
    @Getter
    @Setter
    private String login;

    @NotBlank
    @Getter
    @Setter
    private String password;

    @Inject
    private MemberDao memberDao;

    @Inject
    private ConnectedUser connectedUser;

    public void connect() {
        Optional<Member> optionalMember = memberDao.authentificate(login, password);
        if (! optionalMember.isPresent()) {
            password = login = "";
            throw new ValidationException("Authentification échouée");
        }
        connectedUser.setConnectedUser(optionalMember.get());
    }
}
