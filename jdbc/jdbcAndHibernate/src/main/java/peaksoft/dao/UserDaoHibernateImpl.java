package peaksoft.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import peaksoft.model.User;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

//    public UserDaoHibernateImpl() {
//
//    }

    public static org.hibernate.SessionFactory buildSessionFactory() {
        try{
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        }
        catch (Throwable e) {
            System.out.println("Session doesnt created...");
            throw new ExceptionInInitializerError(e);
        }
    }
    private static final SessionFactory sessionFactory = buildSessionFactory();

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutDown() {
        getSessionFactory().close();
    }

    @Override
    public void createUsersTable() {
        Session session = UserDaoHibernateImpl.getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("create table if not exists users ( " +
                " id serial not null ," +
                " name  varchar(50) not null, " +
                " lastName varchar(50) not null ," +
                " age int  not null )").executeUpdate();
        session.getTransaction().commit();
        session.close();
        }

    @Override
    public void dropUsersTable() {
        Session session = UserDaoHibernateImpl.getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("drop table if exists users").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = UserDaoHibernateImpl.getSessionFactory().openSession();
        session.beginTransaction();
        User user = new User(name, lastName, age);
        session.save(user);
        session.getTransaction().commit();
        session.close();
        System.out.println("User " + user + " successfully added...");
    }

    @Override
    public void removeUserById(long id) {
        Session session = UserDaoHibernateImpl.getSessionFactory().openSession();
        session.beginTransaction();
        User user = (User) session.get(User.class, id);
        session.delete(user);
        session.getTransaction().commit();
        session.close();
        System.out.println("User " + user + " successfully deleted...");
    }

    @Override
    public List<User> getAllUsers() {
        Session session = UserDaoHibernateImpl.getSessionFactory().openSession();
        session.beginTransaction();
//        List<User> userList = session.createQuery("from User").getResultList();
        session.getTransaction().commit();
        session.close();
//        return userList;
        return getAllUsers();
    }

    @Override
    public void cleanUsersTable() {
        Session session = UserDaoHibernateImpl.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("delete User");
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        System.out.println("Deleted successfully...");

    }
}
