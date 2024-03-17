package com.mysite.service;

import com.mysite.Model.LegalClient;
import com.mysite.Model.RealClient;
import com.mysite.Model.contact.Address;
import com.mysite.Model.contact.Contact;
import com.mysite.Model.contact.PhoneNumber;
import org.h2.tools.Server;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.sql.SQLException;

public class DatabaseManagement {

    private static final String JDBC_URL = "jdbc:h2:mem:BankSystemDB";
    private static final String USERNAME = "amir";
    private static final String PASSWORD = "pass";
    private static final String PORT = "8082";
    private SessionFactory sessionFactory;
    private static final DatabaseManagement INSTANCE;

    private DatabaseManagement() {
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                .configure()
                .applySetting(Environment.DRIVER, "org.h2.Driver")
                .applySetting(Environment.URL, JDBC_URL)
                .applySetting(Environment.USER, USERNAME)
                .applySetting(Environment.PASS, PASSWORD)
                .applySetting(Environment.DIALECT, "org.hibernate.dialect.H2Dialect")
                .applySetting(Environment.SHOW_SQL, "true")
                .applySetting("hibernate.hbm2ddl.auto", "update")
                .build();

        try {
            Server.createTcpServer("-tcpAllowOthers").start();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            Server.createWebServer("-webPort", PORT, "-web", "-webAllowOthers").start();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        MetadataSources sources = new MetadataSources(standardServiceRegistry);

        sources.addAnnotatedClass(LegalClient.class);
        sources.addAnnotatedClass(RealClient.class);
        sources.addAnnotatedClass(Contact.class);
        sources.addAnnotatedClass(PhoneNumber.class);
        sources.addAnnotatedClass(Address.class);
        sources.addAnnotatedClass(Readable.class);

        Metadata metadata = sources.getMetadataBuilder().build();
        sessionFactory = metadata.getSessionFactoryBuilder().build();

    }

    static {
        INSTANCE = new DatabaseManagement();
    }

    public static DatabaseManagement getInstance() {
        return INSTANCE;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

}
