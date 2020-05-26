package fr.polytech.bean;

import fr.polytech.constants.Constants;
import fr.polytech.dao.Page;
import fr.polytech.dao.message.MessageDao;
import fr.polytech.dao.reaction.ReactionDao;
import fr.polytech.model.Message;
import fr.polytech.model.Reaction;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ValidationException;
import java.io.Serializable;
import java.util.Optional;

@Named("messages")
@ViewScoped
public class MessagesBeans implements Serializable {

    public static final int DEFAULT_PAGE = 1;

    @Getter
    private Page<Message> currentPage;

    @Inject
    private MessageDao messageDao;

    @Inject
    private ReactionDao reactionDao;

    @Inject
    private ConnectedUser connectedUser;

    @PostConstruct
    private void load() {
        changeCurrentPage(DEFAULT_PAGE);
    }

    public void changeCurrentPage(int pageNumber) {
        if (pageNumber <= 0) {
            throw new IllegalArgumentException(Constants.Errors.NEGATIVE_PAGE_NUMBER);
        }
        currentPage = messageDao.getMessagePage(pageNumber);
    }

    public void delete(Long id) {
        messageDao.delete(id);
        reload();
    }

    public void upvote(Long id) {
        react(id, true);
    }

    public void downvote(Long id) {
        react(id, false);
    }

    private void react(Long id, boolean value) {
        if (! connectedUser.isConnected()) {
            throw new IllegalArgumentException(Constants.Errors.MUST_BE_CONNECTED_TO_REACT);
        }
        Message message = messageDao.getById(id).orElseThrow(() -> new ValidationException(Constants.Errors.MESSAGE_DOESNT_EXIST));

        Optional<Reaction> anyReaction = message
            .getReactions().stream()
            .filter(r -> r.getUser().getId().equals(connectedUser.getMember().getId()))
            .findAny();
        Reaction reaction = anyReaction.orElseGet(() -> new Reaction(message, connectedUser.getMember(), null));
        if (reaction.getValue() == null) {
            message.setReputation(message.getReputation() + (value ? 1 : -1));
            message.addReaction(reaction);
            reaction.setValue(value);
            reactionDao.save(reaction);
        } else {
            if (reaction.getValue() != value) {
                message.setReputation(message.getReputation() + (value ? 2 : -2));
                reaction.setValue(value);
                reactionDao.save(reaction);
            } else if (reaction.getValue() == value) {
                message.setReputation(message.getReputation() + (value ? -1 : 1));
                message.removeReaction(reaction);
                reactionDao.remove(reaction);
            }
        }
        messageDao.save(message);
        reload();
    }

    public void reload() {
        changeCurrentPage(currentPage.getPageNumber());
    }

    public boolean isUpvoted(Message message) {
        if (! connectedUser.isConnected()) {
            return false;
        }
        return message.getReactions()
            .stream()
            .filter(Reaction::getValue)
            .map(r -> r.getUser().getId())
            .anyMatch(r -> r.equals(connectedUser.getMember().getId()));
    }

    public boolean isDownVoted(Message message) {
        if (! connectedUser.isConnected()) {
            return false;
        }
        return message.getReactions()
            .stream()
            .filter(r -> !r.getValue())
            .map(r -> r.getUser().getId())
            .anyMatch(r -> r.equals(connectedUser.getMember().getId()));
    }
}
