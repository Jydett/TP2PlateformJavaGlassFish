package fr.polytech.dao.reaction;

import fr.polytech.model.Message;
import fr.polytech.model.Reaction;

public interface ReactionDao {
    Integer getReactionValueForMessage(Message message);
    void save(Reaction reaction);
    void remove(Reaction reaction);
}
