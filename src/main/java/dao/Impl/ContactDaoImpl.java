package dao.Impl;

import com.mysite.Model.contact.Contact;
import com.mysite.service.DatabaseManagement;
import dao.ContactDao;
import org.hibernate.Session;

public class ContactDaoImpl implements ContactDao {
    private static final ContactDaoImpl INSTANCE;
    private DatabaseManagement databaseManagement;

    private ContactDaoImpl() {
        databaseManagement = DatabaseManagement.getInstance();
    }

    public static ContactDaoImpl getInstance() {
        return INSTANCE;
    }

    static {
        INSTANCE = new ContactDaoImpl();
    }


    @Override
    public void save(Contact contact) {
        try(Session session = databaseManagement.getSession()) {
            session.beginTransaction();
            session.save(contact);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Contact contact) {
        try(Session session = databaseManagement.getSession()) {
            session.beginTransaction();
            session.update(contact);
            session.getTransaction().commit();
        }
    }

    @Override
    public Contact getById(Integer id) {
        try(Session session = databaseManagement.getSession()) {
            return session.get(Contact.class, id);
        }
    }
}
