package fr.polytech.bean;

import fr.polytech.model.Member;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class ConnectedUser implements Serializable {

    private Member user;

    public boolean isConnected() {
        return user != null;
    }

    public boolean isAdminOrId(Long memberId) {
        System.out.println("isAdminOrId(" + memberId + ")");
        return isConnected() && (user.getAdministrator() || user.getId().equals(memberId));
    }

    public Member getMember() {
        return user;
    }

    public void setConnectedUser(Member u) {
        user = u;
    }

    public void disconnect() {
        user = null;
    }
}
