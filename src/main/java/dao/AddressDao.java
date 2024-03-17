package dao;

import com.mysite.Model.contact.Address;

public interface AddressDao {
    void save(Address address);
    void update(Address address);
    Address getById(Integer id);
}
