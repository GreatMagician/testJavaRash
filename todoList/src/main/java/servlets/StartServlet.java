package servlets;
import bd.dao.CaseDao;
import bd.dao.CaseDaoImpl;
import bd.table.Job;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.*;

/**
 * Стартовый сервлет
 */
@WebServlet("/StartServlet/*")
public class StartServlet extends DispetcherServlet {

    List<Job> jobs = null; // список дел

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // установить кодировку
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        // если нажата кнопка
        String name; // имя кнопки

            if(request.getParameter("filter1") != null){ // фильтр все
                showAllJobs(request);
                super.forward("/index.jsp", request, response);

            }else if(request.getParameter("filter2") != null){ // фильтр показать невыполненные
                showAllJobs(request);
                for (Job job: new LinkedList<Job>(jobs)){
                    if (job.isMade()){
                        jobs.remove(job);
                    }
                }
                request.setAttribute("jobs", jobs);
                super.forward("/index.jsp", request, response);

            }else if(request.getParameter("filter3") != null){ // фильтр показать выполненные
                showAllJobs(request);
                for (Job job: new LinkedList<Job>(jobs)){
                    if (!job.isMade()){
                        jobs.remove(job);
                    }
                }
                request.setAttribute("jobs", jobs);
                super.forward("/index.jsp", request, response);

            }else if(request.getParameter("load") != null){ // загрузить данные из БД
                showAllJobs(request);
                super.forward("/index.jsp", request, response);

            }else if(request.getParameter("save") != null){ // сохранить данные в БД
                save(request);
                showAllJobs(request);
                super.forward("/index.jsp", request, response);

            }else if(request.getParameter("addJob") != null){ // добавить данные в БД
                addJobToBD(request);
                showAllJobs(request);
                super.forward("/index.jsp", request, response);

            }else if(!(name = isDelete(request)).equals("") ) { // кнопка удалить
                if (jobs != null){
                    int count = Integer.parseInt(name.substring(6));
                    int num = 0; // счётчик
                    for (Job job: jobs){
                        if (count == num++){
                            CaseDao caseDao = new CaseDaoImpl();
                            try {
                                caseDao.deleteCase(job);
                                showAllJobs(request);
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
     private void showAllJobs(HttpServletRequest request){
         CaseDao caseDao = new CaseDaoImpl();
         try {
             jobs = caseDao.queryJob("FROM Job");
             request.setAttribute("jobs", jobs);
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
        Map<Integer, Job> saveJobMap = new LinkedHashMap<Integer, Job>();
        List<Integer> checkBoxID = new ArrayList<Integer>(); // добовляем чекбоксы true
        while (enumeration.hasMoreElements()){
            obj = enumeration.nextElement();
            String name =(String) obj;
            String value = request.getParameter(name);
            if (name.contains("name")){
                int id = Integer.parseInt(name.substring(4));
                Job job;
                if (saveJobMap.containsKey(id))
                    job = saveJobMap.get(id);
                else
                    job = getJobToID(id);
                if (job != null) {
                    if (!job.getName().equals(value)) {
                        job.setName(value);
                        if (!saveJobMap.containsKey(id))
                            saveJobMap.put(id, job);
                        if (jobs.contains(job))
                            jobs.remove(job);
                    }
                }
            }else if (name.contains("time")){
                int id = Integer.parseInt(name.substring(4));
                Job job;
                if (saveJobMap.containsKey(id))
                    job = saveJobMap.get(id);
                else
                    job = getJobToID(id);
                if (job != null) {
                    if (!job.getTime().equals(value)) {
                        job.setTime(value);
                        if (!saveJobMap.containsKey(id))
                            saveJobMap.put(id, job);
                        if (jobs.contains(job))
                            jobs.remove(job);
                    }
                }
            }else if (name.contains("checkbox")) {
                int id = Integer.parseInt(name.substring(8));
                Job job;
                if (saveJobMap.containsKey(id))
                    job = saveJobMap.get(id);
                else
                    job = getJobToID(id);
                if (job != null) {
                    job.setMade(true);
                    checkBoxID.add(id);
                    if (!saveJobMap.containsKey(id))
                        saveJobMap.put(id, job);
                    if (jobs.contains(job))
                        jobs.remove(job);
                }
            }
        }
        // устанавливаем остальные чекбоксы false
        for (Map.Entry<Integer, Job> pair: saveJobMap.entrySet() ){
            if (!checkBoxID.contains(pair.getKey())){
                pair.getValue().setMade(false);
            }
        }
        for(Job job: new LinkedList<Job>(jobs)){
            if (!checkBoxID.contains(job.getId())){
                if(job.isMade()){
                    job.setMade(false);
                    if (!saveJobMap.containsKey(job.getId())) {
                        saveJobMap.put(job.getId(), job);
                        jobs.remove(job);
                    }
                }
            }
        }

        // save
        CaseDao caseDao = new CaseDaoImpl();
        for (Map.Entry<Integer, Job> pair: saveJobMap.entrySet() ){
            try {
                caseDao.updateCase(pair.getValue());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     *  Возвращает job из списка jobs  по id
     * @param id
     * @return возвращает job если есть id иначе null
     */
    private Job getJobToID(int id){
        for (Job job: jobs){
            if (job.getId() == id)
                return job;
        }
        return null;
    }

    /**
     *  Добавить данные в БД
     * @param request
     */
    private void addJobToBD(HttpServletRequest request){
        String name = request.getParameter("addName");
        String time = request.getParameter("addTime");
        if (name == null && time == null || name.equals("") && time.equals("")) return;
        Job job = new Job();
        job.setName(name);
        job.setTime(time);
        job.setMade(false);
        CaseDao caseDao = new CaseDaoImpl();
        try {
            caseDao.addCase(job);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
