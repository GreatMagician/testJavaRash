<%@ page import="bd.table.Job" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Список дел</title>
    </head>
    <body>
         <h1>Список дел</h1>
         <form action="StartServlet" method="POST">
             <input type="submit" name="load" value="Загрузить данные из БД">
         </form>
         <form action="StartServlet" method="POST">
             <input type="submit" name="filter1" value="Фильтр все">
             <input type="submit" name="filter2" value="Фильтр невыполненные">
             <input type="submit" name="filter3" value="Фильтр выполненные">
         </form>
         <%
             // получить список дел из БД

             List<Job> jobs =(List) request.getAttribute("jobs");
         %>
         <form action="StartServlet"  method="post">
             <table border="1">
                 <tr>
                     <td><h3>id</h3></td>
                     <td><h3>Название дела</h3></td>
                     <td><h3>Срок выполнения</h3></td>
                     <td><h3>Статус выполнения</h3></td>
                     <td><h3>Удалить</h3></td>
                 </tr>
                     <%
                         request.setCharacterEncoding("UTF-8");
                         if (jobs == null || jobs.size() == 0)
                         {
                     %>
                             <tr>
                                 <td><h4>1</h4></td>
                                 <td></td>
                                 <td></td>
                                 <td></td>
                                 <td></td>
                             </tr>
                    <%   }else{
                            for (Job job: jobs){
                    %>
                             <tr>
                                 <td ><h4><%=job.getId()%></h4></td>
                                 <td><input type="text" size="40" name="<%="name" + job.getId() %>" value="<%=job.getName()%>"></td>
                                 <td><input type="text" size="20" name="<%="time" + job.getId() %>" value="<%=job.getTime()%>"></td>
                                 <% if(job.isMade()){ %>
                                        <td><h4><input type="checkbox" size="5" name="<%="checkbox" + job.getId()  %>" checked></h4></td>
                                 <% }else{ %>
                                        <td><h4><input type="checkbox" size="5" name="<%="checkbox" + job.getId()  %>" ></h4></td>
                                 <% } %>
                                 <td><input type="submit" name="<%="delete" + job.getId() %>" value="Удалить"></td>
                             </tr>
                    <%
                            }
                         }
                    %>
             </table>
             <input type="submit" name="save" value="Сохранить изменения">
         </form>

         <h3>Добавить дело</h3>
         <form action="StartServlet"  method="post">
             <table border="1">
                 <tr>
                     <td><h3>Название дела</h3></td>
                     <td><h3>Срок выполнения</h3></td>
                     <td><h3>Добавить</h3></td>
                 </tr>
                 <tr>
                     <td><input type="text" name="addName" size="40"></td>
                     <td><input type="text" name="addTime" size="20"></td>
                     <td><input type="submit" name="addJob" value="Добавить"></td>
                 </tr>
             </table>
         </form>
    </body>
</html>