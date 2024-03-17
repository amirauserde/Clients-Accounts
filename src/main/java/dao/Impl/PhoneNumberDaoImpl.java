package dao.Impl;

import com.mysite.Model.contact.PhoneNumber;
import com.mysite.service.DatabaseManagement;
import dao.PhoneNumberDao;
import org.hibernate.Session;

public class PhoneNumberDaoImpl implements PhoneNumberDao {
    private static final PhoneNumberDaoImpl INSTANCE;
    private DatabaseManagement databaseManagement;

    private PhoneNumberDaoImpl() {
        databaseManagement = DatabaseManagement.getInstance();
    }

    public static PhoneNumberDaoImpl getInstance() {
        return INSTANCE;
    }

    static {
        INSTANCE = new PhoneNumberDaoImpl();
    }

    @Override
    public void save(PhoneNumber phoneNumber) {
        try(Session session = databaseManagement.getSession()) {
            session.beginTransaction();
            session.save(phoneNumber);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(PhoneNumber phoneNumber) {
        try(Session session = databaseManagement.getSession()) {
            session.beginTransaction();
            session.update(phoneNumber);
            session.getTransaction().commit();
        }
    }

    @Override
    public PhoneNumber getById(Integer id) {
        try(Session session = databaseManagement.getSession()) {
            return session.get(PhoneNumber.class, id);
        }
    }
}
