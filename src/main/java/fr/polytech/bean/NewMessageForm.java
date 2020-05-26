package fr.polytech.bean;

import fr.polytech.constants.Constants;
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
    @NotBlank(message = Constants.Errors.COMMENT_IS_EMPTY)
    private String message;

    @Inject
    private MessageDao messageDao;

    @Inject
    private ConnectedUser connectedUser;

    @Inject
    private MessagesBeans messagesBeans;

    public void post() {
        if (! connectedUser.isConnected()) {
            throw new ValidationException(Constants.Errors.MUST_BE_CONNECTED_TO_POST);
        }
        messageDao.save(new Message(message, connectedUser.getMember(), LocalDateTime.now()));
        messagesBeans.reload();
        message = "";
    }
}
