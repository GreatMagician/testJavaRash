package bd.dao;


import bd.table.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Работа с информацией о клиенте
 */
public interface UserDao {
     /**
      *  добавить клиента
      * @param user клиент которого нужно добавить
      * @throws SQLException
      */
     void addUser(User user) throws SQLException;

     /**
      *  удалить клиента
      * @param user клиент которого нужно удалить
      * @throws SQLException
      */
     void deleteUser(User user) throws SQLException;

     /**
      * Получить клиента по id
      * @param id номер клиента
      * @return Возвращает клиента
      * @throws SQLException
      */
     User getUser(int id) throws SQLException;

     /**
      *  Обновление информации о клиенте
      * @param user клиент
      * @throws SQLException
      */
     void updateUser(User user) throws SQLException;

     /**
      *  Создать запрос в БД
      * @param param запрос
      * @return Возвращает список клиентов
      * @throws SQLException
      */
     List<User> query(String  param) throws SQLException;
}
