package fr.polytech.dao.reaction;

import fr.polytech.model.Message;

public interface ReactionDao {
    Integer getReactionValueForMessage(Message message);
}
