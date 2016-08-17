package bd.dao;


import bd.table.Job;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

/**
 * Работа с информацией о клиенте
 */
public interface CaseDao {
     /**
      *  добавить дело
      * @param job дело
      * @throws SQLException
      */
     void addCase(Job job) throws SQLException;

     /**
      *  удалить дело
      * @param job дело
      * @throws SQLException
      */
     void deleteCase(Job job) throws SQLException;

     /**
      * Получить дело по id
      * @param id номер дела
      * @return Возвращает дело
      * @throws SQLException
      */
     Job getCase(int id) throws SQLException;

     /**
      *  Обновление информации о деле
      * @param job  клиент
      * @throws SQLException
      */
     void updateCase(Job job) throws SQLException;

     /**
      *  Создать запрос в БД по job
      * @param param запрос
      * @return Возвращает список дел
      * @throws SQLException
      */
     List<Job> queryJob(String param) throws SQLException;

     /**
      *  Общий запрос в БД
      * @param param запрос
      * @return
      * @throws SQLException
      */
     Query query(String param) throws SQLException;
}
