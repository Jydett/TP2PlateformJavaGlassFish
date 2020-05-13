package fr.polytech.dao.message.impl;

import fr.polytech.dao.HibernateDao;
import fr.polytech.dao.Page;
import fr.polytech.dao.message.MessageDao;
import fr.polytech.model.Message;

import javax.ejb.Stateless;
import javax.inject.Named;

@Named
@Stateless
public class HibernateMessageDao extends HibernateDao<Message> implements MessageDao {

    private static final int PAGE_SIZE = 10;

    public HibernateMessageDao() {
        super(Message.class);
    }

    @Override
    public Page<Message> getMessagePage(int pageNumber) {
        return super.getPage("from Message m order by m.reputation desc", PAGE_SIZE, pageNumber);
    }
}
