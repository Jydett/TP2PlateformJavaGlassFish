package fr.polytech.dao.reaction;

import fr.polytech.model.Reaction;

public interface ReactionDao {
    void save(Reaction reaction);
    void remove(Reaction reaction);
}
