package bd.dao;

import bd.hibernate.HibernateUtil;
import bd.table.Job;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *  Реализация запросов к БД по User
 */
public class CaseDaoImpl implements CaseDao
{
    @Override
    public void addCase(Job job) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(job);
            session.getTransaction().commit();
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            if ((session != null) && (session.isOpen())) session.close();
        }
    }
    @Override
    public void deleteCase(Job job) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(job);
            session.getTransaction().commit();
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            if ((session != null) && (session.isOpen())) session.close();
        }
    }
    @Override
    public Job getCase(int id) throws SQLException {
        Job result = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            result = session.get(Job.class, id);
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            if ((session != null) && (session.isOpen())) session.close();
        }
        return result;
    }
    @Override
    public void updateCase(Job job) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(job);
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
     * @return Возвращает список дел
     * @throws SQLException
     */
    @Override
    public List<Job> queryJob(String param) throws SQLException {
        List<Job> resultList = new LinkedList<Job>();
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

    @Override
    public Query query(String param) throws SQLException {
        Session session = null;
        Query result = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            result = session.createQuery(param);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if ((session != null) && (session.isOpen())) session.close();
        }
        return result;
    }

}
