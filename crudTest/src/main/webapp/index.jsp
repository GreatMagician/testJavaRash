<%@ page import="java.util.List" %>
<%@ page import="bd.table.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Список пользователей</title>
    </head>
    <body>
         <h1>Список пользователей</h1>
         <form action="StartServlet" method="POST">
             <input type="submit" name="load" value="Загрузить пользователей">
         </form>
         <form action="StartServlet" method="POST">
             Введите имя: <input type="text" size="40" name="searchName">
             <input type="submit" name="search" value="Поиск">
         </form>
         <%
             // получить список пользователей из БД

             List<User> users =(List) request.getAttribute("users");
             boolean errorAge = false;
             if( request.getAttribute("errorAge") != null){
                 errorAge = (boolean) request.getAttribute("errorAge");
             }
             if (errorAge){
         %>
         <div style="color: red" >
             Ошибка заполнения полей. Имя не должно быть пустым, возраст > 0
         </div>
         <%
             }
         %>

         <form action="StartServlet"  method="post">
             <table border="1">
                 <tr>
                     <td><h3>id</h3></td>
                     <td><h3>Имя</h3></td>
                     <td><h3>Возраст</h3></td>
                     <td><h3>Администратор</h3></td>
                     <td><h3>Дата последнего обновления</h3></td>
                     <td><h3>Удалить</h3></td>
                 </tr>
                     <%
                         request.setCharacterEncoding("UTF-8");
                         if (users == null || users.size() == 0)
                         {
                     %>
                             <tr>
                                 <td><h4>1</h4></td>
                                 <td></td>
                                 <td></td>
                                 <td></td>
                                 <td></td>
                                 <td></td>
                             </tr>
                    <%   }else{
                            for (User user: users){
                    %>
                             <tr>
                                 <td ><h4><%=user.getId()%></h4></td>
                                 <td><input type="text" size="40" name="<%="name" + user.getId() %>" value="<%=user.getName()%>"></td>
                                 <td><input type="text" size="10" name="<%="age" + user.getId() %>" value="<%=user.getAge()%>"></td>
                                 <% if(user.isAdmin()){ %>
                                        <td><h4><input type="checkbox" size="10" name="<%="checkbox" + user.getId()  %>" checked></h4></td>
                                 <% }else{ %>
                                        <td><h4><input type="checkbox" size="10" name="<%="checkbox" + user.getId()  %>" ></h4></td>
                                 <% } %>
                                 <td><%=user.getDate()%></td>
                                 <td><input type="submit" name="<%="delete" + user.getId() %>" value="Удалить"></td>
                             </tr>
                    <%
                            }
                         }
                    %>
             </table>
             <input type="submit" name="save" value="Сохранить изменения">
         </form>

         <h3>Добавить пользователя</h3>
         <%
             boolean errorAddUser = false;
             if( request.getAttribute("errorAddUser") != null){
                 errorAddUser = (boolean) request.getAttribute("errorAddUser");
             }
             if (errorAddUser){
         %>
                <div style="color: red" >
                    Ошибка заполнения полей. Имя не должно быть пустым, возраст > 0
                </div>
         <%
             }
         %>
         <form action="StartServlet"  method="post">
             <table border="1">
                 <tr>
                     <td><h3>Имя пользователя</h3></td>
                     <td><h3>Возраст</h3></td>
                     <td><h3>Администратор</h3></td>
                     <td><h3>Добавить</h3></td>
                 </tr>
                 <tr>
                     <td><input type="text" name="addName" size="40"></td>
                     <td><input type="text" name="addAge" size="10"></td>
                     <td><input type="checkbox" name="addAdmin" size="10"></td>
                     <td><input type="submit" name="addUser" value="Добавить"></td>
                 </tr>
             </table>
         </form>
    </body>
</html>