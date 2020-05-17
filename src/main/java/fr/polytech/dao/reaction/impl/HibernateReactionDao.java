package fr.polytech.dao.reaction.impl;

import fr.polytech.dao.HibernateDao;
import fr.polytech.dao.reaction.ReactionDao;
import fr.polytech.model.Message;
import fr.polytech.model.Reaction;

import javax.ejb.Stateless;
import javax.inject.Named;

@Named
@Stateless
public class HibernateReactionDao extends HibernateDao<Long, Reaction> implements ReactionDao {
    public HibernateReactionDao() {
        super(Reaction.class);
    }

    @Override //TODO useless ?
    public Integer getReactionValueForMessage(Message message) {
        return null;
    }
}
