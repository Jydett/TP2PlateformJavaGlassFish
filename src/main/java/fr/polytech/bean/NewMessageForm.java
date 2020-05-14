package fr.polytech.bean;

import fr.polytech.dao.message.MessageDao;
import fr.polytech.model.Message;
import lombok.Getter;
import lombok.Setter;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ValidationException;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Named("newMessage")
@ViewScoped
public class NewMessageForm implements Serializable {

    @Getter
    @Setter
    @NotBlank(message = "Votre commentaire ne peux pas être vide")
    private String message;

    @Inject
    private MessageDao messageDao;

    @Inject
    private ConnectedUser connectedUser;

    @Inject
    private MessagesBeans messagesBeans;

    public void post() {
        if (! connectedUser.isConnected()) {
            throw new ValidationException("Vous devez etre connecté pour poster un message");
        }
        messageDao.save(new Message(message, connectedUser.getMember(), LocalDateTime.now(), 0));
        messagesBeans.reload();
        message = "";
    }
}
