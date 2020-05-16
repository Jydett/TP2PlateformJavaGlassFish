package fr.polytech.dao.message.impl;

import fr.polytech.dao.HibernateDao;
import fr.polytech.dao.Page;
import fr.polytech.dao.message.MessageDao;
import fr.polytech.model.Message;

import javax.ejb.Stateless;
import javax.inject.Named;
import java.util.Optional;

@Named
@Stateless
public class HibernateMessageDao extends HibernateDao<Long, Message> implements MessageDao {

    private static final int PAGE_SIZE = 6;

    public HibernateMessageDao() {
        super(Message.class);
    }

    @Override
    public Page<Message> getMessagePage(int pageNumber) {
        return super.getPage("from Message m order by m.reputation desc", PAGE_SIZE, pageNumber);
    }

    @Override
    public boolean delete(long id) {
        final Optional<Message> message = getById(id);
        if (! message.isPresent()) {
            return false;
        }
        super.remove(message.get());
        return true;
    }
}
