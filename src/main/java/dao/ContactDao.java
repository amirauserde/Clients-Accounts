package dao;

import com.mysite.Model.contact.Contact;

public interface ContactDao {
    void save(Contact contact);
    void update(Contact contact);
    Contact getById(Integer id);
}
