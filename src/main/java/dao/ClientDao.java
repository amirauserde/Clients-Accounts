package dao;

import com.mysite.Model.Client;

public interface ClientDao {
    void save(Client client);
    void update(Client client);
    Client getById(Integer id);
}
