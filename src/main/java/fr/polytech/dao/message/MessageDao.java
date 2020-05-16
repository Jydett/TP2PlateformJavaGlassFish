package fr.polytech.dao.message;

import fr.polytech.dao.Page;
import fr.polytech.model.Message;

import java.util.Optional;

public interface MessageDao {
    Page<Message> getMessagePage(int pageNumber);
    void save(Message message);
    boolean delete(long id);
    Optional<Message> getById(Long id);
}
