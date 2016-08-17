package bd.dao;

import bd.hibernate.HibernateUtil;
import bd.table.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 *  Реализация запросов к БД по User
 */
public class UserDaoImpl implements UserDao
{
    @Override
    public void addUser(User user) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            if ((session != null) && (session.isOpen())) session.close();
        }
    }

    @Override
    public void deleteUser(User user) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            if ((session != null) && (session.isOpen())) session.close();
        }
    }

    @Override
    public User getUser(int id) throws SQLException {
        User result = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            result = session.get(User.class, id);
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            if ((session != null) && (session.isOpen())) session.close();
        }
        return result;
    }

    @Override
    public void updateUser(User user) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            if ((session != null) && (session.isOpen())) session.close();
        }

    }

    /**
     *  Поиск по параметру
     * @param param список параметров
     * @return Возвращает список клиентов
     * @throws SQLException
     */
    @Override
    public List<User> query(String param) throws SQLException {
        List<User> resultList = new LinkedList<User>();
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery(param);
            resultList =  query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if ((session != null) && (session.isOpen())) session.close();
        }
        return resultList;
    }

}
