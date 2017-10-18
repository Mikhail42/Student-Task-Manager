package com.ionkin.web;

import com.fasterxml.jackson.annotation.JsonView;
import com.ionkin.Views;
import com.ionkin.beans.AjaxResponseBody;
import com.ionkin.beans.Student;
import com.ionkin.beans.StudentTask;
import com.ionkin.beans.StudentTaskView;
import com.ionkin.dao.StudentDao;
import com.ionkin.dao.StudentTaskDao;
import com.ionkin.dao.TaskDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by mikhail on 10/16/17.
 */
@RestController
public class StudentTaskRestController {

    private static final Logger logger = LoggerFactory.getLogger(StudentTaskRestController.class);

    @Autowired
    TaskDao taskDao;
    @Autowired
    StudentDao studentDao;
    @Autowired
    StudentTaskDao studentTaskDao;

    @RequestMapping(value = "/studentTaskViews")
    public AjaxResponseBody getStudentTaskViews() {
        logger.info("studentTaskViews");
        final AjaxResponseBody result = new AjaxResponseBody();
        try {
            result.setCode("200");
            result.setErrorMessage("");

            List<Student> studentList = studentDao.getAll();
            List<StudentTaskView> studentTaskViewList =
                    studentTaskDao.createStudentTaskViewList(studentList);
            Collections.sort(studentTaskViewList);

            result.setStudents(studentList);
            result.setStudentTaskViews(studentTaskViewList);
            result.setTasks(taskDao.getAll());
        } catch (Exception e) {
            logger.warn("error while get data (students, tasks, etc.", e);
            result.setCode("500");
            result.setErrorMessage("Error: " + e.getMessage());
        }
        return result;
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/student/create")
    public AjaxResponseBody createStudent(@RequestBody Student student) {
        logger.info("createStudent with studentToAdd: {}", student);
        final AjaxResponseBody result = new AjaxResponseBody();
        try {
            student.setFirstName(student.getFirstName());
            student.setLastName(student.getLastName());
            logger.info("studentToAdd: {}", student);

            boolean isEmpty = student.getFirstName().trim().isEmpty() || student.getLastName().trim().isEmpty();
            if (!isEmpty) {
                studentDao.create(student);
                result.setCode("200");
                result.setErrorMessage("");
            } else {
                result.setCode("400");
                result.setErrorMessage("Please, input both first and last names");
            }
        } catch (Exception e) {
            logger.warn("error while create student", e);
            result.setCode("500");
            result.setErrorMessage("Error: " + e.getMessage());
        }
        return result;
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/studentTask/update")
    public AjaxResponseBody updateStudentTask(@RequestBody StudentTask studentTask) {
        logger.info("updateStudentTask with studentTask: {}", studentTask);
        final AjaxResponseBody result = new AjaxResponseBody();
        try {
            Integer taskId = studentTask.getTaskId();
            Optional<StudentTask> studentTaskOpt = studentTaskDao.getAll().stream()
                    .filter(st -> st.getTaskId().equals(taskId))
                    .findFirst();
            if (!studentTaskOpt.isPresent()) {
                studentTaskDao.addOrUpdate(studentTask);

                List<Student> studentList = studentDao.getAll();
                List<StudentTaskView> studentTaskViewList =
                        studentTaskDao.createStudentTaskViewList(studentList);
                Collections.sort(studentTaskViewList);

                result.setCode("200");
                result.setErrorMessage("");
                result.setStudentTaskViews(studentTaskViewList);
            } else {
                int studentId = studentTaskOpt.get().getStudentId();
                String name = studentDao.get(studentId).getFullName();
                result.setErrorMessage("Task №" + taskId + " has already been chosen by " + name);
                result.setCode("400");
            }
        } catch (Exception e) {
            logger.warn("error while update student task", e);
            result.setCode("500");
            result.setErrorMessage("Error: " + e.getMessage());
        }
        return result;
    }
}