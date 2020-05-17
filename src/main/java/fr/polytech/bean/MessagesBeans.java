package fr.polytech.bean;

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
        changeCurrentPage(1);
    }

    public void changeCurrentPage(int pageNumber) {
        if (pageNumber < 0) {
            throw new IllegalArgumentException("page number should be > 0");
        }
        currentPage = messageDao.getMessagePage(pageNumber);
    }

    public void delete(Long id) {
        messageDao.delete(id);
    }

    public void upvote(Long id) {
        react(id, true);
    }

    public void downvote(Long id) {
        react(id, false);
    }

    private void react(Long id, boolean value) {
        if (! connectedUser.isConnected()) {
            throw new IllegalArgumentException("You must be connected to upvote");
        }
        Message message = messageDao.getById(id).orElseThrow(() -> new ValidationException("This id doesn't exist"));

        Optional<Reaction> anyReaction = message.getReactions().stream().filter(r -> r.getUser().getId().equals(connectedUser.getMember().getId())).findAny();
        Reaction reaction = anyReaction.orElseGet(() -> new Reaction(message, connectedUser.getMember(), null));
        if (reaction.getValue() == null) {
            message.setReputation(message.getReputation() + (value ? 1 : -1));
            message.addReaction(reaction);
            reaction.setValue(value);
            reactionDao.save(reaction);
        } else {
            if (reaction.getValue().booleanValue() != value) {
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
