package servlets;
import bd.dao.UserDao;
import bd.dao.UserDaoImpl;
import bd.table.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Стартовый сервлет
 */
@WebServlet("/StartServlet/*")
public class StartServlet extends DispetcherServlet {

    List<User> users = null; // список дел
    ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // установить кодировку
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String name; // имя кнопки

           if(request.getParameter("search") != null){ // поиск по имени
                for (User user: new LinkedList<User>(users)){
                    if (!request.getParameter("searchName").equalsIgnoreCase(user.getName()))
                        users.remove(user);
                }
                request.setAttribute("users", users);
                super.forward("/index.jsp", request, response);

            }else if(request.getParameter("load") != null){ // загрузить данные из БД
                showAllUsers(request);
                super.forward("/index.jsp", request, response);

            }else if(request.getParameter("save") != null){ // сохранить данные в БД
                save(request);
                showAllUsers(request);
                super.forward("/index.jsp", request, response);

            }else if(request.getParameter("addUser") != null){ // добавить данные в БД
                addUserToBD(request);
                showAllUsers(request);
                super.forward("/index.jsp", request, response);

            }else if(!(name = isDelete(request)).equals("") ) { // кнопка удалить
                if (users != null){
                    int id = Integer.parseInt(name.substring(6));
                    for (User user: users){
                        if (user.getId() == id){
                            UserDao caseDao = (UserDao) context.getBean("userDao");
                            try {
                                caseDao.deleteUser(user);
                                showAllUsers(request);
                                super.forward("/index.jsp", request, response);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
     }

    /**
     *  Загружает все данные о делах для отображения на странице
     * @param request запрос
     */
     private void showAllUsers(HttpServletRequest request){
         UserDao userDao = (UserDao) context.getBean("userDao");
         try {
             users = userDao.query("FROM User");
             request.setAttribute("users", users);
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }

    /**
     * проверка на нажатие кнопок удалить
     * @param request запрос
     * @return возвращает имя кнопки (удалить), если была нажата, иначе ""
     */
     private String isDelete(HttpServletRequest request){
         String result = "";
         Enumeration enumeration = request.getParameterNames();
         Object obj;
         while (enumeration.hasMoreElements()){
             obj = enumeration.nextElement();
             String name =(String) obj;
            if (name.contains("delete")){
              if(request.getParameter(name) != null)
                return name;
            }
         }
         return result;
     }


    /**
     *  сохранить изменения
     * @param request запрос
     */
    private void save(HttpServletRequest request){
        Enumeration enumeration = request.getParameterNames();
        Object obj;
        Map<Integer, User> saveUserMap = new LinkedHashMap<Integer, User>();
        List<Integer> checkBoxID = new ArrayList<Integer>(); // добовляем чекбоксы true
        while (enumeration.hasMoreElements()){
            obj = enumeration.nextElement();
            String name =(String) obj;
            String value = request.getParameter(name);
            if (name.contains("name")){
                if (value.equals("")){
                    request.setAttribute("errorAge", true);
                    return;
                }
                int id = Integer.parseInt(name.substring(4));
                User user;
                if (saveUserMap.containsKey(id))
                    user = saveUserMap.get(id);
                else
                    user = getUserToID(id);
                if (user != null) {
                    if (!user.getName().equals(value)) {
                        user.setName(value);
                        if (!saveUserMap.containsKey(id))
                            saveUserMap.put(id, user);
                        if (users.contains(user))
                            users.remove(user);
                    }
                }
            }else if (name.contains("age")){
                int id = Integer.parseInt(name.substring(3));
                User user;
                if (saveUserMap.containsKey(id))
                    user = saveUserMap.get(id);
                else
                    user = getUserToID(id);
                if (user != null) {
                    int age = -1;
                    boolean errorAge = false;
                    try {
                        age = Integer.parseInt(value);
                    } catch (NumberFormatException e) {
                        errorAge = true;
                        request.setAttribute("errorAge", errorAge);
                        return;
                    }
                    if (!(user.getAge() == age)) {
                        user.setAge(age);
                        if (!saveUserMap.containsKey(id))
                            saveUserMap.put(id, user);
                        if (users.contains(user))
                            users.remove(user);
                    }
                }
            }else if (name.contains("checkbox")) {
                int id = Integer.parseInt(name.substring(8));
                User user;
                if (saveUserMap.containsKey(id))
                    user = saveUserMap.get(id);
                else
                    user = getUserToID(id);
                if (user != null) {
                    user.setAdmin(true);
                    checkBoxID.add(id);
                    if (!saveUserMap.containsKey(id))
                        saveUserMap.put(id, user);
                    if (users.contains(user))
                        users.remove(user);
                }
            }
        }
        // устанавливаем остальные чекбоксы false
        for (Map.Entry<Integer, User> pair: saveUserMap.entrySet() ){
            if (!checkBoxID.contains(pair.getKey())){
                pair.getValue().setAdmin(false);
            }
        }
        for(User user: new LinkedList<User>(users)){
            if (!checkBoxID.contains(user.getId())){
                if(user.isAdmin()){
                    user.setAdmin(false);
                    if (!saveUserMap.containsKey(user.getId())) {
                        saveUserMap.put(user.getId(), user);
                        users.remove(user);
                    }
                }
            }
        }

        // save
        UserDao userDao = (UserDao) context.getBean("userDao");
        for (Map.Entry<Integer, User> pair: saveUserMap.entrySet() ){
            try {
                userDao.updateUser(pair.getValue());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     *  Возвращает user из списка users  по id
     * @param id
     * @return возвращает user если есть id иначе null
     */
    private User getUserToID(int id){
        for (User user: users){
            if (user.getId() == id)
                return user;
        }
        return null;
    }

    /**
     *  Добавить данные в БД
     * @param request
     */
    private void addUserToBD(HttpServletRequest request){
        String name = request.getParameter("addName");
        boolean err = false; // ошибка заполнения данных
        int age = -1;
        try {
            age = Integer.parseInt(request.getParameter("addAge"));
        } catch (NumberFormatException e) {
            err = true;
        }
        if (name == null || age <= 0 || name.equals("")){
            err = true;
        }
        request.setAttribute("errorAddUser", err);
        if (err) return; // ошибка заполнения данных

        boolean isAdmin = false;
        if (request.getParameter("addAdmin") != null) isAdmin = true;
        User user = new User();
        user.setName(name);
        user.setAge(age);
        user.setAdmin(isAdmin);
        user.setDate(new Date());
        UserDao caseDao = (UserDao) context.getBean("userDao");
        try {
            caseDao.addUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
