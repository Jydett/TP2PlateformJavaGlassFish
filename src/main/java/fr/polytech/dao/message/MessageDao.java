package fr.polytech.dao.message;

import fr.polytech.dao.Page;
import fr.polytech.model.Message;

public interface MessageDao {
    Page<Message> getMessagePage(int pageNumber);
}
