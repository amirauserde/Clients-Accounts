package dao;

import com.mysite.Model.contact.PhoneNumber;

public interface PhoneNumberDao {
    void save(PhoneNumber phoneNumber);
    void update(PhoneNumber phoneNumber);
    PhoneNumber getById(Integer id);
}
