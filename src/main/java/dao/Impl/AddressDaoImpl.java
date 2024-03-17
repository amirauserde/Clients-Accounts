package dao.Impl;

import com.mysite.Model.contact.Address;
import com.mysite.service.DatabaseManagement;
import dao.AddressDao;
import org.hibernate.Session;

public class AddressDaoImpl implements AddressDao {
    private static final AddressDaoImpl INSTANCE;
    private DatabaseManagement databaseManagement;

    private AddressDaoImpl() {
        databaseManagement = DatabaseManagement.getInstance();
    }

    public static AddressDaoImpl getInstance() {
        return INSTANCE;
    }

    static {
        INSTANCE = new AddressDaoImpl();
    }

    @Override
    public void save(Address address) {
        try(Session session = databaseManagement.getSession()) {
            session.beginTransaction();
            session.save(address);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Address address) {
        try(Session session = databaseManagement.getSession()) {
            session.beginTransaction();
            session.update(address);
            session.getTransaction().commit();
        }
    }

    @Override
    public Address getById(Integer id) {
        try(Session session = databaseManagement.getSession()) {
            return session.get(Address.class, id);
        }
    }
}
