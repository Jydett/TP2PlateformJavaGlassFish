package fr.polytech.bean;

import fr.polytech.dao.Page;
import fr.polytech.dao.message.MessageDao;
import fr.polytech.model.Message;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named("messages")
@ViewScoped
public class MessagesBeans implements Serializable {

    @Getter
    private Page<Message> currentPage;

    @Inject
    private MessageDao messageDao;

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

        reload();
    }

    public void downvote(Long id) {

        reload();
    }

    public void reload() {
        changeCurrentPage(currentPage.getPageNumber());
    }
}
