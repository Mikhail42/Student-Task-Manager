package com.ionkin.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.sql.SQLException;

@Controller
@SessionAttributes
public class StudentTaskController {

    private static final Logger logger = LoggerFactory.getLogger(StudentTaskController.class);

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String doGet(ModelMap model) throws SQLException {
        logger.info("doGet");
        return "index";
    }
}
