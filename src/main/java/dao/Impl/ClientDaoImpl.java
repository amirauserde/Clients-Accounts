package dao.Impl;

import com.mysite.Model.Client;
import com.mysite.service.DatabaseManagement;
import dao.ClientDao;
import org.hibernate.Session;

public class ClientDaoImpl implements ClientDao {

    private static final ClientDaoImpl INSTANCE;
    private DatabaseManagement databaseManagement;

    private ClientDaoImpl() {
        databaseManagement = DatabaseManagement.getInstance();
    }

    public static ClientDaoImpl getInstance() {
        return INSTANCE;
    }

    static {
        INSTANCE = new ClientDaoImpl();
    }
    @Override
    public void save(Client client) {
        try(Session session = databaseManagement.getSession()) {
            session.beginTransaction();
            session.save(client);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Client client) {
        try(Session session = databaseManagement.getSession()) {
            session.beginTransaction();
            session.update(client);
            session.getTransaction().commit();
        }
    }

    @Override
    public Client getById(Integer id) {
        try(Session session = databaseManagement.getSession()) {
            return session.get(Client.class, id);
        }

    }

}
