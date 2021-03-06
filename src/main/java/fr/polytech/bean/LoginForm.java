package fr.polytech.bean;

import fr.polytech.constants.Constants;
import fr.polytech.dao.member.MemberDao;
import fr.polytech.model.Member;
import lombok.Getter;
import lombok.Setter;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Optional;

@Named
@ViewScoped
public class LoginForm implements Serializable {

    @NotBlank(message = Constants.Errors.LOGIN_IS_EMPTY)
    @Getter
    @Setter
    private String login;

    @NotBlank(message = Constants.Errors.PASSWORD_IS_EMPTY)
    @Getter
    @Setter
    private String password;

    @Inject
    private MemberDao memberDao;

    @Inject
    private ConnectedUser connectedUser;

    public String connect() {
        Optional<Member> optionalMember = memberDao.authentificate(login, password);
        if (! optionalMember.isPresent()) {
            password = login = "";
            FacesContext.getCurrentInstance().addMessage("loginForm", new FacesMessage(Constants.Errors.AUTHENTICATION_FAILED));
            return null;
        }
        connectedUser.setConnectedUser(optionalMember.get());

        return Constants.Routing.HOME_PAGE;
    }
}
